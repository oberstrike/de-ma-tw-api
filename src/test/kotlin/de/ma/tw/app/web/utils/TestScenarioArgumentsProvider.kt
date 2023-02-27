package de.ma.tw.app.web.utils

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.AnnotationConsumer
import java.util.stream.Stream
import kotlin.reflect.full.createInstance

class TestScenarioArgumentsProvider : ArgumentsProvider, AnnotationConsumer<TestScenario> {


    private lateinit var iScenario: IScenario

    override fun accept(t: TestScenario) {
        val scenario = t.scenario
        iScenario = scenario.createInstance()

    }

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(iScenario)
        )
    }


}