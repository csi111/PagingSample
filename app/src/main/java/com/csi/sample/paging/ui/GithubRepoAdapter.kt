package com.csi.sample.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.csi.sample.paging.R
import com.csi.sample.paging.databinding.GithubRepoItemBinding
import com.csi.sample.paging.databinding.SeparatorItemBinding

class GithubRepoAdapter : PagingDataAdapter<PagingUIModel, UIViewHolder<*>>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingUIModel>() {
            override fun areItemsTheSame(oldItem: PagingUIModel, newItem: PagingUIModel): Boolean {
                return (oldItem is PagingUIModel.GithubRepoItem &&
                        newItem is PagingUIModel.GithubRepoItem &&
                        oldItem.repo.id == newItem.repo.id) ||
                        (oldItem is PagingUIModel.SeparatorItem &&
                                newItem is PagingUIModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(
                oldItem: PagingUIModel,
                newItem: PagingUIModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingUIModel.GithubRepoItem -> R.layout.github_repo_item
            is PagingUIModel.SeparatorItem -> R.layout.separator_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: UIViewHolder<*>, position: Int) {
        when (holder) {
            is GithubRepoViewHolder -> {
                holder.onBind(getItem(position) as PagingUIModel.GithubRepoItem)
            }
            is SeparatorViewHolder -> {
                holder.onBind(getItem(position) as PagingUIModel.SeparatorItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIViewHolder<*> {
        return when (viewType) {
            R.layout.github_repo_item -> GithubRepoViewHolder(
                GithubRepoItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.separator_item -> SeparatorViewHolder(
                SeparatorItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw UnsupportedOperationException("Unknown type")
        }
    }
}