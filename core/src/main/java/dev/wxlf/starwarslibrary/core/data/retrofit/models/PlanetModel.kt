package dev.wxlf.starwarslibrary.core.data.retrofit.models

import com.google.gson.annotations.SerializedName


data class PlanetModel(
    var name: String,
    var diameter: String,
    var gravity: String,
    var population: String,
    var climate: String,
    var terrain: String,
    var created: String,
    var edited: String,
    var url: String,

    @SerializedName("rotation_period")
    var rotationPeriod: String,
    
    @SerializedName("orbital_period")
    var orbitalPeriod: String,
    
    @SerializedName("surface_water")
    var surfaceWater: String,
    
    @SerializedName("residents")
    var residentsUrls: ArrayList<String>,
    
    @SerializedName("films")
    var filmsUrls: ArrayList<String>
)