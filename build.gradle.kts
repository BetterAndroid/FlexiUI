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
        configure<MavenPublishBaseExtension> {
            configure(KotlinMultiplatform(javadocJar = JavadocJar.Empty()))
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