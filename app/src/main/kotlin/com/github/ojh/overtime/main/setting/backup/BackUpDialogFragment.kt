package com.github.ojh.overtime.main.setting.backup

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.view.BaseDialogFragment
import com.github.ojh.overtime.main.setting.backup.adapter.BackUpAdapter
import com.github.ojh.overtime.util.extensions.toast
import kotlinx.android.synthetic.main.fragment_dialog_backup.*
import java.io.File
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class BackUpDialogFragment : BaseDialogFragment(), BackUpContract.View {

    @Inject
    lateinit var presenter: BackUpContract.Presenter<BackUpContract.View>

    private val backUpAdapter by lazy(NONE) {
        BackUpAdapter()
    }

    override fun setComponent(appComponent: AppComponent) {
        appComponent
                .plus(BackUpModule())
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_dialog_backup, container, false)
        presenter.attachView(this)
        return view
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        presenter.loadBackUpFilePaths()
    }

    private fun initRecyclerView() {
        rv_backup.layoutManager = LinearLayoutManager(context)
        rv_backup.adapter = backUpAdapter
        backUpAdapter.onClickHandler = { _ ,position ->
            val internalFilePath = File(context.filesDir, "overtime.realm").path
            val selectedFilePath = backUpAdapter.backUpFilePathList[position]
            presenter.restoreData(internalFilePath, selectedFilePath)
        }
    }

    override fun setBackUpRecyclerView(pathList: List<String>) {
        backUpAdapter.setBackupFilePath(pathList)
    }

    override fun showRestoreResult(message: String) {
        toast(message)
        activity.finish()
    }
}