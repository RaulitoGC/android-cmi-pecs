package com.cmi.presentation.config.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemSelectCategoryBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.CategoryModel

class SelectCategoryAdapter : RecyclerView.Adapter<EditCategoryCardViewHolder>() {

    interface ItemListener {
        fun onItemClick(data: CategoryModel)
    }

    private var items: List<CategoryModel> = emptyList()
    private var itemListener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCategoryCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditCategoryCardViewHolder(
            binding = ItemSelectCategoryBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: EditCategoryCardViewHolder, position: Int) {
        holder.bind(data = items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<CategoryModel>) {
        val diffResult = DiffUtil.calculateDiff(CategoryDiffCallback(this.items, items))

        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }
}

class EditCategoryCardViewHolder(
    private val binding: ItemSelectCategoryBinding,
    private val itemListener: SelectCategoryAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CategoryModel) = with(binding) {
        imgPictogram.loadImage(data = data)
        txtDescription.text = data.name
        cardView.setSafeOnClickListener {
            itemListener?.onItemClick(data = data)
        }
    }
}