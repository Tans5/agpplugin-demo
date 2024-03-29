plugins {
    id("java-library")
    id("java-gradle-plugin")
    id("maven-publish")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

gradlePlugin {
    plugins {
        val myPlugin = this.create("TansPlugin")
        myPlugin.id = properties["GROUP_ID"].toString()
        myPlugin.implementationClass = "com.tans.agpplugin.plugin.TansPlugin"
    }
}

dependencies {

    implementation(libs.agp.core)
    implementation(libs.agp.api)

    implementation(libs.asm)
    implementation(libs.asm.commons)
}

val localMavenDir = File(rootProject.rootDir, "maven")
if (!localMavenDir.exists()) {
    localMavenDir.mkdirs()
}

publishing {
    repositories {
        // Local
        maven {
            name = "LocalMaven"
            url = uri(localMavenDir.canonicalPath)
        }

//        // Remote
//        maven {
//            name = "RemoteMaven"
//            credentials {
//                username = ""
//                password = ""
//            }
//            url = uri("")
//        }
    }

    val sourceJar by tasks.creating(Jar::class.java) {
        archiveClassifier.set("sources")
        from(sourceSets.getByName("main").allSource)
    }

    publications {
        val defaultPublication: MavenPublication = this.create("Default", MavenPublication::class.java)
        with(defaultPublication) {
            groupId = properties["GROUP_ID"].toString()
            artifactId = properties["PLUGIN_ARTIFACT_ID"].toString()
            version = properties["VERSION"].toString()

            // For aar
//            afterEvaluate {
//                artifact(tasks.getByName("bundleReleaseAar"))
//            }
            // jar
//            artifact("${layout.buildDirectory.asFile.get().absolutePath}${File.separator}libs${File.separator}plugin.jar")
            afterEvaluate {
                artifact(tasks.getByName("jar"))
            }
            // source Code.
            artifact(sourceJar)

            pom {
                name = "tans-plugin"
                description = "Plugin demo for AGP."
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "Tans5"
                        name = "Tans Tan"
                        email = "tans.tan096@gmail.com"
                    }
                }
            }

            pom.withXml {
                val dependencies = asNode().appendNode("dependencies")
                configurations.implementation.get().allDependencies.all {
                    val dependency = this
                    if (dependency.group == null || dependency.version == null || dependency.name == "unspecified") {
                        return@all
                    }
                    val dependencyNode = dependencies.appendNode("dependency")
                    dependencyNode.appendNode("groupId", dependency.group)
                    dependencyNode.appendNode("artifactId", dependency.name)
                    dependencyNode.appendNode("version", dependency.version)
                    dependencyNode.appendNode("scope", "implementation")
                }
            }
        }
    }
}

//project.afterEvaluate {
//    val buildTask = tasks.getByName("build")
//    tasks.all {
//        if (group == "publishing") {
//            this.dependsOn(buildTask)
//        }
//    }
//}