package com.scribblex.tutorial.data.models

import com.google.gson.annotations.SerializedName


data class Categories(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("products")
    val products: List<Products>
)
