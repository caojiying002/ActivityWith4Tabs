package com.example.activitywith4tabs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

class MainActivity : AppCompatActivity() {

    private lateinit var tabHome: TextView
    private lateinit var tabDashboard: TextView
    private lateinit var tabNotifications: TextView
    private lateinit var tabProfile: TextView

    private var homeFragment: Fragment? = null
    private var dashboardFragment: Fragment? = null
    private var notificationsFragment: Fragment? = null
    private var profileFragment: Fragment? = null

    companion object {
        // Fragment tags
        private const val TAG_PREFIX = "MAIN_ACTIVITY_TAB_"
        private const val TAG_HOME = "${TAG_PREFIX}HOME"
        private const val TAG_DASHBOARD = "${TAG_PREFIX}DASHBOARD"
        private const val TAG_NOTIFICATIONS = "${TAG_PREFIX}NOTIFICATIONS"
        private const val TAG_PROFILE = "${TAG_PREFIX}PROFILE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tabHome = findViewById(R.id.tabHome)
        tabDashboard = findViewById(R.id.tabDashboard)
        tabNotifications = findViewById(R.id.tabNotifications)
        tabProfile = findViewById(R.id.tabProfile)

        setupTabs()

        if (savedInstanceState == null) {
            loadFragment(TAG_HOME)
        }
    }

    private fun setupTabs() {
        tabHome.setOnClickListener { loadFragment(TAG_HOME) }
        tabDashboard.setOnClickListener { loadFragment(TAG_DASHBOARD) }
        tabNotifications.setOnClickListener { loadFragment(TAG_NOTIFICATIONS) }
        tabProfile.setOnClickListener { loadFragment(TAG_PROFILE) }
    }

    private fun loadFragment(tag: String) {
        supportFragmentManager.beginTransaction().apply {
            // 只隐藏和管理我们自己的 Fragment，避免影响第三方添加的 Fragment，例如权限请求库
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment.tag?.startsWith(TAG_PREFIX) == true) {
                    hide(fragment)
                    setMaxLifecycle(fragment, Lifecycle.State.CREATED)
                }
            }

            // 获取或创建目标 Fragment
            var targetFragment = supportFragmentManager.findFragmentByTag(tag)
            if (targetFragment == null) {
                targetFragment = when (tag) {
                    TAG_HOME -> HomeFragment().also { homeFragment = it }
                    TAG_DASHBOARD -> DashboardFragment().also { dashboardFragment = it }
                    TAG_NOTIFICATIONS -> NotificationsFragment().also { notificationsFragment = it }
                    TAG_PROFILE -> ProfileFragment().also { profileFragment = it }
                    else -> throw IllegalArgumentException("Unknown fragment tag: $tag")
                }
                add(R.id.fragmentContainer, targetFragment, tag)
            } else {
                // 更新 Activity 持有的引用
                when (tag) {
                    TAG_HOME -> homeFragment = targetFragment
                    TAG_DASHBOARD -> dashboardFragment = targetFragment
                    TAG_NOTIFICATIONS -> notificationsFragment = targetFragment
                    TAG_PROFILE -> profileFragment = targetFragment
                }
                show(targetFragment)
            }

            // 将所需的 Fragment 设置为 RESUMED 状态
            setMaxLifecycle(targetFragment, Lifecycle.State.RESUMED)
        }.commitNowAllowingStateLoss()
    }
}