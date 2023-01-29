package ch.protonmail.android.protonmailtest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.usecase.GetTaskUseCase
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun onDownloadImageClicked() {

    }

    fun setId(id: String) {
        viewModelScope.launch {
            getTaskUseCase.getTask(id)
                .map { domain -> domain.toModel() }
                .flowOn(Dispatchers.Default)
                .catch { _viewState.postValue(ViewState.Error) }
                .collect { task -> _viewState.postValue(ViewState.Success(task)) }
        }
    }

    sealed class ViewState {
        data class Success(val task: TaskModel) : ViewState()
        object Error : ViewState()
    }
}