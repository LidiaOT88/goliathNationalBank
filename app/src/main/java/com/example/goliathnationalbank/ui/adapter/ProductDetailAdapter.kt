package com.example.goliathnationalbank.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goliathnationalbank.data.model.TransactionBo
import com.example.goliathnationalbank.databinding.RowTransactionBinding

class ProductDetailAdapter()
    : ListAdapter<TransactionBo, ProductDetailAdapter.ProductViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(RowTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ProductViewHolder(
        private val binding: RowTransactionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionBo) {
            binding.productDetailLabelAmount.text = transaction.amount
            binding.productDetailLabelCurrency.text = transaction.currency
        }

    }
}

class TransactionDiffCallback() : DiffUtil.ItemCallback<TransactionBo>() {

    override fun areItemsTheSame(oldItem: TransactionBo, newItem: TransactionBo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TransactionBo, newItem: TransactionBo): Boolean {
        return oldItem.id == newItem.id
    }
}