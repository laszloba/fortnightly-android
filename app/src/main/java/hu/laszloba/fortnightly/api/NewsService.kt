package hu.laszloba.fortnightly.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import hu.laszloba.fortnightly.BuildConfig
import hu.laszloba.fortnightly.data.api.TopHeadlinesApiModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.Date

interface NewsService {

    @GET("/v2/top-headlines?country=us&pageSize=15&apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlines(): Response<TopHeadlinesApiModel>

    companion object {
        private const val BASE_URL = "https://newsapi.org/"

        fun create(): NewsService {
            val moshi = Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NewsService::class.java)
        }
    }
}
