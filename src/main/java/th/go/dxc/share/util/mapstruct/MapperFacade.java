package th.go.dxc.share.util.mapstruct;

import org.mapstruct.Mapper;

import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.infra.connector.dopalinkage2.model.response.JobLinkage2Response;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2Response;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2TokenResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenIntrospectResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenRevokeResponse;

@Mapper(componentModel = "spring")
public interface MapperFacade {
    public JobLinkage2 toJobLinkage2(JobLinkage2Response model);
    public LoginLinkage2 toLoginLinkage2(LoginLinkage2Response model);
    public LoginLinkage2Token toLoginLinkage2Token(LoginLinkage2TokenResponse model);
    public ThaidToken toThaidToken(TokenResponse model);
    public IntrospectToken toIntrospectToken(TokenIntrospectResponse model);
    public RevokeToken toRevokeToken(TokenRevokeResponse model);
}
