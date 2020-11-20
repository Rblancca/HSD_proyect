package com.hsd.contest.spain.component.bottomview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.getResourceIdOrThrow
import com.google.android.material.bottomappbar.BottomAppBar
import com.hsd.contest.spain.R

class MyBottomNavigationView : BottomAppBar {

    enum class Selected {
        NONE,
        START,
        END
    }

    var itemPressed: ((Selected) -> Unit)? = null
    var itemReselected: (() -> Unit)? = null

    var selected = Selected.NONE
        set(value) {
            selectItem(selected = value)
            field = value
        }

    private fun selectItem(selected: Selected) {
        when (selected) {
            Selected.NONE -> {
                // Do nothing.
            }
            Selected.START -> {
                select(
                    idSelectedIcon = R.id.ib_view_my_bottom_navigation_start,
                    idUnselectedIcon = R.id.ib_view_my_bottom_navigation_end,
                    selected = Selected.START
                )
            }
            Selected.END -> {
                select(
                    idSelectedIcon = R.id.ib_view_my_bottom_navigation_end,
                    idUnselectedIcon = R.id.ib_view_my_bottom_navigation_start,
                    selected = Selected.END
                )
            }
        }
    }

    private fun select(idSelectedIcon: Int, idUnselectedIcon: Int, selected: Selected) {
        val selectedIcon = findViewById<AppCompatImageButton>(idSelectedIcon)
        selectedIcon?.setColorFilter(Color.BLACK)

        val unselectedIcon = findViewById<AppCompatImageButton>(idUnselectedIcon)
        unselectedIcon?.setColorFilter(
            ContextCompat.getColor(
                context,
                R.color.black
            )
        )

        if (this.selected == selected) {
            itemReselected?.invoke()
        } else {
            itemPressed?.invoke(selected)
        }
    }

    private var startIconResource: Int = -1
    private var endIconResource: Int = -1

    constructor(context: Context) : super(context) {
        throw IllegalStateException("It is not possible to call this constructor.")
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initComponent(context, attrs)
    }

    private fun initComponent(context: Context, attrs: AttributeSet?) {
        inflateChildLayout(context)
        retrieveCustomAttributes(context, attrs)
        initIcons()
    }

    private fun inflateChildLayout(context: Context) {
        View.inflate(
            context,
            R.layout.view_my_bottom_navigation, this
        )
    }

    private fun retrieveCustomAttributes(
        context: Context,
        attrs: AttributeSet?
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyCustomNavigationView,
            0, 0
        )

        startIconResource =
            a.getResourceIdOrThrow(R.styleable.MyCustomNavigationView_startIcon)
        endIconResource = a.getResourceIdOrThrow(R.styleable.MyCustomNavigationView_endIcon)

        a.recycle()
    }

    private fun initIcons() {
        initIcon(
            idImage = R.id.ib_view_my_bottom_navigation_start,
            iconResource = startIconResource,
            selected = Selected.START
        )
        initIcon(
            idImage = R.id.ib_view_my_bottom_navigation_end,
            iconResource = endIconResource,
            selected = Selected.END
        )
    }

    private fun initIcon(idImage: Int, iconResource: Int, selected: Selected) {
        findViewById<AppCompatImageButton>(idImage)?.run {
            setImageResource(
                iconResource
            )

            setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )

            setOnClickListener {
                this@MyBottomNavigationView.selected = selected
            }
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponent(context, attrs)
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(SELECTED_DATA, selected)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        (savedInstanceState.getSerializable(SELECTED_DATA) as? Selected?)?.let { selected ->
            this.selected = selected
        }
    }

    companion object {
        private const val SELECTED_DATA = "SELECTED_DATA"
    }
}
