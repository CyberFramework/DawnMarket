package test.teamfresh.dawnmarket.ui.category

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import test.teamfresh.dawnmarket.R
import test.teamfresh.dawnmarket.ui.component.DefaultIconButton
import test.teamfresh.dawnmarket.ui.component.NonlazyGrid
import test.teamfresh.dawnmarket.ui.theme.DawnMarketTheme
import test.teamfresh.dawnmarket.ui.theme.Gray50
import test.teamfresh.dawnmarket.ui.theme.Gray900
import test.teamfresh.dawnmarket.utils.toastDevelop

@Composable
internal fun CategoryRoute(
    onCategoryClick: (Int, String) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val dispClasListUiState by viewModel.dispClasListUiState.collectAsStateWithLifecycle()
    val quickMenuListUiState by viewModel.quickMenuListUiState.collectAsStateWithLifecycle()

    CategoryScreen(
        dispClasListUiState = dispClasListUiState,
        quickMenuListUiState = quickMenuListUiState,
        onCategoryClick = onCategoryClick
    )
}

@Composable
private fun CategoryScreen(
    dispClasListUiState: DispClasListUiState,
    quickMenuListUiState: QuickMenuListUiState,
    onCategoryClick: (Int, String) -> Unit,
    hasNotification: Boolean = true,
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        CategoryTopBar(hasNotification)

        NonlazyGrid(
            columns = 4,
            itemCount = dispClasListUiState.list.size,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalItemPadding = 13.dp,
            verticalItemPadding = 16.dp
        ) {
            CategoryItem(
                imageUrl = dispClasListUiState.list[it].dispClasImgPath ?: "",
                title = dispClasListUiState.list[it].dispClasNm ?: "",
                onClick = {
                    onCategoryClick(
                        dispClasListUiState.list[it].dispClasSeq ?: 0,
                        dispClasListUiState.list[it].dispClasNm ?: "",
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Divider(thickness = 8.dp, color = Gray50)

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
            text = stringResource(id = R.string.special_and_event),
            style = DawnMarketTheme.typography.font13B
        )

        NonlazyGrid(
            columns = 5,
            itemCount = quickMenuListUiState.list.size,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalItemPadding = 9.5.dp,
            verticalItemPadding = 16.dp
        ) {
            QuickMenuItem(
                imageUrl = quickMenuListUiState.list[it].quickMenuImgPath,
                title = quickMenuListUiState.list[it].quickMenuNm,
                onClick = { toastDevelop(context) }
            )
        }
    }
}

@Composable
private fun CategoryTopBar(
    hasNotification: Boolean,
    context: Context = LocalContext.current
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.login_please),
            style = DawnMarketTheme.typography.font17B
        )
        DefaultIconButton(
            modifier = if (hasNotification) Modifier.notificationDot() else Modifier,
            onClick = { toastDevelop(context) },
            vectorResId = R.drawable.ic_bell_big
        )
        Spacer(Modifier.width(16.dp))
        DefaultIconButton(onClick = { toastDevelop(context) }, vectorResId = R.drawable.ic_setting)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CategoryItem(imageUrl: String, title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier.aspectRatio(1f),
            model = stringResource(id = R.string.image_index_url) + imageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = DawnMarketTheme.typography.font12M,
            color = Color(0xFF5C6270),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun QuickMenuItem(imageUrl: String, title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier.aspectRatio(1f),
            model = stringResource(id = R.string.image_index_url) + imageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = DawnMarketTheme.typography.font12M,
            color = Gray900,
            textAlign = TextAlign.Center
        )
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val secondaryColor = MaterialTheme.colorScheme.secondary
        val onSecondaryColor = MaterialTheme.colorScheme.onSecondary
        drawWithContent {
            drawContent()
            drawCircle(
                secondaryColor,
                radius = 7.dp.toPx(),
                center = center + Offset(
                    7.dp.toPx(),
                    (-4).dp.toPx(),
                ),
            )
            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    color = onSecondaryColor.toArgb()
                    textSize = 8.dp.toPx()
                }

                canvas.nativeCanvas.drawText(
                    "N",
                    center.x + 4.dp.toPx(),
                    center.y + ((-1).dp.toPx()),
                    paint
                )
            }
        }
    }