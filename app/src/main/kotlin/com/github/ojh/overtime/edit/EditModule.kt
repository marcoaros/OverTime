package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.scope.PerDialog
import dagger.Module
import dagger.Provides

@Module
class EditModule {

    @PerDialog
    @Provides
    fun provideOptionDialogPresenter(
            editPresenter: EditPresenter<EditContract.View>

    ): EditContract.Presenter<EditContract.View> = editPresenter
}