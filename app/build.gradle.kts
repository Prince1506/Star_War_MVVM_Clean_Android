plugins {
    // Application Specific Plugins
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroidExtensions)

    // Internal Script plugins
    id(ScriptPlugins.variants)
    id(ScriptPlugins.quality)
    id(ScriptPlugins.compilation)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    lintOptions {
        isCheckReleaseBuilds = false
        //If you want to continue even if errors found use following line
        isAbortOnError = false
    }

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        applicationId = AndroidClient.appId
        versionCode = AndroidClient.versionCode
        versionName = AndroidClient.versionName
        testInstrumentationRunner = AndroidClient.testRunner
    }
    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
        getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

/* Compile time dependencies */
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.daggerCompiler)
    compileOnly(Libraries.javaxAnnotation)
    compileOnly(Libraries.javaxInject)
    kapt(Libraries.databinding)
/*Application dependencies*/

    // Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(Libraries.ktxCore)


    // Design related libraries
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.material)
    implementation(Libraries.circularImageView)

    // MVVM Libraries
    implementation(Libraries.viewModel)
    implementation(Libraries.liveData)
    implementation(Libraries.lifecycleExtensions)

    // Annotations
    implementation(Libraries.androidAnnotations)

    // Image
    implementation(Libraries.glide)

    // Dependency Injection
    implementation(Libraries.dagger)

    // Network
    implementation(Libraries.retrofit)
    implementation(Libraries.okHttpLoggingInterceptor)
    implementation(Libraries.moshiConveter)

    // Unit/Android tests dependencies
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kluent)
    testImplementation(TestLibraries.robolectric)

    // Acceptance tests dependencies
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.testCore)
    androidTestImplementation(TestLibraries.espressoCore)
    implementation(TestLibraries.espressoIdlings)
    androidTestImplementation(TestLibraries.espressoContrib)
    androidTestImplementation(TestLibraries.testExtJunit)
    androidTestImplementation(TestLibraries.testRules)
    androidTestImplementation(TestLibraries.espressoIntents)
    annotationProcessor("com.github.bumptech.glide:compiler:4.8.0")

    // Development dependencies
    debugImplementation(DevLibraries.leakCanary)
}