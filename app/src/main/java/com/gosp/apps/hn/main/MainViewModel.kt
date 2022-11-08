package com.gosp.apps.hn.main

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.gosp.apps.hn.R
import com.gosp.apps.hn.data.NewsRepository
import com.gosp.apps.hn.data.database.entities.NewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: NewsRepository) : ViewModel()  {


    var dataList = MutableLiveData<ArrayList<NewsEntity>>()
    var urlNews : String = ""

    fun getAllNewsFromApi(context: Context){
        viewModelScope.launch {
            try {
                if (!checkInternetOn(context)){
                    Log.e("EL RESULTADO ES:", "--------NO INTERNET----------")
                } else {
                    val searchList = repo.getAllNewsFromApi()
                   if (searchList != null){
                        insertAllNewsList(searchList?.hits)
                   }
                }
           }catch (e:Exception){
                Log.e("EL RESULTADO ES:", "--------catch---------- " + e.message.toString())
            }
        }
    }

    private fun checkInternetOn(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = cm.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }

    fun getDataInDataBase(context: Context) {
        viewModelScope.launch {
            val dataRepo = repo.getAllNewsFromDataBase() as ArrayList<NewsEntity>?
            if (dataRepo.isNullOrEmpty()) {
                getAllNewsFromApi(context)
            } else {
                val apiList = repo.getAllNewsFromApi()
                if (apiList != null){
                    checkForNews(apiList?.hits)
                }
            }
        }
    }
    fun checkForNews (apiList: ArrayList<NewsEntity>) {
        viewModelScope.launch {
            for (news in apiList) {
                val newsInData = repo.getNewsFromDataBase(news.objectID)
                if (newsInData == null){
                    repo.insertNews(news)
                }
            }
            getAllNews()
        }
    }

     fun insertAllNewsList(list: ArrayList<NewsEntity>){
        viewModelScope.launch {
            repo.insertAllNews(list)
            getAllNews()
        }
    }

     fun getAllNews() {
        viewModelScope.launch {
            dataList.value = repo.getAllNewsFromDataBase() as ArrayList<NewsEntity>?
        }
    }

    fun hideNews(hide: NewsEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.hideNews(hide)
        }
    }
}