package com.spiritflightapps.podcastlistprototype.network


import com.spiritflightapps.podcastlistprototype.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Created by neil on 10/17/17.
 */
interface ITunesRSSFeed {

// http://podrunner.wm.wizzard.tv/rss
    @GET("/rss/")
    fun getFeed(): Call<ArticleResponse>
    //e.g yields /feed?paged=2 if called with getFeed("2");
    // e.g https://jeaniesjourneys.com/feed/?paged=2



    companion object {


        val RSS_URL = "http://podrunner.wm.wizzard.tv"
    }

    // TODO: Find out if this is an ok place to get this, if there's a better one, probably there's an official one or the one he uses.
}
