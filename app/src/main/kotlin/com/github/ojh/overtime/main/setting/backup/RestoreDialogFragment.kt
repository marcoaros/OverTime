package com.github.ojh.overtime.main.setting.backup

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.view.BaseDialogFragment
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.main.setting.backup.adapter.RestoreAdapter
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.extensions.toast
import kotlinx.android.synthetic.main.fragment_dialog_restore.*
import java.io.File
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class RestoreDialogFragment : BaseDialogFragment(), RestoreContract.View {

    @Inject
    lateinit var presenter: RestoreContract.Presenter<RestoreContract.View>

    private val progressDialog by lazy(NONE) {
        ProgressDialog(context).apply {
            setTitle("복원")
            setMessage("데이터를 복원중입니다...")
        }
    }

    private val backUpAdapter by lazy(NONE) {
        RestoreAdapter()
    }

    override fun setComponent(appComponent: AppComponent) {
        appComponent
                .plus(RestoreModule())
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_dialog_restore, container, false)
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

    override fun setRecyclerView(pathList: List<String>) {
        if(pathList.isEmpty())
            dismiss()

        backUpAdapter.setBackupFilePath(pathList)
    }

    override fun showRestoreResult(message: String) {
        toast(message)
        EventBus.post(Events.RefreshEvent())
        dismiss()
    }

    override fun showProgress() {
        if(!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    override fun dismissProgress() {
        if(progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}