package com.example.pricecomparisonapp.data.model

object HardcodedData {
    val cities = listOf("Sarajevo", "Tuzla", "Mostar", "Banja Luka", "Zenica")

    val categories = listOf("Dairy", "Bakery", "Beverages", "Snacks", "Household")

    val products = listOf(
        Product(1, "Milk 1L", "Dairy", 2.30, "Bingo", "Sarajevo"),
        Product(2, "Bread", "Bakery", 1.70, "Konzum", "Sarajevo"),
        Product(3, "Coffee 500g", "Beverages", 8.90, "Mercator", "Tuzla"),
        Product(4, "Cheese 250g", "Dairy", 4.40, "Robot", "Mostar"),
        Product(5, "Chocolate", "Snacks", 2.10, "Bingo", "Zenica"),
        Product(6, "Dish Soap", "Household", 3.95, "Konzum", "Banja Luka")
    )
}
