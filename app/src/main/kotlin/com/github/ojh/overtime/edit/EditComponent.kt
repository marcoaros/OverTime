package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(
        modules = arrayOf(EditModule::class)
)
interface EditComponent {
    fun inject(editDialogFragment: EditDialogFragment)
}