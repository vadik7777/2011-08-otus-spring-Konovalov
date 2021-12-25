package ru.otus.homework15.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.homework15.dto.MatryoshkaDto;

@Configuration
public class MatryoshkaProcess {

    @Bean
    public MessageChannel matreshkaInputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel matreshkaOutputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow matreshkaFlow() {
        return IntegrationFlows.from(matreshkaInputChannel())
                .split()
                .channel(matreshkaCreateFlow().getInputChannel())
                .get();
    }

    @Bean
    public IntegrationFlow matreshkaCreateFlow() {
        return flow -> flow
                .handle("matryoshkaServiceImpl", "create")
                .channel("matreshkaRouteFlow.input");
    }

    @Bean
    public IntegrationFlow matreshkaRouteFlow() {
        return flow -> flow
                .<MatryoshkaDto, Boolean>route(matryoshka -> matryoshka.getMaxSteps() == matryoshka.getStep(),
                mapping -> mapping
                        .channelMapping(true, matreshkaOutputFlow().getInputChannel())
                        .channelMapping(false, matreshkaCreateFlow().getInputChannel())
        );
    }

    @Bean
    public IntegrationFlow matreshkaOutputFlow() {
        return flow -> flow
                .aggregate()
                .channel(matreshkaOutputChannel());
    }
}