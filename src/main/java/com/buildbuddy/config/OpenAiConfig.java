package com.buildbuddy.config;

import com.buildbuddy.util.openai.TextLoader;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.InMemoryVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAiConfig {

    @Value("classpath:/knowledge.txt")
    private Resource resource;

    @Bean
    public VectorStore vectorStore(EmbeddingClient embeddingClient){
        return new InMemoryVectorStore(embeddingClient);
    }

    @Bean
    public ApplicationRunner loadKnowledge(VectorStore vectorStore){
        return args -> {
            vectorStore.add(new TextLoader(resource).load());
        };
    }

}
