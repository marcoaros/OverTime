package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
class EditModule {

    @PerFragment
    @Provides
    fun provideOptionDialogPresenter(
            editPresenter: EditPresenter<EditContract.View>

    ): EditContract.Presenter<EditContract.View> = editPresenter
}