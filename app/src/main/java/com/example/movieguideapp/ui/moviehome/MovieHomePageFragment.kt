package com.example.movieguideapp.ui.moviehome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieguideapp.data.local.model.genre.MovieCategory
import com.example.movieguideapp.data.local.model.movie.MovieBanner
import com.example.movieguideapp.data.local.model.movie.MovieHomePageEvent
import com.example.movieguideapp.data.local.model.movie.MovieHomePageUiData
import com.example.movieguideapp.databinding.FragmentMovieHomePageBinding
import com.example.movieguideapp.extensions.addHorizontalItemSpace
import com.example.movieguideapp.extensions.mapTo
import com.example.movieguideapp.extensions.observeFlow
import com.example.movieguideapp.extensions.postDelay
import com.example.movieguideapp.ui.base.AppListAdapter
import com.example.movieguideapp.ui.base.AppListAdapterImp
import com.example.movieguideapp.ui.base.BaseFragment
import com.example.movieguideapp.ui.base.BaseViewModel
import com.example.movieguideapp.ui.home.MainViewModel
import com.example.movieguideapp.utils.MovieHomePageDiffCallback
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieHomePageFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieHomePageBinding
    private val viewModel: MovieHomePageViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    override fun initViewModel(): BaseViewModel = viewModel
    override fun bindActivityViewModel(): BaseViewModel = activityViewModel

    private lateinit var movieBannerAdapter: AppListAdapter<MovieBanner>
    private lateinit var categoryAdapter: AppListAdapter<MovieCategory>
    private lateinit var popularMovieAdapter: AppListAdapter<MovieHomePageUiData>
    private lateinit var topRatedMovieAdapter: AppListAdapter<MovieHomePageUiData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observePopularMovies()
        observeTopRatedMovies()


        movieBannerAdapter = AppListAdapterImp(
            listOf(),
            transform = { movieBanner ->
                movieBanner.mapTo()
            }
        )
        viewModel.loadMovieBanners()
        categoryAdapter = AppListAdapterImp(
            listOf(),
            transform = {
                it.mapTo()
            }
        )
        viewModel.loadGenres()
        popularMovieAdapter = AppListAdapterImp(
            listOf(),
            transform = {
                it.mapTo()
            },
            diffCallback = MovieHomePageDiffCallback()
        )

        topRatedMovieAdapter = AppListAdapterImp(
            listOf(),
            transform = {
                it.mapTo()
            },
            diffCallback = MovieHomePageDiffCallback()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupUiEvents()
        binding.viewPagerMovieBanner.adapter = movieBannerAdapter.getRecyclerViewAdapter()
        binding.circleIndicator.setViewPager(binding.viewPagerMovieBanner)
        binding.viewPagerMovieBanner.adapter!!.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)

        observeMovieHomePageEvents()
        observeSliderMovieBanner()
        observeMovieBanners()
        observeCategories()


    }

    private fun observeMovieHomePageEvents() {
        observeFlow(viewModel.movieHomePageEvent) { event ->
            when (event) {
                is MovieHomePageEvent.MovieDetailNavigation -> {
                    findNavController().navigate(
                        MovieHomePageFragmentDirections.actionMovieHomePageFragmentToMovieDetailFragment(
                            event.movieDetailBasicInfo
                        )
                    )
                }
            }
        }
    }

    private fun setupUiEvents() {
        popularMovieAdapter.setOnItemClickListener { position ->
            viewModel.onClickPopularMovieItem(position)
        }

        topRatedMovieAdapter.setOnItemClickListener {
            viewModel.onClickTopRatedMovieItem(it)
        }
    }

    override fun onResume() {
        super.onResume()
        postDelay(1000,2000) {
            viewModel.slideMovieBanner()
        }
    }

    private fun observeTopRatedMovies() {
        observeFlow(
            viewModel.topRatedMoviesFlow,
            lifecycleState = Lifecycle.State.CREATED
        ) { state ->
            if (state.isLoading) {
                topRatedMovieAdapter.showLoading()
            } else {
                topRatedMovieAdapter.hideLoading()
                topRatedMovieAdapter.set(state.items)
            }
        }
    }

    private fun observePopularMovies() {
        observeFlow(
            viewModel.popularMoviesFlow,
            lifecycleState = Lifecycle.State.CREATED
        ) { state ->
            if (state.isLoading) {
                popularMovieAdapter.showLoading()
            } else {
                popularMovieAdapter.hideLoading()
                popularMovieAdapter.set(state.items)
            }
        }
    }

    private fun setupUi() {
        with(binding.recyclerViewMovieCategories) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            addHorizontalItemSpace()
            adapter = categoryAdapter.getRecyclerViewAdapter()
        }

        with(binding.recyclerViewPopularMovies) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            addHorizontalItemSpace()
            adapter = popularMovieAdapter.getRecyclerViewAdapter()
            addOnScrollListener(
                object : EndlessRecyclerOnScrollListener() {
                    override fun onLoadMore(currentPage: Int) {
                        viewModel.loadPopularMovies(page = currentPage)
                    }
                }
            )
            itemAnimator = null
            setHasFixedSize(true)
        }

        with(binding.recyclerViewTopRatedMovies) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            addHorizontalItemSpace()
            adapter = topRatedMovieAdapter.getRecyclerViewAdapter()
            addOnScrollListener(
                object : EndlessRecyclerOnScrollListener() {
                    override fun onLoadMore(currentPage: Int) {
                        viewModel.loadTopRatedMovies(page = currentPage)
                    }
                }
            )
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    private fun observeCategories() {
        observeFlow(viewModel.categoriesFlow, lifecycleState = Lifecycle.State.STARTED) {
            categoryAdapter.addAll(it)
        }
    }

    private fun observeSliderMovieBanner() {
        observeFlow(viewModel.bannerSliderPage,
            viewLifecycleOwner = viewLifecycleOwner,
            lifecycleState = Lifecycle.State.STARTED) {
            binding.viewPagerMovieBanner.currentItem = it
        }
    }

    private fun observeMovieBanners() {
        observeFlow(
            viewModel.movieBannersFlow,
            viewLifecycleOwner = viewLifecycleOwner,
            lifecycleState = Lifecycle.State.STARTED
        ) { movieBanners ->
            movieBannerAdapter.set(movieBanners)

        }
    }

}