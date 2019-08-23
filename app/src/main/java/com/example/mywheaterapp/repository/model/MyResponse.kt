package com.example.mywheaterapp.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyResponse (

    @SerializedName("base")
    @Expose
    var base: String? = null,

    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null,

    @SerializedName("clouds")
    @Expose
    var dt: Int? = null,

    @SerializedName("sys")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("cod")
    @Expose
    var cod: Int? = null


)