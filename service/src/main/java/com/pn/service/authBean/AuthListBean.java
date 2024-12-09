package com.pn.service.authBean;

import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.enums.scope.AuthGitlabScope;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: javadadi
 * @Time: 19:34
 * @ClassName: AuthListBean
 */
@Component
public class AuthListBean {
    @Resource
    private AuthStateCache authStateCache;

    //github
    @Value("${github.client_id}")
    private String githubClientId;
    @Value("${github.client_secret}")
    private String githubClientSecret;
    @Value("${github.callback_url}")
    private String githubCallbackUrl;

    //gitee
    @Value("${gitee.client_id}")
    private String giteeClientId;
    @Value("${gitee.client_secret}")
    private String giteeClientSecret;
    @Value("${gitee.callback_url}")
    private String giteeCallbackUrl;

    //gitlab
    @Value("${gitlab.client_id}")
    private String gitlabClientId;

    @Value("${gitlab.client_secret}")
    private String gitlabClientSecret;

    @Value("${gitlab.callback_url}")
    private String gitlabCallbackUrl;

    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(githubClientId)
                        .clientSecret(githubClientSecret)
                        .redirectUri(githubCallbackUrl)
                        .build(), authStateCache);
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeClientId)
                        .clientSecret(giteeClientSecret)
                        .redirectUri(giteeCallbackUrl)
                        .scopes(AuthScopeUtils.getScopes(AuthGiteeScope.USER_INFO, AuthGiteeScope.EMAILS))
                        .build(), authStateCache);
                break;
            case"gitlab":
                authRequest = new AuthGitlabRequest(AuthConfig.builder()
                        .clientId(gitlabClientId)
                        .clientSecret(gitlabClientSecret)
                        .redirectUri(gitlabCallbackUrl)
                        .scopes(AuthScopeUtils.getScopes(AuthGitlabScope.READ_USER,AuthGitlabScope.EMAIL))
                        .build());
                break;
            default:
                break;
        }
        if (Objects.isNull(authRequest)) {
            throw new RuntimeException("不支持的第三方登录");
        }
        return authRequest;
    }
}
