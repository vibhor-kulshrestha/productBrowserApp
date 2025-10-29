package com.productbrowser.app.presentation.navigation

import androidx.compose.runtime.*
import com.productbrowser.app.domain.model.Product

sealed class Screen {
    object ProductList : Screen()
    data class ProductDetail(val productId: Int) : Screen()
}

@Composable
fun AppNavigation(
    onNavigateToDetail: (Product) -> Unit,
    onNavigateBack: () -> Unit,
    productListScreen: @Composable () -> Unit,
    productDetailScreen: @Composable (Int) -> Unit
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.ProductList) }

    when (val screen = currentScreen) {
        is Screen.ProductList -> {
            productListScreen()
        }
        is Screen.ProductDetail -> {
            productDetailScreen(screen.productId)
        }
    }
}
