package com.github.ojh.overtime.main.setting.restore

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Module
import dagger.Provides

@Module
class RestoreModule {

    @PerDialog
    @Provides
    fun provideRestorePresenter(
            presenter: RestorePresenter<RestoreContract.View>

    ): RestoreContract.Presenter<RestoreContract.View> = presenter
}