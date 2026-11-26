package com.mika.jchatmind.evaluation;

public record ConfusionMatrix(
        int tp,
        int fp,
        int tn,
        int fn
) {
    public ConfusionMatrix {
        if (tp < 0 || fp < 0 || tn < 0 || fn < 0) {
            throw new IllegalArgumentException(
                    "Confusion matrix counts cannot be negative"
            );
        }
    }

    public double accuracy() {
        return divide(tp + tn, tp + fp + tn + fn);
    }

    public double precision() {
        return divide(tp, tp + fp);
    }

    public double recall() {
        return divide(tp, tp + fn);
    }

    public double falsePositiveRate() {
        return divide(fp, fp + tn);
    }

    private static double divide(int numerator, int denominator) {
        if (denominator == 0) {
            return Double.NaN;
        }

        return (double) numerator / denominator;
    }
}
