package com.github.ojh.overtime.main.calendar


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R


/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment private constructor(): Fragment() {

    companion object {
        private val fragment by lazy { CalendarFragment() }
        fun getInstance(): CalendarFragment {
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_calendar, container, false)
    }

}// Required empty public constructor
