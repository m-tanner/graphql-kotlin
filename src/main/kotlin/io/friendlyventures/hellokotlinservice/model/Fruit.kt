package io.friendlyventures.hellokotlinservice.model

sealed class Fruit(val color: String) {
    class Apple(private val variety: String) : Fruit(if (variety == "red delicious") "red" else "green")
    class Orange : Fruit("orange")
}
