package com.github.ojh.overtime.edit

import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Component

@PerDialog
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(EditModule::class)
)
interface EditComponent {
    fun inject(editDialogFragment: EditDialogFragment)
}