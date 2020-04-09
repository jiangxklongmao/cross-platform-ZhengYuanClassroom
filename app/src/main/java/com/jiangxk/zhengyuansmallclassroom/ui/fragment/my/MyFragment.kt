package com.jiangxk.zhengyuansmallclassroom.ui.fragment.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jiangxk.zhengyuansmallclassroom.R

class MyFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myViewModel =
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        myViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}