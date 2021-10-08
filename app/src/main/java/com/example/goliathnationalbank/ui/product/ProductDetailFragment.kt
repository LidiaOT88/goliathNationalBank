package com.example.goliathnationalbank.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliathnationalbank.R
import com.example.goliathnationalbank.data.lifecycle.EventAsyncResultObserver
import com.example.goliathnationalbank.databinding.FragmentProductDetailBinding
import com.example.goliathnationalbank.ui.adapter.ProductDetailAdapter
import com.example.goliathnationalbank.ui.viewModel.MainViewModel
import com.example.goliathnationalbank.ui.vo.ProductVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_product_detail_list.view.*

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentProductDetailBinding
    private var productDetailAdapter: ProductDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_detail,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeProductDetail()
    }

    private fun setupAdapter() {
        productDetailAdapter = ProductDetailAdapter()
        binding.productDetailListProductTransactions.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            this.adapter = productDetailAdapter
        }
    }

    private fun observeProductDetail() {
        mainViewModel.getProductsBySku().observe(viewLifecycleOwner, object : EventAsyncResultObserver<ProductVo?>() {
            override fun onLoading(loading: Boolean) {
                setLoading(loading)
            }

            override fun onSuccess(response: ProductVo?) {
                response?.let {
                    binding.toolbar.rootView.toolbar_product_detail_list__label__product.text = response.sku
                    binding.toolbar.rootView.toolbar_product_detail_list__label__total_amount.text = getString(R.string.product_total_amount, response.totalAmount)
                    productDetailAdapter?.submitList(it.transactions)
                }
                setLoading(false)
            }

            override fun onError(error: String, response: ProductVo?) {
                super.onError(error, response)
                setLoading(false)
            }
        })
        mainViewModel.requestProductBySku(args.productSku)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.productDetailAnimationLoading.apply {
                visibility = VISIBLE
                playAnimation()
            }
            binding.productDetailContainerRoot.visibility = GONE

        } else {
            binding.productDetailAnimationLoading.apply {
                visibility = GONE
                pauseAnimation()
            }
            binding.productDetailContainerRoot.visibility = VISIBLE
        }
    }
}