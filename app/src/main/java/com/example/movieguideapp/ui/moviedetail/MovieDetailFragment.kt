package com.example.movieguideapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieguideapp.data.local.model.cast.MovieDetailCastInfo
import com.example.movieguideapp.databinding.FragmentMovieDetailBinding
import com.example.movieguideapp.extensions.addHorizontalItemSpace
import com.example.movieguideapp.extensions.loadAsync
import com.example.movieguideapp.extensions.mapTo
import com.example.movieguideapp.extensions.observeFlow
import com.example.movieguideapp.ui.base.AppListAdapter
import com.example.movieguideapp.ui.base.AppListAdapterImp
import com.example.movieguideapp.ui.base.BaseFragment
import com.example.movieguideapp.ui.base.BaseViewModel
import com.example.movieguideapp.ui.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun initViewModel(): BaseViewModel = viewModel
    override fun bindActivityViewModel(): BaseViewModel = activityViewModel

    private val navArgs: MovieDetailFragmentArgs by navArgs()
    private lateinit var castsAdapter: AppListAdapter<MovieDetailCastInfo>
    private lateinit var infoAdapter: AppListAdapter<Pair<String,String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initBasicInfoState(navArgs.movieDetailBasicInfo)
        castsAdapter = AppListAdapterImp(
            listItems = listOf(),
            transform = { info: MovieDetailCastInfo ->
                info.mapTo()
            }
        )
        viewModel.loadCasts()

        infoAdapter = AppListAdapterImp(
            listItems = listOf(),
            transform = {
                MovieDetailInfoItem(it)
            }
        )

        viewModel.loadDetailInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeFlow(viewModel.basicInfoState) {
            with(binding) {
                textNameMovieDetail.text = it.name
                imageViewPosterMovieDetail.loadAsync(it.posterImageUrl)
                imageBackgroundMovieDetail.loadAsync(it.backgroundImageUrl)
                textCategoryMovieDetail.text = it.category
                textOverviewMovieDetail.text = it.overview
                textLengthMovieDetail.text = it.runtime.toString()
                textRatingMovieDetail.text = it.rating.toString()
                textYearMovieDetail.text = it.releaseDate
                textLanguageMovieDetail.text = it.language
            }
        }

        observeCasts()
        observeDetailInfo()
    }

    private fun observeDetailInfo() {
        observeFlow(viewModel.detailInfoState) {
            infoAdapter.set(it)
        }
    }

    private fun setupUI() {
        with(binding) {
            recyclerViewCastsMovieDetail.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerViewCastsMovieDetail.addHorizontalItemSpace()
            recyclerViewCastsMovieDetail.adapter = castsAdapter.getRecyclerViewAdapter()

            recyclerViewInfoMovieDetail.layoutManager = LinearLayoutManager(requireActivity())
            recyclerViewInfoMovieDetail.adapter = infoAdapter.getRecyclerViewAdapter()
        }
    }

    private fun observeCasts() {
        observeFlow(viewModel.castsFlow) {
            castsAdapter.set(it)
        }
    }


}