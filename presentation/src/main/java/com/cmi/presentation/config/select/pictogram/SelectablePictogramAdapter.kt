package com.cmi.presentation.config.select.pictogram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemSelectBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.PictogramSelectableModel

class SelectablePictogramAdapter : RecyclerView.Adapter<SelectablePictogramViewHolder>() {

    interface ItemListener {
        fun onItemClick(data: PictogramSelectableModel)
    }

    private var items: List<PictogramSelectableModel> = emptyList()
    private var itemListener: ItemListener? = null

    val selectedItems: List<PictogramSelectableModel>
        get() = items.asSequence().filter { it.isSelected }.toList()

    fun getItems() = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectablePictogramViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SelectablePictogramViewHolder(
            binding = ItemSelectBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: SelectablePictogramViewHolder, position: Int) {
        holder.bind(data = items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<PictogramSelectableModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }

    fun updateItem(data: PictogramSelectableModel) {
        val idx =
            items.indexOfFirst { it.pictogramModel.pictogramId == data.pictogramModel.pictogramId }
        if (idx != -1) {
            notifyItemChanged(idx)
        }
    }
}

class SelectablePictogramViewHolder(
    private val binding: ItemSelectBinding,
    private val itemListener: SelectablePictogramAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PictogramSelectableModel) = with(binding) {
        imgPictogram.loadImage(data = data.pictogramModel)
        txtDescription.text = data.pictogramModel.name
        val isSelected = data.isSelected
        radioButton.isChecked = isSelected
        cardView.setSafeOnClickListener {
            data.isSelected = !isSelected
            itemListener?.onItemClick(data = data)
        }
    }
}

