enum class Dependencies(
    val groupId: String,
    val artifactId: String,
    val version: String
) {
    // Android
    CONSTRAINT_LAYOUT(
        groupId = "androidx.constraintlayout",
        artifactId = "constraintlayout",
        version = "1.1.3"
    ),
    APP_COMPAT(
        groupId = "androidx.appcompat",
        artifactId = "appcompat",
        version = "1.0.2"
    ),
    MATERIAL_DESIGN(
        groupId = "com.google.android.material",
        artifactId = "material",
        version = "1.0.0"
    ),
    GOOGLE_MAPS(
        groupId = "com.google.android.gms",
        artifactId = "play-services-maps",
        version = "17.0.0"
    ),

    // KTX
    CORE_KTX(
        groupId = "androidx.core",
        artifactId = "core-ktx",
        version = "1.0.0"
    ),
    LIFECYCLE(
        groupId = "androidx.lifecycle",
        artifactId = "lifecycle-extensions",
        version = "2.1.0-rc01"
    ),
    LIFECYCLE_KTX(
        groupId = LIFECYCLE.groupId,
        artifactId = "lifecycle-viewmodel-ktx",
        version = LIFECYCLE.version
    ),

    // Kotlin
    KOTLIN_JDK(
        groupId = "org.jetbrains.kotlin",
        artifactId = "kotlin-stdlib-jdk7",
        version = ProjectGradleDependencies.KOTLIN.version
    ),
    KOTLIN_COROUTINES(
        groupId = "org.jetbrains.kotlinx",
        artifactId = "kotlinx-coroutines-android",
        version = "1.3.0"
    ),
    KOTLIN_COROUTINES_TEST(
        groupId = KOTLIN_COROUTINES.groupId,
        artifactId = "kotlinx-coroutines-test",
        version = KOTLIN_COROUTINES.version
    ),

    // Koin
    KOIN_CORE(
        groupId = "org.koin",
        artifactId = "koin-core",
        version = "2.0.1"
    ),
    KOIN_VIEW_MODEL(
        groupId = "org.koin",
        artifactId = "koin-androidx-viewmodel",
        version = KOIN_CORE.version
    ),
    KOIN_TESTS(
        groupId = "org.koin",
        artifactId = "koin-test",
        version = KOIN_CORE.version
    ),
    CORE_TESTING(
        groupId = "androidx.arch.core",
        artifactId = "core-testing",
        version = LIFECYCLE.version
    ),

    // Retrofit
    RETROFIT(
        groupId = "com.squareup.retrofit2",
        artifactId = "retrofit",
        version = "2.6.0"
    ),
    RETROFIT_LOGGING(
        groupId = "com.squareup.okhttp3",
        artifactId = "logging-interceptor",
        version = "3.10.0"
    ),
    RETROFIT_GSON_CONVERTER(
        groupId = RETROFIT.groupId,
        artifactId = "converter-gson",
        version = RETROFIT.version
    ),

    // Glide
    GLIDE(
        groupId = "com.github.bumptech.glide",
        artifactId = "glide",
        version = "4.9.0"
    ),

    // Testing
    JUNIT4(
        groupId = "junit",
        artifactId = "junit",
        version = "4.12"
    ),
    TEST_RUNNER(
        groupId = "androidx.test",
        artifactId = "runner",
        version = "1.2.0"
    ),
    TEST_RULES(
        groupId = TEST_RUNNER.groupId,
        artifactId = "rules",
        version = TEST_RUNNER.version
    ),
    TEST_ORCHESTRATOR(
        groupId = TEST_RUNNER.groupId,
        artifactId = "orchestrator",
        version = TEST_RUNNER.version
    ),
    ESPRESSO(
        groupId = "androidx.test.espresso",
        artifactId = "espresso-core",
        version = "3.2.0"
    ),
    ESPRESSO_CONTRIB(
        groupId = ESPRESSO.groupId,
        artifactId = "espresso-contrib",
        version = ESPRESSO.version
    ),
    IDLING_RESOURCES(
        groupId = "androidx.test.espresso.idling",
        artifactId = "idling-concurrent",
        version = ESPRESSO.version
    ),
    MOCKK(
        groupId = "io.mockk",
        artifactId = "mockk",
        version = "1.9"
    );

    val notation
        get() = "$groupId:$artifactId:$version"
}
















