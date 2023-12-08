package test.teamfresh.dawnmarket.data.model

enum class SearchValue(val value: String) {
    RECOMMENDED("추천순"),
    SALES("판매량순"),
    LOW_PRICE("낮은가격순"),
    HIGH_PRICE("높은가격순")
}