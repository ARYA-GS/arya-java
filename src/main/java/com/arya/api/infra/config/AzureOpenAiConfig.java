package com.arya.api.infra.config;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureOpenAiConfig {

    @Bean
    public AzureOpenAiChatModel azureOpenAiChatModel() {
        var openAIClientBuilder = new OpenAIClientBuilder()
                .endpoint("https://mr553-mak5nrtw-swedencentral.cognitiveservices.azure.com/")
                .credential(new AzureKeyCredential("EO4QYkYu6S9TslvPXHA4fAmqwS1bdwYn74FS3ZonE02RBuN8qipnJQQJ99BEACfhMk5XJ3w3AAAAACOGjlDk"));

        var options = AzureOpenAiChatOptions.builder()
                .deploymentName("DeepSeek-R1")
                .temperature(0.7)
                .build();

        return AzureOpenAiChatModel.builder()
                .openAIClientBuilder(openAIClientBuilder)
                .defaultOptions(options)
                .build();
    }


    @Bean
    public ChatClient chatClient(AzureOpenAiChatModel azureOpenAiChatModel) {
        return ChatClient.builder(azureOpenAiChatModel).build();
    }
}
