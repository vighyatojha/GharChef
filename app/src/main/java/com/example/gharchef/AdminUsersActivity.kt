package com.example.gharchef

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

data class AppUser(
    val uid: String    = "",
    val name: String   = "",
    val email: String  = "",
    val mobile: String = "",
    val city: String   = "",
    val status: String = "active",
    val role: String   = "user"
)

class AdminUsersActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var rvUsers: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var etSearch: EditText

    private val allUsers      = mutableListOf<AppUser>()
    private val filteredUsers = mutableListOf<AppUser>()
    private var currentFilter = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_users)

        db          = FirebaseFirestore.getInstance()
        rvUsers     = findViewById(R.id.rvUsers)
        tvEmpty     = findViewById(R.id.tvEmpty)
        progressBar = findViewById(R.id.progressBar)
        etSearch    = findViewById(R.id.etSearchUsers)

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = AdminUsersAdapter(filteredUsers, {}, {}, {})

        try { findViewById<android.widget.ImageView>(R.id.ivBack).setOnClickListener { finish() } }
        catch (_: Exception) {}

        setupBottomNavigation()
        loadUsers()

        listOf(R.id.tabAllUsers to "all", R.id.tabActive to "active", R.id.tabSuspended to "suspended")
            .forEach { (id, key) ->
                try { findViewById<TextView>(id).setOnClickListener { setFilter(key) } } catch (_: Exception) {}
            }

        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) { filterUsers(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        try { findViewById<android.widget.ImageView>(R.id.ivMore).setOnClickListener { showMoreOptions() } }
        catch (_: Exception) {}

        try {
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddUser)
                .setOnClickListener { showUserDialog(null) }
        } catch (_: Exception) {}
    }

    override fun onResume() { super.onResume(); loadUsers() }

    private fun setupBottomNavigation() {
        try {
            findViewById<LinearLayout>(R.id.navAdminDashboard).setOnClickListener {
                startActivity(Intent(this, AdminDashboardActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            findViewById<LinearLayout>(R.id.navAdminOrders).setOnClickListener {
                startActivity(Intent(this, AdminOrdersActivity::class.java))
            }
            findViewById<LinearLayout>(R.id.navAdminItems).setOnClickListener {
                startActivity(Intent(this, AdminItemsActivity::class.java))
            }
            findViewById<LinearLayout>(R.id.navAdminUsers).setOnClickListener { /* already here */ }
            findViewById<LinearLayout>(R.id.navAdminProfile).setOnClickListener {
                startActivity(Intent(this, AdminProfileActivity::class.java))
            }
        } catch (_: Exception) {}
    }

    private fun loadUsers() {
        progressBar.visibility = View.VISIBLE
        db.collection("users").whereEqualTo("role", "user").get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                allUsers.clear()
                for (doc in result.documents) {
                    allUsers.add(AppUser(
                        uid    = doc.id,
                        name   = doc.getString("name")   ?: doc.getString("username") ?: "Unknown",
                        email  = doc.getString("email")  ?: "",
                        mobile = doc.getString("mobile") ?: "",
                        city   = listOfNotNull(doc.getString("city"), doc.getString("state"))
                            .joinToString(", ").ifEmpty { "India" },
                        status = doc.getString("status") ?: "active",
                        role   = doc.getString("role")   ?: "user"
                    ))
                }
                filterUsers(etSearch.text.toString())
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed to load users", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setFilter(filter: String) {
        currentFilter = filter
        mapOf("all" to R.id.tabAllUsers, "active" to R.id.tabActive, "suspended" to R.id.tabSuspended)
            .forEach { (key, id) ->
                try {
                    val tv = findViewById<TextView>(id)
                    tv.setTextColor(if (key == filter) Color.parseColor("#E8871A") else Color.parseColor("#AAAAAA"))
                    tv.setTypeface(null, if (key == filter) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
                } catch (_: Exception) {}
            }
        filterUsers(etSearch.text.toString())
    }

    private fun filterUsers(query: String) {
        filteredUsers.clear()
        val base = if (currentFilter == "all") allUsers
        else allUsers.filter { it.status == currentFilter }.toMutableList()
        filteredUsers.addAll(
            if (query.isEmpty()) base
            else base.filter {
                it.name.contains(query, true) || it.email.contains(query, true) || it.mobile.contains(query, true)
            }
        )
        updateRecycler()
    }

    private fun updateRecycler() {
        if (filteredUsers.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rvUsers.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            rvUsers.visibility = View.VISIBLE
            rvUsers.adapter = AdminUsersAdapter(
                filteredUsers,
                onViewOrders = { viewUserOrders(it) },
                onEdit       = { showUserDialog(it) },
                onDelete     = { confirmDeleteUser(it) }
            )
        }
    }

    // ─── Create / Edit dialog ─────────────────────────────────────────────
    private fun showUserDialog(existing: AppUser?) {
        val isEdit = existing != null
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(56, 40, 56, 16)
        }

        fun field(hint: String, value: String = "", type: Int = android.text.InputType.TYPE_CLASS_TEXT): EditText =
            EditText(this).apply {
                this.hint      = hint
                this.inputType = type
                setText(value)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 20 }
            }

        val etName   = field("Full Name",         existing?.name   ?: "",
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        val etEmail  = field("Email Address",     existing?.email  ?: "",
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS).apply {
            isEnabled = !isEdit   // email cannot be changed after creation
        }
        val etMobile = field("Mobile (10 digits)", existing?.mobile ?: "", android.text.InputType.TYPE_CLASS_PHONE)
        val etCity   = field("City",              existing?.city?.split(",")?.firstOrNull()?.trim() ?: "")

        layout.addView(etName); layout.addView(etEmail); layout.addView(etMobile); layout.addView(etCity)

        var etPwd: EditText? = null
        if (!isEdit) {
            etPwd = field("Temporary Password (min 6 chars)", "",
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
            layout.addView(etPwd)
        }

        AlertDialog.Builder(this)
            .setTitle(if (isEdit) "Edit — ${existing?.name}" else "Add New Customer")
            .setView(layout)
            .setPositiveButton(if (isEdit) "Save Changes" else "Create Customer") { _, _ ->
                val name   = etName.text.toString().trim()
                val email  = etEmail.text.toString().trim()
                val mobile = etMobile.text.toString().trim()
                val city   = etCity.text.toString().trim()
                val pwd    = etPwd?.text?.toString()?.trim() ?: ""

                if (name.isEmpty()) { Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show(); return@setPositiveButton }
                if (!isEdit) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { Toast.makeText(this, "Valid email required", Toast.LENGTH_SHORT).show(); return@setPositiveButton }
                    if (mobile.length != 10) { Toast.makeText(this, "10-digit mobile required", Toast.LENGTH_SHORT).show(); return@setPositiveButton }
                    if (pwd.length < 6)      { Toast.makeText(this, "Password min 6 chars", Toast.LENGTH_SHORT).show(); return@setPositiveButton }
                    createUser(name, email, mobile, city, pwd)
                } else if (existing != null) {
                    updateUser(existing.uid, name, mobile, city)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createUser(name: String, email: String, mobile: String, city: String, password: String) {
        progressBar.visibility = View.VISIBLE
        val uid = "gc_${System.currentTimeMillis()}"
        db.collection("users").document(uid).set(hashMapOf(
            "uid" to uid, "username" to name.lowercase().replace(" ", "_"),
            "name" to name, "mobile" to mobile, "email" to email, "city" to city,
            "role" to "user", "status" to "active",
            "createdAt" to System.currentTimeMillis(),
            "adminCreated" to true, "tempPassword" to password
        ))
            .addOnSuccessListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "$name added ✓", Toast.LENGTH_SHORT).show()
                loadUsers()
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUser(uid: String, name: String, mobile: String, city: String) {
        progressBar.visibility = View.VISIBLE
        db.collection("users").document(uid).update(mapOf("name" to name, "mobile" to mobile, "city" to city))
            .addOnSuccessListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Updated ✓", Toast.LENGTH_SHORT).show()
                loadUsers()
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // ─── Delete ───────────────────────────────────────────────────────────
    private fun confirmDeleteUser(user: AppUser) {
        AlertDialog.Builder(this)
            .setTitle("Delete Customer")
            .setMessage("Permanently delete \"${user.name}\"?\nAll their data will be removed.")
            .setPositiveButton("Delete") { _, _ ->
                progressBar.visibility = View.VISIBLE
                db.collection("users").document(user.uid).delete()
                    .addOnSuccessListener {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "${user.name} deleted ✓", Toast.LENGTH_SHORT).show()
                        loadUsers()
                    }
                    .addOnFailureListener { e ->
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun viewUserOrders(user: AppUser) {
        startActivity(Intent(this, AdminUserOrdersActivity::class.java)
            .putExtra("userId", user.uid).putExtra("userName", user.name))
    }

    private fun showMoreOptions() {
        AlertDialog.Builder(this).setItems(arrayOf("Refresh")) { _, _ -> loadUsers() }.show()
    }
}

class AdminUsersAdapter(
    private val users: List<AppUser>,
    private val onViewOrders: (AppUser) -> Unit,
    private val onEdit:       (AppUser) -> Unit,
    private val onDelete:     (AppUser) -> Unit
) : RecyclerView.Adapter<AdminUsersAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName:        TextView = view.findViewById(R.id.tvUserName)
        val tvCity:        TextView = view.findViewById(R.id.tvUserCity)
        val tvEmail:       TextView = view.findViewById(R.id.tvUserEmail)
        val tvMobile:      TextView = view.findViewById(R.id.tvUserMobile)
        val tvStatus:      TextView = view.findViewById(R.id.tvUserStatus)
        val btnViewOrders: Button   = view.findViewById(R.id.btnViewOrders)
        val btnEdit:       Button   = view.findViewById(R.id.btnEditUser)
        val btnDelete:     Button   = view.findViewById(R.id.btnDeleteUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_admin_user, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val user = users[position]
        holder.tvName.text   = user.name
        holder.tvCity.text   = user.city.ifEmpty { "India" }
        holder.tvEmail.text  = user.email
        holder.tvMobile.text = if (user.mobile.isNotEmpty()) "+91 ${user.mobile}" else "No mobile"

        val isActive = user.status != "suspended"
        holder.tvStatus.text = if (isActive) "ACTIVE" else "SUSPENDED"
        holder.tvStatus.setBackgroundColor(Color.parseColor(if (isActive) "#4CAF50" else "#F44336"))

        holder.btnViewOrders.setOnClickListener { onViewOrders(user) }
        holder.btnEdit.setOnClickListener       { onEdit(user) }
        holder.btnDelete.setOnClickListener     { onDelete(user) }
    }

    override fun getItemCount() = users.size
}