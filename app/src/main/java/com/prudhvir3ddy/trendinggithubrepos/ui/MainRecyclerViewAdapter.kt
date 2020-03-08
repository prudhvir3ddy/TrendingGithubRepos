package com.prudhvir3ddy.trendinggithubrepos.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prudhvir3ddy.trendinggithubrepos.R
import com.prudhvir3ddy.trendinggithubrepos.database.UIResponse
import com.prudhvir3ddy.trendinggithubrepos.ui.MainRecyclerViewAdapter.MainRecyclerViewHolder
import kotlinx.android.synthetic.main.item_model.view.description
import kotlinx.android.synthetic.main.item_model.view.imageView
import kotlinx.android.synthetic.main.item_model.view.name

object DiffCallback : DiffUtil.ItemCallback<UIResponse>() {
  override fun areContentsTheSame(oldItem: UIResponse, newItem: UIResponse): Boolean {
    return oldItem.description == newItem.description
  }

  override fun areItemsTheSame(oldItem: UIResponse, newItem: UIResponse): Boolean {
    return oldItem == newItem
  }
}

class MainRecyclerViewAdapter : ListAdapter<UIResponse, MainRecyclerViewHolder>(DiffCallback) {

  lateinit var context: Context

  class MainRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.imageView
    val name: TextView = itemView.name
    val description: TextView = itemView.description
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
    context = parent.context
    return MainRecyclerViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.item_model,
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
    val item = getItem(position)
    Glide.with(context)
      .load(item.avatar)
      .apply(RequestOptions.circleCropTransform())
      .into(holder.imageView)
    holder.name.text = item.author
    holder.description.text = item.name
  }

}