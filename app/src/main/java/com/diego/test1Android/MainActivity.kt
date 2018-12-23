package com.diego.test1Android

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.prof.rssparser.Article
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        RSSFragment.OnListFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener displayView(item.itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        displayView(navigation.selectedItemId)
    }

    private val homeFragment = HomeFragment()
    private val rssFragment = RSSFragment.newInstance(this)

    private fun displayView(viewId: Int): Boolean {
        var fragment: Fragment? = null
        var title = getString(R.string.app_name)

        when (viewId) {
            R.id.navigation_home -> {
                fragment = homeFragment
                title = "Home"
            }
            R.id.navigation_dashboard -> {
                fragment = rssFragment
                title = "RSS"
            }
        }

        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.mainFrame, fragment)
            ft.commit()
            return true
        }

        return false
    }

    override fun onListFragmentInteraction(article: Article?) {
        homeFragment.displayArticle(article)
    }
}
