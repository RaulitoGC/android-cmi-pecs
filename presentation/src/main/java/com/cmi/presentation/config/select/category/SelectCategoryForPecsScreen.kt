package com.cmi.presentation.config.select.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.ui.theme.CmiTypography

@Composable
fun SelectCategoryForPecsScreen(items: List<CategorySelectableModel>) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO: Cómo agregar IDs a los elementos
        Text(
            text = stringResource(id = R.string.text_select_pictogram),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            textAlign = TextAlign.Center,
            style = CmiTypography.bodyMedium
        )
        ListItems(listItems = items)
    }
}

@Preview(
    showBackground = true,
    heightDp = 800,
    widthDp = 360
)
@Composable
fun SelectCategoryForPecsScreenPreview() {
    SelectCategoryForPecsScreen(arrayListOf())
}

@Composable
fun ListItems(listItems: List<CategorySelectableModel>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(listItems) { item ->
            Item(item)
        }
    }
}

@Composable
fun Item(item: CategorySelectableModel) {
    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //TODO: Cómo llamar extension functions
            Image(
                painter = painterResource(id = R.drawable.img_author),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.content_description_img_author),
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp)
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(2.dp, colorResource(id = R.color.colorPrimaryDark), CircleShape)
            )
            Text(
                text = item.categoryModel.name.orEmpty(),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 24.dp),
                textAlign = TextAlign.Center,
                style = CmiTypography.bodyMedium
            )

        }
        RadioButton(
            selected = item.isSelected,
            onClick = {
                item.isSelected = !item.isSelected
            }
        )
    }
}