package ch.protonmail.android.protonmailtest.details

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.commonandroid.launchFragmentInHiltContainer
import ch.protonmail.android.protonmailtest.detail.DetailsFragment
import ch.protonmail.android.protonmailtest.detail.DetailsViewModel
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    private val _viewState = MutableStateFlow<DetailsViewModel.ViewState>(DetailsViewModel.ViewState.Loading)

    @BindValue
    @JvmField
    val viewModel: DetailsViewModel = mockk(relaxed = true) {
        every { viewState } returns _viewState
    }

    private val model = TaskModel(
        "", "", "", "title", "", "", false
    )

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun given_a_task_when_the_screen_is_opened_then_check_everything_is_displayed_and_image_not_loaded() {

        val args = bundleOf("ID" to "1")
        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = args,
            themeResId = R.style.AppTheme
        )

        _viewState.value = DetailsViewModel.ViewState.Success(model)

        onView(withId(R.id.title)).check(matches(withText("title")))
        onView(withId(R.id.download_button)).check(matches(isDisplayed()))
    }

    @Test
    fun given_a_task_when_the_screen_is_opened_then_user_clicks_on_button_and_image_is_loaded() {

        val args = bundleOf("ID" to "1")
        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = args,
            themeResId = R.style.AppTheme
        )

        _viewState.value = DetailsViewModel.ViewState.Success(model)

        onView(withId(R.id.download_button)).check(matches(isDisplayed()))
        onView(withId(R.id.download_button)).perform(click())

        _viewState.value = DetailsViewModel.ViewState.Success(model.copy(imageDownloaded = true))

        onView(withId(R.id.download_button)).check(matches(not(isDisplayed())))

    }
}