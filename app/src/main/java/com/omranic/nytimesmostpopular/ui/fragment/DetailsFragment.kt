package com.omranic.nytimesmostpopular.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.omranic.nytimesmostpopular.databinding.FragmentDetailsBinding
import com.omranic.nytimesmostpopular.ui.viewmodel.MostPopularViewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val mostPopularViewModel: MostPopularViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mostPopularViewModel = mostPopularViewModel
    }

}