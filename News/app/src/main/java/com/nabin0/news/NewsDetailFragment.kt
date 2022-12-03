package com.nabin0.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nabin0.news.databinding.FragmentNewsDetailBinding
import com.nabin0.news.databinding.FragmentSavedNewsBinding
import com.nabin0.news.presentation.viewmodel.NewsViewModel

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        val args: NewsDetailFragmentArgs by navArgs()
        val article = args.selectedArticle
        binding.newsDetailWebView.apply {
            webViewClient = WebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            } else {
                Toast.makeText(activity, "Url is null", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveArticleFloatingButton.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article Saved Successfully!", Snackbar.LENGTH_LONG).show()
        }
    }
}