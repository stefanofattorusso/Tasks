package ch.protonmail.android.protonmailtest.main.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.main.all.AllTasksViewModel
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import ch.protonmail.android.protonmailtest.utils.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UpcomingTasksViewModel @Inject constructor() : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    private val _viewInteraction = LiveEvent<AllTasksViewModel.ViewInteraction>()
    val viewInteraction: LiveData<AllTasksViewModel.ViewInteraction> = _viewInteraction

    fun setData(data: List<TaskDomain>) {
        viewModelScope.launch(context = Dispatchers.Default) {
            val filtered = data.filter { task -> task.dueDate.after(Date()) }.sortedBy { task -> task.dueDate }
            _tasks.postValue(filtered.map { task -> task.toModel() })
        }
    }

    fun onTaskClicked(task: TaskModel) {
        _viewInteraction.value = AllTasksViewModel.ViewInteraction.TaskClicked(task)
    }

    sealed class ViewInteraction {
        data class TaskClicked(val task: TaskModel): ViewInteraction()
    }
}