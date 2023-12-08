package test.teamfresh.dawnmarket.ui.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import test.teamfresh.dawnmarket.R
import test.teamfresh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import test.teamfresh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import test.teamfresh.dawnmarket.data.model.SearchValue
import test.teamfresh.dawnmarket.ui.component.DefaultIconButton
import test.teamfresh.dawnmarket.ui.component.NonlazyGrid
import test.teamfresh.dawnmarket.ui.theme.DawnMarketTheme
import test.teamfresh.dawnmarket.ui.theme.Gray100
import test.teamfresh.dawnmarket.ui.theme.Gray200
import test.teamfresh.dawnmarket.ui.theme.Gray50
import test.teamfresh.dawnmarket.ui.theme.Gray900
import test.teamfresh.dawnmarket.utils.toastAddCart

@Composable
fun SubcategoryRoute(
    subcategoryViewModel: SubcategoryViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val subcategoryListUiState by subcategoryViewModel.subcategoryListUiStateState.collectAsStateWithLifecycle()
    val optionsUiState by subcategoryViewModel.optionsUiState.collectAsStateWithLifecycle()
    val goods = subcategoryViewModel.goodsPager.collectAsLazyPagingItems()

    SubcategoryScreen(
        subcategoryListUiState = subcategoryListUiState,
        optionsUiState = optionsUiState,
        goods = goods,
        viewModel = subcategoryViewModel,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubcategoryScreen(
    subcategoryListUiState: SubcategoryListUiState,
    optionsUiState: OptionsUiState,
    goods: LazyPagingItems<PageResponseAppGoodsInfoDTO.Data>,
    viewModel: SubcategoryViewModel,
    onBackClick: () -> Unit,
) {
    Column {
        SubcategoryTopBar(
            title = optionsUiState.title,
            onBackClick = onBackClick
        )

        LazyColumn {
            item {
                SubcategoryGrid(
                    itemList = subcategoryListUiState.list,
                    selected = optionsUiState.dispClasSeq
                ) { id, name ->
                    viewModel.updateTitle(name)
                    viewModel.updateSubcategory(id)
                    viewModel.invalidateDataSource()
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            stickyHeader {
                SearchResultHeader(
                    totalResult = if (goods.loadState.refresh is LoadState.NotLoading) viewModel.getItemCount() else 0,
                    sortBy = optionsUiState.searchValue,
                    onSortClick = {
                        viewModel.updateSearchValue(it)
                        viewModel.invalidateDataSource()
                    }
                )
            }

            items(goods.itemCount) {
                if (it % 2 == 1) {
                    GoodsItemRow(item1 = goods[it - 1] ?: return@items, item2 = goods[it] ?: return@items)
                } else if (it == goods.itemCount - 1) {
                    GoodsItemRow(item1 = goods[it] ?: return@items, item2 = null)
                }
            }
        }
    }
}

@Composable
private fun SubcategoryTopBar(title: String, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = DawnMarketTheme.typography.font15M,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultIconButton(onClick = onBackClick, vectorResId = R.drawable.ic_chevron_left)
            Spacer(modifier = Modifier.weight(1f))
            DefaultIconButton(onClick = { /*TODO*/ }, vectorResId = R.drawable.ic_search)
            Spacer(modifier = Modifier.width(14.dp))
            DefaultIconButton(onClick = { /*TODO*/ }, vectorResId = R.drawable.ic_cart_gray)
        }
    }
}

@Composable
private fun SubcategoryGrid(
    itemList: List<SingleResultAppDispClasInfoBySubDispClasInfoDTO.Data.AppSubDispClasInfo>,
    selected: Int,
    onItemClick: (Int, String) -> Unit
) {
    NonlazyGrid(
        modifier = Modifier.background(Color.White),
        columns = 3,
        itemCount = itemList.size,
        borderWidth = 1.dp,
        borderColor = Color(0xFFF3F4F6)
    ) {

        val code = itemList[it].dispClasSeq ?: 0
        val name = itemList[it].subDispClasNm ?: ""

        SubcategoryItem(
            text = name,
            isSelected = code == selected,
            onItemClick = {
                if (code == 0) {
                    onItemClick(code, itemList[it].dispClasNmstring ?: "")
                } else {
                    onItemClick(code, name)
                }
            }
        )
    }
}

@Composable
private fun SubcategoryItem(text: String, isSelected: Boolean, onItemClick: () -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onItemClick
            )
            .padding(10.dp),
        text = text,
        style = DawnMarketTheme.typography.font14,
        color = if (isSelected) MaterialTheme.colorScheme.onBackground else Color(0xFF737B8C)
    )
}

@Composable
private fun SearchResultHeader(
    totalResult: Int = 0,
    sortBy: SearchValue,
    onSortClick: (SearchValue) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            // sticky header 클릭 이벤트 버그 방지
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {  }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = totalResult.toString(),
                style = DawnMarketTheme.typography.font13M,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(id = R.string.search_result_count),
                style = DawnMarketTheme.typography.font13,
                color = Color(0xFF1C1B1F)
            )
        }

        Divider(color = Gray50, modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            SearchValue.values().forEach {
                SortButton(
                    text = it.value,
                    isSelected = sortBy == it,
                    onClick = { onSortClick(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SortButton(text: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            } ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(3.dp)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.secondary else Color(0xFFCACFDB), CircleShape
                )
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = text,
            style = DawnMarketTheme.typography.font13M,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
private fun GoodsList(result: LazyPagingItems<PageResponseAppGoodsInfoDTO.Data>) {
    when (result.loadState.refresh) {
        LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.failed_to_load_goods))
            }
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(result.itemCount) {
                    GoodsItem(item = result[it] ?: return@items) {

                    }
                }
            }
        }
    }
}

@Composable
private fun GoodsItemRow(item1: PageResponseAppGoodsInfoDTO.Data, item2: PageResponseAppGoodsInfoDTO.Data?) {
    val mContext = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (item2 != null) {
            GoodsItem(modifier = Modifier.weight(1f), item = item1) { toastAddCart(item1.goodsGroupNm ?: "", mContext) }
            Spacer(modifier = Modifier.width(16.dp))
            GoodsItem(modifier = Modifier.weight(1f), item = item2) { toastAddCart(item2.goodsGroupNm ?: "", mContext) }
        } else {
            GoodsItem(modifier = Modifier.weight(1f), item = item1) { toastAddCart(item1.goodsGroupNm ?: "", mContext) }
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(8.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun GoodsItem(
    modifier: Modifier = Modifier,
    item: PageResponseAppGoodsInfoDTO.Data,
    onAddCart: () -> Unit
) {
    if (item.slePrice == null || item.dcPrice == null) return

    Column(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            GlideImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp)),
                model = stringResource(id = R.string.image_index_url) + item.imgPath,
                contentDescription = null
            )

            CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false){
                IconButton(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Gray100, CircleShape),
                    onClick = onAddCart,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cart_outlined),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // 일시품절 (사용 X)
            /*
            if (item.goodsStat == GoodsStat.OUT_OF_STOCK.value) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x990E0E0E))
                        .padding(10.dp),
                    text = GoodsStat.OUT_OF_STOCK.value,
                    style = DawnMarketTheme.typography.font12M,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            } else {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Gray100, CircleShape),
                    onClick = onAddCart,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cart_outlined),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
*/
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 상품명
        Text(
            text = item.goodsGroupNm ?: "",
            style = DawnMarketTheme.typography.font15,
            color = Gray900
        )

        // 할인 전 가격
        if (item.slePrice != item.dcPrice) {
            Row {
                Text(
                    text = item.slePrice.toString(),
                    style = DawnMarketTheme.typography.font13M,
                    color = Gray200,
                    textDecoration = TextDecoration.LineThrough
                )

                Text(
                    text = stringResource(id = R.string.won),
                    style = DawnMarketTheme.typography.font13M,
                    color = Gray200,
                )
            }
        }

        // 가격
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.slePrice != item.dcPrice) {
                val percentage = ((item.slePrice - item.dcPrice).toDouble() / item.slePrice) * 100

                Text(
                    text = stringResource(id = R.string.percentage, percentage.toInt()),
                    style = DawnMarketTheme.typography.font16B,
                    color = Color(0xFFEB4B49)
                    )
            }

            Text(
                text = item.dcPrice.toString(),
                style = DawnMarketTheme.typography.font16B,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(id = R.string.won),
                style = DawnMarketTheme.typography.font15M,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 옵션
        if (!item.goodsGroupOptnValue.isNullOrBlank() && !item.goodsGroupOptnNm.isNullOrBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .background(Gray50)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    text = item.goodsGroupOptnNm,
                    style = DawnMarketTheme.typography.font12M,
                    color = Color(0xFF737B8C)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = item.goodsGroupOptnValue,
                    style = DawnMarketTheme.typography.font13M,
                    color = Gray900
                )
            }
        }
    }
}