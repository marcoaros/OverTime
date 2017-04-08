package com.github.ojh.overtime.detail

import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @PerActivity
    @Provides
    fun provideDetailPresenter(
            detailPresenter: DetailPresenter<DetailContract.View>

    ): DetailContract.Presenter<DetailContract.View> = detailPresenter
}