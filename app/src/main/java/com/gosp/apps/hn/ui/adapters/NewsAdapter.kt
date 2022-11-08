package com.gosp.apps.hn.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gosp.apps.hn.data.models.NewsModel
import com.gosp.apps.hn.R
import com.gosp.apps.hn.data.database.entities.NewsEntity
import com.gosp.apps.hn.databinding.RowNewsBinding
import com.gosp.apps.hn.ui.fragments.NewsListFragment

class NewsAdapter(
    private val list: ArrayList<NewsEntity>,
    private val context: Context,
    private var onItemClick: (id: String) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(list[position],context,position,onItemClick)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowNewsBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun binding(
            data: NewsEntity,
            context: Context,
            position: Int,
            onItemClick: (id: String) -> Unit
        ) {
            binding.tvTitle.text = data.story_title
            binding.tvAuthor.text = "${data.author} -   ${data.created_at}"

            binding.rowNews.setOnClickListener {
                data.story_url?.let { url -> onItemClick(url) }?: run {
                    Snackbar.make(binding.rowNews,"EL ARTÍCULO NO TIENE UNA WEB ASOCIADA", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
