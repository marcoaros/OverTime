package com.github.ojh.overtime.write

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.*
import com.github.ojh.overtime.write.WriteContract.Companion.REQUEST_GALLERY
import kotlinx.android.synthetic.main.activity_write.*
import org.parceler.Parcels
import javax.inject.Inject


class WriteActivity : BaseActivity(), WriteContract.View {

    private var isShowingAnimation = false
    private var isUpdate = false

    @Inject
    lateinit var writePresenter: WritePresenter<WriteContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerWriteComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        writePresenter.attachView(this)

        initToolbar()
        initTimeLine()
        initEventListener()
        initAnimation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_write, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            R.id.menu_write -> {
                writePresenter.saveTimeLine()
                true
            }

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_write)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTimeLine() {
        val timeLine = Parcels.unwrap <TimeLine>(
                intent?.getParcelableExtra<Parcelable>(TimeLine.KEY_TIMELINE)
        ) ?: TimeLine()

        if (timeLine.mId != null) {
            isUpdate = true
        }

        writePresenter.initTimeLine(timeLine)
    }

    override fun initView(timeLine: TimeLine) {
        timeLine.mContent?.let {
            edit_content.setText(it)
        }

        timeLine.mImgUri?.let {
            iv_gallery.load(Uri.parse(it))
        }
    }

    private fun initEventListener() {
        iv_gallery.setOnClickListener {
            writePresenter.checkStoragePermission(this)
        }

        edit_content.setOnSimpleTextWather {
            writePresenter.onContentTextChanged(it)
        }
    }

    private fun initAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isUpdate) {
            val transition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.changebounds_with_arcmotion)

            window.sharedElementEnterTransition = transition

            isShowingAnimation = true

            transition.addSimpleEndTransitionListener {
                animateRevealShow(rl_write_reveal_hide)
            }

            val fade = Fade().apply {
                duration = resources.getInteger(R.integer.animation_duration).toLong()
            }

            window.returnTransition = fade
        } else {
            fab_write.visibility = View.INVISIBLE
            ll_write_reveal_show.visibility = View.VISIBLE
        }
    }

    private fun animateRevealShow(viewRoot: View) {
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        GUIUtils.animateRevealShow(
                this,
                viewRoot,
                fab_write.width / 2,
                R.color.colorAccent,
                cx,
                cy,
                {
                    val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
                    animation.duration = 300
                    ll_write_reveal_show.startAnimation(animation)
                    ll_write_reveal_show.visibility = View.VISIBLE
                    isShowingAnimation = false
                }
        )
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isUpdate) {

            if (isShowingAnimation) {
                return
            }

            isShowingAnimation = true
            GUIUtils.animateRevealHide(
                    this,
                    rl_write_reveal_hide,
                    R.color.colorAccent,
                    fab_write.width / 2,
                    {
                        super.onBackPressed()
                    }
            )
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        writePresenter.detachView()
        super.onDestroy()
    }

    override fun setErrorContent(isError: Boolean) {
        if (isError) {
            input_content.error = getString(R.string.write_content_error)
            requestFocus(input_content)
        } else {
            input_content.isErrorEnabled = false
        }
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    override fun navigateToMain() {
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        writePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showRationalDialog() {
        val deniedMessage = getString(R.string.permission_storage_message)
        PermissionUtil.showRationalDialog(this, deniedMessage)
    }

    override fun navigateToGallery(uri: Uri) {
        val intent = Intent().cropIntent(uri, iv_gallery.width, iv_gallery.height)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        writePresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun loadCroppedImage(uri: Uri) {
        iv_gallery.load(uri)
    }
}