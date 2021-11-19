package com.android.kit.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.android.kit.R
import com.android.kit.logD
import com.squareup.picasso.Picasso
import java.lang.Exception

class KitImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {


    private var errorImageRes: Int = R.drawable.transparent_placeholder
    private var placeholderImageRes: Int = R.drawable.transparent_placeholder

    var imageUrl: String? = null
        set(value) {
            field = value
            value?.let { url ->
                Picasso.get()
                    .load(url)
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .error(errorImageRes)
                    .placeholder(placeholderImageRes)
                    .into(this@KitImageView)
                logD("Loaded: $url")
            } ?: kotlin.run {
                setImageResource(R.drawable.transparent_placeholder)
            }
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.KitImageView, 0, 0).apply {
            try {
                placeholderImageRes = getResourceId(
                    R.styleable.KitImageView_placeholderSrc,
                    R.drawable.transparent_placeholder
                )
                errorImageRes = getResourceId(
                    R.styleable.KitImageView_errorSrc,
                    R.drawable.transparent_placeholder
                )
                imageUrl = getString(R.styleable.KitImageView_imageUrl)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                recycle()
            }
        }
    }
}