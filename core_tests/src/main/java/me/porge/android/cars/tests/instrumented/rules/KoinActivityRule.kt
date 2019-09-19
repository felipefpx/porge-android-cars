package me.porge.android.cars.tests.instrumented.rules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.test.rule.ActivityTestRule
import me.porge.android.cars.core.layers.presentation.views.BaseActivity
import me.porge.android.cars.tests.unit.extensions.invokeHiddenMethod
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class KoinActivityRule<T : AppCompatActivity>(
    activityClass: Class<T>,
    initialTouchMode: Boolean = true,
    launchActivity: Boolean = true,
    private val modules: List<Module> = emptyList(),
    private val overrideModules: Module.() -> Unit = {}
) : ActivityTestRule<T>(activityClass, initialTouchMode, launchActivity) {

    private val modulesToLoad =
        modules.toMutableList().apply {
            add(module(override = true) { overrideModules() })
        }

    override fun beforeActivityLaunched() {
        loadKoinModules(modulesToLoad)
        super.beforeActivityLaunched()
    }

    override fun finishActivity() {
        (activity as? BaseActivity<*, *>)?.viewModel?.also { viewModel ->
            invokeHiddenMethod<ViewModel>(viewModel, "onCleared")
        }
        super.finishActivity()
    }

    override fun afterActivityFinished() {
        super.afterActivityFinished()
        unloadKoinModules(modulesToLoad)
    }
}