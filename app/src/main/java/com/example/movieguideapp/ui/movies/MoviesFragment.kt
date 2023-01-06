package com.example.movieguideapp.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MoviesEvent
import com.example.movieguideapp.databinding.FragmentMoviesBinding
import com.example.movieguideapp.extensions.addVerticalItemSpace
import com.example.movieguideapp.extensions.observeFlow
import com.example.movieguideapp.ui.base.AppListAdapter
import com.example.movieguideapp.ui.base.AppListAdapterImp
import com.example.movieguideapp.ui.base.BaseFragment
import com.example.movieguideapp.ui.base.BaseViewModel
import com.example.movieguideapp.ui.home.MainViewModel
import com.example.movieguideapp.utils.MovieItemListDiffCallback
import com.mikepenz.fastadapter.listeners.ItemFilterListener
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {


    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun initViewModel(): BaseViewModel = viewModel
    override fun bindActivityViewModel(): BaseViewModel = activityViewModel
    private var movieAdapter: AppListAdapter<MovieItemData>? = null
    private val movieEndlessScrollListener = object : EndlessRecyclerOnScrollListener() {
        override fun onLoadMore(currentPage: Int) {
            viewModel.loadMovies(page = currentPage)
        }
    }

    private val movieFragmentParam: MoviesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.movieType = movieFragmentParam.movieType
        movieAdapter = AppListAdapterImp(
            listOf(),
            transform = { data ->
                MovieListItem(data)
            },
            diffCallback = MovieItemListDiffCallback(),
            filterPredicate = { item: MovieListItem, constraint: CharSequence? ->
                item.movie.name.contains(constraint!!, true)
            },
            itemFilterListener = object : ItemFilterListener<MovieListItem> {
                override fun itemsFiltered(
                    constraint: CharSequence?,
                    results: List<MovieListItem>?
                ) {
                    viewModel.onMovieItemFiltered(constraint?.toString(), !results.isNullOrEmpty())
                }

                override fun onReset() {
                    viewModel.onMovieQueryEmpty()
                }

            }
        )

        viewModel.loadMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupUiEvents()
        observeMovies()
        observeEvents()
        observeMovieSearchResult()
    }

    private fun observeMovieSearchResult() {
        observeFlow(viewModel.isSearchResultFound) { hasResult ->
            when(hasResult) {
                true -> binding.viewSwitcher.showPrevious()
                false -> binding.viewSwitcher.showNext()
            }
        }
    }

    private fun observeEvents() {
        observeFlow(viewModel.movieEventFlow) {
            when (it) {
                is MoviesEvent.MovieDetailNavigation -> {
                    findNavController().navigate(
                        MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it.movieDetailBasicInfo)
                    )
                }
            }
        }
    }

    private fun setupUiEvents() {
        with(binding.recyclerViewMovies) {
            addOnScrollListener(movieEndlessScrollListener)
        }

        movieAdapter?.setOnItemClickListener { position ->
            viewModel.onClickMovieItem(position)
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.editTextMovieSearching.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                binding.recyclerViewMovies.addOnScrollListener(movieEndlessScrollListener)
            }
            movieAdapter?.filter(it.toString())
        }

        binding.editTextMovieSearching.onFocusChangeListener =
            OnFocusChangeListener { p0, isFocus ->
                if (isFocus) {
                    binding.recyclerViewMovies.removeOnScrollListener(movieEndlessScrollListener)
                }
            }
    }

    private fun observeMovies() {
        observeFlow(viewModel.moviesFlow) {
            movieAdapter!!.set(it)
        }
    }

    private fun setupUI() {
        with(binding) {
            recyclerViewMovies.addVerticalItemSpace()
            recyclerViewMovies.itemAnimator = null
            recyclerViewMovies.layoutManager = LinearLayoutManager(requireActivity())
            recyclerViewMovies.adapter = movieAdapter!!.getRecyclerViewAdapter()
        }
    }
}