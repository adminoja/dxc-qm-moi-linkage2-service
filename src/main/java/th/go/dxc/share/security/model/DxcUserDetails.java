package th.go.dxc.share.security.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DxcUserDetails {
	private String principalName;
	private String clientId;
	private String username;	
	private String userNin;
	private String userOrganizationId;
	private String userId;
	private String userGroupId;	
	private String userGivenName;
	private String userFamilyName;
	private String userIpAddress;
}
