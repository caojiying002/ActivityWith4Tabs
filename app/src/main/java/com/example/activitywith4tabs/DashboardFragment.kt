package com.example.activitywith4tabs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {
    private lateinit var contentView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView = view.findViewById(R.id.contentView)
    }

    /** 不要使用此方法监听，避免与Fragment生命周期函数不一致 */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        android.util.Log.d("Fragment", "DashboardFragment onHiddenChanged: $hidden")
    }

    override fun onStart() {
        super.onStart()
        // Fragment 变为可见时的逻辑
        updateContent()
    }

    override fun onStop() {
        super.onStop()
        // Fragment 变为不可见时的逻辑
    }

    @SuppressLint("SetTextI18n")
    private fun updateContent() {
        contentView.text = "Dashboard Fragment - Last updated: ${System.currentTimeMillis()}"
    }
}