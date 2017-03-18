package com.github.ojh.overtime.detail

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(DetailModule::class)
)
interface DetailComponent {
    fun inject(detailActivity: DetailActivity)
}