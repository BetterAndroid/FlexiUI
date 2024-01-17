import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    autowire(libs.plugins.kotlin.multiplatform) apply false
    autowire(libs.plugins.android.application) apply false
    autowire(libs.plugins.android.library) apply false
    autowire(libs.plugins.jetbrains.compose) apply false
    autowire(libs.plugins.maven.publish) apply false
}

libraryProjects {
    afterEvaluate {
        resolveDevPublishWorkflow()
        configure<PublishingExtension> {
            repositories {
                val repositoryDir = gradle.gradleUserHomeDir
                    .resolve("highcapable-maven-repository")
                    .resolve("repository")
                maven {
                    name = "HighCapableMavenReleases"
                    url = repositoryDir.resolve("releases").toURI()
                }
                maven {
                    name = "HighCapableMavenSnapShots"
                    url = repositoryDir.resolve("snapshots").toURI()
                }
            }
        }
        configure<MavenPublishBaseExtension> {
            configure(KotlinMultiplatform(javadocJar = JavadocJar.Empty()))
        }
    }
}

/**
 * How to enable dev publish:
 *
 * 1. Create a file named "publish_dev" in the project build directory.
 *
 * 2. Run "./gradlew :(project name):publishDev" to publish a dev version.
 *
 * - Note: If you delete the "publish_dev" file, the dev publish will be disabled and lost the last dev version code.
 */
fun Project.resolveDevPublishWorkflow() {
    fun String.parseCode() = (trim().toIntOrNull() ?: 0).toString().padStart(4, '0')
    fun String.nextCode() = (toInt() + 1).toString().parseCode()
    val devFile = projectDir.resolve("build").resolve("publish_dev")
    val isDevMode = devFile.exists()
    val code = (if (isDevMode) devFile.readText() else "1").parseCode()
    val devVersion = "$version-dev$code"
    version = if (isDevMode) devVersion else version
    if (isDevMode) println("Detected dev mode of $name, publish version is $devVersion")
    tasks.register("publishDev") {
        group = "publishing"
        dependsOn("publishAllPublicationsToHighCapableMavenSnapShotsRepository")
        doLast {
            val nextCode = code.nextCode()
            println("Dev publish is finished, next dev code is $nextCode")
            devFile.writeText(nextCode)
        }
    }
}

fun libraryProjects(action: Action<in Project>) {
    val libraries = listOf(
        Libraries.FLEXIUI_CORE,
        Libraries.FLEXIUI_RESOURCES,
    )
    allprojects { if (libraries.contains(name)) action.execute(this) }
}

object Libraries {
    const val FLEXIUI_CORE = "flexiui-core"
    const val FLEXIUI_RESOURCES = "flexiui-resources"
}