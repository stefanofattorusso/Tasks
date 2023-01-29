package ch.protonmail.android.protonmailtest.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ch.protonmail.android.protonmailtest.main.all.AllTasksFragment
import ch.protonmail.android.protonmailtest.main.upcoming.UpcomingFragment

class TabsAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return AllTasksFragment()
        }
        return UpcomingFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "All" else "Upcoming"
    }

    override fun getCount(): Int {
        return 2
    }
}