package com.nabin0.news.data

import com.google.common.truth.Truth.assertThat
import com.nabin0.news.data.api.NewsAPIService
import com.nabin0.news.data.model.Article
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var newsAPIService: NewsAPIService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        newsAPIService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val request = mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=74636600ffb947089b6bae3ce84066c0")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctArticleCount() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val articles: List<Article> = responseBody!!.articles
            assertThat(articles.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctArticleContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val articles: Article = responseBody!!.articles[0]
            assertThat(articles.author).isEqualTo("Yahoo Sports Staff")
            assertThat(articles.content).isEqualTo("Mike Tomlin, Kenny Pickett and the Steelers do battle with the Colts on Monday Night Football. (Philip G. Pavely-USA TODAY Sports)\r\nThe Indianapolis Colts nearly moved to 2-0 under interim head coach… [+214 chars]")
            assertThat(articles.url).isEqualTo("https://sports.yahoo.com/nfl-monday-night-football-live-inactives-scores-news-highlights-pittsburgh-steelers-indianapolis-colts-234533715.html")
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}