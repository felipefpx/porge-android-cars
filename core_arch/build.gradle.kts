plugins {
    id(ModuleGradlePlugins.ANDROID_LIBRARY)
    id(ModuleGradlePlugins.KOTLIN_ANDROID)
    id(ModuleGradlePlugins.KOTLIN_ANDROID_EXT)
    id(ModuleGradlePlugins.KOTLIN_KAPT)
    id(ModuleGradlePlugins.MERGED_JACOCO_REPORT)
}

jacoco {
    toolVersion = ProjectGradleDependencies.JACOCO.version
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

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
        }
    }
}

dependencies {
    // App
    api(Dependencies.KOTLIN_JDK.notation)
    api(Dependencies.KOTLIN_COROUTINES.notation)
    api(Dependencies.CORE_KTX.notation)

    api(Dependencies.APP_COMPAT.notation)
    api(Dependencies.MATERIAL_DESIGN.notation)
    api(Dependencies.CONSTRAINT_LAYOUT.notation)

    api(Dependencies.KOIN_CORE.notation)
    api(Dependencies.KOIN_VIEW_MODEL.notation)

    api(Dependencies.LIFECYCLE.notation)
    api(Dependencies.LIFECYCLE_KTX.notation)

    api(Dependencies.RETROFIT.notation)
    api(Dependencies.RETROFIT_GSON_CONVERTER.notation)
    implementation(Dependencies.RETROFIT_LOGGING.notation)

    api(Dependencies.GLIDE.notation)

    api(Dependencies.IDLING_RESOURCES.notation)

    // Tests
    testImplementation(project(Modules.CORE_TESTS.notation))
    testImplementation(Dependencies.JUNIT4.notation)
    testImplementation(Dependencies.KOIN_TESTS.notation)
    testImplementation(Dependencies.CORE_TESTING.notation)
    testImplementation(Dependencies.MOCKK.notation)
    testImplementation(Dependencies.KOTLIN_COROUTINES_TEST.notation)

    androidTestImplementation(Dependencies.ESPRESSO.notation)
    androidTestImplementation(Dependencies.ESPRESSO_CONTRIB.notation)
    androidTestImplementation(Dependencies.TEST_RULES.notation)
    androidTestImplementation(Dependencies.TEST_RUNNER.notation)
    androidTestUtil(Dependencies.TEST_ORCHESTRATOR.notation)
}