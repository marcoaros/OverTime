package com.github.ojh.overtime.ads

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.AppComponent
import com.github.ojh.overtime.base.view.BaseDialogFragment
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_dialog_ad.*
import javax.inject.Inject

class AdDialogFragment : BaseDialogFragment(), AdContract.View {

    @Inject
    lateinit var adPresenter: AdContract.Presenter<AdContract.View>

    override fun setComponent(appComponent: AppComponent) {
        appComponent
                .plus(AdModule())
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_dialog_ad, container, false)
        adPresenter.attachView(this)
        return view
    }

    override fun onDestroyView() {
        adPresenter.detachView()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cancel.setOnClickListener {
            dismiss()
        }

        btn_close.setOnClickListener {
            activity.finish()
        }

        adPresenter.getAd()

    }

    override fun setAdView(adRequest: AdRequest) {
        adView.loadAd(adRequest)
    }
}