package com.csi.sample.paging.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.csi.sample.paging.data.remote.GithubRepoDto

class GithubRepoAdapter : PagingDataAdapter<GithubRepoDto, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubRepoDto>() {
            override fun areItemsTheSame(oldItem: GithubRepoDto, newItem: GithubRepoDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubRepoDto, newItem: GithubRepoDto): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }
}