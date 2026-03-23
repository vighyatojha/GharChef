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
import com.google.firebase.firestore.SetOptions

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
        try {
            findViewById<ImageView>(R.id.ivCart).setOnClickListener {
                startActivity(Intent(this, CartActivity::class.java))
            }
        } catch (_: Exception) {}

        mapOf(
            R.id.chipAll         to "all",
            R.id.chipNorthIndian to "north_indian",
            R.id.chipStreetFood  to "street_food",
            R.id.chipEastIndian  to "east_indian"
        ).forEach { (id, cat) ->
            try {
                findViewById<TextView>(id).setOnClickListener { loadMenuItems(cat) }
            } catch (_: Exception) {}
        }
    }

    override fun onResume() {
        super.onResume()
        loadMenuItems()
    }

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
                    val name = doc.getString("name")?.takeIf { it.isNotEmpty() }
                        ?: return@mapNotNull null
                    MenuItemData(
                        id                  = doc.id,
                        name                = name,
                        description         = doc.getString("description")         ?: "",
                        price               = doc.getDouble("price")               ?: 0.0,
                        imageUrl            = doc.getString("imageUrl")            ?: "",
                        category            = doc.getString("category")            ?: "",
                        prepTime            = doc.getString("prepTime")            ?: "20 mins",
                        rating              = doc.getDouble("rating")              ?: 4.0,
                        available           = doc.getBoolean("available")          ?: true,
                        popular             = doc.getBoolean("popular")            ?: false,
                        isVeg               = doc.getBoolean("isVeg")              ?: true,
                        serves              = doc.getLong("serves")?.toInt()       ?: 2,
                        difficulty          = doc.getString("difficulty")          ?: "Easy",
                        ingredients         = doc.getString("ingredients")         ?: "",
                        recipeSteps         = doc.getString("recipeSteps")         ?: "",
                        cookware            = doc.getString("cookware")            ?: "",
                        cookwareSubstitutes = doc.getString("cookwareSubstitutes") ?: ""
                    )
                }

                rvPopular.adapter = PopularItemsAdapter(
                    items.filter { it.popular },
                    onAddToCart = { addToCart(it) },
                    onItemClick = { openKitDetail(it) }
                )
                rvAll.adapter = AllItemsAdapter(
                    items,
                    onAddToCart = { addToCart(it) },
                    onItemClick = { openKitDetail(it) }
                )
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

    // ── Launch detail screen ──────────────────────────────────────────
    private fun openKitDetail(item: MenuItemData) {
        val intent = Intent(this, MealKitDetailActivity::class.java).apply {
            putExtra("ITEM_ID",            item.id)
            putExtra("ITEM_NAME",          item.name)
            putExtra("ITEM_PRICE",         item.price)
            putExtra("ITEM_IMAGE",         item.imageUrl)
            putExtra("ITEM_DESC",          item.description)
            putExtra("ITEM_RATING",        item.rating)
            putExtra("ITEM_PREP_TIME",     item.prepTime)
            putExtra("ITEM_CATEGORY",      item.category)
            putExtra("ITEM_INGREDIENTS",   item.ingredients)
            putExtra("ITEM_STEPS",         item.recipeSteps)
            putExtra("ITEM_COOKWARE",      item.cookware)
            putExtra("ITEM_COOKWARE_SUBS", item.cookwareSubstitutes)
            putExtra("ITEM_SERVES",        item.serves)
            putExtra("ITEM_DIFFICULTY",    item.difficulty)
        }
        startActivity(intent)
    }

    private fun addToCart(item: MenuItemData) {
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        val itemRef = db.collection("carts").document(uid).collection("items").document(item.id)
        itemRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                val qty = doc.getLong("quantity") ?: 1
                itemRef.update("quantity", qty + 1)
                Toast.makeText(this, "${item.name} quantity updated 🛒", Toast.LENGTH_SHORT).show()
            } else {
                itemRef.set(
                    hashMapOf(
                        "itemId"   to item.id,
                        "name"     to item.name,
                        "price"    to item.price,
                        "imageUrl" to item.imageUrl,
                        "quantity" to 1
                    ), SetOptions.merge()
                ).addOnSuccessListener {
                    Toast.makeText(this, "${item.name} added to cart! 🛒", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupBottomNavigation() {
        val navHome    = findViewById<LinearLayout?>(R.id.navHome)
        val navSearch  = findViewById<LinearLayout?>(R.id.navSearch)
        val navOrders  = findViewById<LinearLayout?>(R.id.navOrders)
        val navCart    = findViewById<LinearLayout?>(R.id.navCart)
        val navProfile = findViewById<LinearLayout?>(R.id.navProfile)

        navHome?.setOnClickListener    { /* already on home */ }
        navSearch?.setOnClickListener  { startActivity(Intent(this, SearchActivity::class.java)) }
        navOrders?.setOnClickListener  { startActivity(Intent(this, OrdersActivity::class.java)) }
        navCart?.setOnClickListener    { startActivity(Intent(this, CartActivity::class.java)) }
        navProfile?.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }

    // ── Popular Items Adapter (horizontal) ───────────────────────────
    class PopularItemsAdapter(
        private val items: List<MenuItemData>,
        private val onAddToCart: (MenuItemData) -> Unit,
        private val onItemClick: (MenuItemData) -> Unit
    ) : RecyclerView.Adapter<PopularItemsAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val ivImage:  ImageView = view.findViewById(R.id.ivPopularImage)
            val tvName:   TextView  = view.findViewById(R.id.tvPopularName)
            val tvPrice:  TextView  = view.findViewById(R.id.tvPopularPrice)
            val tvRating: TextView  = view.findViewById(R.id.tvPopularRating)
            val btnAdd:   Button    = view.findViewById(R.id.btnAddPopular)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VH(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_popular_dish, parent, false))

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]
            holder.tvName.text   = item.name
            holder.tvPrice.text  = "₹%.0f".format(item.price)
            holder.tvRating.text = "⭐ ${"%.1f".format(item.rating)}"
            if (item.imageUrl.isNotEmpty())
                Glide.with(holder.itemView.context).load(item.imageUrl)
                    .placeholder(R.drawable.bg_image_placeholder)
                    .centerCrop().into(holder.ivImage)
            holder.btnAdd.setOnClickListener    { onAddToCart(item) }
            holder.itemView.setOnClickListener  { onItemClick(item) }
        }

        override fun getItemCount() = items.size
    }

    // ── All Items Adapter (grid) ──────────────────────────────────────
    class AllItemsAdapter(
        private val items: List<MenuItemData>,
        private val onAddToCart: (MenuItemData) -> Unit,
        private val onItemClick: (MenuItemData) -> Unit
    ) : RecyclerView.Adapter<AllItemsAdapter.VH>() {

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {
            val ivImage: ImageView = view.findViewById(R.id.ivGridImage)
            val tvName:  TextView  = view.findViewById(R.id.tvGridName)
            val tvPrice: TextView  = view.findViewById(R.id.tvGridPrice)
            val tvTime:  TextView  = view.findViewById(R.id.tvGridTime)
            val btnAdd:  Button    = view.findViewById(R.id.btnAddGrid)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VH(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grid_dish, parent, false))

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]
            holder.tvName.text  = item.name
            holder.tvPrice.text = "₹%.0f".format(item.price)
            holder.tvTime.text  = "⏱ ${item.prepTime}"
            if (item.imageUrl.isNotEmpty())
                Glide.with(holder.itemView.context).load(item.imageUrl)
                    .placeholder(R.drawable.bg_image_placeholder)
                    .centerCrop().into(holder.ivImage)
            holder.btnAdd.setOnClickListener    { onAddToCart(item) }
            holder.itemView.setOnClickListener  { onItemClick(item) }
        }

        override fun getItemCount() = items.size
    }
}