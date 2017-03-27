package com.github.ojh.overtime.detail

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(DetailModule::class)
)
interface DetailComponent : ActivityComponent {
    fun inject(detailActivity: DetailActivity)
}