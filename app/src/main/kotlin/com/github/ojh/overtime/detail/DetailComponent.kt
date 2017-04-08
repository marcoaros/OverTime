package com.github.ojh.overtime.detail

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(
        modules = arrayOf(DetailModule::class)
)
interface DetailComponent : ActivityComponent {
    fun inject(detailActivity: DetailActivity)
}