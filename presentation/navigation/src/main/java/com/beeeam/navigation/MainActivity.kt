package com.beeeam.navigation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.beeeam.base.BaseActivity
import com.beeeam.navigation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
) {
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    private lateinit var navController: NavController

    override fun initView() {
        initNavBar()

        binding.apply {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    com.beeeam.signup.R.id.signupFragment, com.beeeam.login.R.id.loginFragment, com.beeeam.quiz.R.id.quizDailyOXFragment,
                    com.beeeam.quiz.R.id.quizDailyMultiChoiceFragment, com.beeeam.feed.R.id.feedWriteFragment, com.beeeam.feed.R.id.feedDetailFragment,
                    com.beeeam.myinfo.R.id.changeMyInfoFragment, com.beeeam.feed.R.id.feedCommentChangeFragment,
//                    com.beeeam.more.R.id.myPageFragment, R.id.splashFragment,
                    -> bottomNavigationBar.visibility = View.INVISIBLE
                    else -> bottomNavigationBar.visibility = View.VISIBLE
                }
            }

            bottomNavigationBar.setOnItemSelectedListener { item ->
                navController.navigate(
                    when(item.itemId) {
                        R.id.navigation_quiz_main -> com.beeeam.quiz.R.id.nav_graph_quiz
                        R.id.navigation_feed -> com.beeeam.feed.R.id.nav_graph_feed
                        R.id.navigation_ranking -> com.beeeam.ranking.R.id.nav_graph_ranking
                        R.id.navigation_more -> com.beeeam.myinfo.R.id.nav_graph_more
                        else -> 0
                    }
                )
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun initNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavigationBar.itemIconTintList = null

        binding.bottomNavigationBar.setupWithNavController(navController)
        binding.bottomNavigationBar.setOnItemReselectedListener { }
    }
}
