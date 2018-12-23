package com.diego.test1Android


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diego.test1Android.RSSFragment.OnListFragmentInteractionListener
import com.prof.rssparser.Article
import kotlinx.android.synthetic.main.fragment_rss.view.*

class MyRSSRecyclerViewAdapter(
        var mValues: MutableList<Article>? = null,
        var mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyRSSRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Article
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_rss, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues?.get(position)
        if (item != null) {
            holder.mIdView.text = item.title
            holder.mContentView.text = item.content
            with(holder.mView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        if (mValues == null) {
            return 0
        }
        return mValues!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
