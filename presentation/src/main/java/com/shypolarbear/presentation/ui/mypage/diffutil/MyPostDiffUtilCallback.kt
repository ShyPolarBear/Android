package com.shypolarbear.presentation.ui.mypage.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.shypolarbear.domain.model.mypage.MyFeed

class MyPostDiffUtilCallback(private val oldList: List<Any?>, private val newList: List<Any?>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is MyFeed && newItem is MyFeed) {
            oldItem.feedId == newItem.feedId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}