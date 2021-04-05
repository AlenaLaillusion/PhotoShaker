package com.example.photoshaker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoshaker.data.Photo
import com.example.photoshaker.databinding.FragmentSavedPhotosBinding
import com.example.photoshaker.presentation.FragmentSavedPhotosViewModel
import com.example.photoshaker.presentation.SavedPhotosViewModelFactory


class FragmentSavedPhotos : Fragment() {

    private lateinit var viewModel: FragmentSavedPhotosViewModel

    private var _binding: FragmentSavedPhotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedPhotosBinding.inflate(inflater, container, false)

        val viewModelFactory = SavedPhotosViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(FragmentSavedPhotosViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPhotos.adapter =
            SavedPhotosAdapter()
        binding.rvPhotos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setObservers()
    }

    private fun setObservers() {
        viewModel.photos.observe(viewLifecycleOwner, {
            setPhotos(it)
        })
    }

    fun setPhotos(photos: List<Photo>) {
        if (photos.isNotEmpty()) {
            (binding.rvPhotos.adapter as? SavedPhotosAdapter)?.bindPhotos(photos)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}