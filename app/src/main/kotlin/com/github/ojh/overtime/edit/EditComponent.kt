package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Subcomponent

@PerDialog
@Subcomponent(
        modules = arrayOf(EditModule::class)
)
interface EditComponent {
    fun inject(editDialogFragment: EditDialogFragment)
}