package com.mika.jchatmind;

import com.mika.jchatmind.AgentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * V1 version: basic chat functionality
 * Goal: integrate LLM and enable basic conversation
 *
 * Core features:
 * - Use ChatClient to interact with LLM
 * - Use ChatMemory to manage conversation history
 * - Support system prompt
 * - Simple user input -> AI response flow
 */
@Slf4j
public class JChatMindV1 {

    // Name
    protected String name;

    // Description
    protected String description;

    // System prompt
    protected String systemPrompt;

    // ChatClient instance
    protected ChatClient chatClient;

    // Chat memory
    protected ChatMemory chatMemory;

    // Agent state
    protected AgentState agentState;

    // Session ID (used for memory management)
    protected String sessionId;

    private static final Integer DEFAULT_MAX_MESSAGES = 20;

    public JChatMindV1() {}

    public JChatMindV1(String name,
                       String description,
                       String systemPrompt,
                       ChatClient chatClient,
                       Integer maxMessages,
                       String sessionId) {

        this.name = name;
        this.description = description;
        this.systemPrompt = systemPrompt;
        this.chatClient = chatClient;
        this.sessionId = sessionId != null ? sessionId : "default-session";
        this.agentState = AgentState.IDLE;

        // Initialize chat memory
        this.chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(maxMessages != null ? maxMessages : DEFAULT_MAX_MESSAGES)
                .build();

        // Add system prompt
        if (StringUtils.hasLength(systemPrompt)) {
            this.chatMemory.add(this.sessionId, new SystemMessage(systemPrompt));
        }
    }

    /**
     * Process user input and return AI response
     */
    public String chat(String userInput) {

        Assert.hasText(
                userInput,
                "User input must not be null or blank"
        );

        if (agentState != AgentState.IDLE) {
            throw new IllegalStateException("Agent is not in IDLE state, current state: " + agentState);
        }

        try {
            agentState = AgentState.THINKING;

            // Add user message to memory
            UserMessage userMessage = new UserMessage(userInput);
            chatMemory.add(sessionId, userMessage);

            // Build prompt
            Prompt prompt = Prompt.builder()
                    .messages(chatMemory.get(sessionId))
                    .build();

            // Call LLM
            ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

            Assert.hasText(
                    userInput,
                    "User input must not be null or blank"
            );

            AssistantMessage assistantMessage = response.getResult().getOutput();
            String aiResponse = assistantMessage.getText();

            // Add AI response to memory
            chatMemory.add(sessionId, assistantMessage);

            agentState = AgentState.FINISHED;

            return aiResponse;

        } catch (Exception e) {
            agentState = AgentState.ERROR;
            log.error("Error occurred during chat processing", e);
            throw new RuntimeException("Error occurred during chat processing", e);

        } finally {
            // Reset state for next request
            agentState = AgentState.IDLE;
        }
    }

    public AgentState getAgentState() {
        return agentState;
    }

    /**
     * Get current conversation history
     */
    public List<Message> getConversationHistory() {
        return chatMemory.get(sessionId);
    }

    /**
     * Reset conversation history
     */
    public void reset() {
        chatMemory.clear(sessionId);
        if (StringUtils.hasLength(systemPrompt)) {
            chatMemory.add(sessionId, new SystemMessage(systemPrompt));
        }
        agentState = AgentState.IDLE;
    }

    @Override
    public String toString() {
        return "JChatMindV1 {" +
                "name = " + name + ",\n" +
                "description = " + description + ",\n" +
                "systemPrompt = " + systemPrompt + "}";
    }
}