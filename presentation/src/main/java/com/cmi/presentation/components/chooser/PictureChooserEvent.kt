package com.cmi.presentation.components.chooser

sealed class PictureChooserEvent {
    data object Reload : PictureChooserEvent()
}