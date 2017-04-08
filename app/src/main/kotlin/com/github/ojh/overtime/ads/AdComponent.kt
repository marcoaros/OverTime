package com.github.ojh.overtime.ads

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Subcomponent

@PerDialog
@Subcomponent(
        modules = arrayOf(AdModule::class)
)
interface AdComponent {
    fun inject(adDialogFragment: AdDialogFragment)
}