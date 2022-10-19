package com.omranic.nytimesmostpopular.ui.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = true)
fun ImageView.loadImage(url: String?, placeholder: Drawable) {
    if (url.isNullOrEmpty())
        setImageDrawable(placeholder)
    else
        Glide.with(this).load(url).placeholder(placeholder).into(this)
}
