package th.go.dxc.share.security.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class NinAuthentication implements Authentication{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6933304313845933396L;
	private final String nin;
	private Boolean isAuthenticated = true;
	
	
	public NinAuthentication(String nin) {
		super();
		this.nin = nin;
	}

	@Override
	public String getName() {
		return this.nin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {			
		return Collections.singletonList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public Object getCredentials() {
		return this.nin;
	}

	@Override
	public Object getDetails() {
		return this.nin;
	}

	@Override
	public Object getPrincipal() {
		return this.nin;
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
