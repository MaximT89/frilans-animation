package com.example.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.splashscreen.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 1000 = 1sec

// Через сколько начнется анимация
const val START_DELAY = 1000

// Через сколько после окончания анимации переходить на следующий экран
const val END_DELAY = 1000

// Скорость анимации слова EVVA
const val SPEED_EVVA = 400

// Скорость анимации слова STAFF
const val SPEED_STAFF = 300

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listAnimatorSet = mutableListOf<AnimatorSet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimators()
        initAnimation()
    }

    private fun initAnimation() {
        lifecycleScope.launch(Dispatchers.Main){
            delay(START_DELAY.toLong())
            listAnimatorSet.playAllSets(lifecycleScope){
//                // тут код для перехода на нужную активность
//                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }
    }

    @SuppressLint("Recycle")
    private fun initAnimators() {
        val translationY = ObjectAnimator.ofFloat(binding.textStaff, View.TRANSLATION_Y, 1f, 55f)
        val alphaText = ObjectAnimator.ofFloat(binding.textEvva, View.ALPHA, 0f, 1f)

        val animatorSetAlpha = AnimatorSet().apply {
            duration = SPEED_EVVA.toLong()
            play(alphaText)
        }

        val animatorSetTranslationY = AnimatorSet().apply {
            duration = SPEED_STAFF.toLong()
            play(translationY)
        }

        listAnimatorSet.add(animatorSetAlpha)
        listAnimatorSet.add(animatorSetTranslationY)
    }
}

fun List<AnimatorSet>.playAllSets(coroutineScope: CoroutineScope, end : () -> Unit){
    coroutineScope.launch(Dispatchers.Main){
        for(animSet in this@playAllSets){
            animSet.start()
            delay(animSet.duration)
        }
        delay(END_DELAY.toLong())
        end.invoke()
    }
}