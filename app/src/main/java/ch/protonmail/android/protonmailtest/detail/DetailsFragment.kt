package ch.protonmail.android.protonmailtest.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.databinding.FragmentDetailsBinding
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding by lazy { FragmentDetailsBinding.inflate(layoutInflater) }

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = arguments?.getString(TASK_ID)
        taskId?.let { id -> viewModel.setId(id) } ?: activity?.finish()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUp()
        observeData()
    }

    private fun FragmentDetailsBinding.setUp() {
        downloadButton.setOnClickListener { viewModel.onDownloadImageClicked() }
    }

    private fun observeData() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsViewModel.ViewState.Success -> handleSuccess(state.task)
                is DetailsViewModel.ViewState.Error -> handleErrorState()
            }
        }
    }

    private fun handleSuccess(data: TaskModel) {
        with(binding) {
            if (data.imageDownloaded) {
                Glide.with(root.context).load(data.image).into(image)
                downloadButton.isVisible = false
            } else {
                downloadButton.isVisible = true
            }
            title.text = data.title
            description.text = data.description
            creationDate.text = data.creationDate
            dueDate.text = data.dueDate
        }
    }

    private fun handleErrorState() {
        Snackbar.make(binding.root, getString(R.string.generic_error), Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val TASK_ID = "ID"
    }
}
