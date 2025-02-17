package flab.gumipayments.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiKeyIssueCreateCommand {

    private String type;
    private Long accountId;
    private LocalDateTime expireDate;


}
