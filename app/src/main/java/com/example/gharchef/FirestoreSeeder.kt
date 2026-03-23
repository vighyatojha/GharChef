package com.example.gharchef

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreSeeder {

    fun seedAll(db: FirebaseFirestore) {
        val items = listOf(

            // ══════════════════ NORTH INDIAN (6) ══════════════════

            mapOf(
                "name" to "Paneer Butter Masala Kit",
                "description" to "Silky restaurant-style paneer in a rich tomato-cashew-cream gravy. Everything pre-measured and ready.",
                "price" to 280.0, "category" to "north_indian", "prepTime" to "25 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Marinated Paneer Cubes (200g)\nTomato-Cashew Puree (200ml)\nFresh Cream Sachet (30ml)\nGinger-Garlic Paste (1 tbsp)\nButter Portion (20g)\nKasuri Methi Sachet (1 tsp)\nSpice Mix Pack\nSalt Pack",
                "recipeSteps" to "1. Melt butter in a pan on medium heat.\n2. Add ginger-garlic paste, sauté 1 minute until fragrant.\n3. Pour in the tomato-cashew puree, cook 5 minutes until oil separates.\n4. Add the spice mix pack and salt, mix well. Cook 1 minute.\n5. Add ½ cup water, bring to a gentle simmer.\n6. Add paneer cubes, stir gently to coat.\n7. Pour in the cream sachet. Cook on low 3 minutes.\n8. Crush kasuri methi between palms, sprinkle on top.\n9. Serve hot with naan or rice.",
                "cookware" to "Heavy-bottom pan or kadhai",
                "cookwareSubstitutes" to "Kadhai → Any deep non-stick pan",
                "imageUrl" to "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400"
            ),

            mapOf(
                "name" to "Dal Makhani Kit",
                "description" to "Pre-soaked black lentils with a measured butter-tomato base. We did the overnight soaking for you — ready in 30 mins.",
                "price" to 220.0, "category" to "north_indian", "prepTime" to "30 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-soaked Black Urad Dal (200g)\nKidney Beans / Rajma (50g, pre-soaked)\nTomato Puree (150ml)\nButter Portion (25g)\nFresh Cream Sachet (25ml)\nGinger-Garlic Paste (1 tbsp)\nWhole Spice Pack (bay leaf, cloves, cardamom)\nDal Makhani Spice Mix\nSalt Pack",
                "recipeSteps" to "1. Pressure cook lentils + rajma with 2 cups water for 4 whistles. Release naturally.\n2. Melt butter in a pan, add whole spice pack.\n3. Add ginger-garlic paste, sauté 1 minute.\n4. Add tomato puree, cook 5 minutes until thick.\n5. Add spice mix, cook 1 minute.\n6. Add cooked dal, mix well. Simmer 15 minutes on low.\n7. Stir in cream sachet. Adjust salt.\n8. Garnish with a pat of butter. Serve with naan.",
                "cookware" to "Pressure Cooker, Pan",
                "cookwareSubstitutes" to "Pressure Cooker → Pot with lid (cook 45 mins on low instead)",
                "imageUrl" to "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400"
            ),

            mapOf(
                "name" to "Butter Chicken Kit",
                "description" to "Marinated chicken tikka pieces in a velvety tomato-butter sauce with measured cream. Restaurant taste in 30 mins.",
                "price" to 320.0, "category" to "north_indian", "prepTime" to "30 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to false, "difficulty" to "Medium",
                "ingredients" to "Marinated Chicken Tikka Pieces (250g)\nTomato-Onion Butter Sauce Base (200ml)\nFresh Cream Sachet (40ml)\nButter Portion (20g)\nKasuri Methi Sachet (1 tsp)\nButter Chicken Spice Mix\nSalt Pack",
                "recipeSteps" to "1. Heat pan on high. Sear chicken 3-4 mins each side until charred. Remove and set aside.\n2. Lower heat to medium, add butter.\n3. Pour in the sauce base. Cook 5 minutes stirring.\n4. Add spice mix, stir 2 minutes.\n5. Add ½ cup water, bring to a simmer.\n6. Add seared chicken back in. Simmer covered 10 minutes on low.\n7. Add cream sachet. Stir gently.\n8. Crush kasuri methi, sprinkle over. Serve with naan.",
                "cookware" to "Wide pan or kadhai",
                "cookwareSubstitutes" to "Wide pan → Any large pan with lid",
                "imageUrl" to "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=400"
            ),

            mapOf(
                "name" to "Chole Bhature Kit",
                "description" to "Pre-soaked chole, ready-to-fry maida dough balls, and measured spice packs. No 8-hour soaking needed.",
                "price" to 180.0, "category" to "north_indian", "prepTime" to "25 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Pre-soaked Chole / Chickpeas (200g)\nReady Maida Dough Balls (6 portions)\nOnion (1, chopped)\nTomato Puree (100ml)\nGinger-Garlic Paste (1 tbsp)\nChole Masala Pack (20g)\nOil Pack (100ml)\nSalt Pack\nPickled Onion Rings",
                "recipeSteps" to "1. CHOLE: Heat 1 tbsp oil. Sauté onion till golden (4 mins).\n2. Add ginger-garlic paste, cook 1 min.\n3. Add tomato puree, cook 3 mins till oil separates.\n4. Add chole masala + salt. Mix well.\n5. Add pre-soaked chole + 1.5 cups water. Cover and cook 15 minutes.\n6. Mash a few chole for thickness.\n7. BHATURE: Heat oil in deep kadhai (3 inches deep) on medium-high.\n8. Flatten each dough ball into a 6-inch circle.\n9. Fry one at a time, pressing gently with spoon to puff up. 1 min per side.\n10. Serve hot chole with bhature and pickled onion rings.",
                "cookware" to "Kadhai (2 needed), Rolling Pin",
                "cookwareSubstitutes" to "Kadhai → Deep non-stick pan | Rolling Pin → Glass bottle",
                "imageUrl" to "https://images.unsplash.com/photo-1626200926093-e2b738cfad7f?w=400"
            ),

            mapOf(
                "name" to "Rajma Chawal Kit",
                "description" to "Classic Punjabi kidney beans slow-cooked in spiced tomato gravy with perfectly fluffy jeera rice.",
                "price" to 190.0, "category" to "north_indian", "prepTime" to "30 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-soaked Rajma / Kidney Beans (200g)\nOnion (1, finely chopped)\nTomato Puree (150ml)\nGinger-Garlic Paste (1 tbsp)\nRajma Masala Pack (20g)\nOil Pack (1.5 tbsp)\nBasmati Rice (150g, pre-washed)\nWhole Spices Pack (cumin, bay leaf)\nGhee Portion (1 tbsp)\nSalt Pack",
                "recipeSteps" to "1. RAJMA: Pressure cook rajma with 2 cups water for 5 whistles. Reserve cooking liquid.\n2. Heat oil, sauté onion till golden. Add ginger-garlic paste, cook 1 min.\n3. Add tomato puree, cook 4 minutes. Add rajma masala and salt.\n4. Add cooked rajma + 1 cup cooking liquid. Simmer 15 minutes.\n5. RICE: Heat ghee, add whole spice pack till cumin crackles.\n6. Add drained rice, stir 1 minute. Add 1.5 cups water + salt.\n7. Cover and cook 12 minutes on lowest heat. Rest 5 minutes.\n8. Fluff rice and serve with rajma.",
                "cookware" to "Pressure Cooker, Pot with lid",
                "cookwareSubstitutes" to "Pressure Cooker → Deep pot (cook 45 mins)",
                "imageUrl" to "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400"
            ),

            mapOf(
                "name" to "Matar Paneer Kit",
                "description" to "Fresh paneer cubes with green peas in a spiced onion-tomato gravy. Quick and wholesome.",
                "price" to 240.0, "category" to "north_indian", "prepTime" to "20 mins",
                "rating" to 4.4, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Fresh Paneer Cubes (180g)\nGreen Peas / Matar (100g)\nOnion-Tomato Gravy Base (180ml)\nGinger-Garlic Paste (1 tbsp)\nMatar Paneer Spice Mix\nOil Pack (1 tbsp)\nSalt Pack\nFresh Coriander (garnish)",
                "recipeSteps" to "1. Heat oil in pan on medium heat.\n2. Add ginger-garlic paste, sauté 1 minute.\n3. Add onion-tomato gravy base, cook 4 minutes until oil separates.\n4. Add spice mix + salt, cook 1 minute.\n5. Add green peas + ½ cup water. Cover and cook 5 minutes.\n6. Add paneer cubes. Stir gently. Cover and cook 4 more minutes on low.\n7. Garnish with coriander. Serve with roti or rice.",
                "cookware" to "Pan with lid",
                "cookwareSubstitutes" to "Any pan with a lid or cover",
                "imageUrl" to "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400"
            ),

            // ══════════════════ SOUTH INDIAN (5) ══════════════════

            mapOf(
                "name" to "Masala Dosa Kit",
                "description" to "Pre-fermented dosa batter with spiced potato filling and fresh sambar + chutneys. Crispy South Indian classic at home.",
                "price" to 160.0, "category" to "south_indian", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Pre-fermented Dosa Batter (400ml)\nSpiced Potato Filling — Aloo Masala (250g, ready)\nSambar Sachet (200ml, ready-to-heat)\nCoconut Chutney Sachet (100g)\nTomato Chutney Sachet (80g)\nMustard Seeds + Curry Leaf Tadka Pack\nOil / Ghee Pack (2 tbsp)",
                "recipeSteps" to "1. Heat sambar gently in a small pot. Keep warm.\n2. Heat a non-stick tawa on medium-high. Lightly brush with oil.\n3. Pour one ladle of batter at center. Spread in circular motion to a thin circle.\n4. Drizzle a few drops of oil / ghee on edges.\n5. Let crisp up 2-3 minutes — bottom should be golden and edges lifting.\n6. Place 2-3 tbsp potato filling on one half of the dosa.\n7. Fold the other half over or roll it up.\n8. Serve immediately with sambar and both chutneys.",
                "cookware" to "Non-stick tawa (flat griddle)",
                "cookwareSubstitutes" to "Tawa → Any flat non-stick pan",
                "imageUrl" to "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400"
            ),

            mapOf(
                "name" to "Idli Sambar Kit",
                "description" to "Soft steamed rice cakes with piping hot sambar and two chutneys. The quintessential South Indian breakfast.",
                "price" to 130.0, "category" to "south_indian", "prepTime" to "20 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-fermented Idli Batter (300ml, makes 8-10 idlis)\nSambar Concentrate Sachet (makes 400ml sambar)\nCoconut Chutney Sachet (100g)\nTomato Chutney Sachet (80g)\nMustard + Curry Leaf Tadka Pack\nOil Pack",
                "recipeSteps" to "1. SAMBAR: Mix sambar concentrate with 300ml water in a pot. Bring to a boil, reduce heat. Add tadka pack (heat oil + mustard + curry leaves), stir into sambar. Simmer 5 minutes.\n2. IDLI: Grease idli molds lightly with oil.\n3. Pour batter into molds, filling 3/4 of each mold (they rise).\n4. Steam in a pressure cooker (without weight) or idli steamer for 10-12 minutes.\n5. Let rest 2 minutes, then gently unmold with a spoon.\n6. Serve hot idlis with sambar and chutneys.",
                "cookware" to "Idli Steamer or Pressure Cooker (without weight), Idli Mold",
                "cookwareSubstitutes" to "Idli Mold → Any small steel cups | Steamer → Pressure cooker without whistle weight",
                "imageUrl" to "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400"
            ),

            mapOf(
                "name" to "Kerala Fish Curry Kit",
                "description" to "Tangy-spicy Malabar-style fish curry with coconut milk, kudampuli (Gambooge), and fresh curry leaves.",
                "price" to 350.0, "category" to "south_indian", "prepTime" to "25 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to false, "difficulty" to "Medium",
                "ingredients" to "Fresh Fish Pieces (300g, cleaned and cut)\nCoconut Milk Pack (200ml)\nKudampuli / Kokum (4 pieces, soaked)\nOnion (1, sliced)\nTomato (1, chopped)\nGreen Chilies (2, slit)\nFresh Curry Leaves Pack\nKerala Fish Curry Masala Pack\nMustard Seeds + Dry Red Chili Tadka Pack\nCoconut Oil (1.5 tbsp)\nSalt Pack",
                "recipeSteps" to "1. Heat coconut oil in a clay pot or heavy pan.\n2. Add mustard seeds + dry red chilies from tadka pack. Let crackle.\n3. Add curry leaves, sliced onion. Cook 3 minutes on medium.\n4. Add green chilies, chopped tomato. Cook 2 minutes.\n5. Add Kerala fish curry masala pack + salt. Stir and cook 1 minute.\n6. Add soaked kudampuli + its water. Add ½ cup water. Bring to simmer.\n7. Gently add fish pieces. Do NOT stir — gently shake the pot.\n8. Pour in coconut milk. Simmer uncovered 8-10 minutes on low.\n9. Taste for salt and tanginess. Garnish with curry leaves. Serve with rice.",
                "cookware" to "Clay pot (meen chatti) or heavy-bottom pan",
                "cookwareSubstitutes" to "Clay pot → Any heavy-bottom pan or wide skillet",
                "imageUrl" to "https://images.unsplash.com/photo-1626777553635-be342a86f1a8?w=400"
            ),

            mapOf(
                "name" to "Ven Pongal Kit",
                "description" to "Comforting South Indian rice-lentil porridge with ghee, black pepper, and cashews. Temple-style home comfort food.",
                "price" to 120.0, "category" to "south_indian", "prepTime" to "20 mins",
                "rating" to 4.4, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Raw Rice (150g)\nSplit Yellow Moong Dal (75g)\nGhee Portion (2 tbsp)\nCashews (10-12 pieces)\nWhole Peppercorns (1 tsp)\nCumin Seeds (1 tsp)\nGinger Piece (1 inch, grated)\nFresh Curry Leaves Pack\nSalt Pack",
                "recipeSteps" to "1. Dry roast moong dal in pot for 2 minutes until lightly fragrant. Add rice, mix.\n2. Add 4 cups water + salt. Bring to a boil.\n3. Reduce heat to low, cover, and cook 15 minutes until very soft and mushy (this is the texture you want).\n4. In a separate small pan, heat ghee on medium.\n5. Add cashews — fry until golden. Remove and set aside.\n6. In same ghee: add cumin, peppercorns, grated ginger, curry leaves. Crackle 30 seconds.\n7. Pour the ghee tadka over cooked rice-dal. Mix gently.\n8. Top with fried cashews. Adjust salt. Serve hot with coconut chutney.",
                "cookware" to "Heavy-bottom pot with lid, small tadka pan",
                "cookwareSubstitutes" to "Tadka pan → Any small pan for tempering",
                "imageUrl" to "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400"
            ),

            mapOf(
                "name" to "Medu Vada Kit",
                "description" to "Crispy-outside, fluffy-inside urad dal donuts. Perfect with sambar and coconut chutney.",
                "price" to 140.0, "category" to "south_indian", "prepTime" to "20 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Ready Vada Batter (300g, pre-soaked and ground urad dal)\nCumin + Black Pepper Spice Pack\nFresh Curry Leaves Pack\nGreen Chili (1, finely chopped)\nOil Pack (200ml for frying)\nSambar Sachet (200ml)\nCoconut Chutney Sachet (100g)\nSalt Pack",
                "recipeSteps" to "1. Mix batter with spice pack, curry leaves, green chili, and salt. The batter must be thick.\n2. Heat oil in a kadhai on medium heat. Test by dropping a tiny piece — it should rise slowly.\n3. Wet your palm with water. Take a portion of batter, make a ball.\n4. Poke a hole in the center with your thumb (donut shape).\n5. Gently slide into oil. Fry 3-4 minutes, flip, fry 3 more minutes until golden all over.\n6. Drain on paper towel.\n7. Heat sambar separately.\n8. Serve hot vadas with sambar and coconut chutney.",
                "cookware" to "Kadhai or deep pan",
                "cookwareSubstitutes" to "Kadhai → Any deep pot for frying",
                "imageUrl" to "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400"
            ),

            // ══════════════════ WEST INDIAN (4) ══════════════════

            mapOf(
                "name" to "Mumbai Pav Bhaji Kit",
                "description" to "Pre-boiled and mashed vegetables with measured spice pack and fresh butter-toasted pavs. Mumbai street food in 15 minutes.",
                "price" to 140.0, "category" to "west_indian", "prepTime" to "15 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-boiled Mashed Vegetables (potato, cauliflower, peas — 300g)\nOnion (1, finely chopped)\nTomato (1, finely chopped)\nCapsicum (½, finely chopped)\nButter Portion (30g)\nPav Bhaji Masala Pack (15g)\nFresh Pav Buns (4)\nLemon Wedges (2)\nCoriander Leaves (garnish)",
                "recipeSteps" to "1. Heat butter on a tawa or flat pan on medium heat.\n2. Add onion, cook 2 minutes until soft.\n3. Add capsicum, cook 1 more minute.\n4. Add tomato, mash and cook 2 minutes.\n5. Add pav bhaji masala pack, stir well.\n6. Add the pre-boiled mashed vegetables. Mix and mash everything together.\n7. Add ¼ cup water if too thick. Cook 5 minutes, mashing as you go.\n8. PAVS: Slit pavs horizontally. Butter both sides.\n9. Toast buttered pavs on the same tawa until golden and crisp.\n10. Serve bhaji hot topped with butter pat, coriander, and lemon wedge.",
                "cookware" to "Tawa or flat pan",
                "cookwareSubstitutes" to "Tawa → Any flat pan or griddle",
                "imageUrl" to "https://images.unsplash.com/photo-1606491956689-2ea866880c84?w=400"
            ),

            mapOf(
                "name" to "Steamed Dhokla Kit",
                "description" to "Fluffy Gujarati gram flour steamed cake with a mustard-sesame-curry leaf tempering. Light and tangy.",
                "price" to 110.0, "category" to "west_indian", "prepTime" to "20 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 3,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Besan / Gram Flour (200g)\nCurd Sachet (100ml)\nEno Fruit Salt Sachet (1.5 tsp)\nGreen Chili-Ginger Paste (1 tbsp)\nTurmeric + Salt Pack\nSugar (1 tsp)\nTadka Pack (mustard seeds, sesame seeds, curry leaves, dry red chili)\nOil Pack (2 tbsp)\nFresh Coriander + Grated Coconut (garnish)",
                "recipeSteps" to "1. Mix besan, curd, green chili-ginger paste, turmeric, salt, sugar with ¼ cup water. Make a smooth thick batter.\n2. Grease a flat plate or thali with oil.\n3. Add Eno to batter and mix immediately — the batter will froth up. Do NOT delay.\n4. Pour batter quickly onto greased plate.\n5. Steam in steamer or pressure cooker (without weight) for 12-15 minutes.\n6. Check with toothpick — it should come out clean.\n7. TADKA: Heat oil in small pan. Add mustard seeds, sesame seeds, curry leaves, dry red chili. Crackle 20 seconds.\n8. Pour tadka over steamed dhokla. Let absorb 2 minutes.\n9. Cut into squares. Garnish with coriander and coconut.",
                "cookware" to "Steamer or Pressure Cooker (without weight), Flat plate",
                "cookwareSubstitutes" to "Steamer → Pressure cooker without whistle",
                "imageUrl" to "https://images.unsplash.com/photo-1630851840628-a46cf523ef54?w=400"
            ),

            mapOf(
                "name" to "Misal Pav Kit",
                "description" to "Spicy sprouted lentil curry topped with farsan, onion, and tomato. Classic Pune-style breakfast with soft pav.",
                "price" to 150.0, "category" to "west_indian", "prepTime" to "20 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Pre-sprouted Moth / Matki Beans (250g)\nOnion (1, finely chopped)\nTomato (1, chopped)\nMisal Masala Pack (20g)\nOil Pack (2 tbsp)\nFresh Pav Buns (4)\nFarsan / Chivda (1 cup, in separate pack)\nLemon Wedges, Coriander, Onion (garnish pack)\nSalt Pack",
                "recipeSteps" to "1. Heat oil in pressure cooker. Add onion, cook 3 minutes.\n2. Add tomato, cook 2 minutes. Add misal masala pack + salt.\n3. Add sprouted beans + ½ cup water. Pressure cook 2 whistles.\n4. Release pressure, open, and mash some beans for thicker gravy.\n5. Simmer open 5 minutes for the rassa (thin gravy) to deepen in colour.\n6. Warm pav buns by pressing on a buttered tawa.\n7. To serve: Ladle the rassa in a bowl. Top generously with farsan/chivda.\n8. Garnish with chopped onion, tomato, coriander, and a squeeze of lemon.\n9. Eat with soft pav dipped into the spicy rassa.",
                "cookware" to "Pressure Cooker, Tawa",
                "cookwareSubstitutes" to "Pressure Cooker → Deep pot (cook 20 minutes covered)",
                "imageUrl" to "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400"
            ),

            mapOf(
                "name" to "Vada Pav Kit",
                "description" to "Mumbai's most iconic street snack — spiced potato vada deep-fried in gram flour batter, served in a pav with dry garlic chutney.",
                "price" to 120.0, "category" to "west_indian", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Spiced Potato Filling (250g, ready to shape)\nBesan Batter Mix (150g, just add water)\nOil Pack (150ml for frying)\nDry Garlic Chutney Sachet (spicy, red)\nGreen Chutney Sachet\nFresh Pav Buns (4)\nFried Green Chilies (ready, in pack)\nSalt Pack",
                "recipeSteps" to "1. Mix besan batter with water to make a thick coating (like pancake batter). Add salt.\n2. Divide potato filling into 4 equal balls.\n3. Heat oil in kadhai on medium. Test: a drop of batter should rise slowly.\n4. Dip each potato ball in besan batter to coat evenly.\n5. Fry 3-4 minutes turning occasionally until golden all over.\n6. SERVING: Lightly toast pav on a buttered tawa.\n7. Spread dry garlic chutney on inside of pav (this is the key!).\n8. Add green chutney on other side.\n9. Place a hot vada inside. Add fried green chili alongside.\n10. Serve immediately.",
                "cookware" to "Kadhai or deep pan, Tawa",
                "cookwareSubstitutes" to "Kadhai → Any deep pot for frying",
                "imageUrl" to "https://images.unsplash.com/photo-1606491956689-2ea866880c84?w=400"
            ),

            // ══════════════════ EAST INDIAN (3) ══════════════════

            mapOf(
                "name" to "Hyderabadi Biryani Kit",
                "description" to "Marinated meat, par-cooked saffron basmati, fried onions, and a dum seal. Everything for the legendary layered biryani.",
                "price" to 380.0, "category" to "east_indian", "prepTime" to "40 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 3,
                "isVeg" to false, "difficulty" to "Medium",
                "ingredients" to "Marinated Mutton/Chicken Pieces (300g)\nPar-cooked Saffron Basmati Rice (300g)\nFried Onion / Birista Pack (30g)\nFresh Mint Leaves Pack\nFresh Coriander Pack\nGhee Portion (2 tbsp)\nSaffron Milk Sachet (pre-mixed)\nBiryani Spice Pack\nDough Strip (for dum sealing)\nSalt Pack",
                "recipeSteps" to "1. In a heavy pot, spread marinated meat at the bottom.\n2. Layer half the par-cooked rice on top of meat.\n3. Sprinkle half the birista, mint, and coriander.\n4. Add remaining rice as the top layer.\n5. Pour saffron milk evenly over rice.\n6. Scatter remaining birista, mint, coriander on top.\n7. Drizzle ghee over everything.\n8. Place lid on pot. Seal edges with the dough strip.\n9. Cook on high heat 3 minutes, then reduce to LOWEST heat.\n10. Cook on dum for 25 minutes. Do NOT open lid.\n11. Break the dough seal at the table for dramatic reveal.\n12. Mix gently from bottom and serve hot.",
                "cookware" to "Heavy-bottom pot with tight lid (handi preferred)",
                "cookwareSubstitutes" to "Handi → Any heavy pot | Place a tawa under pot to distribute heat",
                "imageUrl" to "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400"
            ),

            mapOf(
                "name" to "Bengali Macher Jhol Kit",
                "description" to "Traditional Bengali light fish curry with turmeric, ginger, and mustard. Eaten with plain white rice — soul food from West Bengal.",
                "price" to 290.0, "category" to "east_indian", "prepTime" to "25 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to false, "difficulty" to "Easy",
                "ingredients" to "Rohu / Catla Fish Pieces (300g, marinated with turmeric + salt)\nPotato (1, quartered)\nMustard Oil (2 tbsp)\nPanch Phoron / 5-spice Mix Pack\nGreen Chilies (2, slit)\nGinger Paste (1 tbsp)\nTurmeric + Red Chili Powder Pack\nSalt Pack\nFresh Coriander (garnish)",
                "recipeSteps" to "1. Heat mustard oil in kadhai until it starts to smoke (this removes bitterness). Reduce to medium.\n2. Gently fry fish pieces 2-3 minutes per side until lightly golden. Remove and set aside.\n3. In same oil, add panch phoron. Let crackle 10 seconds.\n4. Add potato quarters, fry 2 minutes.\n5. Add green chilies, ginger paste. Cook 1 minute.\n6. Add turmeric + chili powder pack + salt. Stir 30 seconds.\n7. Add 1.5 cups hot water. Bring to a boil.\n8. Gently add fried fish pieces. Reduce heat.\n9. Simmer uncovered 8-10 minutes until potatoes are cooked.\n10. Adjust salt. Garnish with coriander. Serve with plain white rice.",
                "cookware" to "Kadhai or wide pan",
                "cookwareSubstitutes" to "Kadhai → Any wide pan or skillet",
                "imageUrl" to "https://images.unsplash.com/photo-1626777553635-be342a86f1a8?w=400"
            ),

            mapOf(
                "name" to "Bihari Litti Chokha Kit",
                "description" to "Sattu-stuffed wheat dough balls roasted over flame or oven, served with smoky charred baigan chokha. Bihar's pride.",
                "price" to 200.0, "category" to "east_indian", "prepTime" to "35 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Litti Dough Balls (6, pre-stuffed with spiced sattu)\nCharcoal-roasted Baigan Chokha (250g, ready)\nGhee Portion (3 tbsp)\nPickled Green Chili Pack\nOnion (½, finely chopped for chokha)\nFresh Coriander + Lemon Pack",
                "recipeSteps" to "1. LITTI — OVEN METHOD: Preheat oven to 200°C (fan mode).\n2. Place litti dough balls on a wire rack or baking sheet.\n3. Bake 20-25 minutes, turning once, until charred spots appear and dough sounds hollow.\n4. Remove and immediately dip in melted ghee for 30 seconds. This is essential.\n5. CHOKHA: Take ready baigan chokha, add chopped onion, coriander, lemon juice, and chili. Mix.\n6. TAWA METHOD (no oven): Cook litti on a hot tawa on low flame, turning every 2-3 minutes until cooked through (takes ~25 mins).\n7. Serve hot litti with chokha and pickled green chilies. Dip every bite in extra ghee.",
                "cookware" to "Oven or tawa (slow roasting)",
                "cookwareSubstitutes" to "Oven → Tawa on very low flame (takes longer)",
                "imageUrl" to "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400"
            ),

            // ══════════════════ INDO-CHINESE (3) ══════════════════

            mapOf(
                "name" to "Veg Hakka Noodles Kit",
                "description" to "Street-style Indo-Chinese noodles with julienned vegetables in soy-ginger-garlic sauce. Classic takeout taste at home.",
                "price" to 160.0, "category" to "chinese", "prepTime" to "15 mins",
                "rating" to 4.6, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-boiled Hakka Noodles (300g)\nJulienned Veggies (carrot, cabbage, capsicum, spring onion — 250g)\nGarlic (4 cloves, minced)\nGinger (½ inch, grated)\nHakka Noodles Sauce Pack (soy, vinegar, chili sauce mix)\nOil Pack (2 tbsp)\nSalt + White Pepper Pack",
                "recipeSteps" to "1. Heat oil in a wok or wide pan on HIGH heat. High heat is essential for wok hei.\n2. Add garlic + ginger, toss 30 seconds — do not burn.\n3. Add all vegetables at once. Toss on high heat 2-3 minutes — vegetables should stay slightly crunchy.\n4. Pour in the sauce pack. Toss everything together 1 minute.\n5. Add pre-boiled noodles. Toss vigorously to coat every strand.\n6. Add salt + white pepper to taste.\n7. Cook 2 more minutes on high, tossing constantly.\n8. Garnish with spring onion greens. Serve immediately.",
                "cookware" to "Wok or large wide pan",
                "cookwareSubstitutes" to "Wok → Largest flat pan you have — high heat is more important than pan shape",
                "imageUrl" to "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400"
            ),

            mapOf(
                "name" to "Chilli Paneer Kit",
                "description" to "Crispy battered paneer cubes tossed in a spicy Indo-Chinese sauce with onion and capsicum. Best Indo-Chinese dish.",
                "price" to 240.0, "category" to "chinese", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Paneer Cubes (200g)\nBesan / Cornflour Batter Mix (100g, just add water)\nOnion (1, cubed)\nCapsicum Mix (red + green, 1 each, cubed)\nGarlic (4 cloves, minced)\nGreen Chili (2, slit)\nChilli Paneer Sauce Pack (soy + chili + vinegar + cornflour mix)\nOil Pack (150ml for frying + 2 tbsp for stir-fry)\nSpring Onion (garnish)",
                "recipeSteps" to "1. Mix batter with water to a thick coating consistency. Coat paneer cubes.\n2. Deep fry paneer in hot oil until golden and crispy. Drain on paper. Keep aside.\n3. In a separate wok, heat 2 tbsp oil on HIGH heat.\n4. Add garlic + green chili, toss 20 seconds.\n5. Add onion cubes, toss 1 minute — keep them slightly crunchy.\n6. Add capsicum cubes, toss 1 minute.\n7. Pour in the sauce pack. Toss everything together.\n8. Add ¼ cup water if sauce is too thick.\n9. Add fried paneer cubes. Toss to coat evenly. Cook 1 minute.\n10. Garnish with spring onion. Serve as starter or with fried rice.",
                "cookware" to "Deep kadhai for frying, Wok for stir-fry",
                "cookwareSubstitutes" to "Wok → Wide heavy pan",
                "imageUrl" to "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400"
            ),

            mapOf(
                "name" to "Veg Fried Rice Kit",
                "description" to "Restaurant-style Indian fried rice with spring onions, eggs (optional), and Indo-Chinese sauces. Perfect partner to Chilli Paneer.",
                "price" to 150.0, "category" to "chinese", "prepTime" to "15 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Pre-cooked Long-grain Rice (300g, day-old or cooled)\nJulienned Mixed Vegetables (carrot, peas, cabbage, beans — 200g)\nSpring Onion Bunch\nGarlic (3 cloves, minced)\nFried Rice Sauce Pack (soy + vinegar + chili sauce)\nOil Pack (2 tbsp)\nSalt + White Pepper Pack",
                "recipeSteps" to "1. Heat oil in wok on HIGH heat — very high heat is crucial.\n2. Add garlic, toss 15 seconds.\n3. Add all vegetables, toss vigorously 2 minutes.\n4. Push vegetables to one side. (Optional: crack 1-2 eggs and scramble on the other side.)\n5. Add the cooled rice. Break any clumps.\n6. Pour sauce pack over rice. Toss everything together vigorously.\n7. Season with salt + white pepper.\n8. Add spring onion greens, toss 30 seconds.\n9. Serve immediately — fried rice is best piping hot.",
                "cookware" to "Wok or large wide pan",
                "cookwareSubstitutes" to "Wok → Largest flat pan, HIGH heat throughout",
                "imageUrl" to "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400"
            ),

            // ══════════════════ STREET FOOD (4) ══════════════════

            mapOf(
                "name" to "Classic Samosa Kit (6 pcs)",
                "description" to "Ready-to-fold samosa pastry sheets with spiced potato-pea filling and two chutneys. The ultimate Indian snack.",
                "price" to 120.0, "category" to "street_food", "prepTime" to "20 mins",
                "rating" to 4.7, "available" to true, "popular" to true, "serves" to 2,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Ready Samosa Pastry Sheets (12 strips)\nSpiced Potato-Pea Filling (300g, ready to use)\nFlour-Water Paste (for sealing edges)\nOil Pack (150ml for frying)\nGreen Chutney Sachet\nDate-Tamarind Chutney Sachet",
                "recipeSteps" to "1. Take a pastry strip, fold one end diagonally to form a cone shape.\n2. Seal the overlapping edge with flour-water paste.\n3. Fill the cone with 2 tbsp of potato-pea filling. Do not overfill.\n4. Fold the top edge over and seal with paste to form a triangle.\n5. Repeat for all 6 samosas.\n6. Heat oil in kadhai on medium heat (NOT high — low heat makes them crispy).\n7. Test: drop a tiny piece of dough — it should rise slowly.\n8. Fry samosas 4-5 minutes per side on medium-low until golden brown.\n9. Drain on paper towel.\n10. Serve with green chutney and tamarind chutney.",
                "cookware" to "Kadhai or deep pan",
                "cookwareSubstitutes" to "Kadhai → Any deep pot works for frying",
                "imageUrl" to "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400"
            ),

            mapOf(
                "name" to "Pani Puri Kit",
                "description" to "Ready-to-eat crispy puris with spiced mashed filling and two flavors of pani (spicy mint + sweet tamarind). Party favorite.",
                "price" to 100.0, "category" to "street_food", "prepTime" to "10 mins",
                "rating" to 4.8, "available" to true, "popular" to true, "serves" to 3,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Crispy Puris (30 pieces, pre-fried and packed)\nSpicy Mint Pani Concentrate (makes 600ml — just add chilled water)\nSweet Tamarind Pani Sachet (200ml, ready)\nSpiced Filling Mix (boiled potato + sprouted moong + masala, 300g)\nBlack Salt + Chaat Masala Pack",
                "recipeSteps" to "1. PANI: Mix mint pani concentrate with 600ml chilled water. Taste and adjust. It should be spicy, tangy, and refreshing.\n2. Add black salt from spice pack to pani.\n3. FILLING: The filling is ready — taste and adjust salt and chaat masala.\n4. ASSEMBLY (do this just before eating):\n5. Make a small hole on top of a puri with your thumb.\n6. Fill with 1 tsp of filling mixture.\n7. Dip entirely in spicy pani or pour pani into the puri.\n8. Pop the whole thing into your mouth in one bite.\n9. Alternatively, serve sweet tamarind pani on the side for variation.\n10. Eat immediately — do not let puris sit or they will go soggy.",
                "cookware" to "Serving bowls (no cooking required)",
                "cookwareSubstitutes" to "No cooking needed — this is an assembly job!",
                "imageUrl" to "https://images.unsplash.com/photo-1613915617609-c2b1b86a80f6?w=400"
            ),

            mapOf(
                "name" to "Aloo Tikki Chaat Kit",
                "description" to "Crispy pan-fried potato patties topped with chole, chutneys, yogurt, and sev. North Indian chaat at its finest.",
                "price" to 130.0, "category" to "street_food", "prepTime" to "15 mins",
                "rating" to 4.6, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Spiced Potato Patties (6, pre-shaped)\nSmall Chole / Chickpeas (150g, cooked and spiced)\nWhisked Yogurt Pack (150ml)\nGreen Chutney Sachet\nTamarind Chutney Sachet\nFine Sev / Namkeen Pack\nChaat Masala + Red Chili Pack\nOil Pack (2 tbsp for frying)\nOnion + Coriander (garnish)",
                "recipeSteps" to "1. Heat oil in a flat pan on medium. Press it should be a thin layer.\n2. Place potato patties on pan. Cook 3-4 minutes per side on medium until crispy and golden. Do not touch while cooking.\n3. Warm the chole in a small pot (2 minutes).\n4. ASSEMBLY:\n5. Place 2-3 hot tikkis on a plate.\n6. Spoon warm chole over them.\n7. Drizzle whisked yogurt.\n8. Drizzle green chutney + tamarind chutney.\n9. Sprinkle chaat masala + red chili powder.\n10. Top with a generous handful of sev and chopped onion + coriander.\n11. Eat immediately.",
                "cookware" to "Flat pan or tawa, Small pot for chole",
                "cookwareSubstitutes" to "Tawa → Any flat non-stick pan",
                "imageUrl" to "https://images.unsplash.com/photo-1527736947477-2790e28f3443?w=400"
            ),

            mapOf(
                "name" to "Bhel Puri Kit",
                "description" to "Mumbai-style puffed rice chaat with chutneys, sev, onion, and tomato. The ultimate no-cook chaat — ready in 5 minutes.",
                "price" to 80.0, "category" to "street_food", "prepTime" to "5 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 2,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Puffed Rice (Murmura) Pack (200g)\nSev Pack (coarse)\nPuri Pieces (small, crispy, 20 pieces)\nBoiled Potato (1, diced small)\nOnion (1, finely chopped)\nTomato (1, finely chopped)\nGreen Chutney Sachet\nTamarind Chutney Sachet\nChaat Masala + Red Chili Pack\nFresh Coriander",
                "recipeSteps" to "1. In a large bowl, combine puffed rice, boiled potato, onion, and tomato.\n2. Add chaat masala and red chili powder. Mix well.\n3. Add green chutney — start with half the sachet, adjust to your spice level.\n4. Add tamarind chutney — adds sweetness and tang.\n5. Add sev and crushed puris. Toss everything together.\n6. Taste and adjust chutneys, chaat masala.\n7. Garnish with fresh coriander and more sev on top.\n8. CRITICAL: Eat within 2 minutes of assembling — bhel gets soggy fast!\n9. For parties: keep all components separate and mix right before serving each portion.",
                "cookware" to "Large mixing bowl (no cooking required)",
                "cookwareSubstitutes" to "No cooking needed — pure assembly",
                "imageUrl" to "https://images.unsplash.com/photo-1613915617609-c2b1b86a80f6?w=400"
            ),

            // ══════════════════ DESSERTS (3) ══════════════════

            mapOf(
                "name" to "Gulab Jamun Kit (8 pcs)",
                "description" to "Ready-to-fry khoya dough balls in a rose-cardamom sugar syrup. Soft gulab jamuns in 20 minutes — no guesswork.",
                "price" to 130.0, "category" to "desserts", "prepTime" to "20 mins",
                "rating" to 4.9, "available" to true, "popular" to true, "serves" to 4,
                "isVeg" to true, "difficulty" to "Medium",
                "ingredients" to "Khoya Dough Balls (8, ready to fry)\nSugar Syrup Pack (pre-mixed with rose water + cardamom — just heat)\nOil Pack (200ml for frying)\nSaffron Strands (garnish)",
                "recipeSteps" to "1. SYRUP: Pour syrup pack into a saucepan. Heat on medium, stirring until it just comes to a boil. Reduce to LOWEST heat to keep warm.\n2. OIL: Heat oil in kadhai on MEDIUM-LOW heat. CRITICAL: hot oil will harden outside before inside cooks.\n3. Test oil by dropping a tiny piece of dough. It should slowly rise to the surface.\n4. Gently slide in 4 dough balls at a time. Do NOT crowd them.\n5. Stir gently and continuously — this ensures even browning.\n6. Fry 6-8 minutes until deep brown all over.\n7. Immediately transfer hot jamuns into the warm syrup.\n8. Let them soak for minimum 20 minutes (longer = softer and sweeter).\n9. Garnish with saffron strands. Serve warm.",
                "cookware" to "Kadhai, Small saucepan",
                "cookwareSubstitutes" to "Kadhai → Any deep pot | Saucepan → Any pot for the syrup",
                "imageUrl" to "https://images.unsplash.com/photo-1666365434-b64ffb2fd45f?w=400"
            ),

            mapOf(
                "name" to "Bengali Mishti Doi Kit",
                "description" to "Thick sweetened yogurt set with caramelized sugar in clay pots. Bengal's legendary dessert — just mix and chill.",
                "price" to 100.0, "category" to "desserts", "prepTime" to "15 mins",
                "rating" to 4.5, "available" to true, "popular" to false, "serves" to 3,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Full-fat Milk Pack (500ml)\nSugar (60g for caramelizing)\nCurd Starter Sachet (2 tbsp yogurt culture)\nCardamom Powder Sachet\nSaffron Sachet (optional garnish)",
                "recipeSteps" to "1. In a heavy-bottom pot, add sugar. Heat on medium — do NOT stir. Let it melt and turn deep amber (caramel colour).\n2. Immediately pour warm milk into the caramel (it will bubble violently — stand back).\n3. Stir vigorously until caramel dissolves into the milk.\n4. Add cardamom powder. Cool the mixture to lukewarm (you can touch it comfortably).\n5. Add curd starter sachet. Mix gently.\n6. Pour into small clay pots or steel bowls.\n7. Cover with a cloth. Keep in a warm place for 6-8 hours to set.\n8. Once set, refrigerate for at least 2 hours — it firms up beautifully when cold.\n9. Garnish with saffron strands. Serve chilled.",
                "cookware" to "Heavy-bottom pot, Clay pots or steel bowls for setting",
                "cookwareSubstitutes" to "Clay pots → Any small bowls or ramekins",
                "imageUrl" to "https://images.unsplash.com/photo-1695158568573-3f40e0fb0ef8?w=400"
            ),

            mapOf(
                "name" to "Gajar Ka Halwa Kit",
                "description" to "Rich carrot pudding slow-cooked in milk with ghee, sugar, and cardamom. Classic winter Indian dessert.",
                "price" to 150.0, "category" to "desserts", "prepTime" to "30 mins",
                "rating" to 4.7, "available" to true, "popular" to false, "serves" to 3,
                "isVeg" to true, "difficulty" to "Easy",
                "ingredients" to "Grated Carrots (400g, pre-grated)\nFull-fat Milk (300ml)\nSugar Pack (80g)\nGhee Portion (2 tbsp)\nCardamom Powder Sachet\nCashews + Raisins + Almonds Garnish Pack",
                "recipeSteps" to "1. Heat ghee in a heavy-bottom kadhai on medium.\n2. Add grated carrots. Cook stirring often for 5 minutes until they lose some moisture.\n3. Add milk. Stir and cook on medium, stirring every 2-3 minutes.\n4. Keep cooking 15-18 minutes — milk will absorb into carrots and mixture will thicken.\n5. Add sugar. Stir continuously as sugar dissolves and mixture comes together.\n6. Cook 5 more minutes until halwa leaves sides of pan and ghee separates.\n7. Add cardamom powder. Mix well.\n8. In a separate small pan, fry cashews in ½ tsp ghee until golden. Add raisins (they puff up). Add almonds.\n9. Garnish halwa with fried dry fruits.\n10. Serve warm or cold — both are delicious.",
                "cookware" to "Heavy-bottom kadhai or non-stick pan",
                "cookwareSubstitutes" to "Kadhai → Any deep non-stick pan — must be heavy-bottomed to prevent burning",
                "imageUrl" to "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=400"
            )

        ) // end of items list

        val col = db.collection("menu")
        for (item in items) {
            col.add(item)
                .addOnSuccessListener { Log.d("Seeder", "✅ Added: ${item["name"]}") }
                .addOnFailureListener { e -> Log.e("Seeder", "❌ Failed: ${item["name"]} — ${e.message}") }
        }
        Log.d("Seeder", "🌱 Seeding ${items.size} items started!")
    }
}