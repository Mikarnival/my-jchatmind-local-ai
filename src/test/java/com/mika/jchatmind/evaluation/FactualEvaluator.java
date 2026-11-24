package com.mika.jchatmind.evaluation;

import java.util.Locale;

public class FactualEvaluator {

    public boolean evaluate(
            String response,
            String expectedFact
    ) {
        if (response == null || expectedFact == null) {
            return false;
        }

        if (response.isBlank() || expectedFact.isBlank()) {
            return false;
        }

        return response.toLowerCase(Locale.ROOT)
                .contains(expectedFact.toLowerCase(Locale.ROOT));
    }
}
