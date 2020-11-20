package com.hsd.contest.spain.component.bottomview

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

class BottomNavigationGraph (private val defaultModel: BottomNavigationDefaultModel) {

    val graphIdTag: MutableMap<Int, String> = HashMap()

    fun createGraphIdTag(
        actionInLoop: ((
            tag: String,
            item: Map.Entry<MyBottomNavigationView.Selected, Int>
        ) -> Unit)? = null
    ) {
        var i = 0
        defaultModel.bottomIdGraphId.forEach {
            val tag = "$TAG${i++}"
            graphIdTag[it.value] = tag

            actionInLoop?.invoke(tag, it)
        }
    }

    fun getOrCreateNavHostFragment(
        tag: String,
        graphId: Int
    ): Fragment {
        return defaultModel.fragmentManager.findFragmentByTag(tag) ?: run {
            val host = NavHostFragment.create(graphId)
            defaultModel.fragmentManager.beginTransaction()
                .add(defaultModel.containerId, host, tag)
                .commit()
            host
        }
    }

    fun getTag(bottomId: MyBottomNavigationView.Selected): String? =
        getGraphId(bottomId = bottomId)?.let { graphId ->
            graphIdTag[graphId]
        }

    private fun getGraphId(bottomId: MyBottomNavigationView.Selected): Int? {
        return if (defaultModel.bottomIdGraphId.containsKey(defaultModel.mainBottomId)) {
            defaultModel.bottomIdGraphId.getValue(bottomId)
        } else {
            null
        }
    }

    companion object {
        private const val TAG = "BottomNavigationManagerFragment"
    }
}
