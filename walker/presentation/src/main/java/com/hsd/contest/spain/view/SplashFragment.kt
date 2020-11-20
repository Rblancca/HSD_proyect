package com.hsd.contest.spain.view

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hsd.contest.spain.databinding.SplashFragmentBinding

class SplashFragment : Fragment() {
    private var binding: SplashFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        initLottie()
        return binding?.root
    }

    private fun initLottie() {
        binding?.lottieAnimationView?.repeatCount = 3
        binding?.lottieAnimationView?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                // don't do nothing
            }

            override fun onAnimationEnd(animator: Animator) {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToMenuActivity()
                )
            }

            override fun onAnimationCancel(animator: Animator) {
                // don't do nothing
            }

            override fun onAnimationRepeat(animator: Animator) {
                // don't do nothing
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}