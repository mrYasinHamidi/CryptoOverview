package com.yasin.cryptooverview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yasin.cryptooverview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        // Set a custom animation for showing and hiding the FAB
        binding.fab.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)

        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.detail || destination.id == R.id.chartFragment || destination.id == R.id.searchFragment)
                binding.mainBottomNavigation.performHide()
            else
                binding.mainBottomNavigation.performShow()

        }


    }


}