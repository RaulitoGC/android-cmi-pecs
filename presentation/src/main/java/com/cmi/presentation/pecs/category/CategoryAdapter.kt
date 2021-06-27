package com.cmi.presentation.pecs.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemCategoryBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.CategoryModel

class CategoryAdapter() : RecyclerView.Adapter<CategoryCardViewHolder>() {

    companion object {
        private const val MAX_CATEGORY_ITEMS = 6

        fun getItemsPerScreen() = MAX_CATEGORY_ITEMS
    }

    interface ItemListener {
        fun onItemClick(data: CategoryModel)
    }

    private var items: Map<Int, List<CategoryModel>> = emptyMap()
    private var itemListener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryCardViewHolder(
            binding = ItemCategoryBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: CategoryCardViewHolder, position: Int) {
        val list = items[position]

        if (!list.isNullOrEmpty()) {
            holder.bind(list = list)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: Map<Int, List<CategoryModel>>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }
}

class CategoryCardViewHolder(
    private val binding: ItemCategoryBinding,
    private val itemListener: CategoryAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(list: List<CategoryModel>) = with(binding) {

        loadData(
            data = list[0],
            cardView = cardViewFirstCategory,
            textView = txtDescriptionFirstCategory,
            imageView = imgFirstCategory
        )

        if(list.size  >= 2){
            loadData(
                data = list[1],
                cardView = cardViewSecondCategory,
                textView = txtDescriptionSecondCategory,
                imageView = imgSecondCategory
            )
        }else{
            cardViewSecondCategory.visibility = View.INVISIBLE
        }

        if(list.size >= 3){
            loadData(
                data = list[2],
                cardView = cardViewThirdCategory,
                textView = txtDescriptionThirdCategory,
                imageView = imgThirdCategory
            )
        }else{
            cardViewThirdCategory.visibility = View.INVISIBLE
        }

        if(list.size >= 4){
            loadData(
                data = list[3],
                cardView = cardViewFourthCategory,
                textView = txtDescriptionFourthCategory,
                imageView = imgFourthCategory
            )
        }else{
            cardViewFourthCategory.visibility = View.INVISIBLE
        }

        if(list.size >= 5){
            loadData(
                data = list[4],
                cardView = cardViewFifthCategory,
                textView = txtDescriptionFifthCategory,
                imageView = imgFifthCategory
            )
        }else{
            cardViewFifthCategory.visibility = View.INVISIBLE
        }

        if(list.size >= 6){
            loadData(
                data = list[5],
                cardView = cardViewSixthCategory,
                textView = txtDescriptionSixthCategory,
                imageView = imgSixthCategory
            )
        }else{
            cardViewSixthCategory.visibility = View.INVISIBLE
        }
    }

    private fun loadData(data: CategoryModel, cardView: CardView, textView: TextView, imageView: ImageView){
        cardView.visibility = View.VISIBLE
        textView.text = data.name
        imageView.loadImage(data = data)

        cardView.setSafeOnClickListener {
            itemListener?.onItemClick(data = data)
        }
    }
}