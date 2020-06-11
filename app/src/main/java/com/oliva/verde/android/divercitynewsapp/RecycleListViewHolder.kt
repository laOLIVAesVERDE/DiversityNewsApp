package com.oliva.verde.android.divercitynewsapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var imageRow : ImageView
    var titleRow : TextView
    var publishDateRow : TextView

    init {
        imageRow = itemView.findViewById(R.id.image_row)
        titleRow = itemView.findViewById(R.id.title_row)
        publishDateRow = itemView.findViewById(R.id.publish_date_row)
    }
}