package com.productbrowser.app.data

import com.productbrowser.app.data.repository.ProductRepositoryImpl
import com.productbrowser.app.domain.model.Product
import com.productbrowser.app.domain.model.ProductsResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class ProductRepositoryImplTest {

    @Test
    fun `getAllProducts returns success when API call succeeds`() = runTest {
        val fakeApiClient = FakeProductApiClient()
        val repository = ProductRepositoryImpl(fakeApiClient)

        val result = repository.getAllProducts()

        assertTrue(result.isSuccess)
        val productsResponse = result.getOrNull()!!
        assertEquals(2, productsResponse.products.size)
        assertEquals("Test Product", productsResponse.products[0].title)
        assertEquals(99.99, productsResponse.products[0].price)
        assertEquals("Test Brand", productsResponse.products[0].brand)
    }

    @Test
    fun `getAllProducts returns failure when API call fails`() = runTest {
        val fakeApiClient = FakeProductApiClient()
        fakeApiClient.setShouldThrowException(true, "Network error")
        val repository = ProductRepositoryImpl(fakeApiClient)

        val result = repository.getAllProducts()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getProductById returns success when API call succeeds`() = runTest {
        val fakeApiClient = FakeProductApiClient()
        val repository = ProductRepositoryImpl(fakeApiClient)

        val result = repository.getProductById(1)

        assertTrue(result.isSuccess)
        val product = result.getOrNull()!!
        assertEquals(1, product.id)
        assertEquals("Test Product", product.title)
        assertEquals(99.99, product.price)
    }

    @Test
    fun `searchProducts returns success when API call succeeds`() = runTest {
        val fakeApiClient = FakeProductApiClient()
        val repository = ProductRepositoryImpl(fakeApiClient)

        val result = repository.searchProducts("iPhone")

        assertTrue(result.isSuccess)
        val productsResponse = result.getOrNull()!!
        assertEquals(1, productsResponse.products.size)
        assertEquals("iPhone", productsResponse.products[0].title)
        assertEquals("Apple", productsResponse.products[0].brand)
    }
}