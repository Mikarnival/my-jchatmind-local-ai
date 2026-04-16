package com.mika.jchatmind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgentStateTest {

    @Test
    void testEnumValues() {
        assertEquals("IDLE", AgentState.IDLE.name());
        assertEquals("THINKING", AgentState.THINKING.name());
        assertEquals("FINISHED", AgentState.FINISHED.name());
        assertEquals("ERROR", AgentState.ERROR.name());
    }
}