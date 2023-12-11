package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.support.specification.Condition;

public class ExistApiKeyCondition implements Condition<IssueFactor> {
    @Override
    public boolean isSatisfiedBy(IssueFactor command) {
        return command.isApiKeyExist();
    }
}
