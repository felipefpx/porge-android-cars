plugins {
    id(ModuleGradlePlugins.ANDROID_LIBRARY)
    id(ModuleGradlePlugins.KOTLIN_ANDROID)
    id(ModuleGradlePlugins.KOTLIN_ANDROID_EXT)
    id(ModuleGradlePlugins.KOTLIN_KAPT)
}


android {
    compileSdkVersion(AppConfig.TARGET_ANDROID_API)

    defaultConfig {
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = true

        minSdkVersion(AppConfig.MIN_ANDROID_API)
        targetSdkVersion(AppConfig.TARGET_ANDROID_API)

        testInstrumentationRunner = AppConfig.TEST_RUNNER
    }
}

dependencies {
    implementation(project(Modules.CORE_ARCH.notation))

    implementation(Dependencies.JUNIT4.notation)
    implementation(Dependencies.KOIN_TESTS.notation)
    implementation(Dependencies.CORE_TESTING.notation)
    implementation(Dependencies.MOCKK.notation)

    implementation(Dependencies.ESPRESSO.notation)
    implementation(Dependencies.ESPRESSO_CONTRIB.notation)
    implementation(Dependencies.TEST_RULES.notation)
    implementation(Dependencies.TEST_RUNNER.notation)

    implementation(Dependencies.KOTLIN_COROUTINES.notation)
    implementation(Dependencies.KOTLIN_COROUTINES_TEST.notation)
    implementation(Dependencies.KOIN_CORE.notation)
    implementation(Dependencies.KOIN_VIEW_MODEL.notation)
}