package com.example.gharchef

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId    = intent.getStringExtra("orderId")    ?: ""
        val orderTotal = intent.getDoubleExtra("orderTotal", 0.0)
        val orderStatus = intent.getStringExtra("orderStatus") ?: "Confirmed"

        val tvOrderId   = findViewById<TextView>(R.id.tvDetailOrderId)
        val tvStatus    = findViewById<TextView>(R.id.tvDetailStatus)
        val tvTotal     = findViewById<TextView>(R.id.tvDetailTotal)
        val container   = findViewById<LinearLayout>(R.id.recipeContainer)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        tvOrderId.text = "Order #GC-${orderId.takeLast(4).uppercase()}"
        tvStatus.text  = orderStatus
        tvTotal.text   = "₹%.2f".format(orderTotal)

        // Color the status
        val statusColor = when (orderStatus) {
            "Delivered"        -> "#4CAF50"
            "Cancelled"        -> "#F44336"
            "Out for Delivery" -> "#2196F3"
            "Preparing"        -> "#FF9800"
            else               -> "#E8871A"
        }
        tvStatus.setTextColor(Color.parseColor(statusColor))

        // Back button
        try { findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() } }
        catch (_: Exception) {}

        // Load the order, then for each item fetch its recipe from menu collection
        progressBar.visibility = View.VISIBLE
        val db = FirebaseFirestore.getInstance()

        db.collection("orders").document(orderId).get()
            .addOnSuccessListener { orderDoc ->
                progressBar.visibility = View.GONE
                val items = (orderDoc.get("items") as? List<Map<String, Any>>) ?: emptyList()

                if (items.isEmpty()) {
                    container.addView(makeTextCard("No items found in this order."))
                    return@addOnSuccessListener
                }

                // For each item, look up its menu entry to get recipe
                var fetched = 0
                val total   = items.size

                items.forEach { itemMap ->
                    val itemName = itemMap["name"] as? String ?: ""

                    db.collection("menu")
                        .whereEqualTo("name", itemName)
                        .limit(1)
                        .get()
                        .addOnSuccessListener { menuResult ->
                            fetched++
                            val doc = menuResult.documents.firstOrNull()

                            val ingredients          = doc?.getString("ingredients")          ?: ""
                            val recipeSteps          = doc?.getString("recipeSteps")          ?: ""
                            val cookware             = doc?.getString("cookware")             ?: ""
                            val cookwareSubstitutes  = doc?.getString("cookwareSubstitutes")  ?: ""
                            val serves               = doc?.getLong("serves")?.toInt()        ?: 2
                            val difficulty           = doc?.getString("difficulty")           ?: "Easy"
                            val prepTime             = doc?.getString("prepTime")             ?: "20 mins"

                            val kitCard = buildKitRecipeCard(
                                itemName, serves, difficulty, prepTime,
                                ingredients, recipeSteps, cookware, cookwareSubstitutes
                            )
                            container.addView(kitCard)

                            if (fetched == total && recipeSteps.isEmpty()) {
                                container.addView(makeTextCard("Recipe not available for this item yet."))
                            }
                        }
                        .addOnFailureListener { fetched++ }
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                container.addView(makeTextCard("Failed to load order details."))
            }
    }

    // Builds a full recipe card for one kit item
    private fun buildKitRecipeCard(
        name: String, serves: Int, difficulty: String, prepTime: String,
        ingredients: String, recipeSteps: String, cookware: String, cookwareSubstitutes: String
    ): View {
        val inflater = LayoutInflater.from(this)
        val card     = inflater.inflate(R.layout.item_recipe_card, null) as ViewGroup

        try {
            card.findViewById<TextView>(R.id.tvRecipeKitName).text    = name
            card.findViewById<TextView>(R.id.tvRecipeServes).text     = "Serves $serves"
            card.findViewById<TextView>(R.id.tvRecipeDifficulty).text = difficulty
            card.findViewById<TextView>(R.id.tvRecipePrepTime).text   = "⏱ $prepTime"
        } catch (_: Exception) {}

        // Ingredients section
        if (ingredients.isNotEmpty()) {
            try {
                val container = card.findViewById<LinearLayout>(R.id.ingredientsContainer)
                ingredients.split("\n").filter { it.isNotBlank() }.forEach { ingredient ->
                    val tv = TextView(this).apply {
                        text    = "• $ingredient"
                        textSize = 13f
                        setTextColor(Color.parseColor("#444444"))
                        setPadding(0, 4, 0, 4)
                    }
                    container.addView(tv)
                }
            } catch (_: Exception) {}
        }

        // Recipe steps
        if (recipeSteps.isNotEmpty()) {
            try {
                val container = card.findViewById<LinearLayout>(R.id.stepsContainer)
                recipeSteps.split("\n").filter { it.isNotBlank() }.forEach { step ->
                    val tv = TextView(this).apply {
                        text    = step
                        textSize = 13f
                        setTextColor(Color.parseColor("#333333"))
                        setPadding(0, 6, 0, 6)
                    }
                    container.addView(tv)
                }
            } catch (_: Exception) {}
        }

        // Cookware
        if (cookware.isNotEmpty()) {
            try {
                card.findViewById<TextView>(R.id.tvCookware).text = "🍳 $cookware"
            } catch (_: Exception) {}
        }
        if (cookwareSubstitutes.isNotEmpty()) {
            try {
                card.findViewById<TextView>(R.id.tvCookwareSubs).text = cookwareSubstitutes
            } catch (_: Exception) {}
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = 16
        card.layoutParams = params
        return card
    }

    private fun makeTextCard(msg: String): TextView = TextView(this).apply {
        text    = msg
        textSize = 14f
        setTextColor(Color.parseColor("#888888"))
        setPadding(16, 24, 16, 24)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}