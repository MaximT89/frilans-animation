package com.example.splashscreen

import android.animation.AnimatorSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun List<AnimatorSet>.playAllSets(coroutineScope: CoroutineScope, end : () -> Unit){
    coroutineScope.launch(Dispatchers.Main){
        for(animSet in this@playAllSets){
            animSet.start()
            delay(animSet.duration)
        }
        end.invoke()
    }
}