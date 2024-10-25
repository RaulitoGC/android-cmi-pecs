package com.cmi.presentation.config

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmi.presentation.R
import com.cmi.presentation.common.navigation.CategoryConfigurationTypeHost
import com.cmi.presentation.common.navigation.ConfigurationFlowType
import com.cmi.presentation.components.common.title.DefaultTitle
import com.cmi.presentation.ktx.DefaultHorizontalSpacer
import com.cmi.presentation.ktx.DefaultVerticalSpacer

@Composable
fun ConfigurationScreen(
    configurationFlow: FLOW,
    onBack: () -> Unit,
    onItemSelected: (configurationFlowType: ConfigurationFlowType) -> Unit
) {
    Column {
        val context = LocalContext.current
        DefaultTitle(
            title = getTitleByFlow(context, configurationFlow),
            onBackClick = onBack
        )

        DefaultVerticalSpacer(height = 8.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            SelectCardType(
                modifier = Modifier.weight(0.5f),
                configurationFlow = configurationFlow,
                onItemSelected = {
                    onItemSelected.invoke(ConfigurationFlowType.SELECT)
                }
            )
            DefaultHorizontalSpacer(width = 8.dp)
            AddCardType(
                modifier = Modifier.weight(0.5f),
                configurationFlow = configurationFlow,
                onItemSelected = {
                    onItemSelected.invoke(ConfigurationFlowType.ADD)
                }
            )
        }

        DefaultVerticalSpacer(height = 8.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {
            EditCardType(
                modifier = Modifier.weight(0.5f),
                configurationFlow = configurationFlow,
                onItemSelected = {
                    onItemSelected.invoke(ConfigurationFlowType.EDIT)
                }
            )
            DefaultHorizontalSpacer(width = 8.dp)
            RemoveCardType(
                modifier = Modifier.weight(0.5f),
                configurationFlow = configurationFlow,
                onItemSelected = {
                    onItemSelected.invoke(ConfigurationFlowType.REMOVE)
                }
            )
        }
    }
}

@Composable
private fun SelectCardType(
    modifier: Modifier = Modifier,
    configurationFlow: FLOW,
    onItemSelected: () -> Unit
) {
    val context = LocalContext.current
    val (title, image) = getSelectDataByFlow(context, configurationFlow)
    ConfigurationTypeCard(
        modifier = modifier,
        title = title,
        image = image,
        textFontSize = 16.sp,
        onItemSelected = onItemSelected
    )
}

@Composable
private fun AddCardType(
    modifier: Modifier = Modifier,
    configurationFlow: FLOW,
    onItemSelected: () -> Unit
) {
    val context = LocalContext.current
    val (title, image) = getAddDataByFlow(context, configurationFlow)
    ConfigurationTypeCard(
        modifier = modifier,
        title = title,
        image = image,
        textFontSize = 16.sp,
        onItemSelected = onItemSelected
    )
}

@Composable
private fun EditCardType(
    modifier: Modifier = Modifier,
    configurationFlow: FLOW,
    onItemSelected: () -> Unit
) {
    val context = LocalContext.current
    val (title, image) = getEditDataByFlow(context, configurationFlow)
    ConfigurationTypeCard(
        modifier = modifier,
        title = title,
        image = image,
        textFontSize = 16.sp,
        onItemSelected = onItemSelected
    )
}

@Composable
private fun RemoveCardType(
    modifier: Modifier = Modifier,
    configurationFlow: FLOW,
    onItemSelected: () -> Unit
) {
    val context = LocalContext.current
    val (title, image) = getDeleteDataByFlow(context, configurationFlow)
    ConfigurationTypeCard(
        modifier = modifier,
        title = title,
        image = image,
        textFontSize = 16.sp,
        onItemSelected = onItemSelected
    )
}


private fun getEditDataByFlow(
    context: Context,
    flow: FLOW
): Pair<String, Int> {
    val title = when (flow) {
        FLOW.CATEGORY -> {
            val categoryName = context.getString(R.string.text_category)
            context.getString(R.string.text_edit_format, categoryName)
        }

        FLOW.PICTOGRAM -> {
            val pictogramName = context.getString(R.string.text_pictogram)
            context.getString(R.string.text_edit_format, pictogramName)
        }
    }
    return title to R.drawable.img_edit
}

private fun getDeleteDataByFlow(
    context: Context,
    flow: FLOW
): Pair<String, Int> {
    val title = when (flow) {
        FLOW.CATEGORY -> {
            val categoryName = context.getString(R.string.text_category)
            context.getString(R.string.text_delete_format, categoryName)
        }

        FLOW.PICTOGRAM -> {
            val pictogramName = context.getString(R.string.text_pictogram)
            context.getString(R.string.text_delete_format, pictogramName)
        }
    }
    return title to R.drawable.img_remove
}

private fun getSelectDataByFlow(
    context: Context,
    flow: FLOW
): Pair<String, Int> {
    val title = when (flow) {
        FLOW.CATEGORY -> {
            val categoryName = context.getString(R.string.text_category)
            context.getString(R.string.text_select_format, categoryName)
        }

        FLOW.PICTOGRAM -> {
            val pictogramName = context.getString(R.string.text_pictogram)
            context.getString(R.string.text_select_format, pictogramName)
        }
    }
    return title to R.drawable.img_select
}

private fun getAddDataByFlow(
    context: Context,
    flow: FLOW
): Pair<String, Int> {
    val title = when (flow) {
        FLOW.CATEGORY -> {
            val categoryName = context.getString(R.string.text_category)
            context.getString(R.string.text_add_format, categoryName)
        }

        FLOW.PICTOGRAM -> {
            val pictogramName = context.getString(R.string.text_pictogram)
            context.getString(R.string.text_add_format, pictogramName)
        }
    }
    return title to R.drawable.img_add
}

private fun getTitleByFlow(
    context: Context,
    flow: FLOW
): String {
    val title = context.getString(R.string.text_settings)
    val name = when (flow) {
        FLOW.CATEGORY -> context.getString(R.string.text_category)
        FLOW.PICTOGRAM -> context.getString(R.string.text_pictogram)
    }

    return context.getString(R.string.text_toolbar_name_format, title, name)
}