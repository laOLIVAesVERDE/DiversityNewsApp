package com.oliva.verde.android.divercitynewsapp.domain.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

// imageUrl属性が存在する要素に対して自動で呼ばれる
object ImageLoadingUtil {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url : String?) {
        Picasso.get().load(url).into(this)
    }
}