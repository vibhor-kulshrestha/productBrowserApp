package com.productbrowser.app.data

import com.productbrowser.app.data.api.ProductApiClient
import com.productbrowser.app.data.dto.ProductDto
import com.productbrowser.app.data.dto.ProductsResponseDto

class FakeProductApiClient : ProductApiClient() {
    private var shouldThrowException = false
    private var exceptionMessage = "Fake error"

    private val mockProducts = listOf(
        ProductDto(
            id = 1,
            title = "Test Product",
            description = "Test Description",
            price = 99.99,
            discountPercentage = 10.0,
            rating = 4.5,
            stock = 100,
            brand = "Test Brand",
            category = "Test Category",
            thumbnail = "test-thumbnail.jpg",
            images = listOf("image1.jpg", "image2.jpg")
        ),
        ProductDto(
            id = 2,
            title = "iPhone",
            description = "Apple iPhone",
            price = 999.99,
            discountPercentage = 5.0,
            rating = 4.8,
            stock = 50,
            brand = "Apple",
            category = "smartphones",
            thumbnail = "iphone-thumbnail.jpg",
            images = listOf("iphone1.jpg", "iphone2.jpg")
        )
    )

    private val mockProductsResponse = ProductsResponseDto(
        products = mockProducts,
        total = mockProducts.size,
        skip = 0,
        limit = 30
    )

    fun setShouldThrowException(shouldThrow: Boolean, message: String = "Fake error") {
        shouldThrowException = shouldThrow
        exceptionMessage = message
    }

    fun getMockProducts() = mockProducts
    fun getMockProductsResponse() = mockProductsResponse

    override suspend fun getAllProducts(): ProductsResponseDto {
        if (shouldThrowException) throw Exception(exceptionMessage)
        return mockProductsResponse
    }

    override suspend fun getProductById(id: Int): ProductDto {
        if (shouldThrowException) throw Exception(exceptionMessage)
        return mockProducts.find { it.id == id }
            ?: throw Exception("Product with id $id not found")
    }

    override suspend fun searchProducts(query: String): ProductsResponseDto {
        if (shouldThrowException) throw Exception(exceptionMessage)
        val filteredProducts = mockProducts.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.brand.contains(query, ignoreCase = true)
        }
        return ProductsResponseDto(
            products = filteredProducts,
            total = filteredProducts.size,
            skip = 0,
            limit = 30
        )
    }

    override suspend fun getProductsByCategory(category: String): ProductsResponseDto {
        if (shouldThrowException) throw Exception(exceptionMessage)
        val filteredProducts = mockProducts.filter {
            it.category.equals(category, ignoreCase = true)
        }
        return ProductsResponseDto(
            products = filteredProducts,
            total = filteredProducts.size,
            skip = 0,
            limit = 30
        )
    }
}