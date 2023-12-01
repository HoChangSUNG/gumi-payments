package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.domain.apikey.ReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ApiKeyTypeCondition implements ApiKeyReIssueCondition {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ReIssueCommand command) {
        return command.getApiKeyType() == apiKeyType;
    }

}