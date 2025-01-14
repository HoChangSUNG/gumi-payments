package flab.gumipayments.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ApiKeyCreateCommand {

    private ApiKeyType keyType;
    private LocalDateTime expireDate;
    private Long accountId;
}
