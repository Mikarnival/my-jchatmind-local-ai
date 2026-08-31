package com.mika.jchatmind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AgentStateTest {

    @Test
    void shouldContainAllAgentStatesInExpectedOrder() {
        AgentState[] expectedStates = {
                AgentState.IDLE,
                AgentState.PLANNING,
                AgentState.THINKING,
                AgentState.EXECUTING,
                AgentState.FINISHED,
                AgentState.ERROR
        };

        AgentState[] actualStates = AgentState.values();

        assertArrayEquals(expectedStates, actualStates);
    }
}