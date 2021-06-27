package com.cmi.presentation.config.edit.pictogram.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.config.edit.CategoryDiffCallback
import com.cmi.presentation.databinding.ItemSelectPictogramBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.PictogramModel

class SelectPictogramAdapter : RecyclerView.Adapter<EditPictogramCardViewHolder>(){

    interface ItemListener {
        fun onItemClick(data: PictogramModel)
    }

    private var items: List<PictogramModel> = emptyList()
    private var itemListener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditPictogramCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditPictogramCardViewHolder(
            binding = ItemSelectPictogramBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: EditPictogramCardViewHolder, position: Int) {
        holder.bind(data = items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<PictogramModel>) {
        val diffResult = DiffUtil.calculateDiff(PictogramDiffCallback(this.items, items))

        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }
}

class EditPictogramCardViewHolder(
    private val binding: ItemSelectPictogramBinding,
    private val itemListener: SelectPictogramAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PictogramModel) = with(binding) {
        imgPictogram.loadImage(data =data)
        txtDescription.text = data.name
        cardView.setSafeOnClickListener {
            itemListener?.onItemClick(data = data)
        }
    }
}