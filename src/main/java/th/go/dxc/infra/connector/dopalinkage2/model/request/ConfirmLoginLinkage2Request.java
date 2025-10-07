package th.go.dxc.infra.connector.dopalinkage2.model.request;

import lombok.Data;

@Data
public class ConfirmLoginLinkage2Request {
	private String loginType;
	private String officeID;
	private String personalID;
	private String accessToken;
}
