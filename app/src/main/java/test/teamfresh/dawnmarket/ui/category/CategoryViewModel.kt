package test.teamfresh.dawnmarket.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import test.teamfresh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import test.teamfresh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import test.teamfresh.dawnmarket.data.repo.ApiResult
import test.teamfresh.dawnmarket.data.repo.ProductRepository
import javax.inject.Inject

data class DispClasListUiState(
    val list: List<ListResultAppDispClasInfoDTO.Data> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class QuickMenuListUiState(
    val list: List<ListResultAppMainQuickMenuDTO.Data> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var dispClasListUiState: StateFlow<DispClasListUiState>
    var quickMenuListUiState: StateFlow<QuickMenuListUiState>

    init { // Recompose 수행시 API 중복 호출 방지
        dispClasListUiState = productRepository.getDispClasInfo().map {
            when (it.status) {
                ApiResult.Status.SUCCESS -> DispClasListUiState(list = it.data?.data ?: emptyList())
                ApiResult.Status.API_ERROR -> DispClasListUiState(isError = true)
                ApiResult.Status.NETWORK_ERROR -> DispClasListUiState(isError = true)
                ApiResult.Status.LOADING -> DispClasListUiState(isLoading = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DispClasListUiState(isLoading = true)
        )

        quickMenuListUiState = productRepository.getMainQuickMenu().map {
            when (it.status) {
                ApiResult.Status.SUCCESS -> QuickMenuListUiState(list = it.data?.data ?: emptyList())
                ApiResult.Status.API_ERROR -> QuickMenuListUiState(isError = true)
                ApiResult.Status.NETWORK_ERROR -> QuickMenuListUiState(isError = true)
                ApiResult.Status.LOADING -> QuickMenuListUiState(isLoading = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuickMenuListUiState(isLoading = true)
        )
    }
}
