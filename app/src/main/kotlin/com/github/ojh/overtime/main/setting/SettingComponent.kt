package com.github.ojh.overtime.main.setting

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(
        modules = arrayOf(SettingModule::class)
)
interface SettingComponent {
    fun inject(settingFragment: SettingFragment)
}