package com.github.ojh.overtime.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.ojh.overtime.R
import com.github.ojh.overtime.main.calendar.CalendarFragment
import com.github.ojh.overtime.main.setting.SettingFragment
import com.github.ojh.overtime.main.timeline.TimeLineFragment
import com.github.ojh.overtime.util.BackPressCloseHandler
import com.github.ojh.overtime.util.replaceFrament
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val filterAdapter: ArrayAdapter<CharSequence> by lazy(NONE) {
        ArrayAdapter.createFromResource(application, R.array.filter_array, R.layout.spiiner_item)
                .apply {
                    setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                }
    }

    private val backPressHandler by lazy(NONE) {
        BackPressCloseHandler(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initFragments()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_timeline, menu)
        val item = menu?.findItem(R.id.menu_filter)

        val spinnerFilter = MenuItemCompat.getActionView(item) as Spinner
        spinnerFilter.adapter = filterAdapter
        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TimeLineFragment.getInstance().presenter.getTimeLines(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return true
    }

    override fun onBackPressed() {
        backPressHandler.onBackPressed()
    }
}
