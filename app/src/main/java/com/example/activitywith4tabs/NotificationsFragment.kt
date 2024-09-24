package com.example.activitywith4tabs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {
    private lateinit var contentView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView = view.findViewById(R.id.contentView)
    }

    /** 不要使用此方法监听，避免与Fragment生命周期函数不一致 */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        android.util.Log.d("Fragment", "NotificationFragment onHiddenChanged: $hidden")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        // Fragment 变为可见时的逻辑
        updateContent()
    }

    override fun onPause() {
        super.onPause()
        // Fragment 变为不可见时的逻辑
    }

    override fun onStop() {
        super.onStop()
    }

    @SuppressLint("SetTextI18n")
    private fun updateContent() {
        contentView.text = "Notification Fragment - Last updated: ${System.currentTimeMillis()}"
    }
}