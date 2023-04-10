package com.example.shared.data.network

import com.example.shared.data.network.FileReader.readFileFromResources
import com.example.shared.data.network.ListMock.listUsers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.Buffer
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
class NetworksTest {
    private val mockWebServer = MockWebServer()
    private lateinit var client: OkHttpClient
    private lateinit var api: GithubApi

    @Before
    fun setup() {
        mockWebServer.start()

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.github.com"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers() = runBlocking{

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\n" +
                    "    \"total_count\": 84037,\n" +
                    "    \"incomplete_results\": false,\n" +
                    "    \"items\": [\n" +
                    "        {\n" +
                    "            \"login\": \"super\",\n" +
                    "            \"id\": 44467,\n" +
                    "            \"node_id\": \"MDQ6VXNlcjQ0NDY3\",\n" +
                    "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/44467?v=4\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"url\": \"https://api.github.com/users/super\",\n" +
                    "            \"html_url\": \"https://github.com/super\",\n" +
                    "            \"followers_url\": \"https://api.github.com/users/super/followers\",\n" +
                    "            \"following_url\": \"https://api.github.com/users/super/following{/other_user}\",\n" +
                    "            \"gists_url\": \"https://api.github.com/users/super/gists{/gist_id}\",\n" +
                    "            \"starred_url\": \"https://api.github.com/users/super/starred{/owner}{/repo}\",\n" +
                    "            \"subscriptions_url\": \"https://api.github.com/users/super/subscriptions\",\n" +
                    "            \"organizations_url\": \"https://api.github.com/users/super/orgs\",\n" +
                    "            \"repos_url\": \"https://api.github.com/users/super/repos\",\n" +
                    "            \"events_url\": \"https://api.github.com/users/super/events{/privacy}\",\n" +
                    "            \"received_events_url\": \"https://api.github.com/users/super/received_events\",\n" +
                    "            \"type\": \"User\",\n" +
                    "            \"site_admin\": false,\n" +
                    "            \"score\": 1.0\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")


        mockWebServer.enqueue(mockResponse)
        val response = api.getUsers("super",1,1,"")

        assertEquals(listUsers.items.size, response.body()!!.items.size)

    }
}