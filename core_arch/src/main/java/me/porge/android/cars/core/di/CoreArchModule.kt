package me.porge.android.cars.core.di

import androidx.test.espresso.idling.CountingIdlingResource
import me.porge.android.cars.core.BuildConfig
import me.porge.android.cars.core.DispatcherProvider
import me.porge.android.cars.core.layers.presentation.views.imageloader.ImageLoader
import me.porge.android.cars.core.layers.presentation.views.listable.ListableBinder
import me.porge.android.cars.core.layers.presentation.views.listable.ListableRvAdapter
import me.porge.android.cars.core.layers.presentation.views.listable.OnListableItemClicked
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APP_IDLING_RESOURCE = "appIdlingRes"

val coreArchModule = module {

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single {
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory { (baseUrl: String) ->
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(get())
            .client(get())
            .build()
    }

    factory { (binders: Map<Int, ListableBinder<*>>, onItemClicked: OnListableItemClicked) ->
        ListableRvAdapter(binders, onItemClicked)
    }

    single {
        CountingIdlingResource(APP_IDLING_RESOURCE)
    }

    single {
        DispatcherProvider()
    }

    factory {
        ImageLoader()
    }
}