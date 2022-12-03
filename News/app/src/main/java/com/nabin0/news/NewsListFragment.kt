package com.nabin0.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nabin0.news.data.util.Resource
import com.nabin0.news.databinding.FragmentNewsListBinding
import com.nabin0.news.presentation.adapter.NewsAdapter
import com.nabin0.news.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsListFragment : Fragment() {
    private lateinit var fragmentNewsListBinding: FragmentNewsListBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this  fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsListBinding = FragmentNewsListBinding.bind(view)
        fragmentNewsListBinding.newsSearchView.setIconifiedByDefault(false)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_newsListFragment_to_newsDetailFragment,
                bundle
            )
        }
        initRecyclerView()
        viewNewsList()

    }

    override fun onResume() {
        super.onResume()

        setSearchView()
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if (it.totalResults / 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setSearchView() {
        fragmentNewsListBinding.newsSearchView.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchedNewsHeadLines(country, query.toString(), page)
                searchedNewsList(country, query.toString(), page)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedNewsHeadLines(country, newText.toString(), page)
                    searchedNewsList(country, newText.toString(), page)
                }
                return false
            }

        })

        fragmentNewsListBinding.newsSearchView.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }
    }

    fun searchedNewsList(country: String, searchQuery: String, page: Int) {
        viewModel.getSearchedNewsHeadLines(country, searchQuery, page)
        viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if (it.totalResults / 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun initRecyclerView() {
        fragmentNewsListBinding.newsHeadlinesRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsListFragment.onScrollListener)
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsListBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsListBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =
                fragmentNewsListBinding.newsHeadlinesRecyclerView.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItemCount = layoutManager.childCount
            val topItemPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topItemPosition + visibleItemCount >= sizeOfTheCurrentList
            val shouldPaginateToNext = !isLastPage && !isLoading && hasReachedToEnd && isScrolling
            if (shouldPaginateToNext) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }
        }
    }
}