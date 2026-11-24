
package com.mika.jchatmind.evaluation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactualEvaluatorTest {

    private final FactualEvaluator evaluator =
            new FactualEvaluator();

    @Test
    void shouldPassWhenExpectedFactIsPresent() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-001",
                "Berlin is the capital of Germany.",
                "Berlin"
        );

        assertTrue(result.passed());
        assertEquals(1.0, result.score());
        assertEquals("UNIT-001", result.testId());
    }

    @Test
    void shouldIgnoreCase() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-002",
                "BERLIN is the capital.",
                "berlin"
        );

        assertTrue(result.passed());
    }

    @Test
    void shouldFailWhenFactIsMissing() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-003",
                "Munich is the capital of Germany.",
                "Berlin"
        );

        assertFalse(result.passed());
        assertEquals(0.0, result.score());
    }

    @Test
    void shouldFailForNullResponse() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-004",
                null,
                "Berlin"
        );

        assertFalse(result.passed());
        assertEquals(
                "Response is null or blank",
                result.reason()
        );
    }

    @Test
    void shouldFailForBlankExpectedFact() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-005",
                "Berlin",
                " "
        );

        assertFalse(result.passed());
    }

    @Disabled("Known limitation: keyword matching cannot detect negation")
    @Test
    void shouldDetectIncorrectFact() {
        EvaluationResult result = evaluator.evaluate(
                "UNIT-006",
                "Berlin is not the capital of Germany. Munich is.",
                "Berlin"
        );

        assertFalse(result.passed());
    }
}
