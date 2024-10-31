package com.cmi.presentation.components.remover.type

sealed class PictureRemoverContentType {
    data object Category: PictureRemoverContentType()
    data class Pictogram(val categoryId: Int): PictureRemoverContentType()
}
