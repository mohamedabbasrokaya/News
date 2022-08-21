package com.mabbr.news.ui.news

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mabbr.news.ArticlesItem
import com.mabbr.news.NewsResponse
import com.mabbr.news.api.api_Manger
import com.mabbr.news.constant
import com.mabbr.news.model.SourceResponse
import com.mabbr.news.model.SourcesItem
import com.mabbr.news.ui.category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel:ViewModel() {
    val sourcesLiveData=MutableLiveData<List<SourcesItem?>?>()
    val NewsLiveData=MutableLiveData<List<ArticlesItem?>?>()
    val progressVisiible=MutableLiveData<Boolean>()
    val messageLiveData=MutableLiveData<String>()
    fun getNewsSources(category: category) {
        progressVisiible.value=true
        api_Manger.getApis()
            .getSources(constant.apiKey, category.id)
            .enqueue(object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                    progressVisiible.value=false
                    //progress_bar.isVisible = false
                    //addSourcesTOTabLayout(response.body()?.sources)
                    sourcesLiveData.value=response.body()?.sources

                }

                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                   // Log.e("error", t.localizedMessage)
                    progressVisiible.value=false
                    messageLiveData.value=t.localizedMessage

                }

            })
    }
    fun getNewsBySource(source: SourcesItem) {
        progressVisiible.value=true

        api_Manger.getApis().getNews(constant.apiKey, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    NewsLiveData.value=response.body()?.articles
                    progressVisiible.value=false
       //             progress_bar.isVisible = false
           //         adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
         //           progress_bar.isVisible = false
                    progressVisiible.value=false
                    messageLiveData.value=t.localizedMessage
                }

            })

    }




}