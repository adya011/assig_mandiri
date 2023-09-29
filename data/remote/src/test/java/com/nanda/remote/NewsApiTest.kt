package com.nanda.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nanda.remote.api.NewsApi
import com.nanda.remote.testutils.TestUtilities
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class NewsApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var api: NewsApi

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch source - success`() = runBlocking {
        val category = "business"
        val mockBody = TestUtilities.getResponseBodyFromJsonFile(
            filename = "sources_success.json"
        )

        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockBody)
        mockWebServer.enqueue(mockResponse)

        val response = api.topHeadlines(category = category, apiKey = "ab3b43a3").await()
        val request = mockWebServer.takeRequest()

        assert(response.isSuccessful)
        assert(request.method == "GET")
        assert(request.path == "/top-headlines/sources?category=business&apiKey=ab3b43a3")
        assert(response.body()!!.status == "ok")
        assert(response.body()!!.sources!!.count() == 14)
        assert(response.body()!!.sources!!.first().id == "argaam")
    }

    @Test
    fun `fetch article - success`() = runBlocking {
        val query = "chatgpt"
        val sources = "bloomberg"
        val apiKey = "ab3b43a3"
        val mockBody = TestUtilities.getResponseBodyFromJsonFile(
            filename = "article_success.json"
        )

        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(mockBody)
        mockWebServer.enqueue(mockResponse)

        val response = api.everything(source = sources, query = query, apiKey = apiKey).await()
        val request = mockWebServer.takeRequest()

        assert(response.isSuccessful)
        assert(request.method == "GET")
        assert(request.path == "/everything?sources=bloomberg&q=chatgpt&page=1&pageSize=10&apiKey=ab3b43a3")
        assert(response.body()!!.status == "ok")
        assert(response.body()!!.totalResults == 16)
        assert(response.body()!!.articles!!.count() == 10)
        assert(response.body()!!.articles!!.first().source!!.id == "bloomberg")
    }
}