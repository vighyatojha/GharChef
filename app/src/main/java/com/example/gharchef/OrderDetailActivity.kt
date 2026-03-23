package com.example.gharchef

import android.graphics.Color
import android.graphics.Typeface
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId     = intent.getStringExtra("orderId")    ?: ""
        val orderTotal  = intent.getDoubleExtra("orderTotal", 0.0)
        val orderStatus = intent.getStringExtra("orderStatus") ?: "Confirmed"
        val timestamp   = intent.getLongExtra("timestamp", 0L)

        val tvOrderId   = findViewById<TextView>(R.id.tvDetailOrderId)
        val tvStatus    = findViewById<TextView>(R.id.tvDetailStatus)
        val tvTotal     = findViewById<TextView>(R.id.tvDetailTotal)
        val tvDate      = findViewById<TextView>(R.id.tvDetailDate)
        val container   = findViewById<LinearLayout>(R.id.recipeContainer)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        tvOrderId.text = "Order #GC-${orderId.takeLast(4).uppercase()}"
        tvStatus.text  = orderStatus
        tvTotal.text   = "₹%.2f".format(orderTotal)

        // Format date
        if (timestamp > 0) {
            val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            tvDate.text = sdf.format(Date(timestamp))
        } else {
            tvDate.text = "Date unavailable"
        }

        // Status color
        val statusColor = when (orderStatus) {
            "Delivered"        -> "#4CAF50"
            "Cancelled"        -> "#F44336"
            "Out for Delivery" -> "#2196F3"
            "Preparing"        -> "#FF9800"
            else               -> "#E8871A"
        }
        tvStatus.setBackgroundColor(Color.parseColor(statusColor))

        // Update timeline UI
        updateTimeline(orderStatus)

        try { findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() } } catch (_: Exception) {}

        if (orderId.isEmpty()) {
            container.addView(makeMessageView("Order ID not found."))
            return
        }

        progressBar.visibility = View.VISIBLE
        val db = FirebaseFirestore.getInstance()

        db.collection("orders").document(orderId).get()
            .addOnSuccessListener { orderDoc ->
                progressBar.visibility = View.GONE
                val items = (orderDoc.get("items") as? List<Map<String, Any>>) ?: emptyList()

                if (items.isEmpty()) {
                    container.addView(makeMessageView("No items found in this order."))
                    return@addOnSuccessListener
                }

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

                            val ingredients         = doc?.getString("ingredients")         ?: ""
                            val recipeSteps         = doc?.getString("recipeSteps")         ?: ""
                            val cookware            = doc?.getString("cookware")            ?: ""
                            val cookwareSubstitutes = doc?.getString("cookwareSubstitutes") ?: ""
                            val serves              = doc?.getLong("serves")?.toInt()       ?: 2
                            val difficulty          = doc?.getString("difficulty")          ?: "Easy"
                            val prepTime            = doc?.getString("prepTime")            ?: "20 mins"

                            val kitCard = buildKitRecipeCard(
                                itemName, serves, difficulty, prepTime,
                                ingredients, recipeSteps, cookware, cookwareSubstitutes
                            )
                            container.addView(kitCard)

                            if (fetched == total && recipeSteps.isEmpty() && ingredients.isEmpty()) {
                                container.addView(makeMessageView("Full recipe not available for this item yet."))
                            }
                        }
                        .addOnFailureListener { fetched++ }
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                container.addView(makeMessageView("Failed to load order details. Check your connection."))
            }
    }

    private fun updateTimeline(status: String) {
        // Highlight relevant steps in the timeline
        val steps = mapOf(
            "Confirmed"        to listOf("stepPreparing"),
            "Preparing"        to listOf("stepPreparing"),
            "Out for Delivery" to listOf("stepPreparing", "stepDelivery"),
            "Delivered"        to listOf("stepPreparing", "stepDelivery", "stepDelivered")
        )
        val activeSteps = steps[status] ?: emptyList()

        activeSteps.forEach { stepId ->
            try {
                val resId = resources.getIdentifier(stepId, "id", packageName)
                val stepLayout = findViewById<LinearLayout>(resId)
                val badge = (stepLayout.getChildAt(0) as? android.widget.FrameLayout)
                badge?.setBackgroundColor(Color.parseColor("#E8871A"))
                val label = stepLayout.getChildAt(1) as? TextView
                label?.setTextColor(Color.parseColor("#E8871A"))
                label?.typeface = Typeface.DEFAULT_BOLD
            } catch (_: Exception) {}
        }
    }

    private fun buildKitRecipeCard(
        name: String, serves: Int, difficulty: String, prepTime: String,
        ingredients: String, recipeSteps: String, cookware: String, cookwareSubstitutes: String
    ): View {
        val inflater = LayoutInflater.from(this)
        val card     = inflater.inflate(R.layout.item_recipe_card, null) as ViewGroup

        try { card.findViewById<TextView>(R.id.tvRecipeKitName).text    = name }          catch (_: Exception) {}
        try { card.findViewById<TextView>(R.id.tvRecipeServes).text     = "👥 $serves Serves" } catch (_: Exception) {}
        try { card.findViewById<TextView>(R.id.tvRecipeDifficulty).text = "⚡ $difficulty" }   catch (_: Exception) {}
        try { card.findViewById<TextView>(R.id.tvRecipePrepTime).text   = "⏱ $prepTime" }     catch (_: Exception) {}

        // Difficulty color
        try {
            val diffView = card.findViewById<TextView>(R.id.tvRecipeDifficulty)
            val (fg, bg) = when (difficulty) {
                "Hard"   -> Pair("#B71C1C", "#FFEBEE")
                "Medium" -> Pair("#E65100", "#FFF3E0")
                else     -> Pair("#2E7D32", "#E8F5E9")
            }
            diffView.setTextColor(Color.parseColor(fg))
            diffView.setBackgroundColor(Color.parseColor(bg))
        } catch (_: Exception) {}

        // Ingredients
        if (ingredients.isNotEmpty()) {
            try {
                val ingContainer = card.findViewById<LinearLayout>(R.id.ingredientsContainer)
                ingredients.split("\n").filter { it.isNotBlank() }.forEachIndexed { index, ingredient ->
                    val row = LinearLayout(this).apply {
                        orientation = LinearLayout.HORIZONTAL
                        gravity     = android.view.Gravity.CENTER_VERTICAL
                        setPadding(0, if (index == 0) 0 else 8, 0, 0)
                    }
                    val dot = View(this).apply {
                        val lp = LinearLayout.LayoutParams(8, 8)
                        lp.marginEnd = 10
                        layoutParams = lp
                        setBackgroundColor(Color.parseColor("#E8871A"))
                        // Make it a circle
                        background = android.graphics.drawable.GradientDrawable().apply {
                            shape = android.graphics.drawable.GradientDrawable.OVAL
                            setColor(Color.parseColor("#E8871A"))
                        }
                    }
                    val tv = TextView(this).apply {
                        text      = ingredient.trim()
                        textSize  = 13f
                        setTextColor(Color.parseColor("#444444"))
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }
                    row.addView(dot)
                    row.addView(tv)
                    ingContainer.addView(row)
                }
            } catch (_: Exception) {}
        }

        // Cookware
        if (cookware.isNotEmpty()) {
            try { card.findViewById<TextView>(R.id.tvCookware).text = "🍳 $cookware" } catch (_: Exception) {}
        }
        if (cookwareSubstitutes.isNotEmpty()) {
            try { card.findViewById<TextView>(R.id.tvCookwareSubs).text = "💡 Substitute: $cookwareSubstitutes" } catch (_: Exception) {}
        }

        // Recipe steps — numbered beautifully
        if (recipeSteps.isNotEmpty()) {
            try {
                val stepsContainer = card.findViewById<LinearLayout>(R.id.stepsContainer)
                recipeSteps.split("\n").filter { it.isNotBlank() }.forEachIndexed { index, step ->
                    val stepRow = LinearLayout(this).apply {
                        orientation = LinearLayout.HORIZONTAL
                        gravity     = android.view.Gravity.TOP
                        val lp = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        lp.bottomMargin = 12
                        layoutParams = lp
                    }

                    // Step number circle
                    val numView = TextView(this).apply {
                        // Strip existing number from step text if it starts with "N."
                        val stepNum = (index + 1).toString()
                        text      = stepNum
                        textSize  = 11f
                        typeface  = Typeface.DEFAULT_BOLD
                        setTextColor(Color.WHITE)
                        gravity   = android.view.Gravity.CENTER
                        val size  = (28 * resources.displayMetrics.density).toInt()
                        val lp    = LinearLayout.LayoutParams(size, size)
                        lp.marginEnd = 12
                        lp.topMargin = 2
                        layoutParams = lp
                        background = android.graphics.drawable.GradientDrawable().apply {
                            shape    = android.graphics.drawable.GradientDrawable.OVAL
                            setColor(Color.parseColor("#E8871A"))
                        }
                    }

                    // Step text (strip leading "N. " if already there)
                    val cleanStep = step.trim().replace(Regex("^\\d+\\.\\s*"), "")
                    val textView  = TextView(this).apply {
                        text     = cleanStep
                        textSize = 13f
                        setTextColor(Color.parseColor("#333333"))
                        layoutParams = LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                        )
                    }

                    stepRow.addView(numView)
                    stepRow.addView(textView)
                    stepsContainer.addView(stepRow)
                }
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

    private fun makeMessageView(msg: String): TextView = TextView(this).apply {
        text     = msg
        textSize = 14f
        setTextColor(Color.parseColor("#888888"))
        gravity  = android.view.Gravity.CENTER
        setPadding(16, 48, 16, 48)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}