package com.mika.jchatmind.evaluation;

public record EvaluationResult(
        String testId,
        boolean passed,
        double score,
        String evaluator,
        String reason
) {}
