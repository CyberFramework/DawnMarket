package test.teamfresh.dawnmarket.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import test.teamfresh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import test.teamfresh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import test.teamfresh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import test.teamfresh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO

interface RetrofitNetworkApi {
    @GET("app/disp-clas-infos/disp-clas-nm")
    suspend fun getDispClasInfoList(): Response<ListResultAppDispClasInfoDTO>

    @GET("app/main-infos/quick-menu")
    suspend fun getMainQuickMenuList(): Response<ListResultAppMainQuickMenuDTO>

    @GET("app/disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas-infos")
    suspend fun getSubDispClasInfoList(
        @Path("dispClasSeq") dispClasSeq: Int? = 0,
    ): Response<SingleResultAppDispClasInfoBySubDispClasInfoDTO>

    @GET("app/disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas/{subDispClasSeq}/goods-infos")
    suspend fun getGoodsInfo(
        @Path("dispClasSeq") dispClasSeq: Int = 0,
        @Path("subDispClasSeq") subDispClasSeq: Int = 0,
        @Query("page") pageRequest: Int,
        @Query("size") size: Int = 20,
        @Query("searchValue") searchValue: String
    ): Response<PageResponseAppGoodsInfoDTO>
}