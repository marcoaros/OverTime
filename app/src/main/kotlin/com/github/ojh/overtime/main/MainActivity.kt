package com.github.ojh.overtime.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.view.BaseActivity
import com.github.ojh.overtime.main.calendar.CalendarFragment
import com.github.ojh.overtime.main.setting.SettingFragment
import com.github.ojh.overtime.main.timeline.TimeLineFragment
import com.github.ojh.overtime.util.BackPressCloseHandler
import com.github.ojh.overtime.util.extensions.addFragment
import com.github.ojh.overtime.util.extensions.hideFragment
import com.github.ojh.overtime.util.extensions.showFragment
import com.github.ojh.overtime.util.extensions.startActivityWithTransition
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: MainPresenter<MainContract.View>

    private val backPressHandler by lazy(NONE) {
        BackPressCloseHandler(this)
    }

    override fun setComponent(appComponent: AppComponent): ActivityComponent {
        val component = appComponent.plus(MainModule())
        component.inject(this)
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        initToolbar()
        initFragments()
        initEventListener()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initFragments() {

        addFragment(
                R.id.fragment_main,
                TimeLineFragment.getInstance(),
                CalendarFragment.getInstance(),
                SettingFragment.getInstance()
        )

        hideFragment(
                CalendarFragment.getInstance(),
                SettingFragment.getInstance()
        )

        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initEventListener() {
        fab_write.setOnClickListener {
            presenter.clickWriteButton()
        }
    }

    override fun navigateToWrite() {
        val intent = Intent(this, WriteActivity::class.java)
        startActivityWithTransition(intent, fab_write)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        hideFragment(
                TimeLineFragment.getInstance(),
                CalendarFragment.getInstance(),
                SettingFragment.getInstance()
        )

        return when (item.itemId) {
            R.id.navigation_dashboard -> {
                showFragment(TimeLineFragment.getInstance())
                fab_write.visibility = View.VISIBLE
                true
            }

            R.id.navigation_calendar -> {
                showFragment(CalendarFragment.getInstance())
                fab_write.visibility = View.GONE
                true
            }

            R.id.navigation_settings -> {
                showFragment(SettingFragment.getInstance())
                fab_write.visibility = View.GONE
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        backPressHandler.onBackPressed()
    }
}
