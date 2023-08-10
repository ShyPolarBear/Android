package com.shypolarbear.presentation.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener


object GlideUtil {
    fun loadImage(context: Context, url: String, view: ImageView) {
        initGlide(context, url).into(view)
    }

    fun loadImage(context: Context, url: String, view: ImageView, placeHolder: Int) {
        initGlide(context, url).placeholder(placeHolder).into(view)
    }

    fun loadImage(
        context: Context,
        url: String,
        view: ImageView,
        listener: RequestListener<Drawable>,
    ) {
        initGlide(context, url).listener(listener).into(view)
    }

    fun loadImage(context: Context, uri: Uri, view: ImageView) {
        initGlide(context, uri = uri).into(view)
    }

    fun loadCircleImage(context: Context, uri: Uri, view: ImageView) {
        initGlide(context, uri = uri).centerCrop().circleCrop().into(view)
    }

    fun loadImage(context: Context, urd: Int, view: ImageView) {
        initGlide(context, urd = urd).into(view)
    }

    private fun initGlide(context: Context, url: String? = null, uri: Uri? = null, urd: Int? = null) = run {
        if (uri != null) Glide.with(context).load(uri)
        else if (url != null) Glide.with(context).load(url)
        else Glide.with(context).load(urd)
    }

}