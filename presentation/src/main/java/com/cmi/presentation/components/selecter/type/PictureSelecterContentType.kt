package com.cmi.presentation.components.selecter.type

sealed class PictureSelecterContentType {
    data object Category: PictureSelecterContentType()
    data class Pictogram(val categoryId: Int): PictureSelecterContentType()
}