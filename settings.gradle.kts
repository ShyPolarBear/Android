pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}
rootProject.name = "ShyPolarBear"
include(":app")
include(":presentation")
include(":data")
include(":domain")

include(":presentation:login")
include(":presentation:signup")
include(":presentation:quiz")
include(":presentation:feed")
include(":presentation:ranking")
include(":presentation:more")
include(":core")
include(":core:base")
include(":core:util")
include(":core:designsystem")
include(":core:ui")
include(":presentation:navigation")
