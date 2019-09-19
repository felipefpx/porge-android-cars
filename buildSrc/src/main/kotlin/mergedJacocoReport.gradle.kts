@file:Suppress("UnstableApiUsage")

plugins {
    jacoco
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
    }
}

task<JacocoReport>("mergedJacocoReport") {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    reports {
        xml.isEnabled = false
        html.isEnabled = false
    }

    val fileFilter = mutableSetOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )

    classDirectories.setFrom(
        fileTree(project.buildDir) {
            include(
                "**/classes/**/main/**",
                "**/intermediates/classes/debug/**",
                "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
                "**/tmp/kotlin-classes/debug/**"
            )

            exclude(fileFilter)
        }
    )

    sourceDirectories.setFrom(
        fileTree("${project.buildDir}") {
            include(
                "src/main/java/**",
                "src/main/kotlin/**",
                "src/debug/java/**",
                "src/debug/kotlin/**"
            )
        }
    )
    executionData.setFrom(
        fileTree(project.buildDir) {
            include(
                "outputs/code_coverage/**/*.ec",
                "jacoco/jacocoTestReportDebug.exec",
                "jacoco/testDebugUnitTest.exec",
                "jacoco/test.exec"
            )
        }
    )
}