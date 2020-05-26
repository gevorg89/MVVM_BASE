package com.example.presentation.common.common.extension

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File

const val MAX_WIDTH_IMAGE = 1080
const val MIN_WIDTH_IMAGE = 100

@BindingAdapter("loadUrl")
fun loadUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let { view.loadUrl(it) }
}

fun ImageView.loadUri(
    uri: Uri,
    skipCache: Boolean = false,
    fit: Boolean = false,
    signature: Key? = null,
    skipSize: Boolean = false
) {
    loadUrl(
        uri.toString(),
        skipCache = skipCache,
        fit = fit,
        skipSize = skipSize,
        signature = signature
    ) {}
}

fun ImageView.loadUrl(
    url: String,
    skipCache: Boolean = false,
    fit: Boolean = false,
    signature: Key? = null,
    skipSize: Boolean = false
) {
    loadUrl(url, skipCache = skipCache, fit = fit, skipSize = skipSize, signature = signature) {}
}

fun ImageView.loadUrl(
    url: String,
    skipCache: Boolean = false,
    fit: Boolean = false,
    skipSize: Boolean = false,
    signature: Key? = null,
    action: (resource: Drawable?) -> Unit
) {
    when {
        !skipSize -> {
            when {
                this.measuredHeight != 0 || this.measuredWidth != 0 -> {
                    val size = when (this.measuredWidth) {
                        0 -> this.measuredHeight
                        else -> this.measuredWidth
                    }
                    loadUrlSize(url, skipCache, fit, size, signature) { drawable ->
                        action(drawable)
                    }
                }
                else -> {
                    this.doOnPreDraw {
                        if (url.isEmpty()) {
                            return@doOnPreDraw
                        }
                        loadUrlSize(url, skipCache, fit, it.measuredWidth, signature) { drawable ->
                            action(drawable)
                        }
                    }
                }
            }
        }
        else -> {
            loadUrlSize(url, skipCache, fit, null) { drawable ->
                action(drawable)
            }
        }
    }

}

private fun ImageView.loadUrlSize(
    url: String,
    skipCache: Boolean = false,
    fit: Boolean = false,
    size: Int? = null,
    signature: Key? = null,
    action: (resource: Drawable?) -> Unit
) {
    if (url.isEmpty()) {
        return
    }
    var computedSize = size
    computedSize?.let {
        if (it > MAX_WIDTH_IMAGE) {
            computedSize = MAX_WIDTH_IMAGE
        }
        if (it < MIN_WIDTH_IMAGE) {
            computedSize = MIN_WIDTH_IMAGE
        }
    }
    val requestCreator = when (skipCache) {
        true -> Glide.with(this).load(url).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        false -> Glide.with(this).load(
            when (computedSize) {
                null -> url
                else -> "$url&size=$computedSize"
            }
        )
    }.apply {
        signature?.let { signature(it) }
        if (fit) {
            centerCrop()
        }
    }
    requestCreator.addListener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            action(null)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            action(resource)
            return false
        }

    }).into(this)
}

fun ImageView.loadWithLoader(
    url: String,
    fit: Boolean = false,
    loader: View,
    signature: Key? = null,
    skipCache: Boolean = false
) {
    loadUrl(url, skipCache = false, fit = fit, signature = signature, skipSize = skipCache) {
        loader.gone()
    }
}

fun ImageView.loadFile(file: File, skipSize: Boolean = false) {
    when {
        !skipSize -> {
            when {
                this.measuredHeight != 0 || this.measuredWidth != 0 -> {
                    val size = when (this.measuredWidth) {
                        0 -> this.measuredHeight
                        else -> this.measuredWidth
                    }
                    loadFileSize(file, size)
                }
                else -> {
                    this.doOnPreDraw {
                        if (file.exists().not()) {
                            return@doOnPreDraw
                        }
                        loadFileSize(file, it.measuredWidth)
                    }
                }
            }
        }
        else -> {
            Glide.with(this).load(file).into(this)
        }
    }
}

fun ImageView.loadFileSize(file: File, size: Int) {
    Glide.with(this).load(file).override(size).centerCrop().into(this)
}