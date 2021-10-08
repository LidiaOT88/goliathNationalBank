package com.example.goliathnationalbank.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goliathnationalbank.databinding.RowProductBinding

class ProductListAdapter(
    private val productItemListener: ProductViewHolder.ProductItemListener)
    : PagingDataAdapter<String, ProductListAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(RowProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), productItemListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ProductViewHolder(
        private val binding: RowProductBinding,
        private val productItemListener: ProductItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        interface ProductItemListener {
            fun onItemClick(sku: String)
        }

        fun bind(product: String) {
            setupViews(product)
            binding.root.setOnClickListener {
                productItemListener.onItemClick(product)
            }
        }

        private fun setupViews(product: String) {
            binding.productListLabelProducts.text = product
        }

    }
}

class ProductDiffCallback() : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
