package ch.protonmail.android.protonmailtest.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.common.ResultData
import ch.protonmail.android.protonmailtest.data.remote.model.Task
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val tasks = MutableLiveData<List<Task>>()

    fun fetchTasks() {
        viewModelScope.launch {
            val result = getTasksUseCase.getTasks()
            if (result is ResultData.Success) {
                Log.d("RESULT", result.toString())
            }
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