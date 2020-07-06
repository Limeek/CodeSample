package ru.limeek.codesample.presentation.view

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import ru.limeek.codesample.databinding.FragmentViewImageBinding
import ru.limeek.codesample.presentation.app.GlideApp

class GalleryFragment: Fragment(){

    private lateinit var binding: FragmentViewImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition = ChangeBounds()
        sharedElementEnterTransition = ChangeBounds()
        binding = FragmentViewImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding.ivBack.setOnClickListener { NavHostFragment.findNavController(this).navigateUp() }
        val url = navArgs<GalleryFragmentArgs>().value.imageUrl
        GlideApp.with(requireContext()).load(url).into(binding.ivAvatar)
    }
}