package com.example.photoshaker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoshaker.R
import com.example.photoshaker.data.Photo

class SavedPhotosAdapter :  RecyclerView.Adapter<SavedPhotosViewHolder>() {

    private var photosList = emptyList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPhotosViewHolder =
        SavedPhotosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.view_holder_photo,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = photosList.size

    override fun onBindViewHolder(holder: SavedPhotosViewHolder, position: Int) {
        holder.onBind(photosList[position])
    }

    fun bindPhotos(newPhotos: List<Photo>) {
        photosList = newPhotos
        notifyDataSetChanged()
    }
}