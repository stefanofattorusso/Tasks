package ch.protonmail.android.protonmailtest.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.protonmail.android.protonmailtest.MainDispatcherRule
import ch.protonmail.android.protonmailtest.detail.DetailsViewModel
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.usecase.GetTaskUseCase
import ch.protonmail.android.protonmailtest.domain.usecase.SetImageDownloadedUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getTaskUseCase: GetTaskUseCase = mockk(relaxed = true)
    private val setImageDownloadedUseCase: SetImageDownloadedUseCase = mockk(relaxed = true)

    private val viewModel: DetailsViewModel by lazy { DetailsViewModel(getTaskUseCase, setImageDownloadedUseCase) }

    private val domain = TaskDomain(
        Date(),
        Date(),
        "",
        "",
        "2",
        "",
        false
    )

    @Test
    fun `When a new task id is set, Then retrieve all the information from db`() = runTest {
        coEvery { getTaskUseCase.getTask(any()) } returns flowOf(domain)

        viewModel.setId("2")

        coVerify { getTaskUseCase.getTask(any()) }

        assert(viewModel.viewState.value is DetailsViewModel.ViewState.Success)
    }

    @Test
    fun `When onDownloadImageClicked is invoked, Then verify that the usecase is called`() = runTest {

        viewModel.setId("2")

        viewModel.onDownloadImageClicked()

        coVerify { setImageDownloadedUseCase.setImageDownloaded(any()) }
    }

}