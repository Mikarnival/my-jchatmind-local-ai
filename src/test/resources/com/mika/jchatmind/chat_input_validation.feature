Feature: Chat input validation

  Scenario: Reject a blank user message
    Given the agent is idle
    When the user submits a blank message
    Then the request should be rejected
    And the agent should remain idle
    And the conversation history should remain unchanged