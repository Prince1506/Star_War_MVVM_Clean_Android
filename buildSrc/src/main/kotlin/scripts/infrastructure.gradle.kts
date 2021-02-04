package scripts

import BuildPlugins
import scripts.Variants_gradle.Default
import java.util.*

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = BuildPlugins.Versions.gradleVersion
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register("runUnitTests") {
    description = "Runs all Unit Tests."
    dependsOn(":app:test${Default.BUILD_VARIANT}UnitTest")
}

tasks.register("runAcceptanceTests") {
    description = "Runs all Acceptance Tests in the connected device."
    dependsOn(":app:connected${Default.BUILD_VARIANT}AndroidTest")
}

tasks.register("compileApp") {
    description = "Compiles the Clean Architecture Android Client."
    dependsOn(":app:assemble${Default.BUILD_VARIANT}")
}

tasks.register("runApp", Exec::class) {
    val compileAppTask = "compileApp"
    val installAppTask = ":app:install${Default.BUILD_VARIANT}"

    description = "Compiles and runs the Clean Architecture Android Client in the connected device."
    dependsOn(compileAppTask, installAppTask)
    tasks.findByName(installAppTask)?.mustRunAfter(compileAppTask)

    val localProperties = File(project.rootDir, "local.properties")
    if (localProperties.exists()) {
        val properties = Properties()
        localProperties.inputStream().use { properties.load(it) }
        val sdkDir = properties["sdk.dir"]
        val adb = "${sdkDir}/platform-tools/adb"

        val applicationPackage =
            "com.mvvm_clean.star_wars.${Default.BUILD_FLAVOR}.${Default.BUILD_TYPE}"
        val launchActivity = "com.mvvm_clean.star_wars.core.presentation.navigation.RouteActivity"

        commandLine(adb, "shell", "am", "start", "-n", "${applicationPackage}/${launchActivity}")
    }
}