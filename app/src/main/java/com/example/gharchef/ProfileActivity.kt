package com.example.gharchef

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var dietPref = "Veg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db   = FirebaseFirestore.getInstance()

        val tvName          = findViewById<TextView>(R.id.tvProfileName)
        val tvEmail         = findViewById<TextView>(R.id.tvProfileEmail)
        val tvCityDisplay   = findViewById<TextView>(R.id.tvCityDisplay)
        val tvDietDisplay   = findViewById<TextView>(R.id.tvDietDisplay)
        val tvOrderCount    = findViewById<TextView>(R.id.tvOrderCount)
        val tvCompletionPct = findViewById<TextView>(R.id.tvCompletionPct)
        val tvCompletionHint= findViewById<TextView>(R.id.tvCompletionHint)
        val progressCompl   = findViewById<ProgressBar>(R.id.progressCompletion)
        val etName          = findViewById<EditText>(R.id.etEditName)
        val etMobile        = findViewById<EditText>(R.id.etEditMobile)
        val etAddress       = findViewById<EditText>(R.id.etEditAddress)
        val etCity          = findViewById<EditText>(R.id.etEditCity)
        val etPincode       = findViewById<EditText>(R.id.etEditPincode)
        val btnVeg          = findViewById<Button>(R.id.btnVeg)
        val btnNonVeg       = findViewById<Button>(R.id.btnNonVeg)
        val btnSave         = findViewById<Button>(R.id.btnSave)
        val btnLogout       = findViewById<Button>(R.id.btnLogout)
        val btnLocation     = findViewById<Button>(R.id.btnUpdateLocation)
        val progressBar     = findViewById<ProgressBar>(R.id.progressBar)

        findViewById<android.widget.ImageView>(R.id.ivBack).setOnClickListener { finish() }
        setupBottomNavigation()

        // ── Diet preference buttons ──────────────────────────────────────
        fun selectDiet(pref: String) {
            dietPref = pref
            tvDietDisplay.text = if (pref == "Veg") "Veg 🌿" else "Non-Veg 🍖"
            if (pref == "Veg") {
                btnVeg.setBackgroundResource(R.drawable.bg_diet_veg_selected)
                btnVeg.setTextColor(android.graphics.Color.parseColor("#2E7D32"))
                btnNonVeg.setBackgroundResource(R.drawable.bg_diet_nonveg_unselected)
                btnNonVeg.setTextColor(android.graphics.Color.parseColor("#888888"))
            } else {
                btnNonVeg.setBackgroundResource(R.drawable.bg_diet_veg_selected)
                btnNonVeg.setTextColor(android.graphics.Color.parseColor("#C62828"))
                btnVeg.setBackgroundResource(R.drawable.bg_diet_nonveg_unselected)
                btnVeg.setTextColor(android.graphics.Color.parseColor("#888888"))
            }
        }
        btnVeg.setOnClickListener    { selectDiet("Veg") }
        btnNonVeg.setOnClickListener { selectDiet("Non-Veg") }

        // ── Location button ──────────────────────────────────────────────
        btnLocation.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }

        // ── Load profile ─────────────────────────────────────────────────
        val uid = auth.currentUser?.uid ?: return
        progressBar.visibility = View.VISIBLE

        // Load order count
        db.collection("orders").whereEqualTo("userId", uid).get()
            .addOnSuccessListener { orders -> tvOrderCount.text = orders.size().toString() }

        // Load user profile
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                progressBar.visibility = View.GONE
                if (doc.exists()) {
                    val name    = doc.getString("name")    ?: ""
                    val email   = doc.getString("email")   ?: auth.currentUser?.email ?: ""
                    val mobile  = doc.getString("mobile")  ?: ""
                    val address = doc.getString("address") ?: ""
                    val city    = doc.getString("city")    ?: ""
                    val pincode = doc.getString("pincode") ?: ""
                    val diet    = doc.getString("dietPref")?: "Veg"

                    tvName.text  = name.ifEmpty { "Your Name" }
                    tvEmail.text = email
                    tvCityDisplay.text = city.ifEmpty { "—" }

                    etName.setText(name)
                    etMobile.setText(mobile)
                    etAddress.setText(address)
                    etCity.setText(city)
                    etPincode.setText(pincode)
                    selectDiet(diet)

                    // Completion
                    val fields = listOf(name, mobile, address, city, pincode)
                    val pct = (fields.count { it.isNotEmpty() } * 100) / fields.size
                    progressCompl.progress = pct
                    tvCompletionPct.text   = "$pct%"
                    tvCompletionHint.text  = when {
                        pct < 40 -> "Add more details to get better recommendations!"
                        pct < 80 -> "Almost there! Fill remaining fields."
                        else     -> "Great! Your profile is complete. 🎉"
                    }
                    tvCompletionPct.setTextColor(android.graphics.Color.parseColor(
                        if (pct >= 80) "#4CAF50" else if (pct >= 40) "#FF9800" else "#F44336"
                    ))
                    progressCompl.progressTintList = android.content.res.ColorStateList.valueOf(
                        android.graphics.Color.parseColor(
                            if (pct >= 80) "#4CAF50" else if (pct >= 40) "#FF9800" else "#F44336"
                        )
                    )
                }
            }
            .addOnFailureListener { progressBar.visibility = View.GONE }

        // ── Save ────────────────────────────────────────────────────────
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) { etName.error = "Name is required"; return@setOnClickListener }

            btnSave.isEnabled = false; btnSave.text = "Saving..."
            val city = etCity.text.toString().trim()

            db.collection("users").document(uid).update(mapOf(
                "name"     to name,
                "mobile"   to etMobile.text.toString().trim(),
                "address"  to etAddress.text.toString().trim(),
                "city"     to city,
                "pincode"  to etPincode.text.toString().trim(),
                "dietPref" to dietPref
            )).addOnSuccessListener {
                btnSave.isEnabled = true; btnSave.text = "Save Changes"
                tvName.text = name; tvCityDisplay.text = city.ifEmpty { "—" }
                Toast.makeText(this, "Profile updated! ✓", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                btnSave.isEnabled = true; btnSave.text = "Save Changes"
                Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // ── Logout ──────────────────────────────────────────────────────
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }

    // ── STANDARD 5-TAB NAV ──────────────────────────────────────────────
    private fun setupBottomNavigation() {
        findViewById<android.widget.LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, ActivityHome::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
        findViewById<android.widget.LinearLayout>(R.id.navSearch).setOnClickListener  { startActivity(Intent(this, SearchActivity::class.java)) }
        findViewById<android.widget.LinearLayout>(R.id.navOrders).setOnClickListener  { startActivity(Intent(this, OrdersActivity::class.java)) }
        findViewById<android.widget.LinearLayout>(R.id.navCart).setOnClickListener    { startActivity(Intent(this, CartActivity::class.java)) }
        findViewById<android.widget.LinearLayout>(R.id.navProfile).setOnClickListener { /* already here */ }
    }
}