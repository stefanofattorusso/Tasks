package ch.protonmail.android.protonmailtest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun onDownloadImageClicked() {

    }

    fun setId(id: String) {

    }

    sealed class ViewState {
        data class Success(val task: TaskModel) : ViewState()
        object Error : ViewState()
    }
}