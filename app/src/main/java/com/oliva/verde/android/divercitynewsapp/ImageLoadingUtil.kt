package com.oliva.verde.android.divercitynewsapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ImageLoadingUtil {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(
        imageView: ImageView,
        urlToImage : String
    ) {
        Picasso.get()
            .load(urlToImage)
            .into(imageView)
    }
}