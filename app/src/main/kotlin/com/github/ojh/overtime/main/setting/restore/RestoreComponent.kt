package com.github.ojh.overtime.main.setting.restore

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Subcomponent

@PerDialog
@Subcomponent(
        modules = arrayOf(RestoreModule::class)
)
interface RestoreComponent {
    fun inject(restoreDialogFragment: RestoreDialogFragment)
}