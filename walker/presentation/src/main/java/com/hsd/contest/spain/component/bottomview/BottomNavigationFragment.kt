package com.hsd.contest.spain.component.bottomview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.hsd.contest.spain.R

class BottomNavigationFragment (
    private val defaultModel: BottomNavigationDefaultModel,
    private val configurationModel: BottomNavigationConfigurationModel,
    private val graph: BottomNavigationGraph
) {

    fun attachNavHostFragment(fragment: Fragment) {
        defaultModel.fragmentManager.beginTransaction()
            .attach(fragment)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }

    fun detachNavHostFragment(fragment: Fragment) {
        defaultModel.fragmentManager.beginTransaction()
            .detach(fragment)
            .commit()
    }

    fun performTransaction(
        selectedFragment: NavHostFragment,
        tag: String,
        reset: (NavHostFragment) -> Unit
    ) {
        defaultModel.fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            .attach(selectedFragment)
            .setPrimaryNavigationFragment(selectedFragment)
            .apply {
                detachUnselectedFragments(tag = tag)
            }
            .apply {
                finishTransaction(selectedFragment = selectedFragment, reset = reset)
            }
    }

    private fun FragmentTransaction.detachUnselectedFragments(tag: String) {
        graph.graphIdTag.forEach {
            if (it.value != tag) {
                defaultModel.fragmentManager.findFragmentByTag(it.value)
                    ?.let { unselectedFragment ->
                        detach(unselectedFragment)
                    }
            }
        }
    }

    private fun FragmentTransaction.finishTransaction(
        selectedFragment: NavHostFragment,
        reset: (NavHostFragment) -> Unit
    ) {
        if (!configurationModel.keepWhenSelect) {
            commitNow()
            reset(selectedFragment)
        } else {
            commit()
        }
    }
}
