package me.porge.android.cars.tests.instrumented.koin

import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class KoinInstrumentedTestRunner : AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?) =
        Instrumentation.newApplication(KoinInstrumentedTestApp::class.java, context)
}