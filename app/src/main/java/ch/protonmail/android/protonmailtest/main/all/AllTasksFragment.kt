package ch.protonmail.android.protonmailtest.main.all

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.protonmail.android.protonmailtest.databinding.FragmentAllTasksBinding
import ch.protonmail.android.protonmailtest.detail.DetailsActivity
import ch.protonmail.android.protonmailtest.detail.DetailsFragment.Companion.TASK_ID
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.main.MainViewModel
import ch.protonmail.android.protonmailtest.main.TasksAdapter
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksFragment : Fragment() {

    private val binding by lazy { FragmentAllTasksBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: AllTasksViewModel by viewModels()

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUp()
        observeData()
    }

    private fun FragmentAllTasksBinding.setUp() {
        val layoutManager = LinearLayoutManager(context)
        with(recyclerView) {
            this.adapter = tasksAdapter
            this.layoutManager = layoutManager
        }
        tasksAdapter.onClickListener = { task -> viewModel.onTaskClicked(task) }
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
        viewModel.viewInteraction.observe(viewLifecycleOwner) { interaction ->
            when (interaction) {
                is AllTasksViewModel.ViewInteraction.TaskClicked -> navigateToTaskDetail(interaction.task)
            }
        }
    }

    private fun navigateToTaskDetail(task: TaskModel) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtras(bundleOf(TASK_ID to task.id))
        }
        activity?.startActivity(intent)
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