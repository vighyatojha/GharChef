package com.example.gharchef

data class MenuItemData(
    val id: String                  = "",
    val name: String                = "",
    val description: String         = "",
    val price: Double               = 0.0,
    val imageUrl: String            = "",
    val category: String            = "",
    val prepTime: String            = "20 mins",   // cooking time after receiving kit
    val rating: Double              = 4.0,
    val available: Boolean          = true,
    val popular: Boolean            = false,
    // ── Meal Kit specific fields ──────────────────────────────────────
    val serves: Int                 = 2,            // number of servings
    val difficulty: String          = "Easy",       // Easy / Medium / Hard
    val ingredients: String         = "",           // what's included in the kit (comma / newline separated)
    val recipeSteps: String         = "",           // numbered steps, newline-separated
    val cookware: String            = "",           // e.g. "Kadhai, Tawa, Rolling Pin"
    val cookwareSubstitutes: String = ""            // e.g. "Kadhai → Deep non-stick pan"
)