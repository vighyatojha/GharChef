package com.example.gharchef

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ActivityHome : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var rvPopular: RecyclerView
    private lateinit var rvAll: RecyclerView
    private lateinit var tvGreeting: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        db          = FirebaseFirestore.getInstance()
        auth        = FirebaseAuth.getInstance()
        rvPopular   = findViewById(R.id.rvPopularItems)
        rvAll       = findViewById(R.id.rvAllItems)
        tvGreeting  = findViewById(R.id.tvGreeting)
        progressBar = findViewById(R.id.progressBar)

        rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAll.layoutManager     = GridLayoutManager(this, 2)

        setupBottomNavigation()
        loadUserGreeting()
        loadMenuItems()

        findViewById<RelativeLayout>(R.id.searchBar).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        findViewById<ImageView>(R.id.ivCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        mapOf(
            R.id.chipAll         to "all",
            R.id.chipNorthIndian to "north_indian",
            R.id.chipStreetFood  to "street_food",
            R.id.chipEastIndian  to "east_indian"
        ).forEach { (id, cat) ->
            try { findViewById<TextView>(id).setOnClickListener { loadMenuItems(cat) } } catch (_: Exception) {}
        }
    }

    override fun onResume() { super.onResume(); loadMenuItems() }

    private fun loadUserGreeting() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val name      = doc.getString("name") ?: doc.getString("username") ?: "there"
                val firstName = name.split(" ").firstOrNull() ?: name
                tvGreeting.text = "Hello, $firstName! 👋"
            }
    }

    private fun loadMenuItems(category: String = "all") {
        progressBar.visibility = View.VISIBLE

        val query = if (category == "all")
            db.collection("menu").whereEqualTo("available", true)
        else
            db.collection("menu")
                .whereEqualTo("available", true)
                .whereEqualTo("category", category)

        query.get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                val items = result.documents.mapNotNull { doc ->
                    val name = doc.getString("name")?.takeIf { it.isNotEmpty() } ?: return@mapNotNull null
                    MenuItemData(
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
                    )
                }

                rvPopular.adapter = PopularItemsAdapter(items.filter { it.popular }) { addToCart(it) }
                rvAll.adapter     = AllItemsAdapter(items) { addToCart(it) }

                try {
                    findViewById<TextView>(R.id.tvNoItems).visibility =
                        if (items.isEmpty()) View.VISIBLE else View.GONE
                } catch (_: Exception) {}
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed to load menu", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addToCart(item: MenuItemData) {
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        val cartRef = db.collection("carts").document(uid).collection("items")
        cartRef.whereEqualTo("name", item.name).get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    val doc = result.documents[0]
                    doc.reference.update("quantity", (doc.getLong("quantity") ?: 1) + 1)
                    Toast.makeText(this, "${item.name} quantity updated 🛒", Toast.LENGTH_SHORT).show()
                } else {
                    cartRef.add(hashMapOf(
                        "name" to item.name, "price" to item.price,
                        "imageUrl" to item.imageUrl, "quantity" to 1
                    )).addOnSuccessListener {
                        Toast.makeText(this, "${item.name} added to cart! 🛒", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupBottomNavigation() {
        try {
            findViewById<LinearLayout>(R.id.navHome).setOnClickListener    { /* already here */ }
            findViewById<LinearLayout>(R.id.navSearch).setOnClickListener  { startActivity(Intent(this, SearchActivity::class.java)) }
            findViewById<LinearLayout>(R.id.navOrders).setOnClickListener  { startActivity(Intent(this, OrdersActivity::class.java)) }
            findViewById<LinearLayout>(R.id.navCart).setOnClickListener    { startActivity(Intent(this, CartActivity::class.java)) }
        } catch (_: Exception) {}
    }
}

// ─── Popular Items Adapter (horizontal) ──────────────────────────────────────
class PopularItemsAdapter(
    private val items: List<MenuItemData>,
    private val onAddToCart: (MenuItemData) -> Unit
) : RecyclerView.Adapter<PopularItemsAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage:  ImageView = view.findViewById(R.id.ivPopularImage)
        val tvName:   TextView  = view.findViewById(R.id.tvPopularName)
        val tvPrice:  TextView  = view.findViewById(R.id.tvPopularPrice)
        val tvRating: TextView  = view.findViewById(R.id.tvPopularRating)
        val btnAdd:   Button    = view.findViewById(R.id.btnAddPopular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_popular_dish, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvName.text   = item.name
        holder.tvPrice.text  = "₹%.0f".format(item.price)
        holder.tvRating.text = "⭐ ${"%.1f".format(item.rating)}"
        if (item.imageUrl.isNotEmpty())
            Glide.with(holder.itemView.context).load(item.imageUrl)
                .placeholder(R.drawable.bg_image_placeholder).centerCrop().into(holder.ivImage)
        holder.btnAdd.setOnClickListener { onAddToCart(item) }
    }

    override fun getItemCount() = items.size
}

// ─── All Items Adapter (grid 2-col) ──────────────────────────────────────────
class AllItemsAdapter(
    private val items: List<MenuItemData>,
    private val onAddToCart: (MenuItemData) -> Unit
) : RecyclerView.Adapter<AllItemsAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivGridImage)
        val tvName:  TextView  = view.findViewById(R.id.tvGridName)
        val tvPrice: TextView  = view.findViewById(R.id.tvGridPrice)
        val tvTime:  TextView  = view.findViewById(R.id.tvGridTime)
        val btnAdd:  Button    = view.findViewById(R.id.btnAddGrid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_grid_dish, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvName.text  = item.name
        holder.tvPrice.text = "₹%.0f".format(item.price)
        holder.tvTime.text  = "⏱ ${item.prepTime}"
        if (item.imageUrl.isNotEmpty())
            Glide.with(holder.itemView.context).load(item.imageUrl)
                .placeholder(R.drawable.bg_image_placeholder).centerCrop().into(holder.ivImage)
        holder.btnAdd.setOnClickListener { onAddToCart(item) }
    }

    override fun getItemCount() = items.size
}