package ch.protonmail.android.protonmailtest.main.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import ch.protonmail.android.protonmailtest.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UpcomingTasksViewModel @Inject constructor() : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    private val _viewInteraction = LiveEvent<ViewInteraction>()
    val viewInteraction: LiveData<ViewInteraction> = _viewInteraction

    fun setData(data: List<TaskDomain>) {
        val filtered = data.filter { task -> task.dueDate.after(Date()) }.sortedBy { task -> task.dueDate }
        _tasks.value = filtered.map { task -> task.toModel() }
    }

    fun onTaskClicked(task: TaskModel) {
        _viewInteraction.value = ViewInteraction.TaskClicked(task)
    }

    sealed class ViewInteraction {
        data class TaskClicked(val task: TaskModel) : ViewInteraction()
    }
}