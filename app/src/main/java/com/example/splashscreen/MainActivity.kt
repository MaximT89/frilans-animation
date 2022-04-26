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

// Скорость анимации black ball
const val SPEED_BALL = 4100

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listAnimatorSet = mutableListOf<AnimatorSet>()
    private val listAnimatorSet2 = mutableListOf<AnimatorSet>()

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

        lifecycleScope.launch(Dispatchers.Main){
            delay(START_DELAY.toLong())
            listAnimatorSet2.playAllSets(lifecycleScope){
//                // тут код для перехода на нужную активность
//                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }
    }

    @SuppressLint("Recycle")
    private fun initAnimators() {
        val translationY = ObjectAnimator.ofFloat(binding.textStaff, View.TRANSLATION_Y, -55f, -15f)
        val alphaText = ObjectAnimator.ofFloat(binding.textEvva, View.ALPHA, 0f, 1f)

        val animatorSetAlpha = AnimatorSet().apply {
            duration = SPEED_EVVA.toLong()
            play(alphaText)
        }

        val animatorSetTranslationY = AnimatorSet().apply {
            duration = SPEED_STAFF.toLong()
            play(translationY)
        }

        val animatorSetX = ObjectAnimator.ofFloat(binding.blackBall, View.TRANSLATION_X, 1f, -500f, -50f, -400f)
        val animatorSetY = ObjectAnimator.ofFloat(binding.blackBall, View.TRANSLATION_Y, 1f, -250f, -400f, -600f)

        val animatorSetStaff = AnimatorSet().apply {
            duration = SPEED_BALL.toLong()
            playTogether(animatorSetX, animatorSetY)
        }

        listAnimatorSet2.add(animatorSetStaff)

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