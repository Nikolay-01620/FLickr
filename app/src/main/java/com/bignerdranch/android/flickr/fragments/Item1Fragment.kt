package com.bignerdranch.android.flickr.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.Flickr.databinding.FragmentItem1Binding
import com.bignerdranch.android.flickr.adapter.PhotoAdapter
import com.bignerdranch.android.flickr.view_model.FlickrViewModel

class Item1Fragment : Fragment() {

    private lateinit var binding: FragmentItem1Binding
    private lateinit var photoAdapter: PhotoAdapter

    private val viewModel: FlickrViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItem1Binding.inflate(inflater, container, false)
        val view = binding.root

        photoAdapter = PhotoAdapter()
        binding.recyclerView.adapter = photoAdapter

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = gridLayoutManager

        binding.btnSelectPhoto.setOnClickListener {
            viewModel.loadPhotosFromFlickr()
        }

        viewModel.photoUrls.observe(viewLifecycleOwner) { photoUrls ->
            photoAdapter.photoUrls = photoUrls
            val positionInserted = photoUrls.size
            photoAdapter.notifyItemInserted(positionInserted)
        }

        return view
    }
}
