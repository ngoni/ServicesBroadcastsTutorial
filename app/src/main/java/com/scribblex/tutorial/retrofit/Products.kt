package com.scribblex.tutorial.retrofit

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("id")
    val id: Int,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("salePrice")
    val salePrice: SalePrice
)

