package com.appnew.basecode.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.appnew.basecode.common.extensions.isLandscape

abstract class AppBaseDialogFragment(@LayoutRes contentLayoutId: Int) :
    DialogFragment(contentLayoutId) {

    protected open val landscapeDialogWidthPercentage = 0.4f
    protected open val portraitDialogWidthPercentage = 0.8f
    protected open val landscapeDialogHeightPercentage = 0.75f
    protected open val portraitDialogHeightPercentage = 0.85f
    protected open val dimAmount = 0.7f

    abstract val rootView: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog?.window?.setDimAmount(dimAmount)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        adjustDialogSize()
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, null)
    }

    open fun adjustDialogSize() {
        val window = dialog?.window ?: return
        val metrics = resources.displayMetrics

        val isLandscape = requireContext().isLandscape()
        val widthPercentage = if (isLandscape)
            landscapeDialogWidthPercentage
        else
            portraitDialogWidthPercentage

        val width = (metrics.widthPixels * widthPercentage).toInt()
        window.setLayout(width, LayoutParams.WRAP_CONTENT)

        rootView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                val viewHeight = rootView.height
                val heightPercentage = if (isLandscape) {
                    landscapeDialogHeightPercentage
                } else {
                    portraitDialogHeightPercentage
                }
                val maxHeight = (metrics.heightPixels * heightPercentage).toInt()
                val height = if (viewHeight > maxHeight) {
                    maxHeight
                } else {
                    LayoutParams.WRAP_CONTENT
                }
                window.setLayout(width, height)
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}