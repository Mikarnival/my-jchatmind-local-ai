package com.mika.jchatmind.evaluation;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorMetricsTest {

    private final FactualEvaluator evaluator =
            new FactualEvaluator();

    @Test
    void shouldCalculateConfusionMatrix() {

        List<LabeledResponse> dataset = List.of(
                new LabeledResponse(
                        "001",
                        "Berlin is the capital of Germany.",
                        true
                ),
                new LabeledResponse(
                        "002",
                        "Germany's capital is BERLIN.",
                        true
                ),
                new LabeledResponse(
                        "003",
                        "The federal capital is Berlin.",
                        true
                ),
                new LabeledResponse(
                        "004",
                        "Germany's capital is the city that hosts the Bundestag.",
                        true
                ),
                new LabeledResponse(
                        "005",
                        "Munich is the capital of Germany.",
                        false
                ),
                new LabeledResponse(
                        "006",
                        "Berlin is not the capital. Munich is.",
                        false
                ),
                new LabeledResponse(
                        "007",
                        "Germany moved its capital from Berlin to Hamburg.",
                        false
                ),
                new LabeledResponse(
                        "008",
                        "Frankfurt is Germany's capital.",
                        false
                )
        );

        int tp = 0;
        int fp = 0;
        int tn = 0;
        int fn = 0;

        for (LabeledResponse sample : dataset) {

            EvaluationResult result = evaluator.evaluate(
                    sample.id(),
                    sample.response(),
                    "Berlin"
            );

            boolean predicted = result.passed();
            boolean actual = sample.factuallyCorrect();

            if (predicted && actual) {
                tp++;
            } else if (predicted) {
                fp++;
            } else if (actual) {
                fn++;
            } else {
                tn++;
            }
        }

        System.out.printf(
                "TP=%d, FP=%d, TN=%d, FN=%d%n",
                tp, fp, tn, fn
        );

        assertEquals(3, tp);
        assertEquals(2, fp);
        assertEquals(2, tn);
        assertEquals(1, fn);

        ConfusionMatrix matrix =
                new ConfusionMatrix(tp, fp, tn, fn);

        assertEquals(0.625, matrix.accuracy(), 0.0001);
        assertEquals(0.60, matrix.precision(), 0.0001);
        assertEquals(0.75, matrix.recall(), 0.0001);
        assertEquals(0.50, matrix.falsePositiveRate(), 0.0001);
    }
}
