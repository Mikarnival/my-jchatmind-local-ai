package com.mika.jchatmind;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JChatMindV1Test {

    @Autowired
    @Qualifier("llama3.1:8b")
    private ChatClient chatClient;

    @Test
    public void testBasicChat() {
        JChatMindV1 agent = new JChatMindV1(
                "test-agent-v1",
                "Test Agent V1",
                "You are a helpful assistant.",
                chatClient,
                20,
                "test-session-v1"
        );

        String userInput = "Hello, please introduce yourself.";
        String response = agent.chat(userInput);

        assertNotNull(response);
        assertTrue(response.length() > 0);

        printSeparator("Basic Chat");
        printMessage("User", userInput);
        printMessage("AI", response);
        System.out.printf("History size: %d%n", agent.getConversationHistory().size());
    }

    @Test
    public void testMultiTurnConversation() {
        JChatMindV1 agent = new JChatMindV1(
                "test-agent-v1",
                "Test Agent V1",
                "",
                chatClient,
                20,
                "test-session-v1-multi"
        );

        String userInput1 = "My name is Barbie. I am a Ragdoll cat.";
        String response1 = agent.chat(userInput1);

        assertNotNull(response1);

        printSeparator("Round 1");
        printMessage("User", userInput1);
        printMessage("AI", response1);

        String userInput2 = "What is my name and what kind of cat am I?";
        String response2 = agent.chat(userInput2);

        assertNotNull(response2);

        printSeparator("Round 2");
        printMessage("User", userInput2);
        printMessage("AI", response2);

        assertTrue(agent.getConversationHistory().size() >= 4);
        System.out.printf("History size: %d%n", agent.getConversationHistory().size());
    }

    @Test
    public void testResetConversation() {
        JChatMindV1 agent = new JChatMindV1(
                "test-agent-v1",
                "Test Agent V1",
                "You are an assistant.",
                chatClient,
                20,
                "test-session-v1-reset"
        );

        String userInput = "Hello";
        agent.chat(userInput);

        int historySizeBeforeReset = agent.getConversationHistory().size();

        printSeparator("Before Reset");
        printMessage("User", userInput);
        System.out.printf("History size: %d%n", historySizeBeforeReset);

        agent.reset();

        int historySizeAfterReset = agent.getConversationHistory().size();

        assertTrue(historySizeAfterReset < historySizeBeforeReset);
        assertTrue(historySizeAfterReset <= 1);

        printSeparator("After Reset");
        System.out.printf("History size: %d%n", historySizeAfterReset);
    }

    private void printSeparator(String title) {
        System.out.println();
        System.out.println("==== " + title + " ====");
    }

    private void printMessage(String role, String content) {
        System.out.printf("[%s] %s%n", role, content);
    }
}