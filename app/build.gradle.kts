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

        //TODO: Remove this when migrating the DI framework
        getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
    }
}

dependencies {
    /* Compile time dependencies */
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.daggerCompiler)
    compileOnly(Libraries.javaxAnnotation)
    compileOnly(Libraries.javaxInject)

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
    testImplementation("org.mockito:mockito-core:2.+")

    // Acceptance tests dependencies
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation("androidx.test:core:1.1.0")
    androidTestImplementation(TestLibraries.espressoCore)
    implementation("androidx.test.espresso:espresso-idling-resource:3.2.0")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.2.0")
    androidTestImplementation(TestLibraries.testExtJunit)
    androidTestImplementation(TestLibraries.testRules)
    androidTestImplementation(TestLibraries.espressoIntents)

    // Development dependencies
    debugImplementation(DevLibraries.leakCanary)
}