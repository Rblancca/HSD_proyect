package com.hsd.contest.spain.component.bottomview

import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BottomNavigationDefaultModel (
    val bottomIdGraphId: Map<MyBottomNavigationView.Selected, Int>,
    val mainBottomId: MyBottomNavigationView.Selected,
    val containerId: Int,
    val bottomNavigation: MyBottomNavigationView,
    val centerButton: FloatingActionButton,
    val fragmentManager: FragmentManager
)

data class BottomNavigationConfigurationModel(
    val keepWhenSelect: Boolean,
    val resetWhenReselect: Boolean,
    val goMainWhenBackPressed: Boolean
)

class BottomNavigationManager(
    private val defaultModel: BottomNavigationDefaultModel,
    private val configurationModel: BottomNavigationConfigurationModel
) {
    private val bottomNavigationGraph =
        BottomNavigationGraph(
            defaultModel = defaultModel
        )
    private val bottomNavigationFragment =
        BottomNavigationFragment(
            defaultModel = defaultModel,
            configurationModel = configurationModel,
            graph = bottomNavigationGraph
        )

    fun init() {
        bottomNavigationGraph.createGraphIdTag { tag, it ->
            val fragment =
                bottomNavigationGraph.getOrCreateNavHostFragment(tag = tag, graphId = it.value)

            if (it.key == defaultModel.mainBottomId) {
                bottomNavigationFragment.attachNavHostFragment(fragment = fragment)
            } else {
                bottomNavigationFragment.detachNavHostFragment(fragment = fragment)
            }
        }

        defaultModel.bottomNavigation.selected = defaultModel.mainBottomId
    }

    fun restore() {
        bottomNavigationGraph.createGraphIdTag()
    }

    fun initListeners() {
        defaultModel.bottomNavigation.itemPressed = { menuItem ->
            if (defaultModel.fragmentManager.isStateSaved ||
                !defaultModel.bottomIdGraphId.containsKey(menuItem)
            ) {
                // Nothing to implement.
            } else {
                itemSelected(menuItem = menuItem)
            }
        }

        defaultModel.bottomNavigation.itemReselected = {
            itemReselected()
        }
        defaultModel.centerButton.setOnClickListener { itemSelected(menuItem = MyBottomNavigationView.Selected.NONE) }

    }

    private fun itemSelected(menuItem: MyBottomNavigationView.Selected) {
        bottomNavigationGraph.getTag(bottomId = menuItem)?.let { tag ->
            (defaultModel.fragmentManager.findFragmentByTag(tag) as? NavHostFragment?)?.let { selectedFragment ->
                bottomNavigationFragment.performTransaction(selectedFragment, tag, reset = ::resetGraph)
            }
        }
    }

    private fun resetGraph(host: NavHostFragment) {
        host.navController.run {
            popBackStack(graph.startDestination, false)
        }
    }

    private fun itemReselected() {
        if (configurationModel.resetWhenReselect) {
            bottomNavigationGraph.getTag(bottomId = defaultModel.bottomNavigation.selected)
                ?.let { tag ->
                    (defaultModel.fragmentManager.findFragmentByTag(tag) as? NavHostFragment?)?.let { host ->
                        resetGraph(host = host)
                    }
                }
        }
    }

    fun backPressed(): Boolean =
        if (configurationModel.goMainWhenBackPressed) {
            bottomNavigationGraph.getTag(defaultModel.bottomNavigation.selected)?.let { tag ->
                (defaultModel.fragmentManager.findFragmentByTag(tag) as? NavHostFragment?)?.let { nav ->
                    goMainOptionIfNotThere(navHostFragment = nav)
                } ?: false
            } ?: false
        } else {
            false
        }

    private fun goMainOptionIfNotThere(navHostFragment: NavHostFragment): Boolean {
        return if (navHostFragment.childFragmentManager.backStackEntryCount == 0 &&
            defaultModel.bottomNavigation.selected != defaultModel.mainBottomId
        ) {
            defaultModel.bottomNavigation.selected = defaultModel.mainBottomId
            true
        } else {
            false
        }
    }
}
