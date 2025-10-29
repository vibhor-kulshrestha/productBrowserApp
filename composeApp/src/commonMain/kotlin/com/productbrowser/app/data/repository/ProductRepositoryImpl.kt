package com.productbrowser.app.data.repository

import com.productbrowser.app.data.api.ProductApiClient
import com.productbrowser.app.data.dto.ProductDto
import com.productbrowser.app.data.dto.ProductsResponseDto
import com.productbrowser.app.domain.model.Product
import com.productbrowser.app.domain.model.ProductsResponse
import com.productbrowser.app.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val apiClient: ProductApiClient
) : ProductRepository {

    override suspend fun getAllProducts(): Result<ProductsResponse> {
        return try {
            val response = apiClient.getAllProducts()
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val response = apiClient.getProductById(id)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchProducts(query: String): Result<ProductsResponse> {
        return try {
            val response = apiClient.searchProducts(query)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategory(category: String): Result<ProductsResponse> {
        return try {
            val response = apiClient.getProductsByCategory(category)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun ProductDto.toDomainModel(): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            price = price,
            discountPercentage = discountPercentage,
            rating = rating,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
            images = images
        )
    }

    private fun ProductsResponseDto.toDomainModel(): ProductsResponse {
        return ProductsResponse(
            products = products.map { it.toDomainModel() },
            total = total,
            skip = skip,
            limit = limit
        )
    }
}
