package com.github.ojh.overtime.main.setting.backup

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Module
import dagger.Provides

@Module
class BackUpModule {

    @PerDialog
    @Provides
    fun provideBackUpPresenter(
            presenter: BackUpPresenter<BackUpContract.View>

    ): BackUpContract.Presenter<BackUpContract.View> = presenter
}