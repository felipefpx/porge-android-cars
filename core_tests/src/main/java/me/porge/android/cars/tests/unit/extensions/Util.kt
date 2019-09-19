package me.porge.android.cars.tests.unit.extensions

inline fun <reified T> invokeHiddenMethod(instance: Any, name: String) {
    with(T::class.java.getDeclaredMethod(name)) {
        isAccessible = true
        invoke(instance)
    }
}