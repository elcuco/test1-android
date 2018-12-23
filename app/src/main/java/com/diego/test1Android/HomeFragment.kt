package com.diego.test1Android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prof.rssparser.Article
import java.text.DateFormat
import java.util.*

class HomeFragment : Fragment() {

    var selectedText: String? = ""

    private var selectedRSS : TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        selectedRSS = view.findViewById(R.id.selectedRSS)

        // here is a porblem - when I switch between tabs, a new view is created.
        // IMHO - I am doing something wrong.
        selectedRSS?.text = selectedText

        val now = Date()
        val df = DateFormat.getDateInstance(DateFormat.LONG)
        dateTextView.text = df.format(now)

        return view
    }

    fun displayArticle(article: Article?) {
        selectedRSS?.text = article?.title
        selectedText = article?.title
    }
}
