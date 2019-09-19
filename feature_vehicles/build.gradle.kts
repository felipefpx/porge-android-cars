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

        testInstrumentationRunner = AppConfig.KOIN_TEST_RUNNER
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

    sourceSets {
        getByName("test") {
            java.srcDir("src/commonTests/java")
        }

        getByName("androidTest") {
            java.srcDir("src/commonTests/java")
        }
    }
}

dependencies {
    // Core module
    implementation(project(Modules.CORE_ARCH.notation))

    // App
    implementation(Dependencies.GOOGLE_MAPS.notation)

    // Tests
    testImplementation(project(Modules.CORE_TESTS.notation))
    testImplementation(Dependencies.JUNIT4.notation)
    testImplementation(Dependencies.KOIN_TESTS.notation)
    testImplementation(Dependencies.CORE_TESTING.notation)
    testImplementation(Dependencies.MOCKK.notation)
    testImplementation(Dependencies.KOTLIN_COROUTINES_TEST.notation)

    androidTestImplementation(project(Modules.CORE_TESTS.notation))
    androidTestImplementation(Dependencies.KOIN_TESTS.notation)
    androidTestImplementation(Dependencies.CORE_TESTING.notation)
    androidTestImplementation(Dependencies.ESPRESSO.notation)
    androidTestImplementation(Dependencies.ESPRESSO_CONTRIB.notation)
    androidTestImplementation(Dependencies.TEST_RULES.notation)
    androidTestImplementation(Dependencies.TEST_RUNNER.notation)
    androidTestUtil(Dependencies.TEST_ORCHESTRATOR.notation)
}