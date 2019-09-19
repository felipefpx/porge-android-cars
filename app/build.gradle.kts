plugins {
    id(ModuleGradlePlugins.ANDROID_APPLICATION)
    id(ModuleGradlePlugins.KOTLIN_ANDROID)
    id(ModuleGradlePlugins.KOTLIN_ANDROID_EXT)
    id(ModuleGradlePlugins.KOTLIN_KAPT)
}


val keystoreProperties = rootProject.readPropertiesFromFile("keystore.properties")

android {
    compileSdkVersion(AppConfig.TARGET_ANDROID_API)

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = true

        minSdkVersion(AppConfig.MIN_ANDROID_API)
        targetSdkVersion(AppConfig.TARGET_ANDROID_API)
        testInstrumentationRunner = AppConfig.TEST_RUNNER
    }

    signingConfigs {
        // In a real project must be safe stored
        getByName("debug") {
            storeFile = rootProject.file(keystoreProperties.getString("storeFile"))
            storePassword = keystoreProperties.getString("storePassword")
            keyAlias = keystoreProperties.getString("keyAlias")
            keyPassword = keystoreProperties.getString("keyPassword")
        }

        create("release") {
            storeFile = rootProject.file(keystoreProperties.getString("storeFile"))
            storePassword = keystoreProperties.getString("storePassword")
            keyAlias = keystoreProperties.getString("keyAlias")
            keyPassword = keystoreProperties.getString("keyPassword")
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    // Project modules
    implementation(project(Modules.CORE_ARCH.notation))
    implementation(project(Modules.FEATURE_VEHICLES.notation))
}