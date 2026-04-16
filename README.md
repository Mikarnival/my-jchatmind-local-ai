# JChatMind Local AI

## Overview
This project is a simplified standalone version of JChatMind with local AI model support.

It demonstrates:
- Basic LLM chat interaction
- Conversation memory management
- Multi-turn dialogue
- Integration with Spring AI
- Local model usage with Ollama

## Tech Stack
- Java 17
- Spring Boot 3
- Spring AI
- Ollama (local LLM)
- Maven
- JUnit 5

## How to Run

### Run tests

```bash
mvn test
```

Or run `JChatMindV1Test` directly in IntelliJ.

## Project Structure

```text
src/main/java/com/mika/jchatmind/
├── JChatMindApplication.java
├── JChatMindV1.java
├── AgentState.java
├── ChatClientConfig.java

src/test/java/com/mika/jchatmind/
├── JChatMindV1Test.java

src/main/resources/
├── application.yml
```

## Local Model Setup
This project uses a local model through Ollama.

Make sure Ollama is installed and running, and that the model is available locally.

Example:

```bash
ollama run llama3.1:8b
```

If your configuration uses a different model name, update the project configuration accordingly.

## Features
- Basic chat workflow
- Multi-turn conversation
- Conversation history memory
- Reset conversation state
- Spring Boot test-based validation

## Notes
This project is mainly for learning and experimentation.

Main focus areas:
- Understanding Spring AI integration
- Understanding ChatClient usage
- Understanding how a local LLM can be used in a Java project
- Building a minimal standalone version from a larger learning project
