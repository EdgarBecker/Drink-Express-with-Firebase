package com.example.drink_express_mallorca.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drink_express_mallorca.data.model.Account
import com.example.drink_express_mallorca.data.model.Drink
import com.example.drink_express_mallorca.data.model.WarenkorbItem

class Repository(val api: getraenkeAPI) {

    val warenkorb = mutableListOf<WarenkorbItem>()

    private var _getRandomCocktail = MutableLiveData<MutableList<Drink>>(mutableListOf())
    val getRandomCocktail: LiveData<MutableList<Drink>>
        get() = _getRandomCocktail

    private var _getDrinkFromName = MutableLiveData<Drink>()
    val getDrinkFromName: LiveData<Drink>
        get() = _getDrinkFromName

    private var _getDrinkFromSearch = MutableLiveData<List<Drink>>()
    val getDrinkFromSerach: LiveData<List<Drink>>
        get() = _getDrinkFromSearch

    private var _getKategorie = MutableLiveData<MutableList<Drink>>()
    val getKategorie: LiveData<MutableList<Drink>>
        get() = _getKategorie



    suspend fun loadRandomCocktail(){
        for (i in 1..10){
            _getRandomCocktail.value?.addAll(api.retrofitService.getRandomCocktail().drinks)
        }
        _getRandomCocktail.value = _getRandomCocktail.value
    }

    suspend fun loadDrinkFromName(name: String){
        val drink = api.retrofitService.getDrinkFromName(name)

        _getDrinkFromName.value = drink.drinks.first()
    }

    suspend fun loadDrinkFromSearch(name: String){
        val drink = api.retrofitService.getDrinkFromName(name)

        _getDrinkFromSearch.value = drink.drinks
        _getDrinkFromSearch.value = _getDrinkFromSearch.value
    }

    suspend fun loadKategorie(kategorie: String){
        when (kategorie){
            "alkoholfrei" -> { _getKategorie.value = api.retrofitService.getAlkoholFrei().drinks.toMutableList() }
            "alkohol" -> { _getKategorie.value = api.retrofitService.getAlkohol().drinks.toMutableList() }
            "cocktail" -> { _getKategorie.value = api.retrofitService.getCocktails().drinks.toMutableList() }
            "getränke" -> { _getKategorie.value = api.retrofitService.getGetraenke().drinks.toMutableList() }
            "champagne flute" -> { _getKategorie.value = api.retrofitService.getChampagneFlue().drinks.toMutableList() }
        }
    }
}