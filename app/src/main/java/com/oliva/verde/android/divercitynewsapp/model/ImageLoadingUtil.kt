package com.oliva.verde.android.divercitynewsapp.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


object ImageLoadingUtil {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url : String?) {
        Picasso.get().load(url).into(this)
    }
}