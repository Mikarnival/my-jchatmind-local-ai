package com.mika.jchatmind.evaluation;

import com.mika.jchatmind.JChatMindV1;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LlmEvaluationTest {

    @Autowired
    @Qualifier("llama3.1:8b")
    private ChatClient chatClient;

    @Test
    void shouldAnswerFactualQuestionCorrectly() {

        JChatMindV1 agent = new JChatMindV1(
                "evaluation-agent",
                "LLM Evaluation",
                "Answer questions accurately and concisely.",
                chatClient,
                20,
                "eval-001"
        );

        String response = agent.chat(
                "What is the capital of Germany?"
        );

        assertNotNull(response);

        assertTrue(
                response.toLowerCase().contains("berlin"),
                "Expected response to mention Berlin"
        );

        System.out.println("LLM Response: " + response);
    }
}
