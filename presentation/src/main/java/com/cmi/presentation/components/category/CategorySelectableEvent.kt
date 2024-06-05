package com.cmi.presentation.components.category

sealed class CategorySelectableEvent {

    data class ShowLoading(val isLoading: Boolean): CategorySelectableEvent()
}