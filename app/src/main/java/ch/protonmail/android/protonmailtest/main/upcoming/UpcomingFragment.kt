package ch.protonmail.android.protonmailtest.main.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.databinding.FragmentUpcomingBinding
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.main.MainViewModel
import ch.protonmail.android.protonmailtest.main.TasksAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private val binding by lazy { FragmentUpcomingBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: UpcomingTasksViewModel by viewModels()

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUp()
        observeData()
    }

    private fun FragmentUpcomingBinding.setUp() {
        val layoutManager = LinearLayoutManager(context)
        with(recyclerView) {
            this.adapter = tasksAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun observeData() {
        mainViewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ViewState.Success -> handleSuccessState(state.data)
                is MainViewModel.ViewState.Error -> handleErrorState()
            }
        }
        viewModel.tasks.observe(viewLifecycleOwner) { list ->
            tasksAdapter.submitList(list)
        }
    }

    private fun handleSuccessState(data: List<TaskDomain>) {
        hideProgress()
        viewModel.setData(data)
    }

    private fun handleErrorState() {
        hideProgress()
        Snackbar.make(binding.root, "Ops! An error has occurred", Snackbar.LENGTH_LONG).show()
    }

    private fun hideProgress() {
        binding.progress.isVisible = false
    }
}