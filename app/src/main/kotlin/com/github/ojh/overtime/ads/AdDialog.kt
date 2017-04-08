package com.github.ojh.overtime.ads

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_dialog_ad.*

class AdDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dialog_ad, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cancel.setOnClickListener {
            dismiss()
        }

        btn_close.setOnClickListener {
            activity.finish()
        }

        adView.loadAd(AdRequest.Builder().build())

    }
}