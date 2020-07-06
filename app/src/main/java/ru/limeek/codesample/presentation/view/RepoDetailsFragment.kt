package ru.limeek.codesample.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import ru.limeek.codesample.R
import ru.limeek.codesample.databinding.FragmentRepoDetailsBinding
import ru.limeek.codesample.domain.entities.GithubRepo
import ru.limeek.codesample.presentation.app.GlideApp
import ru.limeek.codesample.presentation.viewmodel.RepoDetailsViewModel
import javax.inject.Inject

class RepoDetailsFragment: DaggerFragment(){

    @Inject
    lateinit var viewModel: RepoDetailsViewModel

    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.repo.observe(viewLifecycleOwner, Observer { refreshUI(it) })
        viewModel.init(navArgs<RepoDetailsFragmentArgs>().value.repo)
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private fun openImageFragment(view: ImageView, imageUrl: String){
        val extras = FragmentNavigatorExtras(view to "imageTransition")
        val direction = RepoDetailsFragmentDirections.actionRepoDetailsFragmentToGalleryFragment(imageUrl)
        findNavController(this).navigate(direction, extras)
    }

    private fun refreshUI(repo: GithubRepo){
        GlideApp.with(requireContext()).load(repo.owner?.avatarUrl).into(binding.ivAvatar)
        binding.toolbar.title = repo.name
        binding.tvRepoName.text = repo.name
        binding.tvUser.text = repo.owner?.login
        binding.tvLanguage.text = repo.language
        binding.tvCreated.text = repo.creationDate.toString("dd.MM.YYYY")
        binding.tvStars.text = repo.stars.toString()
        binding.tvArchived.setText(if(repo.archived) R.string.yes else R.string.no)
        binding.tvFork.setText(if(repo.fork) R.string.yes else R.string.no)
        binding.tvDescription.text = repo.description
        binding.ivAvatar.setOnClickListener {
            openImageFragment(binding.ivAvatar, repo.owner?.avatarUrl ?: "")
        }

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController(this).navigateUp() }
    }
}