package flab.gumipayments.domain;

import flab.gumipayments.application.ApiKeyRenewCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apikey_id")
    private Long id;

    private String secretKey;
    private String clientKey;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private ApiKeyType type;

    private LocalDateTime expireDate;

    private long count;

    @Builder
    public ApiKey(String secretKey,String clientKey, Long accountId, ApiKeyType type, LocalDateTime expireDate) {
        this.secretKey = secretKey;
        this.clientKey = clientKey;
        this.accountId = accountId;
        this.type = type;
        this.expireDate = expireDate;
        this.count = 0;
    }

    public void extendExpireDate(ApiKeyRenewCommand renewCommand) {
        if(renewCommand.getExtendDate().isBefore(this.expireDate) || renewCommand.getExtendDate().isEqual(this.expireDate)){
            throw new ApiKeyExtendException("올바른 기간 연장이 아닙니다.");
        }
        this.expireDate = renewCommand.getExtendDate();
    }
}
