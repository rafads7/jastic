pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Jastic"
include(":app")
include(":feature")
include(":core")
include(":core:navigation")
include(":feature:myjastic")
include(":feature:myjastic:presentation")
include(":core:ui")
include(":feature:myjastic:di")
include(":feature:settings")
include(":feature:settings:di")
include(":feature:settings:presentation")
include(":feature:myjastic:domain")
include(":core:location")
include(":core:utils")
