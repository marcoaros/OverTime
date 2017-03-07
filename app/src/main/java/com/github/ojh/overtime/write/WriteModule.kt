package com.github.ojh.overtime.write

import com.github.ojh.overtime.di.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
@Module
class WriteModule {

    @PerActivity
    @Provides
    fun provideWritePresenter(writePresenter: WritePresenter<WriteContract.View>): WriteContract.Presenter<WriteContract.View> = writePresenter
}