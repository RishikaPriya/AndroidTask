package task.avantari.com.alarm

import android.animation.Animator

interface AnimatorListenerAdapter : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) = Unit
    override fun onAnimationRepeat(animation: Animator) = Unit
    override fun onAnimationCancel(animation: Animator) = Unit
    override fun onAnimationEnd(animation: Animator) = Unit
}
