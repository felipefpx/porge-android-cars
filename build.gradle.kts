buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(ProjectGradleDependencies.ANDROID.notation)
        classpath(ProjectGradleDependencies.KOTLIN.notation)
        classpath(ProjectGradleDependencies.JACOCO_FULL_REPORT.notation)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

plugins {
    id(
        ProjectGradlePlugins.JACOCO_FULL_REPORT
    ) version ProjectGradleDependencies.JACOCO_FULL_REPORT.version
}

jacocoFull {
    excludeProject(Modules.CORE_TESTS.notation)
}

tasks
    .register("clean")
    .configure { delete("build") }
