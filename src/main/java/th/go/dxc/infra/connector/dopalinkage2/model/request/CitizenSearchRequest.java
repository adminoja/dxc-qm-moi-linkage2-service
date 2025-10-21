package th.go.dxc.infra.connector.dopalinkage2.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenSearchRequest {

	private String jobID;
	private List<DataReq> data;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DataReq {
		private Integer serviceID;
		private QueryReq query;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class QueryReq {
		private String limit;
		private String firstName;
		private String lastName;
		private String middleName;
		private String recordNumber;
	}
}
