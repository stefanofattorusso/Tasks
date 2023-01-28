package ch.protonmail.android.protonmailtest

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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