package com.diego.test1Android

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import info.hoang8f.android.segmented.SegmentedGroup
import kotlinx.android.synthetic.main.fragment_rss_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RSSFragment()
    : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private var rssAdapter = MyRSSRecyclerViewAdapter(ArrayList(), listener)
    private var recyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rss_list, container, false)
        val list : View = view.findViewById(R.id.list)
        if (list is RecyclerView) {
            with(list) {
                recyclerView = list
                layoutManager = LinearLayoutManager(context)
                adapter = rssAdapter
            }
        }
        val segmentedGroup = view.findViewById<SegmentedGroup>(R.id.segmentedGroup)
        segmentedGroup.setOnCheckedChangeListener { _: RadioGroup, id: Int ->
            displayFeeds(id)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchRss("businessNews")
        fetchRss("entertainment")
        fetchRss("environment")
    }

    private val feeds = HashMap<String, MutableList<Article>>()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private fun fetchRss(feed: String) {
        coroutineScope.launch(context = Dispatchers.Main) {
            try {
                val basrUrl = "http://feeds.reuters.com/reuters/"
                val url = basrUrl + feed
                val parser = Parser()
                val articleList = parser.getArticles(url)
                feeds[feed] = articleList
                displayFeeds(segmentedGroup.checkedRadioButtonId)
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    private fun displayFeeds(id: Int) {
        when (id) {
            R.id.businessRssButton -> {
                rssAdapter.mValues = feeds.get("businessNews")
                rssAdapter.notifyDataSetChanged()
            }
            R.id.entertainmentRssButton -> {
                rssAdapter.mValues = mutableListOf()
                var e : MutableList<Article>?

                e = feeds.get("entertainment")
                if (e!=null)
                    rssAdapter.mValues!!.addAll(e)
                e = feeds.get("environment")
                if (e!=null)
                    rssAdapter.mValues!! += e
                rssAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(article: Article?)
    }

    companion object {
    @JvmStatic
    fun newInstance(l: OnListFragmentInteractionListener) =
            RSSFragment().apply {
                this.listener = l

                // I am pretty sure I am doing something wrong here...
                // this smells bad.
                this.rssAdapter.mListener = l
            }
    }
}
