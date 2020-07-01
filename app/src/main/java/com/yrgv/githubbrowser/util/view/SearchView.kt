package com.yrgv.githubbrowser.util.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.VisibleForTesting
import androidx.constraintlayout.widget.ConstraintLayout
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.util.SearchClickListener
import kotlinx.android.synthetic.main.view_search.view.*

/**
 * Custom view for Search section
 */
class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    //intentionally late-init since this component is useless without the click listener
    private lateinit var searchClickListener: SearchClickListener

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        setupChildViews()
    }

    private fun setupChildViews() {
        search_view_input_layout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(editable: Editable?) {
                handleTextChanged(editable)
            }
        })

        search_view_button.setOnClickListener {
            search_view_input_layout?.editText?.text?.toString()?.let { query ->
                searchClickListener(query)
            }
        }

    }

    private fun handleTextChanged(newEditable: Editable?) {
        search_view_button.isEnabled = shouldEnableSearchButton(newEditable)
    }

    //todo: test
    @VisibleForTesting
    fun shouldEnableSearchButton(query: Editable?): Boolean {
        return !query.isNullOrBlank()
    }

    fun setClickListener(listener: SearchClickListener) {
        this.searchClickListener = listener
    }
}