package th.go.dxc.share.security.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

public class DxcUserDetailsAuthentication implements Authentication{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6933304313845933396L;
	private final DxcUserDetails dxcUserDetails;
	private Boolean isAuthenticated = true;
	
	
	public DxcUserDetailsAuthentication(DxcUserDetails dxcUserDetails) {
		super();
		Assert.notNull(dxcUserDetails,"dxcUserDetails can't be null.");
		this.dxcUserDetails = dxcUserDetails;
	}

	@Override
	public String getName() {
		return this.dxcUserDetails.getUserGivenName()+" "+this.dxcUserDetails.getUserFamilyName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {			
		return Collections.singletonList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public Object getCredentials() {
		return this.dxcUserDetails.getUserNin();
	}

	@Override
	public Object getDetails() {
		return this.dxcUserDetails;
	}

	@Override
	public Object getPrincipal() {
		return this.dxcUserDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}
	
}