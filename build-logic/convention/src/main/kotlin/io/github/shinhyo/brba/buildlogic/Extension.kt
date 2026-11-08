package io.github.shinhyo.brba.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.findVersion(alias: String) = libs.findVersion(alias).get().toString()

internal fun Project.findLibrary(alias: String) = libs.findLibrary(alias).get()
