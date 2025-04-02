package com.example.cocktails.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.R
import com.example.cocktails.data.local.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding
import com.example.cocktails.utils.CocktailMerged
import com.squareup.picasso.Picasso
import java.util.UUID

class RecyclerCocktailAdapter
    : ListAdapter<CocktailMerged, RecyclerCocktailAdapter.CocktailHolder>(MyDiffUtilCocktail()) {

    private var getCocktailOnClickCallBack: ((CocktailMerged) -> Unit)? = null

    inner class CocktailHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val binding = ItemCocktailBinding.bind(itemView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cocktail, parent, false)
            .let {
                return CocktailHolder(it)
            }
    }

    override fun onBindViewHolder(holder: CocktailHolder, position: Int) {
        getItem(position).let {
            with(holder.binding){
                Picasso.get().load(it.imageUrl.ifEmpty { "boo" }).into(ivItemImage)
                tvItemTitle.text = it.title
                itemCocktail.setOnClickListener {_ ->
                    getCocktailOnClickCallBack?.invoke(it)
                }
            }
        }
    }

    fun setGetCocktailOnClickCallBack(getCocktailOnClickCallBack: (CocktailMerged) -> Unit){
        this.getCocktailOnClickCallBack = getCocktailOnClickCallBack
    }
}

class MyDiffUtilCocktail: DiffUtil.ItemCallback<CocktailMerged>() {
    override fun areItemsTheSame(oldItem: CocktailMerged, newItem: CocktailMerged) =
        (oldItem.id == newItem.id || oldItem.uuid == newItem.uuid)

    override fun areContentsTheSame(oldItem: CocktailMerged, newItem: CocktailMerged) =
        oldItem == newItem
}