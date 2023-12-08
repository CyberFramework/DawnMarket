package test.teamfresh.dawnmarket.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import test.teamfresh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import test.teamfresh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import test.teamfresh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import test.teamfresh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import test.teamfresh.dawnmarket.data.remote.RetrofitNetworkApi
import javax.inject.Inject

class DefaultProductRepository @Inject constructor(
    private val remoteDataSource: RetrofitNetworkApi
) : ProductRepository {

    override fun getDispClasInfo(): Flow<ApiResult<ListResultAppDispClasInfoDTO>> {
        return flow {
            emit(ApiResult.loading())
            val result = getResponse(remoteDataSource.getDispClasInfoList())
            emit(result)
        }
    }

    override fun getMainQuickMenu(): Flow<ApiResult<ListResultAppMainQuickMenuDTO>> {
        return flow {
            emit(ApiResult.loading())
            val result = getResponse(remoteDataSource.getMainQuickMenuList())
            emit(result)
        }
    }

    override fun getSubDispClasInfoList(dispClasSeq: Int): Flow<ApiResult<SingleResultAppDispClasInfoBySubDispClasInfoDTO>> {
        return flow {
            emit(ApiResult.loading())
            val result = getResponse(remoteDataSource.getSubDispClasInfoList(dispClasSeq))
            emit(result)
        }
    }

    override fun getGoodsInfo(
        dispClasSeq: Int,
        subDispClasSeq: Int,
        pageRequest: Int,
        searchValue: String
    ): Flow<ApiResult<PageResponseAppGoodsInfoDTO>> {
        return flow {
            emit(ApiResult.loading())

            val result = getResponse(
                remoteDataSource.getGoodsInfo(
                    dispClasSeq = dispClasSeq,
                    subDispClasSeq = subDispClasSeq,
                    pageRequest = pageRequest,
                    searchValue = searchValue
                )
            )

            emit(result)
        }
    }
}