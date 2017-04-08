package com.github.ojh.overtime.main.setting

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @PerFragment
    @Provides
    fun provideSettingPresenter(
            settingPresenter: SettingPresenter<SettingContract.View>

    ): SettingContract.Presenter<SettingContract.View> = settingPresenter
}