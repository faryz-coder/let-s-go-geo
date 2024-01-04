package com.bmh.letsgogeonative.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bmh.letsgogeonative.databinding.FragmentHomeBinding
import com.bmh.letsgogeonative.model.Constant
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var eventAdapter: EventAdapter
    private lateinit var announcementAdapter: EventAdapter
    private lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val event = mutableListOf<Constant.Event>()
        val announcement = mutableListOf<Constant.Event>()

        eventAdapter = EventAdapter(event)
        announcementAdapter = EventAdapter(announcement)

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)
        val snapHelper2 = CarouselSnapHelper()
        snapHelper2.attachToRecyclerView(binding.carouselRecyclerView2)

        homeViewModel.announcement.observe(viewLifecycleOwner) {
            announcement.clear()
            announcement.addAll(it)
            // Initiate recycler view
            binding.carouselRecyclerView.apply {
                layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
                adapter = announcementAdapter
            }
        }

        homeViewModel.event.observe(viewLifecycleOwner) {
            event.clear()
            event.addAll(it)
            // Initiate recycler view
            binding.carouselRecyclerView2.apply {
                layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
                adapter = eventAdapter
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        FirestoreManager().getListEvent(homeViewModel)
        FirestoreManager().getListAnnouncement(homeViewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}