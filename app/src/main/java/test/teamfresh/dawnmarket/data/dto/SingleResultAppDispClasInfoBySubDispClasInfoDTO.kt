package test.teamfresh.dawnmarket.data.dto

data class SingleResultAppDispClasInfoBySubDispClasInfoDTO(
    val `data`: Data = Data(),
    val message: String?
) {
    data class Data(
        val dispClasNm: String = "",
        val appSubDispClasInfoList: List<AppSubDispClasInfo> = listOf()
    ) {
        data class AppSubDispClasInfo(
            val dispClasCd: String? = "", // 전시분류코드
            val dispClasLvl: String? = "", // 전시분류레벨 Enum:[대분류, 중분류]
            val dispClasSeq: Int? = 0, // 전시분류일련번호
            val prntsDispClasSeq: Int? = 0, // 부모전시분류일련번호
            val subDispClasNm: String? = "", // 전시분류명 Enum:[배추,상추,깻잎, 양파,대파,마늘]}]
            val dispClasNmstring: String? = "" // 전시카테고리 대분류명
        )
    }
}