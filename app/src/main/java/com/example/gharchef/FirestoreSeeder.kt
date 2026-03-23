package com.example.gharchef

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreSeeder {

    fun seedAll(db: FirebaseFirestore) {
        val items = listOf(

            mapOf(
                "name"        to "Paneer Butter Masala Kit",
                "description" to "Everything pre-measured to make silky restaurant-style Paneer Butter Masala at home. Marinated paneer, tomato-cashew base, and a measured cream sachet included.",
                "price"       to 280.0, "category" to "north_indian", "prepTime" to "25 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Marinated Paneer Cubes (200g)\nTomato-Cashew Puree (200ml)\nFresh Cream Sachet (30ml)\nGinger-Garlic Paste (1 tbsp)\nButter Portion (20g)\nKasuri Methi Sachet (1 tsp)\nSpice Mix Pack (cumin, coriander, chili, garam masala)\nSalt Pack",
                "recipeSteps" to "1. Melt butter in a pan on medium heat.\n2. Add ginger-garlic paste, sauté for 1 minute until fragrant.\n3. Pour in the tomato-cashew puree, stir and cook 5 minutes until oil separates at the sides.\n4. Add the spice mix pack and salt, mix well and cook 1 more minute.\n5. Add ½ cup water, bring to a gentle simmer.\n6. Add paneer cubes, stir gently to coat.\n7. Pour in the cream sachet, stir and cook on low heat for 3 minutes.\n8. Crush kasuri methi between palms, sprinkle on top.\n9. Serve hot with naan or rice.",
                "cookware"    to "Heavy-bottom pan or kadhai",
                "cookwareSubstitutes" to "Kadhai → Any deep non-stick pan",
                "imageUrl"    to "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400"
            ),

            mapOf(
                "name"        to "Dal Makhani Kit",
                "description" to "Pre-soaked black lentils with a measured butter-tomato base. Takes just 30 mins on your stove — we did the overnight soaking for you.",
                "price"       to 220.0, "category" to "north_indian", "prepTime" to "30 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Pre-soaked Black Lentils / Urad Dal (200g)\nKidney Beans / Rajma (50g, pre-soaked)\nTomato Puree (150ml)\nButter Portion (25g)\nFresh Cream Sachet (25ml)\nGinger-Garlic Paste (1 tbsp)\nWhole Spice Pack (bay leaf, cloves, cardamom)\nDal Makhani Spice Mix\nSalt Pack",
                "recipeSteps" to "1. In a pressure cooker, add soaked lentils + rajma with 2 cups water.\n2. Pressure cook for 4 whistles on medium heat. Let pressure release naturally.\n3. In a separate pan, melt butter and add whole spice pack.\n4. Add ginger-garlic paste, sauté 1 minute.\n5. Add tomato puree, cook 5 minutes until thick.\n6. Add spice mix, stir and cook 1 minute.\n7. Add cooked dal to this masala. Mix well.\n8. Simmer on low heat for 15 minutes, stirring occasionally.\n9. Stir in cream sachet. Adjust salt.\n10. Garnish with a pat of butter. Serve with naan.",
                "cookware"    to "Pressure Cooker, Pan",
                "cookwareSubstitutes" to "Pressure Cooker → Pot with lid (cook 45 mins on low instead of 4 whistles)",
                "imageUrl"    to "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400"
            ),

            mapOf(
                "name"        to "Butter Chicken Kit",
                "description" to "Marinated chicken tikka pieces, velvety tomato-butter sauce, and measured cream. Just cook and combine — restaurant taste in 30 mins.",
                "price"       to 320.0, "category" to "north_indian", "prepTime" to "30 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Medium",
                "ingredients" to "Marinated Chicken Tikka Pieces (250g, ready to cook)\nTomato-Onion Butter Sauce Base (200ml)\nFresh Cream Sachet (40ml)\nButter Portion (20g)\nKasuri Methi Sachet (1 tsp)\nButter Chicken Spice Mix\nSalt Pack",
                "recipeSteps" to "1. Heat a pan on high heat. Add chicken pieces, sear 3-4 mins each side until charred.\n2. Remove chicken and set aside.\n3. In the same pan, lower heat to medium. Add butter.\n4. Pour in the tomato-onion sauce base. Cook 5 minutes stirring often.\n5. Add butter chicken spice mix, stir and cook 2 minutes.\n6. Add ½ cup water, bring to a simmer.\n7. Add seared chicken back in. Stir to coat.\n8. Simmer covered 10 minutes on low heat.\n9. Add cream sachet. Stir gently.\n10. Crush kasuri methi, sprinkle over. Serve with naan.",
                "cookware"    to "Wide pan or kadhai",
                "cookwareSubstitutes" to "Wide pan → Any large pan with lid",
                "imageUrl"    to "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=400"
            ),

            mapOf(
                "name"        to "Chole Bhature Kit",
                "description" to "Pre-soaked chole, ready-to-fry maida dough balls, and measured spice packs. Skip the 8-hour soaking — just cook and fry.",
                "price"       to 180.0, "category" to "north_indian", "prepTime" to "25 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Medium",
                "ingredients" to "Pre-soaked Chole / Chickpeas (200g)\nReady Maida Dough Balls (6 portions)\nOnion (1, chopped)\nTomato Puree (100ml)\nGinger-Garlic Paste (1 tbsp)\nChole Masala Pack (20g)\nOil Pack (for frying, 100ml)\nSalt Pack\nPickled Onion Rings (garnish)",
                "recipeSteps" to "1. CHOLE: Heat 1 tbsp oil in kadhai on medium.\n2. Add onion, sauté till golden (4 mins).\n3. Add ginger-garlic paste, cook 1 min.\n4. Add tomato puree, cook 3 mins till oil separates.\n5. Add chole masala pack + salt, mix well.\n6. Add pre-soaked chole + 1.5 cups water.\n7. Cover and cook 15 minutes on medium-low. Mash a few chole for thickness.\n8. BHATURE: Heat oil in a deep kadhai (3 inches deep) on medium-high.\n9. Flatten each dough ball into a circle (6 inches).\n10. Fry one bhatura at a time, pressing gently with a spoon to puff up.\n11. Fry 1 min each side until golden.\n12. Serve hot chole with bhature and pickled onion rings.",
                "cookware"    to "Kadhai (2 needed — one for chole, one for frying), Rolling Pin",
                "cookwareSubstitutes" to "Kadhai → Deep non-stick pan | Rolling Pin → Glass bottle or smooth cup",
                "imageUrl"    to "https://images.unsplash.com/photo-1626200926093-e2b738cfad7f?w=400"
            ),

            mapOf(
                "name"        to "Pav Bhaji Kit",
                "description" to "Pre-boiled and mashed vegetables, measured spice pack, and fresh pavs. Mumbai street food in 15 minutes.",
                "price"       to 140.0, "category" to "street_food", "prepTime" to "15 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Pre-boiled Mashed Vegetables (potato, cauliflower, peas — 300g)\nOnion (1, finely chopped)\nTomato (1, finely chopped)\nCapsicum (½, finely chopped)\nButter Portion (30g)\nPav Bhaji Masala Pack (15g)\nFresh Pav Buns (4)\nLemon Wedges (2)\nCoriander Leaves (garnish pack)",
                "recipeSteps" to "1. Heat butter on a tawa or flat pan on medium heat.\n2. Add onion, cook 2 minutes until soft.\n3. Add capsicum, cook 1 more minute.\n4. Add tomato, mash and cook 2 minutes.\n5. Add pav bhaji masala pack, stir well.\n6. Add the pre-boiled mashed vegetables. Mix and mash everything together.\n7. Add ¼ cup water if too thick. Cook 5 minutes, mashing as you go.\n8. PAVS: Slit pavs horizontally. Butter both sides.\n9. Toast buttered pavs on the same tawa until golden and crisp.\n10. Serve bhaji hot, topped with a pat of butter, coriander, and a lemon wedge.",
                "cookware"    to "Tawa or flat pan",
                "cookwareSubstitutes" to "Tawa → Any flat pan or griddle",
                "imageUrl"    to "https://images.unsplash.com/photo-1606491956689-2ea866880c84?w=400"
            ),

            mapOf(
                "name"        to "Classic Samosa Kit (6 pcs)",
                "description" to "Ready-to-fold samosa pastry sheets, spiced potato filling, and a folding guide. Fry to golden perfection in 15 mins.",
                "price"       to 120.0, "category" to "street_food", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "difficulty"  to "Medium",
                "ingredients" to "Ready Samosa Pastry Sheets (12 strips)\nSpiced Potato-Pea Filling (300g, ready to use)\nFlour-Water Paste (for sealing edges)\nOil Pack (for frying, 150ml)\nGreen Chutney Sachet\nDate-Tamarind Chutney Sachet",
                "recipeSteps" to "1. Take a pastry strip, fold one end diagonally to form a cone shape.\n2. Seal the overlapping edge with flour-water paste.\n3. Fill the cone with 2 tbsp of potato-pea filling. Do not overfill.\n4. Fold the top edge over and seal with paste to form a triangle.\n5. Repeat for all 6 samosas.\n6. Heat oil in a kadhai on medium heat (not high — low heat makes them crispy).\n7. Test oil by dropping a tiny piece of dough — it should rise slowly.\n8. Fry samosas 4-5 minutes per side on medium-low until golden brown.\n9. Drain on paper towel.\n10. Serve with green chutney and tamarind chutney.",
                "cookware"    to "Kadhai or deep pan",
                "cookwareSubstitutes" to "Kadhai → Any deep pot works for frying",
                "imageUrl"    to "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400"
            ),

            mapOf(
                "name"        to "Jeera Rice Kit",
                "description" to "Measured basmati rice, whole spice pack with cumin, and a ghee portion. Fluffy, aromatic jeera rice in 15 minutes.",
                "price"       to 120.0, "category" to "north_indian", "prepTime" to "15 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Basmati Rice (200g, pre-washed and soaked 20 mins)\nGhee Portion (1.5 tbsp)\nWhole Spice Pack (cumin seeds, bay leaf, cloves, cardamom)\nSalt Pack",
                "recipeSteps" to "1. Drain the soaked rice.\n2. In a pot, heat ghee on medium heat.\n3. Add whole spice pack — cumin will sizzle immediately.\n4. Once cumin crackles (10 seconds), add rice. Stir gently for 1 minute.\n5. Add 1.5 cups water and salt.\n6. Bring to a boil, then cover and reduce to lowest heat.\n7. Cook exactly 12 minutes. Do not lift lid during cooking.\n8. Turn off heat. Let rest covered for 5 minutes.\n9. Fluff gently with a fork. Serve hot.",
                "cookware"    to "Pot with tight-fitting lid",
                "cookwareSubstitutes" to "Pot with lid → Any vessel with a plate as lid",
                "imageUrl"    to "https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?w=400"
            ),

            mapOf(
                "name"        to "Butter Naan Kit",
                "description" to "Leavened naan dough (already rested), a butter portion, and garlic sachet. Tawa naan at home — no tandoor needed.",
                "price"       to 80.0, "category" to "street_food", "prepTime" to "15 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Rested Naan Dough (4 portions, ready to roll)\nButter Portion (30g)\nMinced Garlic Sachet (1 tsp)\nFresh Coriander Pack (garnish)",
                "recipeSteps" to "1. Mix minced garlic into butter and set aside.\n2. Divide dough into 4 equal balls.\n3. On a lightly floured surface, roll each ball into an oval (6–7 inches, slightly thick).\n4. Heat tawa on HIGH heat until very hot (this is key — high heat makes it puff).\n5. Wet one side of the naan slightly with water.\n6. Place wet-side down on tawa. Cook 1–2 mins until bubbles form.\n7. Flip and cook 1 more minute on other side.\n8. Immediately brush with garlic butter while hot.\n9. Garnish with coriander. Serve at once.",
                "cookware"    to "Tawa (flat iron pan)",
                "cookwareSubstitutes" to "Tawa → Cast iron pan or any flat pan works — must get very hot",
                "imageUrl"    to "https://images.unsplash.com/photo-1505253716362-afaea1d3d1af?w=400"
            ),

            mapOf(
                "name"        to "Matar Paneer Kit",
                "description" to "Fresh paneer cubes, pre-shelled green peas, and a spiced onion-tomato gravy base — measured and ready.",
                "price"       to 240.0, "category" to "north_indian", "prepTime" to "20 mins",
                "rating" to 4.4, "available" to true, "popular" to false, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Fresh Paneer Cubes (180g)\nGreen Peas / Matar (100g)\nOnion-Tomato Gravy Base (180ml)\nGinger-Garlic Paste (1 tbsp)\nMatar Paneer Spice Mix\nOil Pack (1 tbsp)\nSalt Pack\nFresh Coriander (garnish)",
                "recipeSteps" to "1. Heat oil in pan on medium heat.\n2. Add ginger-garlic paste, sauté 1 minute.\n3. Add onion-tomato gravy base, cook 4 minutes stirring until oil separates.\n4. Add spice mix + salt, cook 1 minute.\n5. Add green peas + ½ cup water. Cover and cook 5 minutes.\n6. Add paneer cubes. Stir gently.\n7. Cover and cook 4 more minutes on low heat.\n8. Garnish with coriander. Serve with roti or rice.",
                "cookware"    to "Pan with lid",
                "cookwareSubstitutes" to "Any pan with a lid or cover",
                "imageUrl"    to "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400"
            ),

            mapOf(
                "name"        to "Hyderabadi Biryani Kit",
                "description" to "Marinated meat, par-cooked saffron basmati rice, fried onions, and a dum-cooking seal — everything for the iconic layered biryani.",
                "price"       to 380.0, "category" to "east_indian", "prepTime" to "40 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 3,
                "difficulty"  to "Medium",
                "ingredients" to "Marinated Mutton/Chicken Pieces (300g)\nPar-cooked Saffron Basmati Rice (300g)\nFried Onion / Birista Pack (30g)\nFresh Mint Leaves Pack\nFresh Coriander Pack\nGhee Portion (2 tbsp)\nSaffron Milk Sachet (pre-mixed)\nBiryani Spice Pack\nDough Strip (for sealing lid — dum)\nSalt Pack",
                "recipeSteps" to "1. In a heavy pot, spread marinated meat at the bottom.\n2. Layer half the par-cooked rice on top of meat.\n3. Sprinkle half the birista, mint, and coriander.\n4. Add remaining rice as the top layer.\n5. Pour saffron milk evenly over the rice.\n6. Scatter remaining birista, mint, coriander on top.\n7. Drizzle ghee over everything.\n8. Place lid on pot. Seal the edges with the dough strip (this creates dum — the steam trap).\n9. Cook on high heat 3 minutes, then reduce to lowest heat.\n10. Cook on dum for 25 minutes. Do not open.\n11. Break the dough seal at the table for dramatic reveal.\n12. Mix gently from bottom and serve hot.",
                "cookware"    to "Heavy-bottom pot with tight lid (handi preferred)",
                "cookwareSubstitutes" to "Handi → Any heavy pot | If no heavy pot, place a tawa/flat pan under the pot to distribute heat",
                "imageUrl"    to "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400"
            ),

            mapOf(
                "name"        to "Paneer Tikka Kit",
                "description" to "Paneer and vegetables pre-marinated in tandoori spices. Skewers and a charcoal sachet included for smoky flavour without a tandoor.",
                "price"       to 260.0, "category" to "north_indian", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to false, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Tandoori Marinated Paneer Cubes (200g)\nMarinated Capsicum Chunks (mix of colors, 100g)\nMarinated Onion Chunks (100g)\nBamboo Skewers (6)\nSmoke Flavour Sachet (liquid smoke or coal method pack)\nChaat Masala Pack\nGreen Chutney Sachet\nLemon Wedges",
                "recipeSteps" to "1. Thread paneer, capsicum, and onion alternately onto skewers.\n2. TAWA METHOD: Heat tawa on high heat. Brush with oil.\n3. Place skewers, cook 2–3 minutes per side (4 sides total), until charred marks appear.\n4. SMOKE EFFECT: Heat a small steel bowl in the same pan. Add a few drops of liquid smoke (from packet) and immediately cover the tawa with a dome lid for 2 minutes. This gives tandoori smokiness.\n5. Remove skewers. Sprinkle chaat masala.\n6. Squeeze lemon on top.\n7. Serve with green chutney.",
                "cookware"    to "Tawa, small steel katori (for smoke)",
                "cookwareSubstitutes" to "Tawa → Grill pan or any flat pan | Steel katori → Any small metal cup",
                "imageUrl"    to "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400"
            ),

            mapOf(
                "name"        to "Dal Tadka Kit",
                "description" to "Pre-cooked yellow lentils, measured tempering spices, and a ghee portion. A wholesome meal in 10 minutes.",
                "price"       to 160.0, "category" to "north_indian", "prepTime" to "10 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "difficulty"  to "Easy",
                "ingredients" to "Pre-cooked Yellow Dal / Toor Dal (250ml)\nGhee Portion (1.5 tbsp)\nTadka Spice Pack (cumin seeds, mustard seeds, dry red chili, hing/asafoetida)\nGarlic Cloves (4, sliced)\nOnion (½, finely chopped)\nTomato (½, chopped)\nSalt Pack\nFresh Coriander (garnish)",
                "recipeSteps" to "1. Warm the pre-cooked dal in a pot on low heat. Add salt to taste.\n2. In a small tadka pan or ladle, heat ghee on medium-high until very hot.\n3. Add cumin + mustard seeds from spice pack — they'll crackle in 10 seconds.\n4. Add dry red chili + hing, stir 5 seconds.\n5. Add garlic slices, fry until golden (30 seconds).\n6. Add onion, cook 1 minute until translucent.\n7. Add tomato, cook 1 minute.\n8. Pour this entire tadka directly into the warm dal. It will sizzle — that's the magic!\n9. Stir gently, garnish with coriander. Serve with rice.",
                "cookware"    to "Pot, Small tadka pan or steel ladle",
                "cookwareSubstitutes" to "Tadka pan → A small steel cup placed on flame | Large ladle on direct flame also works",
                "imageUrl"    to "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400"
            ),

            mapOf(
                "name"        to "Gulab Jamun Kit (8 pcs)",
                "description" to "Ready-to-fry khoya dough balls and a measured rose-sugar syrup pack. Soft gulab jamuns in 20 minutes — no guesswork.",
                "price"       to 130.0, "category" to "east_indian", "prepTime" to "20 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 4,
                "difficulty"  to "Medium",
                "ingredients" to "Khoya Dough Balls (8, ready to fry)\nSugar Syrup Pack (pre-mixed with rose water and cardamom — just heat)\nOil Pack (for frying, 200ml)\nSaffron Strands (garnish sachet)",
                "recipeSteps" to "1. SYRUP: Pour syrup pack into a saucepan. Heat on medium, stirring until it just comes to a boil. Reduce to lowest heat to keep warm.\n2. OIL: Heat oil in a kadhai on medium-low heat. IMPORTANT: temperature must be medium-low — hot oil will harden the outside before inside cooks.\n3. Test oil by dropping a tiny piece of dough. It should slowly rise to the surface.\n4. Gently slide in 4 dough balls at a time. Do not crowd.\n5. Stir gently and continuously — this ensures even browning.\n6. Fry 6–8 minutes until deep brown all over.\n7. Immediately transfer hot jamuns into the warm syrup.\n8. Let them soak for minimum 20 minutes (longer = softer and sweeter).\n9. Garnish with saffron strands. Serve warm.",
                "cookware"    to "Kadhai, Small saucepan",
                "cookwareSubstitutes" to "Kadhai → Any deep pot | Saucepan → Any pot for the syrup",
                "imageUrl"    to "https://images.unsplash.com/photo-1666365434-b64ffb2fd45f?w=400"
            ),

            mapOf(
                "name"        to "Kheer Kit",
                "description" to "Pre-measured basmati rice, full-fat milk concentrate, and a nut-saffron garnish pack for authentic Bengali-style kheer.",
                "price"       to 110.0, "category" to "east_indian", "prepTime" to "30 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 3,
                "difficulty"  to "Easy",
                "ingredients" to "Basmati Rice (60g, pre-washed)\nFull-Fat Milk Concentrate Pack (makes 1 litre when diluted with water)\nSugar Pack (60g)\nCardamom Powder Sachet\nSaffron Sachet\nGarnish Pack (sliced almonds, pistachios, raisins)",
                "recipeSteps" to "1. Dissolve milk concentrate in 900ml warm water. Keep aside.\n2. Soak saffron in 2 tbsp warm milk from the concentrate. Set aside.\n3. In a heavy-bottom pot, add the diluted milk. Bring to a boil on medium heat, stirring.\n4. Add washed rice. Stir immediately to prevent sticking.\n5. Reduce to medium-low. Cook 25 minutes, stirring every 3 minutes.\n6. Rice should be completely soft and milk should thicken noticeably.\n7. Add sugar, stir until dissolved (2 mins).\n8. Add cardamom powder and saffron milk. Stir.\n9. Cook 2 more minutes. Remove from heat.\n10. Pour into serving bowls. Top with nut garnish pack.\n11. Serve warm, or chill 2 hours for cold kheer.",
                "cookware"    to "Heavy-bottom pot (to prevent milk from burning)",
                "cookwareSubstitutes" to "Heavy pot → Any pot but keep stirring every 2 mins to prevent scorching",
                "imageUrl"    to "https://images.unsplash.com/photo-1695158568573-3f40e0fb0ef8?w=400"
            )
        )

        val col = db.collection("menu")
        for (item in items) {
            col.add(item)
                .addOnSuccessListener { Log.d("Seeder", "Added: ${item["name"]}") }
                .addOnFailureListener { e -> Log.e("Seeder", "Failed to add ${item["name"]}: ${e.message}") }
        }
    }
}