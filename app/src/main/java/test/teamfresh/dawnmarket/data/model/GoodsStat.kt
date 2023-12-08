package test.teamfresh.dawnmarket.data.model

enum class GoodsStat(val value: String) {
    ON_SALE("판매중"),
    OUT_OF_STOCK("일시품절"),
    SOLD_OUT("판매종료"),
}