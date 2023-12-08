package test.teamfresh.dawnmarket.navigation

import test.teamfresh.dawnmarket.R

enum class RootDestination(
    val iconId: Int,
    val iconTextId: Int
) {
    CATEGORY(
        iconId = R.drawable.ic_hamburger,
        iconTextId = R.string.category
    ),
    BOOKMARK(
        iconId = R.drawable.ic_star_outlined,
        iconTextId = R.string.bookmark
    ),
    HOME(
        iconId = R.drawable.ic_home_outlined,
        iconTextId = R.string.home
    ),
    CART(
        iconId = R.drawable.ic_cart_outlined,
        iconTextId = R.string.cart
    ),
    MY_INFO(
        iconId = R.drawable.ic_profile_outlined,
        iconTextId = R.string.my_info
    ),

}