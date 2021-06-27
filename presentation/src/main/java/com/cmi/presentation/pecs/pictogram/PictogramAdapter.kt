package com.cmi.presentation.pecs.pictogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmi.presentation.databinding.ItemPictogramBinding
import com.cmi.presentation.ktx.loadImage
import com.cmi.presentation.ktx.setSafeOnClickListener
import com.cmi.presentation.model.PictogramModel

class PictogramAdapter : RecyclerView.Adapter<PictogramCardViewHolder>() {

    interface ItemListener {
        fun onItemClick(data: PictogramModel)
    }

    private var items: Map<Int, List<PictogramModel>> = emptyMap()
    private var itemListener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictogramCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PictogramCardViewHolder(
            binding = ItemPictogramBinding.inflate(inflater, parent, false),
            itemListener = itemListener
        )
    }

    override fun onBindViewHolder(holder: PictogramCardViewHolder, position: Int) {
        val list = items[position]
        if (!list.isNullOrEmpty()) {
            holder.bind(list)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: Map<Int, List<PictogramModel>>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListener(itemListener: ItemListener) {
        this.itemListener = itemListener
    }
}

class PictogramCardViewHolder(
    private val binding: ItemPictogramBinding,
    private val itemListener: PictogramAdapter.ItemListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(list: List<PictogramModel>) = with(binding) {
        val size = list.size
        loadData(
            data = list[0],
            cardView = cardViewFirstPictogram,
            textView = txtFirstDescription,
            imageView = imgFirstPictogram
        )

        if (size >= 2) {
            loadData(
                data = list[1],
                cardView = cardViewSecondPictogram,
                textView = txtSecondDescription,
                imageView = imgSecondPictogram
            )
        } else {
            cardViewSecondPictogram.visibility = if (size == 3) View.INVISIBLE else View.GONE
        }

        if (list.size >= 3) {
            loadData(
                data = list[2],
                cardView = cardViewThirdPictogram,
                textView = txtThirdDescription,
                imageView = imgThirdPictogram
            )
        } else {
            cardViewThirdPictogram.visibility = if (size == 3) View.INVISIBLE else View.GONE
        }

    }

    private fun loadData(
        data: PictogramModel,
        cardView: CardView,
        textView: TextView,
        imageView: ImageView
    ) {
        cardView.visibility = View.VISIBLE
        textView.text = data.name
        imageView.loadImage(data = data)

        cardView.setSafeOnClickListener {
            itemListener?.onItemClick(data = data)
        }
    }
}