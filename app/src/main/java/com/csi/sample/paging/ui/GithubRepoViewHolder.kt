package com.csi.sample.paging.ui

import android.view.View
import com.csi.sample.paging.R
import com.csi.sample.paging.databinding.GithubRepoItemBinding

class GithubRepoViewHolder(private val viewBinding: GithubRepoItemBinding) :
    UIViewHolder<PagingUIModel.GithubRepoItem>(viewBinding.root) {

    override fun onBind(item: PagingUIModel.GithubRepoItem) {
        viewBinding.repoName.text = item.repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (item.repo.description != null) {
            viewBinding.repoDescription.text = item.repo.description
            descriptionVisibility = View.VISIBLE
        }
        viewBinding.repoDescription.visibility = descriptionVisibility

        viewBinding.repoStars.text = item.repo.stars.toString()
        viewBinding.repoForks.text = item.repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!item.repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            viewBinding.repoLanguage.text =
                resources.getString(R.string.language, item.repo.language)
            languageVisibility = View.VISIBLE
        }
        viewBinding.repoLanguage.visibility = languageVisibility
    }
}