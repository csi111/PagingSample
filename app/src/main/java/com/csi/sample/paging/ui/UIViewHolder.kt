package com.csi.sample.paging.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class UIViewHolder<T : PagingUIModel>(root: ViewGroup) : RecyclerView.ViewHolder(root) {

    abstract fun onBind(item: T)
}