package com.prudhvir3ddy.trendinggithubrepos.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prudhvir3ddy.trendinggithubrepos.R
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.databinding.ItemModelBinding
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRecyclerViewAdapter.MainRecyclerViewHolder

object DiffCallback : DiffUtil.ItemCallback<UIResponse>() {
  override fun areContentsTheSame(oldItem: UIResponse, newItem: UIResponse): Boolean {
    return oldItem.description == newItem.description
  }

  override fun areItemsTheSame(oldItem: UIResponse, newItem: UIResponse): Boolean {
    return oldItem == newItem
  }
}

class MainRecyclerViewAdapter : ListAdapter<UIResponse, MainRecyclerViewHolder>(DiffCallback) {
  private var mExpandedPosition = -1
  private var mPreviousExpandedPosition = -1
  lateinit var context: Context

  class MainRecyclerViewHolder(val binding: ItemModelBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
    context = parent.context
    val binding: ItemModelBinding = DataBindingUtil.inflate(
      LayoutInflater.from(context),
      R.layout.item_model,
      parent,
      false
    )
    return MainRecyclerViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
    val isExpanded = position == mExpandedPosition
    holder.binding.expandedItemContent.visibility = if (isExpanded) View.VISIBLE else View.GONE
    holder.itemView.isActivated = isExpanded
    if (isExpanded) {
      mPreviousExpandedPosition = position
    }
    holder.binding.root.setOnClickListener {
      mExpandedPosition = if (isExpanded) -1 else position
      notifyItemChanged(mExpandedPosition)
      notifyItemChanged(mPreviousExpandedPosition)
    }
    val item = getItem(position)
    val drawable: GradientDrawable =
      holder.binding.languageIv.background.mutate() as GradientDrawable
    val color = Color.parseColor(item.languageColor ?: "#000000")
    drawable.setColor(color)

    Glide.with(context)
      .load(item.avatar)
      .apply(RequestOptions.circleCropTransform())
      .into(holder.binding.avatarIv)

    holder.binding.apply {
      authorTv.text = item.author
      nameTv.text = item.name
      descriptionTv.text = item.description
      languageTv.text = item.language
      starCountTv.text = item.stars.toString()
      forkTv.text = item.forks.toString()
    }
  }

}