package com.spiritflightapps.podcastlistprototype

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.spiritflightapps.podcastlistprototype.model.ArticleResponse
import com.spiritflightapps.podcastlistprototype.network.ITunesRSSFeed
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val retrofit = Retrofit.Builder()
                .baseUrl(ITunesRSSFeed.RSS_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()

        val service = retrofit.create(ITunesRSSFeed::class.java)

        val feedCall = service.getFeed()
        // https://github.com/ikocijan/Kotlin-Dagger2-Retrofit2-example/blob/master/app/src/main/kotlin/xyz/ivankocijan/kotlinexample/mvp/interactor/impl/PokemonListInteractorImpl.kt
        feedCall.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>?, response: Response<ArticleResponse>?) {
                if (response != null && response.isSuccessful) {
                    val articleResponse : ArticleResponse? = response.body()
                    Log.e("NJW", articleResponse?.channel?.title)
                                                                 Log.e("NJW", "size ${articleResponse?.channel?.items?.size}")
                    val firstTitle = articleResponse?.channel?.items?.first()?.title
                    Log.e("NJW", "first $firstTitle")
                    val firstLink = articleResponse?.channel?.items?.first()?.link
                    Log.e("NJW", "first $firstLink")

                    val text_message = findViewById<TextView>(R.id.message)
                    text_message.text = "http://traffic.libsyn.com/podrunner/122bpm_mindwalk.mp3"
                    // noet: this is enclosure:url
                    // TODO: populate adapter...                                  maybe leave off pics for now so offline mode works better/easier and no jank issues


                } else {
                    // pokemonListListener.onFailure(appContext.getString(R.string.error_fetching_data))
                                        Log.e("NJW", "RSS Feed fetch unsuccessful"  )


                }

            }

            override fun onFailure(call: Call<ArticleResponse>?, t: Throwable?) {
                                                        Log.e("NJW", "RSS Feed fetch error"  )

            }

        })
    }
}

