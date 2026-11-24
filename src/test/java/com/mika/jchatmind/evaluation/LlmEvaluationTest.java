
package com.mika.jchatmind.evaluation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mika.jchatmind.JChatMindV1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LlmEvaluationTest {

    @Autowired
    @Qualifier("llama3.1:8b")
    private ChatClient chatClient;

    static Stream<GoldenTestCase> loadGoldenDataset()
            throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream =
                LlmEvaluationTest.class.getResourceAsStream(
                        "/evaluation/golden-dataset.json"
                );

        if (inputStream == null) {
            throw new IllegalStateException(
                    "Golden dataset file not found"
            );
        }

        try (inputStream) {
            List<GoldenTestCase> cases = mapper.readValue(
                    inputStream,
                    new TypeReference<List<GoldenTestCase>>() {}
            );

            return cases.stream();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loadGoldenDataset")
    void evaluateGoldenCase(GoldenTestCase testCase) {

        assertEquals(
                "factual_accuracy",
                testCase.category(),
                "Unsupported evaluation category"
        );

        assertNotNull(testCase.expectedFacts());
        assertEquals(
                1,
                testCase.expectedFacts().size(),
                "This evaluator currently supports one fact"
        );

        JChatMindV1 agent = new JChatMindV1(
                "evaluation-agent",
                "LLM Evaluation Agent",
                "Answer questions accurately and concisely.",
                chatClient,
                20,
                testCase.id()
        );

        String response = agent.chat(testCase.input());

        FactualEvaluator evaluator =
                new FactualEvaluator();

        EvaluationResult result = evaluator.evaluate(
                testCase.id(),
                response,
                testCase.expectedFacts().get(0)
        );

        System.out.println("----------------------");
        System.out.println("Test ID: " + result.testId());
        System.out.println("Input: " + testCase.input());
        System.out.println("Response: " + response);
        System.out.println("Score: " + result.score());
        System.out.println("Reason: " + result.reason());

        assertTrue(
                result.passed(),
                "Evaluation failed for " + testCase.id()
                        + ": " + result.reason()
        );
    }
}
