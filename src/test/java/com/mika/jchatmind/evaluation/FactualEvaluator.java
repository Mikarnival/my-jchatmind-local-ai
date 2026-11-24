package com.mika.jchatmind.evaluation;

import java.util.Locale;

public class FactualEvaluator {

    public EvaluationResult evaluate(
            String testId,
            String response,
            String expectedFact
    ) {
        if (response == null || response.isBlank()) {
            return new EvaluationResult(
                    testId,
                    false,
                    0.0,
                    "keyword_match",
                    "Response is null or blank"
            );
        }

        if (expectedFact == null || expectedFact.isBlank()) {
            return new EvaluationResult(
                    testId,
                    false,
                    0.0,
                    "keyword_match",
                    "Response is null or blank"
            );
        }

        boolean matched = response
                .toLowerCase(Locale.ROOT)
                .contains(expectedFact.toLowerCase(Locale.ROOT));

        return new EvaluationResult(
                testId,
                matched,
                matched ? 1.0 : 0.0,
                "keyword_match",
                matched
                        ? "Expected keyword found"
                        : "Expected keyword missing"
        );
    }
}
