package com.example.photoshaker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.photoshaker.R
import com.example.photoshaker.data.Photo
import com.example.photoshaker.databinding.FragmentPhotoBinding
import com.example.photoshaker.presentation.FragmentPhotoViewModel
import com.example.photoshaker.presentation.PhotoViewModelFactory

class FragmentPhoto : Fragment() {

    private lateinit var viewModel: FragmentPhotoViewModel

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)

        val viewModelFactory = PhotoViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(FragmentPhotoViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
            viewModel.photoData.observe(viewLifecycleOwner, {
                setPhotoData(it)
            })
    }

    private fun setPhotoData(photo: Photo) {
        photo.urls.let {
            Glide.with(requireContext())
                .load(photo.urls)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(imageOption)
                .into(binding.ivPhoto)
        }
    }

    val imageOption = RequestOptions()
        .placeholder(R.drawable.bg)
        .fallback(R.drawable.bg)
        .centerCrop()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}