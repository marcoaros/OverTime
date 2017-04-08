package com.github.ojh.overtime.ads

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Module
import dagger.Provides


@Module
class AdModule {

    @PerDialog
    @Provides
    fun provideAdPresenter(
            adPresenter: AdPresenter<AdContract.View>

    ): AdContract.Presenter<AdContract.View> = adPresenter

}