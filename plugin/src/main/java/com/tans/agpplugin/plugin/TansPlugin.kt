package com.tans.agpplugin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class TansPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Hello, here is tans plugin.")
    }
}