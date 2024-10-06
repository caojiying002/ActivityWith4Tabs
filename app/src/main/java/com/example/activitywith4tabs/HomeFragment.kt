package com.example.activitywith4tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.lifecycle.Lifecycle
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.activitywith4tabs.homesub.HomeSubFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabTitles = listOf("Sub Home", "Sub Profile", "Sub Settings", "Sub More")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_with_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val adapter = HomeTabAdapter(this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateFragmentLifecycle(position)
            }
        })
    }

    private fun updateFragmentLifecycle(position: Int) {
        val fragmentManager = childFragmentManager
        var fragmentTransaction: FragmentTransaction? = null

        fragmentManager.fragments.forEachIndexed { index, fragment ->
            if (fragment is HomeSubFragment) {
                if (fragmentTransaction == null)
                    fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction!!.setMaxLifecycle(fragment, if (index == position) Lifecycle.State.RESUMED else Lifecycle.State.STARTED)
            }
        }
        fragmentTransaction?.commitNowAllowingStateLoss()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        updateFragmentLifecycle(viewPager.currentItem)
    }

    override fun onPause() {
        super.onPause()
        updateFragmentLifecycle(-1) // 传入一个无效的位置，确保所有子Fragment都处于STARTED状态
    }

    inner class HomeTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = tabTitles.size
        override fun createFragment(position: Int): Fragment = HomeSubFragment.newInstance(tabTitles[position])
    }
}