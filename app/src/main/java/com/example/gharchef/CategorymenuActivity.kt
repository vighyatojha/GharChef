package com.example.gharchef

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class CategoryMenuActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var rvMenu: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvCategoryTitle: TextView
    private lateinit var progressBar: ProgressBar

    private val menuItems = mutableListOf<MenuItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorymenu)

        db   = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        rvMenu          = findViewById(R.id.rvMenu)
        tvEmpty         = findViewById(R.id.tvEmpty)
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle)
        progressBar     = findViewById(R.id.progressBar)

        rvMenu.layoutManager = LinearLayoutManager(this)
        findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() }

        setupBottomNavigation()

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Menu"
        val categoryKey  = intent.getStringExtra("CATEGORY_KEY")  ?: "all"
        tvCategoryTitle.text = categoryName
        loadMenuItems(categoryKey)
    }

    private fun setupBottomNavigation() {
        try { findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, ActivityHome::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }} catch (_: Exception) {}
        try { findViewById<LinearLayout>(R.id.navSearch).setOnClickListener  { startActivity(Intent(this, SearchActivity::class.java)) } } catch (_: Exception) {}
        try { findViewById<LinearLayout>(R.id.navOrders).setOnClickListener  { startActivity(Intent(this, OrdersActivity::class.java)) } } catch (_: Exception) {}
        try { findViewById<LinearLayout>(R.id.navCart).setOnClickListener    { startActivity(Intent(this, CartActivity::class.java)) } } catch (_: Exception) {}
        try { findViewById<LinearLayout>(R.id.navProfile).setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) } } catch (_: Exception) {}
    }

    private fun loadMenuItems(categoryKey: String) {
        progressBar.visibility = View.VISIBLE
        rvMenu.visibility      = View.GONE
        tvEmpty.visibility     = View.GONE

        val query = if (categoryKey == "all")
            db.collection("menu").whereEqualTo("available", true)
        else
            db.collection("menu").whereEqualTo("category", categoryKey).whereEqualTo("available", true)

        query.get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                menuItems.clear()
                for (doc in result.documents) {
                    val item = MenuItemData(
                        id                  = doc.id,
                        name                = doc.getString("name")                ?: "",
                        description         = doc.getString("description")         ?: "",
                        price               = doc.getDouble("price")               ?: 0.0,
                        imageUrl            = doc.getString("imageUrl")            ?: "",
                        category            = doc.getString("category")            ?: "",
                        prepTime            = doc.getString("prepTime")            ?: "20 mins",
                        rating              = doc.getDouble("rating")              ?: 4.0,
                        available           = doc.getBoolean("available")          ?: true,
                        serves              = (doc.getLong("serves")?.toInt())     ?: 2,
                        difficulty          = doc.getString("difficulty")          ?: "Easy",
                        ingredients         = doc.getString("ingredients")         ?: "",
                        recipeSteps         = doc.getString("recipeSteps")         ?: "",
                        cookware            = doc.getString("cookware")            ?: "",
                        cookwareSubstitutes = doc.getString("cookwareSubstitutes") ?: ""
                    )
                    if (item.name.isNotEmpty()) menuItems.add(item)
                }
                if (menuItems.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rvMenu.visibility  = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvMenu.visibility  = View.VISIBLE
                    rvMenu.adapter = MenuItemAdapter(
                        menuItems,
                        onAddToCart = { addToCart(it) },
                        onItemClick = { openKitDetail(it) }
                    )
                }
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Log.e("MENU", "Failed: ${e.message}")
                Toast.makeText(this, "Failed to load menu", Toast.LENGTH_SHORT).show()
                tvEmpty.visibility = View.VISIBLE
            }
    }

    private fun openKitDetail(item: MenuItemData) {
        startActivity(Intent(this, MealKitDetailActivity::class.java).apply {
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
        })
    }

    private fun addToCart(item: MenuItemData) {
        val uid = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show(); return
        }
        val itemRef = db.collection("carts").document(uid).collection("items").document(item.id)
        itemRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                itemRef.update("quantity", (doc.getLong("quantity") ?: 1) + 1)
                Toast.makeText(this, "${item.name} quantity updated ✓", Toast.LENGTH_SHORT).show()
            } else {
                itemRef.set(hashMapOf(
                    "itemId" to item.id, "name" to item.name,
                    "price" to item.price, "imageUrl" to item.imageUrl, "quantity" to 1
                ), SetOptions.merge()).addOnSuccessListener {
                    Toast.makeText(this, "${item.name} added to cart ✓", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

// ── Menu Item Adapter ──────────────────────────────────────────────────
class MenuItemAdapter(
    private val items: List<MenuItemData>,
    private val onAddToCart: (MenuItemData) -> Unit,
    private val onItemClick: (MenuItemData) -> Unit
) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName:     TextView = view.findViewById(R.id.tvMenuItemName)
        val tvDesc:     TextView = view.findViewById(R.id.tvMenuItemDesc)
        val tvPrice:    TextView = view.findViewById(R.id.tvMenuItemPrice)
        val tvPrepTime: TextView = view.findViewById(R.id.tvMenuItemPrepTime)
        val tvRating:   TextView = view.findViewById(R.id.tvMenuItemRating)
        val btnAdd:     Button   = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text     = item.name
        holder.tvDesc.text     = item.description
        holder.tvPrice.text    = "₹%.2f".format(item.price)
        holder.tvPrepTime.text = "⏱ ${item.prepTime}"
        holder.tvRating.text   = "★ ${"%.1f".format(item.rating)}"
        holder.btnAdd.setOnClickListener   { onAddToCart(item) }
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}