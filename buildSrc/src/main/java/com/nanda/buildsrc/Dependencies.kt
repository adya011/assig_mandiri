package com.nanda.buildsrc

import com.nanda.buildsrc.Versions.coroutineAdapter
import com.nanda.buildsrc.Versions.coroutines
import com.nanda.buildsrc.Versions.recyclerview

object AndroidLibraries {
    // UI
    const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerview"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    // Navigation
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

    // Dependency Injection
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitCoroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$coroutineAdapter"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"

    // Test
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkJvm = "io.mockk:mockk-agent-jvm:${Versions.mockk}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
}

object Versions {
    const val recyclerview = "1.3.0"
    const val navigation = "2.5.3"
    const val koin = "3.4.0"
    const val retrofit = "2.9.0"
    const val coroutineAdapter = "0.9.2"
    const val moshiConverter = "2.0.0"
    const val coroutines = "1.6.0"
    const val gson = "2.10.1"
    const val glide = "4.15.1"
    const val mockk = "1.12.1"
    const val turbine = "0.12.1"
    const val archCoreTest = "2.1.0"
    const val okHttp = "5.0.0-alpha.11"
}

object Modules {
    const val DataLocal = ":data:local"
    const val DataRemote = ":data:remote"
    const val DataRepository = ":data:repository"
    const val Domain = ":domain"
    const val App = ":app"
}