package th.go.dxc.infra.connector.thaid.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationCodeRequest {
	private String code;
}
