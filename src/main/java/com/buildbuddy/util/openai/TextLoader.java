package com.buildbuddy.util.openai;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class TextLoader {

    private final Resource resource;

    public List<Document> load() { return load(new TokenTextSplitter()); }

    public List<Document> load(TextSplitter textSplitter){
        try {
            InputStream inputStream = resource.getInputStream();
            String doc = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            return textSplitter.apply(Collections.singletonList(new Document(doc)));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
