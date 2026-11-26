package com.mika.jchatmind.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfusionMatrixTest {

    @Test
    void shouldCalculateMetrics() {
        ConfusionMatrix matrix =
                new ConfusionMatrix(3, 2, 2, 1);

        assertEquals(0.625, matrix.accuracy(), 0.0001);
        assertEquals(0.60, matrix.precision(), 0.0001);
        assertEquals(0.75, matrix.recall(), 0.0001);
        assertEquals(0.50, matrix.falsePositiveRate(), 0.0001);
    }

    @Test
    void shouldRejectNegativeCounts() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ConfusionMatrix(-1, 0, 0, 0)
        );
    }

    @Test
    void shouldHandleUndefinedPrecision() {
        ConfusionMatrix matrix =
                new ConfusionMatrix(0, 0, 5, 0);

        assertTrue(Double.isNaN(matrix.precision()));
    }
}
