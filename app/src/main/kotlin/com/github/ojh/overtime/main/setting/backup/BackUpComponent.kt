package com.github.ojh.overtime.main.setting.backup

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Subcomponent

@PerDialog
@Subcomponent(
        modules = arrayOf(BackUpModule::class)
)
interface BackUpComponent {
    fun inject(backUpDialogFragment: BackUpDialogFragment)
}