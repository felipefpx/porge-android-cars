package me.porge.android.cars.tests.instrumented.koin

import androidx.test.espresso.idling.CountingIdlingResource
import me.porge.android.cars.core.di.APP_IDLING_RESOURCE
import me.porge.android.cars.core.layers.presentation.views.imageloader.ImageLoader
import me.porge.android.cars.tests.instrumented.imageloader.TestImageLoader
import org.koin.dsl.module


val countingIdlingResource = CountingIdlingResource(APP_IDLING_RESOURCE)
val testImageLoader = TestImageLoader()

val androidTestModule = module(override = true) {
    single {
        countingIdlingResource
    }

    single<ImageLoader> {
        testImageLoader
    }
}