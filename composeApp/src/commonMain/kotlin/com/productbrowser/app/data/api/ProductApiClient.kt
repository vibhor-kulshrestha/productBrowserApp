package com.productbrowser.app.data.api

import com.productbrowser.app.data.dto.ProductDto
import com.productbrowser.app.data.dto.ProductsResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

open class ProductApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    private val baseUrl = "https://dummyjson.com"

    open suspend fun getAllProducts(): ProductsResponseDto {
        return try {
            println("Making request to: $baseUrl/products")
            val response = httpClient.get("$baseUrl/products")
            println("Response status: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("Error in getAllProducts: ${e.message}")
            println("Error type: ${e::class.simpleName}")
            e.printStackTrace()
            throw e
        }
    }

    open suspend fun getProductById(id: Int): ProductDto {
        return try {
            println("Making request to: $baseUrl/products/$id")
            val response = httpClient.get("$baseUrl/products/$id")
            println("Response status: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("Error in getProductById: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    open suspend fun searchProducts(query: String): ProductsResponseDto {
        return try {
            println("Making request to: $baseUrl/products/search?q=$query")
            val response = httpClient.get("$baseUrl/products/search") {
                parameter("q", query)
            }
            println("Response status: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("Error in searchProducts: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    open suspend fun getProductsByCategory(category: String): ProductsResponseDto {
        return try {
            println("Making request to: $baseUrl/products/category/$category")
            val response = httpClient.get("$baseUrl/products/category/$category")
            println("Response status: ${response.status}")
            response.body()
        } catch (e: Exception) {
            println("Error in getProductsByCategory: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    fun close() {
        httpClient.close()
    }
}
