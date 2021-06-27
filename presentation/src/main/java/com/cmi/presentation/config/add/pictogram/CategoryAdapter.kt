package com.cmi.presentation.config.add.pictogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemAddPictogramSelectableCategoryBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.CategorySelectableModel
import timber.log.Timber

class CategoryAdapter(private var items: Map<Int, List<CategorySelectableModel>> = emptyMap()) :
    RecyclerView.Adapter<CategoryCardViewHolder>() {

    interface ItemListener {
        fun onItemClick(adapterPosition: Int, data: CategorySelectableModel)
    }

    companion object {
        private const val MAX_CATEGORY_ITEMS = 3

        fun getItemsPerScreen() = MAX_CATEGORY_ITEMS
    }

    private var itemListener: ItemListener? = null
    private var lastItemSelected: CategorySelectableModel? = null
    private var lastAdapterPositionSelected: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryCardViewHolder(
            binding = ItemAddPictogramSelectableCategoryBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: CategoryCardViewHolder, position: Int) {
        val list = items[position]
        if (!list.isNullOrEmpty()) {
            holder.bind(list = list)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: Map<Int, List<CategorySelectableModel>>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getCategorySelected(): CategorySelectableModel? {
        return lastItemSelected
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }

    fun updateSelectedItem(adapterPosition: Int, categorySelectableModel: CategorySelectableModel) {
        notifyItemChanged(adapterPosition)

        val lastItem = lastItemSelected
        val lastAdapterPosition = lastAdapterPositionSelected

        if (lastItem != null && lastAdapterPosition != null) {
            if (lastItem.categoryModel.categoryId != categorySelectableModel.categoryModel.categoryId) {
                val unSelectedIdx =
                    items[lastAdapterPosition]?.indexOfFirst { it.categoryModel.categoryId == lastItem.categoryModel.categoryId }
                        ?: -1

                if (unSelectedIdx != -1) {
                    items[lastAdapterPosition]?.get(unSelectedIdx)?.isSelected = false
                    notifyItemChanged(lastAdapterPosition)
                }
                lastItemSelected = categorySelectableModel
                lastAdapterPositionSelected = adapterPosition
            } else {
                lastItemSelected = null
                lastAdapterPositionSelected = null
            }
        } else {
            lastItemSelected = categorySelectableModel
            lastAdapterPositionSelected = adapterPosition
        }
    }
}

class CategoryCardViewHolder(
    private val binding: ItemAddPictogramSelectableCategoryBinding,
    private val itemListener: CategoryAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(list: List<CategorySelectableModel>) = with(binding) {
        loadData(
            data = list[0],
            cardView = cardViewFirstCategory,
            textView = txtDescriptionFirstPictogram,
            imageView = imgPictogramFirstPictogram,
            radioButton = radioButtonFirstPictogram
        )

        if (list.size >= 2) {
            loadData(
                data = list[1],
                cardView = cardViewSecondCategory,
                textView = txtDescriptionSecondCategory,
                imageView = imgPictogramSecondCategory,
                radioButton = radioButtonSecondCategory
            )
        } else {
            cardViewSecondCategory.visibility = View.INVISIBLE
        }

        if (list.size >= 3) {
            loadData(
                data = list[2],
                cardView = cardViewThirdCategory,
                textView = txtDescriptionThirdCategory,
                imageView = imgPictogramThirdCategory,
                radioButton = radioButtonThirdCategory
            )
        } else {
            cardViewThirdCategory.visibility = View.INVISIBLE
        }
    }

    private fun loadData(
        data: CategorySelectableModel,
        cardView: CardView,
        textView: TextView,
        imageView: ImageView,
        radioButton: RadioButton
    ) {
        cardView.visibility = View.VISIBLE
        textView.text = data.categoryModel.name

        radioButton.isChecked = data.isSelected

        imageView.loadImage(data = data.categoryModel)

        cardView.setSafeOnClickListener {
            data.isSelected = !data.isSelected
            itemListener?.onItemClick(
                adapterPosition = adapterPosition,
                data = data
            )
        }
    }
}