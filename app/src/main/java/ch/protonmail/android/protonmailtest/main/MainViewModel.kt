package ch.protonmail.android.protonmailtest.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    fun fetchTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTasks()
                .catch { Log.e("TASK", it.localizedMessage ?: "An error occurred!") }
                .map { it.map { domain -> domain.toModel() } }
                .collect { _tasks.postValue(it) }
        }
    }

/*    fun fetchTasks(activity: AppCompatActivity) {
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                val list = FetchDataFromServerTask().execute().get()
                tasks.postValue(list)
            }
        })
    }*/

/*    class FetchDataFromServerTask : AsyncTask<String, String, List<Task>>() {
        override fun doInBackground(vararg p0: String?): List<Task> {
            val url = URL("https://proton-android-testcloud.europe-west1.firebasedatabase.app/tasks.json")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            httpURLConnection.inputStream
            val responseCode: Int = httpURLConnection.responseCode

            var response: String = ""
            if (responseCode == 200) {
                response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
            }
            return Json.decodeFromString(ListSerializer(Task.serializer()), response)
        }
    }*/
}