package com.mabbr.news.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mabbar.newsapp.R
import com.mabbar.newsapp.databinding.FragmentNewsBinding
import com.mabbr.news.ArticlesItem
import com.mabbr.news.NewsResponse
import com.mabbr.news.api.api_Manger
import com.mabbr.news.constant
import com.mabbr.news.ui.News_adapter
import com.mabbr.news.model.SourceResponse
import com.mabbr.news.model.SourcesItem
import com.mabbr.news.ui.category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class newsFragment : Fragment() {
    companion object {


        fun getInstance(category: category): newsFragment {
            val fragment = newsFragment()
            fragment.category = category
            return fragment
        }

    }

    lateinit var category: category
    lateinit var viewDataBinding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   return inflater.inflate(R.layout.fragment_news, container, false)
       viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return viewDataBinding.root

    }

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(NewsViewModel::class.java)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial_item()
        subscribeToLiveData()

        viewModel.getNewsSources(category)
    }

     fun subscribeToLiveData() {
    viewModel.sourcesLiveData.observe(viewLifecycleOwner,
        {
            addSourcesTOTabLayout(it)
        })
         viewModel.NewsLiveData.observe(viewLifecycleOwner,{
             showNews(it)
         })
         viewModel.progressVisiible.observe(viewLifecycleOwner,{isVisible->
            viewDataBinding.progressBar.isVisible=isVisible
//             if(isVisible)
//                 progress_bar.isVisible=true
//             else
//                 progress_bar.isVisible=false
         })
         viewModel.messageLiveData.observe(viewLifecycleOwner,{message->
             Toast.makeText(activity,message,Toast.LENGTH_LONG).show()


         })

    }

    private fun showNews(newList: List<ArticlesItem?>?) {
        adapter.changeData(newList)
        //obserable has data
        //observer want to subscribe

    }

    val adapter = News_adapter(null)

    fun initial_item() {
        //ui matters
        viewDataBinding.RecycleView .adapter = adapter
    }


    fun addSourcesTOTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = viewDataBinding.tabLayout.newTab()
            tab.setText(it?.name);
            tab.tag = it
            viewDataBinding.tabLayout.addTab(tab);
        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
        viewModel.getNewsBySource(source)
                   // getNewsBySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                viewModel.getNewsBySource(source)
                //   getNewsBySource(source)

                }

            }
        )
        viewDataBinding.tabLayout.getTabAt(0)?.select()

    }

}