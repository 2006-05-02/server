job("build") {
    container(displayName = "Build", image = "gradle") {
        kotlinScript { api ->
            api.gradlew("build")
        }
    }
}