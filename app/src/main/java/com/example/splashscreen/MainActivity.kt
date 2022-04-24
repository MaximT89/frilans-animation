package com.example.splashscreen

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.splashscreen.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listAnimatorSet = mutableListOf<AnimatorSet>()
    private val listAnimatorSet2 = mutableListOf<AnimatorSet>()
    private val listAnimatorSet3 = mutableListOf<AnimatorSet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimators()
        initAnimation()

    }

    private fun initAnimation() {

        lifecycleScope.launch(Dispatchers.Main){
            delay(2000)
            listAnimatorSet.playAllSets(lifecycleScope){
                // тут код для перехода на нужную активность
            }
        }


        lifecycleScope.launch(Dispatchers.Main){
            listAnimatorSet2.playAllSets(lifecycleScope){

            }
        }

        lifecycleScope.launch(Dispatchers.Main){
            listAnimatorSet3.playAllSets(lifecycleScope){
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }




    }

    @SuppressLint("Recycle")
    private fun initAnimators() {

        val scaleX = ObjectAnimator.ofFloat(binding.topRing, View.TRANSLATION_X, 1f, 500f, -100f)
        val scaleY = ObjectAnimator.ofFloat(binding.topRing, View.TRANSLATION_Y, 1f, 100f, -200f)

        val scaleX2 = ObjectAnimator.ofFloat(binding.bottomRing, View.TRANSLATION_X, 1f, -600f, -100f)
        val scaleY2 = ObjectAnimator.ofFloat(binding.bottomRing, View.TRANSLATION_Y, 1f, -100f, 250f)

        val translationY = ObjectAnimator.ofFloat(binding.textStaff, View.TRANSLATION_Y, 1f, 55f)
        val alphaText = ObjectAnimator.ofFloat(binding.textEvva, View.ALPHA, 0f, 1f)

        val animatorSetAlpha = AnimatorSet().apply {
            duration = 500
            play(alphaText)
        }

        val animatorSetTranslationY = AnimatorSet().apply {
            duration = 400
            play(translationY)
        }




        val animatorSetTopRing = AnimatorSet().apply {
            duration = 4000
            interpolator = LinearInterpolator()
            playTogether(scaleX, scaleY)
        }
//
        val animatorSetBottomRing = AnimatorSet().apply {
            duration = 4000
            interpolator = LinearInterpolator()
            playTogether(scaleX2, scaleY2)
        }
//
//        val animatorSet5 = AnimatorSet().apply {
//            duration = 2500
//            play(alphaBlackWindow)
//        }

        listAnimatorSet.add(animatorSetAlpha)
        listAnimatorSet.add(animatorSetTranslationY)
        listAnimatorSet2.add(animatorSetTopRing)
        listAnimatorSet3.add(animatorSetBottomRing)

    }
}