package com.cmi.presentation.config.edit.pictogram.select

import androidx.recyclerview.widget.DiffUtil
import com.cmi.presentation.model.PictogramModel

class PictogramDiffCallback(
    private val oldPictograms: List<PictogramModel>,
    private val newPictograms: List<PictogramModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldPictograms.size

    override fun getNewListSize(): Int = newPictograms.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPictograms[oldItemPosition].pictogramId == newPictograms[newItemPosition].pictogramId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPictograms[oldItemPosition] == newPictograms[newItemPosition]
    }
}