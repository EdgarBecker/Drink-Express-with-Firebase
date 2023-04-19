package com.example.drink_express_mallorca.data

import com.example.drink_express_mallorca.data.model.ServerResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://www.thecocktaildb.com"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface getraenkeApiServices {
     @GET("/api/json/v1/1/random.php")
     suspend fun getRandomCocktail() : ServerResponse

     @GET("/api/json/v1/1/search.php?")
     suspend fun getDrinkFromName(
         @Query("s") search: String
     ) : ServerResponse

     @GET("/api/json/v1/1/filter.php?a=Non_Alcoholic")
     suspend fun getAlkoholFrei() : ServerResponse

     @GET("/api/json/v1/1/filter.php?a=Alcoholic")
     suspend fun getAlkohol() : ServerResponse

     @GET("/api/json/v1/1/filter.php?c=Cocktail")
     suspend fun getCocktails() : ServerResponse

     @GET("/api/json/v1/1/filter.php?c=Ordinary_Drink")
     suspend fun getGetraenke() : ServerResponse

     @GET("/api/json/v1/1/filter.php?g=Champagne_flute")
     suspend fun getChampagneFlue() : ServerResponse
}

object getraenkeAPI {
    val retrofitService: getraenkeApiServices by lazy { retrofit.create(getraenkeApiServices::class.java) }
}
