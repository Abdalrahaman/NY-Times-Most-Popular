package com.omranic.nytimesmostpopular.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omranic.nytimesmostpopular.data.model.pojo.Article
import com.omranic.nytimesmostpopular.databinding.ItemArticleBinding
import javax.inject.Inject

class MostPopularAdapter @Inject constructor() :
    RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {

    private lateinit var articleListener: ArticleOnClickHandler
    private var articles = ArrayList<Article>()

    interface ArticleOnClickHandler {
        fun onArticleItemClicked(article: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setArticlesData(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    fun setArticleListener(articleListener: ArticleOnClickHandler) {
        this.articleListener = articleListener
    }

    inner class MostPopularViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var articleSelected: Article

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(article: Article) {
            article.let {
                articleSelected = it
                binding.article = it
            }
        }

        override fun onClick(v: View?) {
            articleListener.onArticleItemClicked(articleSelected)
        }

    }
}