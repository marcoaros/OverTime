package com.github.ojh.overtime.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.MenuItem
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.main.calendar.CalendarFragment
import com.github.ojh.overtime.main.setting.SettingFragment
import com.github.ojh.overtime.main.timeline.TimeLineFragment
import com.github.ojh.overtime.util.BackPressCloseHandler
import com.github.ojh.overtime.util.replaceFrament
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val backPressHandler by lazy(NONE) {
        BackPressCloseHandler(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initFragments()
        initEventListener()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_main, TimeLineFragment.getInstance())
                .commit()

        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initEventListener() {
        fab_write.setOnClickListener {
            navigateToWrite()
        }
    }

    fun navigateToWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val p1 = with(fab_write) {
                Pair.create<View, String>(this, this.transitionName)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(this, p1)
            startActivity(Intent(this, WriteActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(this, WriteActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_dashboard -> {
                replaceFrament(R.id.fragment_main, TimeLineFragment.getInstance())
                true
            }

            R.id.navigation_calendar -> {
                replaceFrament(R.id.fragment_main, CalendarFragment.getInstance())
                true
            }

            R.id.navigation_settings -> {
                replaceFrament(R.id.fragment_main, SettingFragment.getInstance())
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        backPressHandler.onBackPressed()
    }
}
