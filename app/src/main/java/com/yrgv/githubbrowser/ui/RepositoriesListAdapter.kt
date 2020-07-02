package com.yrgv.githubbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.yrgv.githubbrowser.R
import com.yrgv.githubbrowser.ui.RepositoriesListAdapter.RepositoryListItemViewHolder
import com.yrgv.githubbrowser.util.RepositoryItemClickListener
import com.yrgv.githubbrowser.util.view.setThrottledClickListener

/**
 * Adapter to render the repositories in a list
 */
class RepositoriesListAdapter(
    private val onItemClickListener: RepositoryItemClickListener
) : ListAdapter<MainScreenUiModel.Repository, RepositoryListItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryListItemViewHolder {
        return RepositoryListItemViewHolder.get(parent)
    }

    override fun onBindViewHolder(holder: RepositoryListItemViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }


    class RepositoryListItemViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        companion object {
            private const val LAYOUT_ID = R.layout.layout_repository_list_item

            /**
             * Invoke this get an instance of the ViewHolder
             * */
            fun get(parent: ViewGroup): RepositoryListItemViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
                return RepositoryListItemViewHolder(itemView)
            }
        }

        private val nameView: MaterialTextView =
            itemView.findViewById(R.id.repository_list_item_name)

        private val descriptionView: MaterialTextView =
            itemView.findViewById(R.id.repository_list_item_description)

        fun bind(
            repository: MainScreenUiModel.Repository,
            onItemClickListener: RepositoryItemClickListener
        ) {
            nameView.text = repository.name
            descriptionView.text = repository.description
            itemView.setThrottledClickListener {
                onItemClickListener(repository)
            }
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<MainScreenUiModel.Repository>() {
        override fun areItemsTheSame(
            oldItem: MainScreenUiModel.Repository,
            newItem: MainScreenUiModel.Repository
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MainScreenUiModel.Repository,
            newItem: MainScreenUiModel.Repository
        ): Boolean {
            return (oldItem.name == newItem.name) && (oldItem.description == newItem.description)
        }
    }

}