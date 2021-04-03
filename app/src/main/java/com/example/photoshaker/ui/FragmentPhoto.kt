package com.example.photoshaker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.photoshaker.data.Photo
import com.example.photoshaker.databinding.FragmentPhotoBinding
import com.example.photoshaker.domain.State
import com.example.photoshaker.presentation.FragmentPhotoViewModel
import com.example.photoshaker.presentation.PhotoViewModelFactory
import com.example.photoshaker.presentation.ShakeSensorViewModel

class FragmentPhoto : Fragment() {

    private lateinit var viewModel: FragmentPhotoViewModel
    private val shakeSensorViewModel: ShakeSensorViewModel by viewModels()

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

        viewModel.photoLast.observe(viewLifecycleOwner, {
            setPhotoData(it)
        })

        shakeSensorViewModel.shakeSensorLiveData.observe(viewLifecycleOwner, {
            if(it) {
                viewModel.StartDownTimer()
                setObservers()
            }
        })
    }

    private fun setObservers() {
            viewModel.photoData.observe(viewLifecycleOwner, {
                setPhotoData(it)
            })
        viewModel.countTimer.observe(viewLifecycleOwner, {
            binding.tvCount.text = it.toString()
        })

        viewModel.state.observe(viewLifecycleOwner, { status ->
            when (status) {
                is State.Start -> {
                    binding.tvCount.visibility = View.VISIBLE
                    shakeSensorViewModel.stopSensor()
                }
                is State.Init, is State.Loading -> {
                    shakeSensorViewModel.setUpSensor()
                }
                is State.Stop -> {
                    binding.tvCount.visibility = View.INVISIBLE
                }
                is State.Error -> {
                    binding.tvCount.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun setPhotoData(photo: Photo) {
        photo.urls.let {
            Glide.with(requireContext())
                .load(photo.urls)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(SavedPhotosViewHolder.imageOption)
                .into(binding.ivPhoto)
        }
    }

    override fun onResume() {
        super.onResume()
        shakeSensorViewModel.setUpSensor()

    }
    override fun onPause() {
        shakeSensorViewModel.stopSensor()
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}