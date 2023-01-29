package ch.protonmail.android.protonmailtest.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.commonandroid.DefaultDispatcher
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @DefaultDispatcher private val default: CoroutineDispatcher = Dispatchers.Default,
    private val getTasksUseCase: GetTasksUseCase,
    private val cryptoLib: CryptoLib,
) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    fun fetchTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTasks()
                .map { it.map { domain -> domain.toModel(cryptoLib) } }
                .flowOn(default)
                .catch { Log.e("TASK", it.localizedMessage ?: "An error occurred!") }
                .collect { _tasks.postValue(it) }
        }
    }
}