package com.otis.homepractical.ui.main

import android.content.Context
import androidx.lifecycle.*
import com.otis.homepractical.models.Pro
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainViewModel : ViewModel() {

    val proDataLiveData: MutableLiveData<Array<Pro>> = MutableLiveData<Array<Pro>> ()

    fun loadProData (context: Context) {
        viewModelScope.launch {
            proDataLiveData.postValue(loadProDataFromAssets(context))
        }
    }

    private fun loadProDataFromAssets (context: Context): Array<Pro> {
        val i: InputStream = context.assets.open("pro_data.json")
        val br = BufferedReader(InputStreamReader(i))
        val array = Gson().fromJson(br, Array<Pro>::class.java)
        array.sortBy { it.companyName }
        return array
    }
}