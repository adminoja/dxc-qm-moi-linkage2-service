package th.go.dxc.infra.connector.dopalinkage2.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmLoginLinkage2Request {
	private String loginType;
	private String officeID;
	private String personalID;
	private String accessToken;
}
