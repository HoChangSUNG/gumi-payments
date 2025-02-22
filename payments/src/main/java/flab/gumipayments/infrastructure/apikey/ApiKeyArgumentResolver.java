package flab.gumipayments.infrastructure.apikey;


import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class ApiKeyArgumentResolver implements HandlerMethodArgumentResolver {

    private final ApiKeyRepository apiKeyRepository;
    private final KeyEncrypt keyEncrypt;
    private final ApiKeyDecoder apiKeyDecoder;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedApiKey.class) &&
                ApiKeyInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String decodedApiKey = apiKeyDecoder.decodeApiKey((HttpServletRequest) webRequest.getNativeRequest());
        String encryptedKey = keyEncrypt.encrypt(decodedApiKey);
        ApiKey apikey = apiKeyRepository.findBySecretKeyOrClientKey(encryptedKey, encryptedKey)
                .orElseThrow(() -> new ApiKeyNotFoundException("존재하지 않는 ApiKey 입니다."));

        return new ApiKeyInfo(apikey.getId(), apikey.getType());
    }
}
