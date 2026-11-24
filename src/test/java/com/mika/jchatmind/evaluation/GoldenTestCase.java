package com.mika.jchatmind.evaluation;

import java.util.List;

public record GoldenTestCase(
        String id,
        String category,
        String input,
        List<String> expectedFacts
) {}
