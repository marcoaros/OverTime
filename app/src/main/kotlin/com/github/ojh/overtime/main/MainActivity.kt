package com.github.ojh.overtime.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.AppComponent
import com.github.ojh.overtime.base.view.BaseActivity
import com.github.ojh.overtime.main.calendar.CalendarFragment
import com.github.ojh.overtime.main.setting.SettingFragment
import com.github.ojh.overtime.main.timeline.TimeLineFragment
import com.github.ojh.overtime.pin.CustomPinActivity
import com.github.ojh.overtime.util.extensions.addFragment
import com.github.ojh.overtime.util.extensions.hideFragment
import com.github.ojh.overtime.util.extensions.showFragment
import com.github.ojh.overtime.util.extensions.startActivityWithTransition
import com.github.ojh.overtime.write.WriteActivity
import com.github.orangegangsters.lollipin.lib.managers.AppLock
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_ad.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: MainPresenter<MainContract.View>

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
        initPinDialog()
        initAd()
    }

    override fun onDestroy() {
        presenter.detachView()
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

        btn_cancel.setOnClickListener {
            presenter.clickAdCancel()
        }

        btn_close.setOnClickListener {
            presenter.clickAdClose()
        }

        layout_ad.setOnTouchListener { _, _ ->
            true
        }
    }

    private fun initPinDialog() {
        presenter.getPinSettings()
    }

    private fun initAd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout_ad.elevation = 24f //다이얼로그가 24의 elevation을 가진다.
        }
        presenter.initAd()
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
        if (layout_ad.visibility != View.VISIBLE) {
            presenter.onBackPress()
        }
    }

    override fun showPinDialog() {
        val intent = Intent(this@MainActivity, CustomPinActivity::class.java)
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN)
        startActivity(intent)
    }

    override fun initAdView(adRequest: AdRequest) {
        adView.loadAd(adRequest)
    }

    override fun showAdView() {
        layout_ad.visibility = View.VISIBLE
    }

    override fun dismissAdView() {
        layout_ad.visibility = View.GONE
    }

}
