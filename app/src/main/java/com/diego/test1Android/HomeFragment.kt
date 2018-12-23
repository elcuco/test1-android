package com.diego.test1Android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DateFormat
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)

        val now = Date()
        val df = DateFormat.getDateInstance(DateFormat.LONG)
        dateTextView.text = df.format(now)

        return view
    }

}
