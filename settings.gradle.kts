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
include(":feature:settings")
include(":feature:settings:di")
include(":feature:settings:presentation")
include(":feature:myjastic:domain")
include(":core:common")
include(":core:contacts")
include(":core:contacts:domain")
include(":core:contacts:di")
include(":core:contacts:data")
include(":core:geofencing")
include(":core:geofencing:domain")
include(":core:geofencing:data")
include(":core:geofencing:di")
include(":feature:saved-destinations")
include(":feature:saved-destinations:presentation")
include(":feature:saved-destinations:domain")
include(":feature:map")
include(":feature:map:presentation")
include(":feature:map:domain")
include(":feature:map:lib")
include(":feature:map:data")
include(":core:coroutines")
include(":core:base")
include(":core:permissions")
include(":core:database")
include(":feature:myjastic:data")
include(":feature:myjastic:lib")
include(":feature:saved-destinations:data")
include(":feature:saved-destinations:lib")
