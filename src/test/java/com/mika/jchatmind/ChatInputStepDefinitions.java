package com.mika.jchatmind;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatInputStepDefinitions {

    private JChatMindV1 agent;
    private Throwable thrownException;
    private int historySizeBeforeAction;

    @Given("the agent is idle")
    public void theAgentIsIdle() {
        agent = new JChatMindV1(
                "cucumber-test-agent",
                "Agent for Cucumber tests",
                "You are a helpful assistant.",
                null,
                20,
                "cucumber-test-session"
        );

        historySizeBeforeAction =
                agent.getConversationHistory().size();
    }

    @When("the user submits a blank message")
    public void theUserSubmitsABlankMessage() {
        try {
            agent.chat(" ");
        } catch (Throwable exception) {
            thrownException = exception;
        }
    }

    @Then("the request should be rejected")
    public void theRequestShouldBeRejected() {
        assertNotNull(thrownException);

        IllegalArgumentException exception =
                assertInstanceOf(
                        IllegalArgumentException.class,
                        thrownException
                );

        assertEquals(
                "User input must not be null or blank",
                exception.getMessage()
        );
    }

    @Then("the agent should remain idle")
    public void theAgentShouldRemainIdle() {
        assertEquals(
                AgentState.IDLE,
                agent.getAgentState()
        );
    }

    @Then("the conversation history should remain unchanged")
    public void theConversationHistoryShouldRemainUnchanged() {
        assertEquals(
                historySizeBeforeAction,
                agent.getConversationHistory().size()
        );
    }
}