enum class Modules {
    APP,
    CORE_ARCH,
    CORE_TESTS,
    FEATURE_VEHICLES;

    val notation
        get() = ":${name.toLowerCase()}"
}