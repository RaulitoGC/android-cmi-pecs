package com.cmi.presentation.config.select.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemSelectBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.CategorySelectableModel

class SelectableCategoryAdapter : RecyclerView.Adapter<SelectableCategoryViewHolder>() {

    interface ItemListener {
        fun onItemClick(data: CategorySelectableModel)
    }

    private var items: List<CategorySelectableModel> = emptyList()
    private var itemListener: ItemListener? = null


    val selectedItems: List<CategorySelectableModel>
        get() = items.asSequence().filter { it.isSelected }.toList()

    fun getItems() = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectableCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SelectableCategoryViewHolder(
            binding = ItemSelectBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: SelectableCategoryViewHolder, position: Int) {
        holder.bind(data = items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<CategorySelectableModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }

    fun updateItem(data: CategorySelectableModel) {
        val idx =
            items.indexOfFirst { it.categoryModel.categoryId == data.categoryModel.categoryId }
        if (idx != -1) {
            notifyItemChanged(idx)
        }
    }
}

class SelectableCategoryViewHolder(
    private val binding: ItemSelectBinding,
    private val itemListener: SelectableCategoryAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CategorySelectableModel) = with(binding) {
        imgPictogram.loadImage(data = data.categoryModel)
        txtDescription.text = data.categoryModel.name
        val isSelected = data.isSelected
        radioButton.isChecked = isSelected
        cardView.setSafeOnClickListener {
            data.isSelected = !isSelected
            itemListener?.onItemClick(data = data)
        }
    }
}
