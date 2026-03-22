package com.example.gharchef

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var rvResults: RecyclerView
    private lateinit var tvEmpty: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var progressBar: ProgressBar

    private val allItems      = mutableListOf<MenuItemData>()
    private val filteredItems = mutableListOf<MenuItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        db          = FirebaseFirestore.getInstance()
        auth        = FirebaseAuth.getInstance()
        rvResults   = findViewById(R.id.rvSearchResults)
        tvEmpty     = findViewById(R.id.tvSearchEmpty)   // LinearLayout in activity_search.xml
        etSearch    = findViewById(R.id.etSearchQuery)   // correct ID from activity_search.xml
        progressBar = findViewById(R.id.progressBar)

        rvResults.layoutManager = LinearLayoutManager(this)

        try { findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() } } catch (_: Exception) {}

        loadAllItems()

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { filterItems(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        etSearch.requestFocus()
    }

    private fun loadAllItems() {
        progressBar.visibility = View.VISIBLE
        db.collection("menu").whereEqualTo("available", true).get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                allItems.clear()
                for (doc in result.documents) {
                    val name = doc.getString("name")?.takeIf { it.isNotEmpty() } ?: continue
                    allItems.add(MenuItemData(
                        id          = doc.id,
                        name        = name,
                        description = doc.getString("description") ?: "",
                        price       = doc.getDouble("price")       ?: 0.0,
                        imageUrl    = doc.getString("imageUrl")    ?: "",
                        category    = doc.getString("category")    ?: "",
                        prepTime    = doc.getString("prepTime")    ?: "20 mins",
                        rating      = doc.getDouble("rating")      ?: 4.0,
                        available   = doc.getBoolean("available")  ?: true,
                        popular     = doc.getBoolean("popular")    ?: false
                    ))
                }
                filterItems("")
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed to load items", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterItems(query: String) {
        filteredItems.clear()
        filteredItems.addAll(
            if (query.isEmpty()) allItems
            else allItems.filter {
                it.name.contains(query, true)        ||
                        it.description.contains(query, true) ||
                        it.category.contains(query, true)
            }
        )
        if (filteredItems.isEmpty()) {
            tvEmpty.visibility   = View.VISIBLE
            rvResults.visibility = View.GONE
        } else {
            tvEmpty.visibility   = View.GONE
            rvResults.visibility = View.VISIBLE
            rvResults.adapter    = SearchResultsAdapter(filteredItems) { addToCart(it) }
        }
    }

    private fun addToCart(item: MenuItemData) {
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        val ref = db.collection("carts").document(uid).collection("items")
        ref.whereEqualTo("name", item.name).get()
            .addOnSuccessListener { res ->
                if (res.documents.isNotEmpty()) {
                    val doc = res.documents[0]
                    doc.reference.update("quantity", (doc.getLong("quantity") ?: 1) + 1)
                    Toast.makeText(this, "${item.name} qty updated 🛒", Toast.LENGTH_SHORT).show()
                } else {
                    ref.add(hashMapOf(
                        "name" to item.name, "price" to item.price,
                        "imageUrl" to item.imageUrl, "quantity" to 1
                    ))
                    Toast.makeText(this, "${item.name} added to cart! 🛒", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

// ─── Search Results Adapter ───────────────────────────────────────────────────
class SearchResultsAdapter(
    private val items: List<MenuItemData>,
    private val onAdd: (MenuItemData) -> Unit
) : RecyclerView.Adapter<SearchResultsAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val ivImage: ImageView = v.findViewById(R.id.ivSearchImage)
        val tvName:  TextView  = v.findViewById(R.id.tvSearchName)
        val tvDesc:  TextView  = v.findViewById(R.id.tvSearchDesc)
        val tvPrice: TextView  = v.findViewById(R.id.tvSearchPrice)
        val tvTime:  TextView  = v.findViewById(R.id.tvSearchTime)
        val btnAdd:  Button    = v.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvName.text  = item.name
        holder.tvDesc.text  = item.description.ifEmpty {
            item.category.replace("_", " ").replaceFirstChar { it.uppercase() }
        }
        holder.tvPrice.text = "₹%.0f".format(item.price)
        holder.tvTime.text  = "⏱ ${item.prepTime}"
        if (item.imageUrl.isNotEmpty())
            Glide.with(holder.itemView.context).load(item.imageUrl)
                .placeholder(R.drawable.bg_image_placeholder).centerCrop().into(holder.ivImage)
        holder.btnAdd.setOnClickListener { onAdd(item) }
    }

    override fun getItemCount() = items.size
}