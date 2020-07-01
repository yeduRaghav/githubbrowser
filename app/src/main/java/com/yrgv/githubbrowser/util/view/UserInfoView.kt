package com.yrgv.githubbrowser.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import com.yrgv.githubbrowser.R
import kotlinx.android.synthetic.main.view_user_info.view.*

/**
 * View to display user info.
 */
class UserInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_user_info, this, true)
    }

    fun setUserInfo(name: String, avatarUrl: String) {
        Picasso.get()
            .load(avatarUrl)
            .placeholder(R.drawable.image_placeholder)
            .into(user_info_view_image)

        user_info_view_name.text = name
    }


}