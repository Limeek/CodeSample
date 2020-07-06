package ru.limeek.codesample.presentation.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_repo_list.*
import ru.limeek.codesample.R
import ru.limeek.codesample.databinding.FragmentRepoListBinding
import ru.limeek.codesample.domain.entities.GithubRepo
import ru.limeek.codesample.domain.entities.NetworkError
import ru.limeek.codesample.presentation.view.adapter.ReposAdapter
import ru.limeek.codesample.presentation.viewmodel.RepoListState
import ru.limeek.codesample.presentation.viewmodel.RepoListState.*
import ru.limeek.codesample.presentation.viewmodel.RepoListViewModel
import javax.inject.Inject

class RepoListFragment : DaggerFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private var adapter: ReposAdapter? = null

    @Inject
    lateinit var viewModel: RepoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        initViewModelListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_repo_list, menu)

        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchView = searchMenuItem.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE

        if (viewModel.queryText.isNotEmpty()) {
            if (!searchMenuItem.isActionViewExpanded) searchMenuItem.expandActionView()
            searchView.setQuery(viewModel.queryText, false)
        }

        searchView.setOnQueryTextListener(queryListener)
        tvRetry.setOnClickListener {
            viewModel.startRepoSearch(searchView.query.toString())
        }
    }

    private fun initViewModelListeners() {
        viewModel.currentState.observe(viewLifecycleOwner, Observer { visualizeState(it) })
    }

    private fun visualizeState(state: RepoListState) {
        if (state is Ready) visualizeReadyState(state)

        if (state is Error) visualizeErrorState(state)

        binding.rvRepos.visibility = if (state is Ready) View.VISIBLE else View.GONE
        binding.startLayout.visibility = if (state is Start) View.VISIBLE else View.GONE
        binding.errorLayout.visibility = if (state is Error) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if (state is Loading) View.VISIBLE else View.GONE
        binding.emptyLayout.visibility = if (state is Empty) View.VISIBLE else View.GONE
    }

    private fun visualizeErrorState(state: Error) {
        when {
            state.error is NetworkError.NoInternetConnection -> binding.tvErrorText.setText(R.string.repo_list_no_internet)
            state.error is NetworkError.HttpError && (state.error as NetworkError.HttpError).errorCode == 403 -> binding.tvErrorText.setText(
                R.string.repo_list_out_of_requests
            )
            state.error is NetworkError.Unprocessed -> binding.tvErrorText.setText(R.string.repo_list_error_text)
        }
    }

    private fun visualizeReadyState(state: Ready) {
        if (binding.rvRepos.adapter == null) {
            adapter = ReposAdapter(state.repoList) { openDetailsFragment(it) }
            binding.rvRepos.adapter = adapter
            binding.rvRepos.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else {
            adapter?.repoList = state.repoList
            adapter?.notifyDataSetChanged()
        }
    }

    private fun openDetailsFragment(repo: GithubRepo) {
        val direction = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(repo)
        findNavController(this).navigate(direction)
    }

    private val queryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            viewModel.startRepoSearch(newText ?: "")
            return true
        }
    }
}
