package th.go.dxc.infra.connector.dopalinkage2.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DopaLinkage2ErrorResponse {
	private String errorMessage;
	private Integer errorNumber;
}
