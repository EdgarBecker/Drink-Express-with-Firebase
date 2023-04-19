package com.example.drink_express_mallorca

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drink_express_mallorca.data.Repository
import com.example.drink_express_mallorca.data.getraenkeAPI
import com.example.drink_express_mallorca.data.model.WarenkorbItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    enum class ApiStatus { LOADING, DONE, ERROR }

    private val repository = Repository(getraenkeAPI)

    val auth = FirebaseAuth.getInstance()

    val randomCocktailList = repository.getRandomCocktail
    val drinkName = repository.getDrinkFromName
    val drinkSearch = repository.getDrinkFromSerach
    val kategorieWahl = repository.getKategorie
    val warenkorb = mutableListOf<WarenkorbItem>()

    private var _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private var _warkorbList = MutableLiveData<MutableList<WarenkorbItem>>(warenkorb)
    val warkorbList: LiveData<MutableList<WarenkorbItem>>
        get() = _warkorbList

    private var _warenkorbTotalPreis = MutableLiveData<Double>(0.00)
    val warenkorbTotalPreis: LiveData<Double>
        get() = _warenkorbTotalPreis

    private var _detailGetraenkeAnzahl = MutableLiveData<Int>(1)
    val detailGetraenkeAnzahl: LiveData<Int>
        get() = _detailGetraenkeAnzahl

    private var _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    private var _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init {
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadRandomCocktail()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                _loading.value = ApiStatus.ERROR
            }

        }
    }

    fun anmelden(email: String, passw: String){
        auth.signInWithEmailAndPassword(email, passw)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _currentUser.value = auth.currentUser
                } else {
                    _toast.value = it.exception?.message
                    _toast.value = null
                }
            }
    }

    fun registrieren(email: String, passw: String){
        auth.createUserWithEmailAndPassword(email, passw)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    anmelden(email, passw)
                } else {
                    _toast.value = it.exception?.message
                    _toast.value = null
                }
            }
    }

    fun abmelden(){
        auth.signOut()
        _currentUser.value = auth.currentUser
    }



    fun loadDrinkFromName(name: String){
        viewModelScope.launch {
            repository.loadDrinkFromName(name)
        }
    }

    fun loadDrinkFromSearch(name: String){
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadDrinkFromSearch(name)
                _loading.value = ApiStatus.DONE
            } catch (e: Exception){
                _loading.value = ApiStatus.ERROR
                Log.d("MainViewModel", "Search invalid : -> $e")
            }
        }
    }

    fun getraenkeAnzahlAendern(onClick: Boolean){
        when (onClick) {
            true -> _detailGetraenkeAnzahl.value = _detailGetraenkeAnzahl.value?.plus(1)
            else -> {
                if(_detailGetraenkeAnzahl.value == 0){
                    _detailGetraenkeAnzahl.value = 0
                } else {
                    _detailGetraenkeAnzahl.value = _detailGetraenkeAnzahl.value?.minus(1)
                }
            }
        }
    }
    fun resetGetraenkAnzahl(){
        _detailGetraenkeAnzahl.value = 1
    }

    fun loadKategorie(kategorie: String){
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadKategorie(kategorie)
                _loading.value = ApiStatus.DONE
            } catch (e: Exception){
                _loading.value = ApiStatus.ERROR
                Log.d("MainViewModel", "Kategorie invalid : -> $e")
            }
        }
    }

    fun loadWarkorbList(){
        _warkorbList.value = warenkorb
    }

    fun warenkorbHinzufügen(image: String?, anzahl: Int, preis: Double){
        val addItem = WarenkorbItem(image, anzahl, preis)
        warenkorb.add(addItem)
    }

    fun preisKalkulation(warenkorb: MutableList<WarenkorbItem>){
        _warenkorbTotalPreis.value = 0.00
        for (i in warenkorb){
            val totalPreis = i.preis * i.anzahl
            _warenkorbTotalPreis.value = _warenkorbTotalPreis.value?.plus(totalPreis)
        }

    }

    fun checkForDuplicates(image: String, anzahl: Int): Boolean{
        for (i in warenkorb){
            if (i.image == image){
                i.anzahl += anzahl
                return true
            }
        }
        return false
    }
}