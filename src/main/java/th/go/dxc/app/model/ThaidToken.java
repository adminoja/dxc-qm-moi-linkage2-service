package th.go.dxc.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThaidToken {
	private Integer expiresIn;
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private String scope;
	private String idToken;
}
