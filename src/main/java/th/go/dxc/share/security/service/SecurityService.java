package th.go.dxc.share.security.service;

import th.go.dxc.share.security.model.DxcUserDetails;
import th.go.dxc.share.security.model.DxcUserDetailsAuthentication;
import th.go.dxc.share.security.model.NinAuthentication;

public interface SecurityService {
	public static final String ANONYMOUS_USER="anonymousUser";
	public static final String ANONYMOUS_CLIENT="anonymousClient";
	public Object getCurrentPrincipal();
	public String getCurrentPrincipalName();
	public String getCurrentUserNin();
	public String getCurrentUserName();	
	public Boolean isLogin();
	public DxcUserDetails getCurrentUser();
	public NinAuthentication loginWithNin(String nin);
	public DxcUserDetailsAuthentication loginWithUserDetails(DxcUserDetails dxcUserDetails);

}
