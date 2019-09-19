enum class ProjectGradleDependencies(
    val groupId: String,
    val artifactId: String,
    val version: String
) {
    ANDROID(
        groupId = "com.android.tools.build",
        artifactId = "gradle",
        version = "3.4.2"
    ),
    KOTLIN(
        groupId = "org.jetbrains.kotlin",
        artifactId = "kotlin-gradle-plugin",
        version = "1.3.41"
    ),
    JACOCO_FULL_REPORT(
        groupId = "com.palantir",
        artifactId = "jacoco-coverage",
        version = "0.4.0"
    ),
    JACOCO(
        groupId = "org.jacoco",
        artifactId = "org.jacoco.core",
        version = "0.8.4"
    );

    val notation
        get() = "$groupId:$artifactId:$version"
}