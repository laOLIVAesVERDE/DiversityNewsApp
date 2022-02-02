package com.oliva.verde.android.divercitynewsapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    val name: String
)