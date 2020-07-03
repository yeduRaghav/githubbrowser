package com.yrgv.githubbrowser.ui

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.util.RepositoryDetailsBottomSheet
import com.yrgv.githubbrowser.util.toBottomSheetData
import com.yrgv.githubbrowser.util.view.hide
import com.yrgv.githubbrowser.util.view.show
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity() {

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application)).get(MainScreenViewModel::class.java)
    }

    private val listAdapter = RepositoriesListAdapter {
        RepositoryDetailsBottomSheet.show(it.toBottomSheetData(), supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        main_screen_search_view.setClickListener { viewModel.searchUser(it) }
        main_screen_recycler_view.adapter = listAdapter
    }

    private fun setupViewModel() {
        viewModel.getUiState().observe(this, Observer {
            handleUiState(it)
        })
        viewModel.getUser().observe(this, Observer {
            handleUser(it)
        })
        viewModel.getUserRepositories().observe(this, Observer {
            handleRepositories(it)
        })
    }

    private fun handleUiState(state: MainScreenUiModel.UiState) {
        when (state) {
            MainScreenUiModel.UiState.LOADING -> showLoadingView()
            MainScreenUiModel.UiState.ERROR -> showErrorView()
            MainScreenUiModel.UiState.LOADED -> showUserViews()
        }
    }

    private fun handleUser(user: MainScreenUiModel.User) {
        main_screen_user_info_view.setUserInfo(
            name = user.name,
            avatarUrl = user.avatarUrl
        )
    }

    private fun handleRepositories(repositories: List<MainScreenUiModel.Repository>) {
        listAdapter.submitList(repositories)
    }

    private fun showErrorView() {
        main_screen_loading_view.hide()
        main_screen_user_info_view.hide()
        main_screen_recycler_view.hide()
        Snackbar.make(main_screen_root_view, R.string.snackbar_general_error, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun showLoadingView() {
        main_screen_loading_view.show()
        main_screen_user_info_view.hide()
        main_screen_recycler_view.hide()
    }

    private fun showUserViews() {
        main_screen_loading_view.hide()
        showUserViewsWithAnimation()
    }

    private fun showUserViewsWithAnimation() {
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    showRecyclerViewWithAnimation()
                }
            })
        }

        main_screen_user_info_view.apply {
            show()
            startAnimation(slideUpAnimation)
        }
    }

    private fun showRecyclerViewWithAnimation() {
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        main_screen_recycler_view.apply {
            show()
            startAnimation(slideUpAnimation)
        }
    }

}