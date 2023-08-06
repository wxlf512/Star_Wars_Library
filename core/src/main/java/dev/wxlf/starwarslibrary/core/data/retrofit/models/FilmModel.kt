package dev.wxlf.starwarslibrary.core.data.retrofit.models

import com.google.gson.annotations.SerializedName


data class FilmModel(
    var title: String,

    @SerializedName("episode_id")
    var episodeId: Int,

    @SerializedName("opening_crawl")
    var openingCrawl: String,
    var director: String,
    var producer: String,
    var url: String,
    var created: String,
    var edited: String,

    @SerializedName("species")
    var speciesUrls: ArrayList<String>,

    @SerializedName("starships")
    var starshipsUrls: ArrayList<String>,

    @SerializedName("vehicles")
    var vehiclesUrls: ArrayList<String>,

    @SerializedName("planets")
    var planetsUrls: ArrayList<String>,

    @SerializedName("characters")
    var charactersUrls: ArrayList<String>
)