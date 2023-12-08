package test.teamfresh.dawnmarket.data.paging

import androidx.compose.runtime.mutableIntStateOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.runBlocking
import test.teamfresh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import test.teamfresh.dawnmarket.data.model.SearchValue
import test.teamfresh.dawnmarket.data.repo.ApiResult
import test.teamfresh.dawnmarket.data.repo.ProductRepository

class GoodsPagingSource(
    private val searchValue: SearchValue,
    private val dispClasSeq: Int,
    private val subDispClasSeq: Int,
    private val repo: ProductRepository
) : PagingSource<Int, PageResponseAppGoodsInfoDTO.Data>() {

    private val _totalItems = mutableIntStateOf(0)
    val totalItems get() = _totalItems.intValue

    override fun getRefreshKey(state: PagingState<Int, PageResponseAppGoodsInfoDTO.Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageResponseAppGoodsInfoDTO.Data> {
        return try {
            val page = params.key ?: 0
            var list = emptyList<PageResponseAppGoodsInfoDTO.Data>()
            var totalPages = 0

            val response = repo.getGoodsInfo(
                dispClasSeq = dispClasSeq,
                    subDispClasSeq = subDispClasSeq,
                    pageRequest = page,
                    searchValue = searchValue.value,
            )

            runBlocking {
                response.collect {
                    when (it.status) {
                        ApiResult.Status.SUCCESS -> {
                            list = it.data?.data ?: emptyList()
                            totalPages = it.data?.pagination?.totalPage ?: 0
                            _totalItems.intValue = it.data?.pagination?.totalElements ?: 0
                        }
                        else -> {
                            list = emptyList()
                        }
                    }
                }
            }

            LoadResult.Page(
                data = list,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (page == totalPages + 1) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}