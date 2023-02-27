package de.ma.tw.app.web.utils

import org.junit.jupiter.params.provider.ArgumentsSource
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ArgumentsSource(TestScenarioArgumentsProvider::class)
annotation class TestScenario(
    val scenario: KClass<out IScenario>
)
