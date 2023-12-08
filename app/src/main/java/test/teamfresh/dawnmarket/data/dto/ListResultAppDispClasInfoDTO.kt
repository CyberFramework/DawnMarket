package test.teamfresh.dawnmarket.data.dto

data class ListResultAppDispClasInfoDTO(
    val `data`: List<Data> = listOf(),
    val message: String?
) {
    data class Data(
        val dispClasSeq: Int? = 0,
        val dispClasNm: String? = "",
        val dispClasImgPath: String? = "",
        val dispClasCd: String? = ""
    )
}