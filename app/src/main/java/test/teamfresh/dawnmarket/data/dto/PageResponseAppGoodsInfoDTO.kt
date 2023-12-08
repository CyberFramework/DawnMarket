package test.teamfresh.dawnmarket.data.dto

data class PageResponseAppGoodsInfoDTO(
    val `data`: List<Data> = listOf(),
    val pagination: Pagination = Pagination()
) {
    data class Data(
        val goodsNm: String? = "",
        val goodsCd: String? = "",
        val goodsStat: String? = "",
        val goodsDispYn: String? = "",
        val taxtnYn: String? = "",
        val mnrBuyYn: String? = "",
        val slePrice: Int? = 0,
        val dcPrice: Int? = 0,
        val splyPrice: Int? = 0,
        val goodsGroupCd: String? = "",
        val goodsGroupNm: String? = "",
        val delYn: String? = "",
        val goodsSuplmtImgSeq: Int? = 0,
        val imgPath: String? = "",
        val maxBuyPsblCntQty: Int? = 0,
        val minBuyPsblCntQty: Int? = 0,
        val goodsNrm: String = "",
        val goodsCntQty: Int? = 0,
        val goodsGroupOptnNm: String? = "",
        val goodsGroupOptnSeq: Int? = 0,
        val goodsGroupOptnValue: String? = ""
    )

    data class Pagination(
        val elementSizeOfPage: Int? = 0,
        val currentPage: Int? = 0,
        val totalPage: Int? = 0,
        val totalElements: Int? = 0
    )
}