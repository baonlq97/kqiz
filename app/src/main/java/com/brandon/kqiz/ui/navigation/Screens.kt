package com.brandon.kqiz.ui.navigation

const val TEST_INDEX_ARG_KEY = "testIndexArg"
const val TEST_ID_ARG_KEY = "testIdArg"

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home")
    data object KqizScreen : Screens("kqiz/{$TEST_ID_ARG_KEY}/{$TEST_INDEX_ARG_KEY}") {
        fun withTestIdAndIndex(id: String, index: String): String {
            return this.route
                .replace("{$TEST_ID_ARG_KEY}", id)
                .replace("{$TEST_INDEX_ARG_KEY}", index)
        }
    }
}