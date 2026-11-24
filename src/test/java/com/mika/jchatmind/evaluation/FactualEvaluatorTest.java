
package com.mika.jchatmind.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactualEvaluatorTest {

    private final FactualEvaluator evaluator =
            new FactualEvaluator();

    @Test
    void shouldPassWhenExpectedFactIsPresent() {
        boolean result = evaluator.evaluate(
                "Berlin is the capital of Germany.",
                "Berlin"
        );

        assertTrue(result);
    }

    @Test
    void shouldIgnoreCase() {
        boolean result = evaluator.evaluate(
                "BERLIN is the capital.",
                "berlin"
        );

        assertTrue(result);
    }

    @Test
    void shouldFailWhenFactIsMissing() {
        boolean result = evaluator.evaluate(
                "Munich is the capital of Germany.",
                "Berlin"
        );

        assertFalse(result);
    }

    @Test
    void shouldFailForNullResponse() {
        assertFalse(evaluator.evaluate(null, "Berlin"));
    }

    @Test
    void shouldFailForBlankExpectedFact() {
        assertFalse(evaluator.evaluate("Berlin", " "));
    }

    @Test
    void shouldDetectIncorrectFact() {

        boolean result = evaluator.evaluate(
                "Berlin is not the capital of Germany. Munich is.",
                "Berlin"
        );

        assertFalse(result);
    }
}
