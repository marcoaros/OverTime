package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class WriteModule {

    @PerActivity
    @Provides
    fun provideWritePresenter(writePresenter: WritePresenter<WriteContract.View>)
            : WriteContract.Presenter<WriteContract.View> = writePresenter
}