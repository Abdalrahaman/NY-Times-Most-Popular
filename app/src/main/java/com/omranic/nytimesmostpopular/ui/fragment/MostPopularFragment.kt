package com.omranic.nytimesmostpopular.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.omranic.nytimesmostpopular.R
import com.omranic.nytimesmostpopular.data.model.Resource
import com.omranic.nytimesmostpopular.data.model.pojo.Article
import com.omranic.nytimesmostpopular.databinding.FragmentMostPopularBinding
import com.omranic.nytimesmostpopular.ui.adapter.MostPopularAdapter
import com.omranic.nytimesmostpopular.ui.viewmodel.MostPopularViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MostPopularFragment :
    BaseFragment<FragmentMostPopularBinding>(FragmentMostPopularBinding::inflate),
    MostPopularAdapter.ArticleOnClickHandler, SearchView.OnQueryTextListener {

    @Inject
    lateinit var mostPopularAdapter: MostPopularAdapter

    private val mostPopularViewModel: MostPopularViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostPopularAdapter.setArticleListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        // Initialize loading articles with time period 1 day
        mostPopularViewModel.getMostViewedArticles(1)

        handleObservers()
    }

    private fun initViews() {
        with(binding) {
            mostPopularRecyclerView.adapter = mostPopularAdapter
        }
    }

    private fun handleObservers() {
        mostPopularViewModel.mostPopularResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    response.data?.let {
                        mostPopularAdapter.setArticlesData(it)
                    }
                }
                is Resource.Error -> {
                    Timber.e(response.message)
                    binding.progressIndicator.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search)
            setOnQueryTextListener(this@MostPopularFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.period_1_day -> {
            mostPopularViewModel.getMostViewedArticles(1)
            true
        }

        R.id.period_7_day -> {
            mostPopularViewModel.getMostViewedArticles(7)
            true
        }

        R.id.period_30_day -> {
            mostPopularViewModel.getMostViewedArticles(30)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun filterWithArticleTitle(text: String) {
        val filteredList = ArrayList<Article>()

        for (article in mostPopularViewModel.mostPopularResult.value!!.data!!) {
            if (article.title.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(article)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            mostPopularAdapter.setArticlesData(filteredList)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filterWithArticleTitle(newText!!)
        return false
    }

    override fun onArticleItemClicked(article: Article) {
        mostPopularViewModel.onArticleItemSelected(article)
        findNavController().navigate(MostPopularFragmentDirections.actionMostPopularFragmentToDetailsFragment())
    }

}