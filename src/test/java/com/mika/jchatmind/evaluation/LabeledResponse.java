package com.mika.jchatmind.evaluation;

public record LabeledResponse(
        String id,
        String response,
        boolean factuallyCorrect
) {}