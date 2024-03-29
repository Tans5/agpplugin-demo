package com.tans.agpplugin.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TansPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val appPlugin = try {
            project.plugins.getPlugin("com.android.application")
        } catch (e: Throwable) {
            null
        }
        if (appPlugin != null) {
            Log.d(msg = "Find android app plugin")
            val androidExt = project.extensions.getByType(AndroidComponentsExtension::class.java)

            androidExt.onVariants { variant ->
                Log.d(msg = "variant=${variant.name}")
                variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
                variant.instrumentation.transformClassesWith(AndroidActivityClassVisitorFactory::class.java, InstrumentationScope.ALL) {}
            }
        } else {
            Log.e(msg = "Do not find android app plugin.")
        }
    }
}