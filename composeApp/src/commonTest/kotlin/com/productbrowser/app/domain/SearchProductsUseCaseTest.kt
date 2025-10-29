package com.productbrowser.app.domain

import com.productbrowser.app.domain.model.Product
import com.productbrowser.app.domain.model.ProductsResponse
import com.productbrowser.app.domain.repository.ProductRepository
import com.productbrowser.app.domain.usecase.SearchProductsUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class FakeProductRepository : ProductRepository {
    private var shouldThrowException = false
    private var exceptionMessage = "Fake error"

    private val mockProducts = listOf(
        Product(
            id = 1,
            title = "iPhone 15",
            description = "Latest iPhone",
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

    fun setShouldThrowException(shouldThrow: Boolean, message: String = "Fake error") {
        shouldThrowException = shouldThrow
        exceptionMessage = message
    }

    override suspend fun getAllProducts(): Result<ProductsResponse> {
        if (shouldThrowException) return Result.failure(Exception(exceptionMessage))
        return Result.success(ProductsResponse(mockProducts, 1, 0, 30))
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        if (shouldThrowException) return Result.failure(Exception(exceptionMessage))
        val product = mockProducts.find { it.id == id }
        return if (product != null) {
            Result.success(product)
        } else {
            Result.failure(Exception("Product not found"))
        }
    }

    override suspend fun searchProducts(query: String): Result<ProductsResponse> {
        if (shouldThrowException) return Result.failure(Exception(exceptionMessage))
        val filteredProducts = mockProducts.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.brand.contains(query, ignoreCase = true)
        }
        return Result.success(ProductsResponse(filteredProducts, filteredProducts.size, 0, 30))
    }

    override suspend fun getProductsByCategory(category: String): Result<ProductsResponse> {
        if (shouldThrowException) return Result.failure(Exception(exceptionMessage))
        val filteredProducts = mockProducts.filter {
            it.category.equals(category, ignoreCase = true)
        }
        return Result.success(ProductsResponse(filteredProducts, filteredProducts.size, 0, 30))
    }
}

class SearchProductsUseCaseTest {

    @Test
    fun `searchProducts returns success when repository call succeeds`() = runTest {
        // Given
        val fakeRepository = FakeProductRepository()
        val useCase = SearchProductsUseCase(fakeRepository)

        // When
        val result = useCase("iPhone")

        // Then
        assertTrue(result.isSuccess)
        val productsResponse = result.getOrNull()!!
        assertEquals(1, productsResponse.products.size)
        assertEquals("iPhone 15", productsResponse.products[0].title)
        assertEquals("Apple", productsResponse.products[0].brand)
    }

    @Test
    fun `searchProducts returns failure when query is blank`() = runTest {
        // Given
        val fakeRepository = FakeProductRepository()
        val useCase = SearchProductsUseCase(fakeRepository)

        // When
        val result = useCase("")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Search query cannot be empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `searchProducts returns failure when query is whitespace only`() = runTest {
        // Given
        val fakeRepository = FakeProductRepository()
        val useCase = SearchProductsUseCase(fakeRepository)

        // When
        val result = useCase("   ")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Search query cannot be empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `searchProducts returns failure when repository call fails`() = runTest {
        // Given
        val fakeRepository = FakeProductRepository()
        fakeRepository.setShouldThrowException(true, "Network error")
        val useCase = SearchProductsUseCase(fakeRepository)

        // When
        val result = useCase("iPhone")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}