package com.diego.test1_android

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.diego.test1_android.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(),
        HomeFragment.OnFragmentInteractionListener,
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

    fun displayView(viewId: Int): Boolean {
        var fragment: Fragment? = null
        var title = getString(R.string.app_name)

        when (viewId) {
            R.id.navigation_home -> {
                fragment = HomeFragment()
                title = "Home"
            }
            R.id.navigation_dashboard -> {
                fragment = RSSFragment()
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

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
