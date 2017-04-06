package com.github.ojh.overtime.main.setting.restore

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.AppComponent
import com.github.ojh.overtime.base.view.BaseDialogFragment
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.main.setting.restore.adapter.RestoreAdapter
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

    companion object {
        const val KEY_PATH_LIST = "key_path_list"

        fun newInstance(pathList: List<String>): RestoreDialogFragment {
            val fragment = RestoreDialogFragment()
            val args = Bundle()
            args.putStringArrayList(KEY_PATH_LIST, ArrayList(pathList))
            fragment.arguments = args
            return fragment
        }
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

        val pathList = arguments.getStringArrayList(KEY_PATH_LIST)
        initRecyclerView(pathList)
    }

    private fun initRecyclerView(pathList: List<String>) {
        rv_backup.layoutManager = LinearLayoutManager(context)

        val restoreAdapter = RestoreAdapter(
                pathList,
                { _, position ->
                    val internalFilePath = File(context.filesDir, "overtime.realm").path
                    val selectedFilePath = pathList[position]
                    presenter.restoreData(internalFilePath, selectedFilePath)
                }
        )

        rv_backup.adapter = restoreAdapter
    }

    override fun showRestoreResult(message: String) {
        toast(message)
        dismiss()
    }

    override fun showProgress() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    override fun dismissProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}