package th.go.dxc.infra.connector.dopalinkage2.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
	private List<ResponseItem<T>> data;
	private Integer executeTimeMs;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResponseItem<T> {
		private T responseData;
		private String responseError;
		private Integer responseStatus;
		private Integer responseTimeMs;
		private Integer serviceID;
	}
}
