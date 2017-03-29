package kr.co.wplanet.android.presidentkim.kt.experience.resurve.component.calendar

import com.github.ojh.overtime.main.calendar.caldroid.CaldroidCustomAdapter
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidGridAdapter

/**
 * Created by ohjaehwan on 2016. 12. 21..
 */
class CustomCaldroidFragment : CaldroidFragment() {

    override fun getNewDatesGridAdapter(month: Int, year: Int): CaldroidGridAdapter {
        return CaldroidCustomAdapter(activity, month, year, getCaldroidData(), extraData)
    }
}