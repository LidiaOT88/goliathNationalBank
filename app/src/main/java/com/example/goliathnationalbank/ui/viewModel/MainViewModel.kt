package com.example.goliathnationalbank.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.goliathnationalbank.data.lifecycle.AsyncResult
import com.example.goliathnationalbank.data.lifecycle.Event
import com.example.goliathnationalbank.data.lifecycle.MutableSourceLiveData
import com.example.goliathnationalbank.data.repository.Repository
import com.example.goliathnationalbank.data.usecase.GetInitialDataUseCase
import com.example.goliathnationalbank.data.usecase.GetProductBySkuUseCase
import com.example.goliathnationalbank.ui.vo.ProductVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val getInitialDataUseCase: GetInitialDataUseCase,
    private val getProductBySkuUseCase: GetProductBySkuUseCase,
    application: Application
) : AndroidViewModel(application) {

    //Initial data
    private val getInitialDataLiveData = MutableSourceLiveData<Event<AsyncResult<Boolean>>>()

    fun requestInitialData() = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            getInitialDataLiveData.changeSource(
                getInitialDataUseCase().asLiveData(coroutineContext)
            )
        }
    }

    fun getInitialData() = getInitialDataLiveData.liveData()
    //end Initial data

    //Products
    fun products(): Flow<PagingData<String>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { repository.getProducts() }
        ).flow.cachedIn(viewModelScope)
    }
    //end Products

    //Product by sku
    private val getProductsBySkuLiveData = MutableSourceLiveData<Event<AsyncResult<ProductVo?>>>()

    fun requestProductBySku(sku: String) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            getProductsBySkuLiveData.changeSource(
                getProductBySkuUseCase(sku).asLiveData(coroutineContext)
            )
        }
    }

    fun getProductsBySku() = getProductsBySkuLiveData.liveData()
    //end Product by sku

    //TODO: incluir implementación de navegación a través de viewModel

}