package com.example.photoshaker.ui

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photoshaker.R
import com.example.photoshaker.data.Photo
import com.example.photoshaker.databinding.ViewHolderPhotoBinding


class SavedPhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    private val binding = ViewHolderPhotoBinding.bind(itemView)

    fun onBind(photo: Photo) {
        Glide.with(itemView.context)
            .load(photo.urls)
            .apply(imageOption)
            .into(binding.ivPhotos)

        binding.tvUrl.text = itemView.resources.getString(R.string.base_url) + photo.id
        Log.d("Parcel", "photo.id = ${photo.id}")
    }

    companion object {
        val imageOption = RequestOptions()
            .placeholder(R.drawable.bg)
            .fallback(R.drawable.bg)
            .centerCrop()
    }
}