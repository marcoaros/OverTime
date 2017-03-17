package com.github.ojh.overtime.setting

import com.github.ojh.overtime.di.PerFragment
import dagger.Module
import dagger.Provides

@Module
class TimeLineSettingDialogModule {

    @PerFragment
    @Provides
    fun provideOptionDialogPresenter(
            timeLineSettingDialogPresenter: TimeLineSettingDialogPresenter<TimeLineSettingDialogContract.View>

    ): TimeLineSettingDialogContract.Presenter<TimeLineSettingDialogContract.View> = timeLineSettingDialogPresenter
}