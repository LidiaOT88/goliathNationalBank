package com.example.goliathnationalbank.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goliathnationalbank.R
import com.example.goliathnationalbank.databinding.FragmentProductListBinding
import com.example.goliathnationalbank.ui.adapter.ProductListAdapter
import com.example.goliathnationalbank.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/*TODO:
*  diseño interfaz, (diseño general, themes y appbar)
*  cuando las listas estén vacías mostrar vista/imagen
*  mostrar vista de carga para lista de productos
*  mostrar mensaje de error
*  navegación a través del viewModel
*  lista de productos: (* cuidado, cada vez que se lanza la app, los productos han cambiado, preguntar)
*       - implementado limpiar la base de datos por cada ejecución para que solo traiga los recientes en el json de la llamada
*       - ¿dejar borrar los productos?
*       - ¿guardar todos los productos en cada ejecución dejando en la bdd los de la llamada anterior?
*  función de cambio recursiva
*  corregir pantalla en blanco al lanzar la app, se muestra antes del splash
*  refectorizar strings y values para incluirlos en ficheros de recursos
*  implementar analytics
*  si da tiempo, separar por MÓDULOS*/

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var binding: FragmentProductListBinding
    private var productAdapter: ProductListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeProducts()
    }

    private fun setupAdapter() {
        productAdapter = ProductListAdapter(
            getProductItemListener()
        )
        binding.productListListProducts.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = productAdapter
        }
    }

    private fun getProductItemListener() =
        object : ProductListAdapter.ProductViewHolder.ProductItemListener {
            override fun onItemClick(sku: String) {
                findNavController().navigate(
                    ProductListFragmentDirections.fromProductListToProductDetailFragment(sku)
                )
            }
        }

    private fun observeProducts() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.products().collectLatest {
                productAdapter?.submitData(it)
            }
        }
    }

}