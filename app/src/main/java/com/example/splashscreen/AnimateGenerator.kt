package com.example.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimateGenerator(private val coroutine: CoroutineScope) {

    fun nextAnimationSet(animatorSet: AnimatorSet, end: () -> Unit) {
        animatorSet.apply {
            start()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    end.invoke()
                }
            })
        }
    }

    fun nextAnimationSetNew(listAnimatorSet: List<AnimatorSet>, end: () -> Unit) {
        coroutine.launch(Dispatchers.Main) {
            for (animSet in listAnimatorSet) {
                animSet.start()
                delay(animSet.duration)
            }
            delay(1500)
            end.invoke()
        }

    }
}