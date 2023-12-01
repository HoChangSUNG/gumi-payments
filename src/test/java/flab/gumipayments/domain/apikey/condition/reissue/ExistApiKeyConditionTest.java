package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ReIssueCommand.*;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.EXIST_API_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ExistApiKeyConditionTest {
    private ReIssueCommandBuilder apiKeyReIssueCommandBuilder;
    private ApiKeyReIssueCondition sut;

    @BeforeEach
    void setup() {
        apiKeyReIssueCommandBuilder = ReIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: API 키가 존재하면 발급 조건을 만족한다.")
    void keyExist01() {
        ReIssueCommand issueCommand = apiKeyReIssueCommandBuilder.apiKeyExist(true).build();
        sut = EXIST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: API 키가 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void keyExist02() {
        ReIssueCommand issueCommand = apiKeyReIssueCommandBuilder.apiKeyExist(false).build();
        sut = EXIST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

}