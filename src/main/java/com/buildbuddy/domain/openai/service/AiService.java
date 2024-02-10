package com.buildbuddy.domain.openai.service;

import com.buildbuddy.domain.openai.dto.AiRequestDto;
import com.buildbuddy.jsonresponse.DataResponse;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.ai.retriever.VectorStoreRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private AiClient aiClient;

    @Value("classpath:/systemPrompt.st")
    private Resource systemPrompt;

    public DataResponse<String> getResponse(AiRequestDto reqDto){

        String reqMessage = reqDto.getMessage();

        List<Document> documents = new VectorStoreRetriever(vectorStore).retrieve(reqMessage);

        UserMessage userMessage = new UserMessage(reqMessage);
        Message systemMessage = getSystemMessage(documents);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        AiResponse response = aiClient.generate(prompt);

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting open ai response")
                .data(response.getGeneration().getText())
                .build();
    }

    private Message getSystemMessage(List<Document> documentList){
        String documents = documentList.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
        return systemPromptTemplate.createMessage(Map.of("documents", documents));
    }

}
