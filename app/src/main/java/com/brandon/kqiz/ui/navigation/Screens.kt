package com.brandon.kqiz.ui.navigation

const val BOOK_ID_ARG_KEY = "bookId"

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home")
}