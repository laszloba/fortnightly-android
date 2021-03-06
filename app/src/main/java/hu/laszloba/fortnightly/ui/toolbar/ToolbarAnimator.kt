package hu.laszloba.fortnightly.ui.toolbar

import kotlin.math.abs
import kotlin.properties.Delegates

class ToolbarAnimator(
    private val animationChangeListener: AnimationChangeListener
) {

    companion object {
        // The animation gets slower as this number gets bigger
        private const val ANIMATION_DECELERATOR = 300f
    }

    private var totalScroll = 0f

    private var animationProgress by Delegates.observable(0f) { _, oldValue, newValue ->
        if (abs(oldValue - newValue) > 0.001f) {
            animationChangeListener.onAnimationProgressChange(newValue / ANIMATION_DECELERATOR)
        }
    }

    fun calculateAnimation(scrollAmount: Int) {
        totalScroll += scrollAmount
        animationProgress = totalScroll.coerceIn(0f, ANIMATION_DECELERATOR)
    }

    interface AnimationChangeListener {
        fun onAnimationProgressChange(progress: Float)
    }
}
