package com.github.ojh.overtime.main.calendar


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import kr.co.wplanet.android.presidentkim.kt.experience.resurve.component.calendar.CustomCaldroidFragment
import kotlin.LazyThreadSafetyMode.NONE
import com.roomorama.caldroid.CaldroidFragment
import java.util.*


class CalendarFragment private constructor() : Fragment() {

    private val caldroidFragment by lazy(NONE) {
        CustomCaldroidFragment()
    }

    companion object {
        private val fragment by lazy { CalendarFragment() }

        fun getInstance(): CalendarFragment = fragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {


        val args = Bundle()
        val cal = Calendar.getInstance()
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)

        caldroidFragment.arguments = args

        childFragmentManager.beginTransaction()
                .replace(R.id.calendar, caldroidFragment)
                .commit()
    }

}
