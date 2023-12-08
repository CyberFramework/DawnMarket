package test.teamfresh.dawnmarket.data.repo

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import test.teamfresh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import test.teamfresh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import test.teamfresh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import test.teamfresh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO

interface ProductRepository {
    fun getDispClasInfo(): Flow<ApiResult<ListResultAppDispClasInfoDTO>>
    fun getMainQuickMenu(): Flow<ApiResult<ListResultAppMainQuickMenuDTO>>
    fun getSubDispClasInfoList(dispClasSeq: Int): Flow<ApiResult<SingleResultAppDispClasInfoBySubDispClasInfoDTO>>
    fun getGoodsInfo(
        dispClasSeq: Int,
        subDispClasSeq: Int,
        pageRequest: Int,
        searchValue: String,
    ): Flow<ApiResult<PageResponseAppGoodsInfoDTO>>

    suspend fun <T> getResponse(response: Response<T>): ApiResult<T> {
        return try {
            if (response.isSuccessful) {
                return ApiResult.success(response.code(), response.body())
            } else {
                ApiResult.error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.error(e)
        }
    }
}