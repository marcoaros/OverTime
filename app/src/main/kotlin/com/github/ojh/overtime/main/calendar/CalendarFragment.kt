package com.github.ojh.overtime.main.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseFragment
import com.github.ojh.overtime.main.MainComponent
import com.roomorama.caldroid.CaldroidFragment
import kr.co.wplanet.android.presidentkim.kt.experience.resurve.component.calendar.CustomCaldroidFragment
import java.util.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


class CalendarFragment private constructor() : BaseFragment<MainComponent>(), CalendarContract.View {

    @Inject
    lateinit var presenter: CalendarContract.Presenter<CalendarContract.View>

    companion object {
        private val fragment by lazy { CalendarFragment() }

        fun getInstance(): CalendarFragment = fragment
    }

    private val caldroidFragment by lazy(NONE) {
        CustomCaldroidFragment()
    }

    override fun setComponent(activityComponent: MainComponent) {
        activityComponent
                .plus(CalendarModule())
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_calendar, container, false)
        presenter.attachView(this)
        return view
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initCalendar()
        presenter.initWrittenDates()
        presenter.initEventListener()
    }

    private fun initCalendar() {
        val args = Bundle()
        val cal = Calendar.getInstance()
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)

        caldroidFragment.arguments = args

        childFragmentManager.beginTransaction()
                .add(R.id.calendar, caldroidFragment)
                .commit()
    }


    override fun setWrittenDate(dateList: List<Date>) {
        caldroidFragment.clearSelectedDates()
        dateList.forEach {
            caldroidFragment.setSelectedDate(it)
        }

        caldroidFragment.refreshView()
    }

}
