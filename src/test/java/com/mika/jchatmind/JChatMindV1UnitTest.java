package com.mika.jchatmind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JChatMindV1UnitTest {

    private JChatMindV1 agent;

    @BeforeEach
    void setUp() {
        agent = new JChatMindV1(
                "test-agent",
                "Agent for unit tests",
                "You are a helpful assistant.",
                null,
                20,
                "unit-test-session"
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t"})
    void shouldRejectNullOrBlankInput(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> agent.chat(input)
        );

        assertAll(
                () -> assertEquals(
                        "User input must not be null or blank",
                        exception.getMessage()
                ),
                () -> assertEquals(
                        AgentState.IDLE,
                        agent.getAgentState()
                ),
                () -> assertEquals(
                        1,
                        agent.getConversationHistory().size()
                )
        );
    }
}