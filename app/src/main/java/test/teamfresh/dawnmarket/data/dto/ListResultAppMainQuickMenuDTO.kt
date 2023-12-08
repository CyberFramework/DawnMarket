package test.teamfresh.dawnmarket.data.dto

data class ListResultAppMainQuickMenuDTO(
    val `data`: List<Data> = listOf(),
    val message: String?,
) {
    data class Data(
        val quickMenuSeq: Int = 0,
        val quickMenuNm: String = "",
        val quickMenuImgPath: String = "",
        val quickMenuConcScrenNm: String = "",
        val quickMenuConcScrenIden: String? = "",
        val quickMenuMovScrenPath: String? = "",
    )
}