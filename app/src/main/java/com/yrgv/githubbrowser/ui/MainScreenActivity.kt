package com.yrgv.githubbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.ui.model.MainScreenUiModel
import com.yrgv.githubbrowser.util.view.hide
import com.yrgv.githubbrowser.util.view.show
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity() {

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }

    private val listAdapter = RepositoriesListAdapter {
        //todo: showBottomDialog, check plaid does onClick in search module
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        main_screen_search_view.setClickListener {
            viewModel.searchUser(it)
        }
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
            MainScreenUiModel.UiState.LOADED -> showUserView()
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
        main_screen_user_views_group.hide()
        //todo: show toast?
    }

    private fun showLoadingView() {
        main_screen_loading_view.show()
        main_screen_user_views_group.hide()
    }

    private fun showUserView() {
        main_screen_loading_view.hide()
        main_screen_user_views_group.show()
    }

}