package flab.gumipayments.domain.condition.issue;


import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.support.specification.Condition;

public class ExistApiKeyCondition implements Condition<IssueFactor> {
    @Override
    public boolean isSatisfiedBy(IssueFactor command) {
        return command.isApiKeyExist();
    }
}
