import org.gradle.api.Project
import java.io.FileInputStream
import java.util.*

fun Properties.getString(key: String) = get(key) as String

fun Project.readPropertiesFromFile(path: String) =
    file(path).let(::FileInputStream).let { inputStream -> Properties().apply { load(inputStream) } }
