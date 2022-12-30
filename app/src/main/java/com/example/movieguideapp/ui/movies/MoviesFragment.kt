package com.example.movieguideapp.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieguideapp.data.local.model.movie.MovieItemData
import com.example.movieguideapp.data.local.model.movie.MovieType
import com.example.movieguideapp.databinding.FragmentMoviesBinding
import com.example.movieguideapp.extensions.addVerticalItemSpace
import com.example.movieguideapp.ui.base.AppListAdapter
import com.example.movieguideapp.ui.base.AppListAdapterImp
import com.example.movieguideapp.ui.base.BaseFragment
import com.example.movieguideapp.ui.base.BaseViewModel
import com.example.movieguideapp.ui.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    companion object {
        private const val KEY_MOVIE_TYPE = "MovieType"
    }

    fun newInstance(movieType: MovieType): MoviesFragment {
        val args = Bundle().apply {
            putSerializable(KEY_MOVIE_TYPE, movieType)
        }

        val fragment = MoviesFragment()
        fragment.arguments = args
        return fragment
    }

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun initViewModel(): BaseViewModel = viewModel
    override fun bindActivityViewModel(): BaseViewModel = activityViewModel
    private var movieAdapter: AppListAdapter<MovieItemData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.movieType = it.getSerializable(KEY_MOVIE_TYPE) as MovieType?
            movieAdapter = AppListAdapterImp(
                listOf(),
                transform = {data ->
                    MovieListItem(data)
                }
            )
        }
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
    }

    private fun setupUI() {
        with(binding) {
            recyclerViewMovies.layoutManager = LinearLayoutManager(requireActivity())
            movieAdapter?.let {
                recyclerViewMovies.addVerticalItemSpace()
                recyclerViewMovies.adapter = it.getRecyclerViewAdapter()
            }
        }
    }
}