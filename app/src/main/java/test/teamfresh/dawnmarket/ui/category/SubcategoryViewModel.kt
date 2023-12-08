package test.teamfresh.dawnmarket.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import test.teamfresh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import test.teamfresh.dawnmarket.data.model.SearchValue
import test.teamfresh.dawnmarket.data.paging.GoodsPagingSource
import test.teamfresh.dawnmarket.data.repo.ApiResult
import test.teamfresh.dawnmarket.data.repo.ProductRepository
import javax.inject.Inject

data class SubcategoryListUiState(
    val list: List<SingleResultAppDispClasInfoBySubDispClasInfoDTO.Data.AppSubDispClasInfo> = listOf(SingleResultAppDispClasInfoBySubDispClasInfoDTO.Data.AppSubDispClasInfo()),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class OptionsUiState(
    val dispClasSeq: Int = 0,
    val searchValue: SearchValue = SearchValue.RECOMMENDED,
    val title: String = "",
)

@HiltViewModel
class SubcategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ViewModel() {
    private var prntsDispClasSeq = savedStateHandle[CATEGORY_ID] ?: 0
    private var prntsDispClasNm = savedStateHandle[CATEGORY_NAME] ?: ""

    private val _optionsUiState = MutableStateFlow(OptionsUiState(title = prntsDispClasNm))
    val optionsUiState = _optionsUiState.asStateFlow()

    val subcategoryListUiStateState = productRepository.getSubDispClasInfoList(prntsDispClasSeq).map {
        when (it.status) {
            ApiResult.Status.SUCCESS -> {
                val addedList = it.data?.data?.appSubDispClasInfoList?.run {
                    toMutableList().apply {
                        add(0, first().copy(
                            dispClasSeq = 0,
                            subDispClasNm = "상품 전체",
                            dispClasNmstring = prntsDispClasNm
                        ))
                    }.toList()
                }

                SubcategoryListUiState(list = addedList ?: emptyList())
            }
            ApiResult.Status.API_ERROR -> SubcategoryListUiState(isError = true)
            ApiResult.Status.NETWORK_ERROR -> SubcategoryListUiState(isError = true)
            ApiResult.Status.LOADING -> SubcategoryListUiState(isLoading = true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SubcategoryListUiState(isLoading = true)
    )

    private lateinit var pagingSource : GoodsPagingSource

    val goodsPager = Pager(PagingConfig(pageSize = 20)) {
        GoodsPagingSource(
            dispClasSeq = prntsDispClasSeq,
            subDispClasSeq = _optionsUiState.value.dispClasSeq,
            searchValue = _optionsUiState.value.searchValue,
            repo = productRepository
        ).also { pagingSource = it }
    }.flow.cachedIn(viewModelScope)

    fun updateSubcategory(dispClasSeq: Int) {
        _optionsUiState.update { it.copy(dispClasSeq = dispClasSeq) }
    }

    fun updateSearchValue(value: SearchValue) {
        _optionsUiState.update { it.copy(searchValue = value) }
    }

    fun updateTitle(title: String) {
        _optionsUiState.update { it.copy(title = title) }
    }

    fun getItemCount() : Int {
        return this.pagingSource.totalItems
    }

    fun invalidateDataSource() {
        pagingSource.invalidate()
    }
}