package ch.protonmail.android.protonmailtest.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import com.bumptech.glide.Glide

class TasksAdapter : ListAdapter<TaskModel, TasksAdapter.TaskViewHolder>(TaskItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(model: TaskModel) {
            with(binding) {
                title.text = model.title
                description.text = model.description
                creationDate.text = model.creationDate
                dueDate.text = model.dueDate
                if (model.imageDownloaded) {
                    Glide.with(root.context).load(model.image).into(image)
                } else {
                    Glide.with(root.context).clear(image)
                }
            }
        }
    }
}

class TaskItemDiffCallback : DiffUtil.ItemCallback<TaskModel>() {
    override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean =
        oldItem == newItem
}
