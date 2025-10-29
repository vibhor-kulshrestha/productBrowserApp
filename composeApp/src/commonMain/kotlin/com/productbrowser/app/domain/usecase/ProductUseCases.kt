package com.productbrowser.app.domain.usecase

import com.productbrowser.app.domain.model.Product
import com.productbrowser.app.domain.model.ProductsResponse
import com.productbrowser.app.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<ProductsResponse> {
        return repository.getAllProducts()
    }
}

class GetProductByIdUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Result<Product> {
        return repository.getProductById(id)
    }
}

class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): Result<ProductsResponse> {
        if (query.isBlank()) {
            return Result.failure(IllegalArgumentException("Search query cannot be empty"))
        }
        return repository.searchProducts(query)
    }
}

class GetProductsByCategoryUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(category: String): Result<ProductsResponse> {
        if (category.isBlank()) {
            return Result.failure(IllegalArgumentException("Category cannot be empty"))
        }
        return repository.getProductsByCategory(category)
    }
}
