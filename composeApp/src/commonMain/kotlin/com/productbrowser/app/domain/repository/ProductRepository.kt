package com.productbrowser.app.domain.repository

import com.productbrowser.app.domain.model.Product
import com.productbrowser.app.domain.model.ProductsResponse

interface ProductRepository {
    suspend fun getAllProducts(): Result<ProductsResponse>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun searchProducts(query: String): Result<ProductsResponse>
    suspend fun getProductsByCategory(category: String): Result<ProductsResponse>
}
