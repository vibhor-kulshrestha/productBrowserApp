package com.productbrowser.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.productbrowser.app.data.api.ProductApiClient
import com.productbrowser.app.data.repository.ProductRepositoryImpl
import com.productbrowser.app.domain.usecase.*
import com.productbrowser.app.presentation.navigation.Screen
import com.productbrowser.app.presentation.ui.ProductDetailScreen
import com.productbrowser.app.presentation.ui.ProductListScreen
import com.productbrowser.app.presentation.viewmodel.ProductDetailViewModel
import com.productbrowser.app.presentation.viewmodel.ProductListViewModel

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                ) {
                    ProductBrowserApp()
                }
            }
        }
    }
}

@Composable
fun ProductBrowserApp() {
    val apiClient = remember { ProductApiClient() }
    val repository = remember { ProductRepositoryImpl(apiClient) }
    
    val getProductsUseCase = remember { GetProductsUseCase(repository) }
    val getProductByIdUseCase = remember { GetProductByIdUseCase(repository) }
    val searchProductsUseCase = remember { SearchProductsUseCase(repository) }
    val getProductsByCategoryUseCase = remember { GetProductsByCategoryUseCase(repository) }
    
    val productListViewModel = viewModel {
        ProductListViewModel(getProductsUseCase, searchProductsUseCase)
    }
    
    val productDetailViewModel = viewModel {
        ProductDetailViewModel(getProductByIdUseCase)
    }
    
    var currentScreen by remember { mutableStateOf<Screen>(Screen.ProductList) }
    
    when (val screen = currentScreen) {
        is Screen.ProductList -> {
            ProductListScreen(
                viewModel = productListViewModel,
                onProductClick = { product ->
                    currentScreen = Screen.ProductDetail(product.id)
                }
            )
        }
        is Screen.ProductDetail -> {
            ProductDetailScreen(
                productId = screen.productId,
                viewModel = productDetailViewModel,
                onBackClick = {
                    currentScreen = Screen.ProductList
                }
            )
        }
    }
}