package th.go.dxc.share.security.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import th.go.dxc.share.security.model.DxcUserDetails;
import th.go.dxc.share.security.model.DxcUserDetailsAuthentication;
import th.go.dxc.share.security.model.NinAuthentication;
import th.go.dxc.share.security.util.HttpUtils;

@Slf4j
public class SecurityServiceJwtImpl implements SecurityService{
	public static final String JWT_CLIENT_ID_KEY = "azp";
	public static final String JWT_GROUP_ID_KEY = "groupId";
	public static final String JWT_ORGANIZATION_ID_KEY = "departmentCode";
	public static final String JWT_USER_ID_KEY = "userId";
	public static final String JWT_USER_NIN_KEY = "nin";
	public static final String JWT_FULL_NAME_KEY = "name";
	public static final String JWT_GIVEN_NAME_KEY = "given_name";
	public static final String JWT_FAMILY_NAME_KEY = "family_name";
	public static final String JWT_USERNAME_KEY = "preferred_username";
	public static final String JWT_USER_STATUS_KEY = "userStatus";
	public static final String JWT_SESSION_STATE_KEY = "session_state";
	
	public Object getCurrentPrincipal() {
		Object principal = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null)
		 {
			principal = authentication.getPrincipal();
		 }
		return principal;
	}
	
	public String getCurrentPrincipalName() {
		log.trace("getCurrentPrincipal");
		String currentPrincipalName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.trace("authentication: "+authentication);
		if(authentication!=null)currentPrincipalName = authentication.getName();
		log.debug("currentPrincipalName: "+currentPrincipalName);
		return currentPrincipalName;
	}
	
	public String getCurrentUserNin() {
		log.trace("getCurrentUserNin");
		String nin = "0000000000000";
		Object principal = getCurrentPrincipal();
		log.debug("principal: "+principal);
		if(principal!=null)
		{
			if(principal instanceof Jwt)
			{
				Jwt jwt = (Jwt)principal;
				log.trace("jwt: "+jwt);
				nin = jwt.getClaimAsString("nin");
			}else if(principal instanceof String) {
				nin = (String)principal;
			}
		}
		log.debug("nin: "+nin);
		return nin;
	}
	
//	public String getCurrentUserName() {
//		log.trace("getCurrentUserNin");
//		DxcUserDetails userDetails=this.getCurrentUser();
//		return userDetails.getUsername();
//	}
	public Mono<String> getCurrentUserName() {
		return this.getCurrentUser()
				.map(DxcUserDetails::getUsername);
	}

//	@Override
//	public Boolean isLogin() {		
//		String currentUserName = this.getCurrentUserName();		
//		return !ANONYMOUS_USER.contentEquals(currentUserName);
//	}
	public Mono<Boolean> isLogin() {
		return this.getCurrentUser()
				.map(user -> !ANONYMOUS_USER.equals(user.getUsername()))
				.defaultIfEmpty(false);
	}

	
//	@Override
//	public DxcUserDetails getCurrentUser() {
//		log.trace("getCurrentUser");
//		DxcUserDetails userDetails = new DxcUserDetails();
//		userDetails.setUsername(ANONYMOUS_USER);
//		userDetails.setClientId(ANONYMOUS_CLIENT);
//		userDetails.setPrincipalName(getCurrentPrincipalName());
//		userDetails.setUserGroupId(null);
//		userDetails.setUserId("0");
//		userDetails.setUserIpAddress(HttpUtils.getCurrentRemoteAddress());
//		Object principal = getCurrentPrincipal();
//		log.trace("principal: "+principal);
//		if(principal!=null)
//		{
//			if(principal instanceof Jwt)
//			{
//				Jwt jwt = (Jwt)principal;
//				log.trace("jwt: {}",jwt);
//				log.trace("all claims: ",jwt.getClaims());
//				userDetails.setClientId(jwt.getClaimAsString(JWT_CLIENT_ID_KEY));
//				userDetails.setUserGroupId(jwt.getClaimAsString(JWT_GROUP_ID_KEY));
//				userDetails.setUserOrganizationId(jwt.getClaimAsString(JWT_ORGANIZATION_ID_KEY));
//				userDetails.setUserId(jwt.getClaimAsString(JWT_USER_ID_KEY));
//				userDetails.setUsername(jwt.getClaimAsString(JWT_USERNAME_KEY));
//				userDetails.setUserNin(jwt.getClaimAsString(JWT_USER_NIN_KEY));
//				userDetails.setUserGivenName(jwt.getClaimAsString(JWT_GIVEN_NAME_KEY));
//				userDetails.setUserFamilyName(jwt.getClaimAsString(JWT_FAMILY_NAME_KEY));
//			}else if(principal instanceof String) {
//				log.trace("Principal String: "+principal);
//				String username = (String)principal;
//				userDetails.setUsername(username);
//			}
//		}
//		log.debug("current userDetails={}",userDetails);
//		return userDetails;
//	}
	
	@Override
	public Mono<DxcUserDetails> getCurrentUser() {
		log.trace("getCurrentUser");
		return Mono.defer(() -> {
			DxcUserDetails userDetails = new DxcUserDetails();
			userDetails.setUsername(ANONYMOUS_USER);
			userDetails.setClientId(ANONYMOUS_CLIENT);
			userDetails.setPrincipalName(getCurrentPrincipalName());
			userDetails.setUserGroupId(null);
			userDetails.setUserId("0");
			userDetails.setUserIpAddress(HttpUtils.getCurrentRemoteAddress());

			Object principal = getCurrentPrincipal();
			log.trace("principal: {}", principal);

			if (principal != null) {
				if (principal instanceof Jwt) {
					Jwt jwt = (Jwt) principal;
					log.trace("jwt: {}", jwt);
					log.trace("all claims: {}", jwt.getClaims());

					userDetails.setClientId(jwt.getClaimAsString(JWT_CLIENT_ID_KEY));
					userDetails.setUserGroupId(jwt.getClaimAsString(JWT_GROUP_ID_KEY));
					userDetails.setUserOrganizationId(jwt.getClaimAsString(JWT_ORGANIZATION_ID_KEY));
					userDetails.setUserId(jwt.getClaimAsString(JWT_USER_ID_KEY));
					userDetails.setUsername(jwt.getClaimAsString(JWT_USERNAME_KEY));
					userDetails.setUserNin(jwt.getClaimAsString(JWT_USER_NIN_KEY));
					userDetails.setUserGivenName(jwt.getClaimAsString(JWT_GIVEN_NAME_KEY));
					userDetails.setUserFamilyName(jwt.getClaimAsString(JWT_FAMILY_NAME_KEY));
					userDetails.setSessionState(jwt.getClaimAsString(JWT_SESSION_STATE_KEY));
				} else if (principal instanceof String) {
					log.trace("Principal String: {}", principal);
					userDetails.setUsername((String) principal);
				}
			}
			log.debug("current userDetails={}", userDetails);
			return Mono.just(userDetails);
		});
	}


	@Override
	public DxcUserDetailsAuthentication loginWithUserDetails(DxcUserDetails dxcUserDetails) {
        log.debug("Logging in with [{}]", dxcUserDetails);
		DxcUserDetailsAuthentication authentication = new DxcUserDetailsAuthentication(dxcUserDetails);
        log.trace("authentication: [{}]", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
	}

	public NinAuthentication loginWithNin(String nin) {
        log.debug("Logging in with [{}]", nin);
		NinAuthentication authentication = new NinAuthentication(nin);
        log.trace("authentication: [{}]", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
	}


}
