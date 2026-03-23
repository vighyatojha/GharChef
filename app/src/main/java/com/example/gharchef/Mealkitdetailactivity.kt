package com.example.gharchef

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class MealKitDetailActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_kit_detail)

        db   = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // ── Extract intent extras safely ───────────────────────────────
        val itemId = intent.getStringExtra("ITEM_ID")
        if (itemId.isNullOrEmpty()) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val itemName     = intent.getStringExtra("ITEM_NAME")      ?: ""
        val itemPrice    = intent.getDoubleExtra("ITEM_PRICE",     0.0)
        val itemImage    = intent.getStringExtra("ITEM_IMAGE")     ?: ""
        val itemDesc     = intent.getStringExtra("ITEM_DESC")      ?: ""
        val itemRating   = intent.getDoubleExtra("ITEM_RATING",    4.0)
        val itemPrepTime = intent.getStringExtra("ITEM_PREP_TIME") ?: "20 mins"
        val itemCategory = intent.getStringExtra("ITEM_CATEGORY")  ?: ""
        val ingredients  = intent.getStringExtra("ITEM_INGREDIENTS") ?: ""
        val steps        = intent.getStringExtra("ITEM_STEPS")     ?: ""
        val cookware     = intent.getStringExtra("ITEM_COOKWARE")  ?: ""
        val cookwareSubs = intent.getStringExtra("ITEM_COOKWARE_SUBS") ?: ""
        val serves       = intent.getIntExtra("ITEM_SERVES",       2)
        val difficulty   = intent.getStringExtra("ITEM_DIFFICULTY") ?: "Easy"

        // ── Bind views ────────────────────────────────────────────────
        val ivHero               = findViewById<ImageView>(R.id.ivDetailHero)
        val ivBack               = findViewById<ImageView>(R.id.ivDetailBack)
        val tvName               = findViewById<TextView>(R.id.tvDetailName)
        val tvCategory           = findViewById<TextView>(R.id.tvDetailCategory)
        val tvRating             = findViewById<TextView>(R.id.tvDetailRating)
        val tvPrepTime           = findViewById<TextView>(R.id.tvDetailPrepTime)
        val tvDifficulty         = findViewById<TextView>(R.id.tvDetailDifficulty)
        val tvServes             = findViewById<TextView>(R.id.tvDetailServes)
        val tvDesc               = findViewById<TextView>(R.id.tvDetailDesc)
        val tvPrice              = findViewById<TextView>(R.id.tvDetailPrice)
        val ingredientsContainer = findViewById<LinearLayout>(R.id.detailIngredientsContainer)
        val stepsContainer       = findViewById<LinearLayout>(R.id.detailStepsContainer)
        val tvCookware           = findViewById<TextView>(R.id.tvDetailCookware)
        val tvCookwareSubs       = findViewById<TextView>(R.id.tvDetailCookwareSubs)
        val cookwareSection      = findViewById<LinearLayout>(R.id.cookwareSection)
        val btnAddToCart         = findViewById<Button>(R.id.btnDetailAddToCart)

        ivBack.setOnClickListener { finish() }

        // ── Populate header ───────────────────────────────────────────
        if (itemImage.isNotEmpty()) {
            Glide.with(this).load(itemImage).centerCrop().into(ivHero)
        }
        tvName.text       = itemName
        tvPrice.text      = "₹%.0f".format(itemPrice)
        tvRating.text     = "⭐ ${"%.1f".format(itemRating)}"
        tvPrepTime.text   = "⏱  $itemPrepTime"
        tvDifficulty.text = difficulty
        tvServes.text     = "🍽  Serves $serves"
        tvCategory.text   = itemCategory
            .replace("_", " ")
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
        tvDesc.text = itemDesc.ifEmpty {
            "A delicious home-style meal kit with all ingredients pre-measured and ready to cook."
        }

        // ── Ingredients ───────────────────────────────────────────────
        ingredientsContainer.removeAllViews()
        if (ingredients.isNotEmpty()) {
            ingredients.split("\n").filter { it.isNotBlank() }.forEach { ing ->
                val row = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    lp.bottomMargin = 10
                    layoutParams = lp
                }
                val dot = TextView(this).apply {
                    text = "●"
                    setTextColor(Color.parseColor("#E8871A"))
                    textSize = 8f
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    lp.topMargin   = 6
                    lp.rightMargin = 10
                    layoutParams = lp
                }
                val tv = TextView(this).apply {
                    text = ing.trim()
                    setTextColor(Color.parseColor("#333333"))
                    textSize = 13.5f
                    layoutParams = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                    )
                }
                row.addView(dot)
                row.addView(tv)
                ingredientsContainer.addView(row)
            }
        } else {
            val tv = TextView(this).apply {
                text = "All fresh ingredients are included and pre-measured in your kit."
                setTextColor(Color.parseColor("#888888"))
                textSize = 13f
            }
            ingredientsContainer.addView(tv)
        }

        // ── Recipe Steps ──────────────────────────────────────────────
        stepsContainer.removeAllViews()
        if (steps.isNotEmpty()) {
            steps.split("\n").filter { it.isNotBlank() }.forEach { step ->
                val stepLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(0, 0, 0, 14)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                val stepNum  = step.substringBefore(".").trim().toIntOrNull()
                val stepText = if (stepNum != null) step.substringAfter(".").trim() else step.trim()

                val numBadge = TextView(this).apply {
                    text = if (stepNum != null) "$stepNum" else "•"
                    setTextColor(Color.WHITE)
                    textSize = 11f
                    setTypeface(null, android.graphics.Typeface.BOLD)
                    gravity = android.view.Gravity.CENTER
                    background = getDrawable(R.drawable.bg_button_orange)
                    val lp = LinearLayout.LayoutParams(28, 28)
                    lp.topMargin   = 2
                    lp.rightMargin = 12
                    layoutParams = lp
                }
                val tv = TextView(this).apply {
                    text = stepText
                    setTextColor(Color.parseColor("#333333"))
                    textSize = 13.5f
                    setLineSpacing(0f, 1.4f)
                    layoutParams = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                    )
                }
                stepLayout.addView(numBadge)
                stepLayout.addView(tv)
                stepsContainer.addView(stepLayout)

                // Divider between steps
                val divider = View(this).apply {
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1
                    )
                    lp.bottomMargin = 14
                    layoutParams = lp
                    setBackgroundColor(Color.parseColor("#F0E8DC"))
                }
                stepsContainer.addView(divider)
            }
        } else {
            val tv = TextView(this).apply {
                text = "Detailed recipe steps will be included with your kit delivery."
                setTextColor(Color.parseColor("#888888"))
                textSize = 13f
            }
            stepsContainer.addView(tv)
        }

        // ── Cookware ──────────────────────────────────────────────────
        if (cookware.isNotEmpty()) {
            cookwareSection.visibility = View.VISIBLE
            tvCookware.text = cookware
            if (cookwareSubs.isNotEmpty()) {
                tvCookwareSubs.text = "💡 Substitutes: $cookwareSubs"
                tvCookwareSubs.visibility = View.VISIBLE
            } else {
                tvCookwareSubs.visibility = View.GONE
            }
        } else {
            cookwareSection.visibility = View.GONE
        }

        // ── Add to Cart ───────────────────────────────────────────────
        btnAddToCart.setOnClickListener {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }

            btnAddToCart.isEnabled = false
            btnAddToCart.text      = "Adding..."

            val ref = db.collection("carts").document(uid).collection("items").document(itemId)
            ref.get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val currentQty = doc.getLong("quantity") ?: 1
                        ref.update("quantity", currentQty + 1)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Quantity updated 🛒", Toast.LENGTH_SHORT).show()
                                btnAddToCart.isEnabled = true
                                btnAddToCart.text      = "Added to Cart ✓"
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                btnAddToCart.isEnabled = true
                                btnAddToCart.text      = "Add to Cart"
                            }
                    } else {
                        val cartData = hashMapOf(
                            "itemId"   to itemId,
                            "name"     to itemName,
                            "price"    to itemPrice,
                            "imageUrl" to itemImage,
                            "quantity" to 1
                        )
                        ref.set(cartData, SetOptions.merge())
                            .addOnSuccessListener {
                                Toast.makeText(this, "$itemName added to cart! 🛒", Toast.LENGTH_SHORT).show()
                                btnAddToCart.isEnabled = true
                                btnAddToCart.text      = "Added to Cart ✓"
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                btnAddToCart.isEnabled = true
                                btnAddToCart.text      = "Add to Cart"
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnAddToCart.isEnabled = true
                    btnAddToCart.text      = "Add to Cart"
                }
        }
    }
}