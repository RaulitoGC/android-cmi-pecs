package com.cmi.presentation.config.category.common

sealed class CategoryChooserEvent {
    data class ShowLoading(val isLoading: Boolean): CategoryChooserEvent()
}