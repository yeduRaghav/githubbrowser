package com.yrgv.githubbrowser.util

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yrgv.githubbrowser.R
import kotlinx.android.synthetic.main.layout_repo_details_bottom_sheet.*

/**
 * BottomSheet that shows details about a user's repository
 */
class RepositoryDetailsBottomSheet private constructor(private val data: Data) :
    BottomSheetDialogFragment() {

    companion object {
        private const val TAG = "RepoDetailsBottomSheet"

        fun show(data: Data, fragmentManager: FragmentManager) {
            RepositoryDetailsBottomSheet(data).show(fragmentManager, TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_repo_details_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        repo_detail_sheet_last_updated_value.text = data.lastUpdated
        repo_detail_sheet_stars_value.text = data.starsCount
        repo_detail_sheet_forks_value.text = data.forksCounts
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                findViewById<View>(com.google.android.material.R.id.design_bottom_sheet).apply {
                    setBackgroundResource(android.R.color.transparent)
                }
            }
        }
    }

    data class Data(val lastUpdated: String, val starsCount: String, val forksCounts: String)

}