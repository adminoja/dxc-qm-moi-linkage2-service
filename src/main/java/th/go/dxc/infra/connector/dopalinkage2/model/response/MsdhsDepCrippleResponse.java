package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MsdhsDepCrippleResponse {

	private Object message;
	private Integer status;
	private Result result;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {
		@JsonProperty("BIRTH_DATE")
		private String birthDate;
		@JsonProperty("CARD_EXPIRE_DATE")
		private Object cardExpireDate;
		@JsonProperty("CARD_ISSUE_DATE")
		private Object cardIssueDate;
		@JsonProperty("DEFORM_NAME")
		private String deformName;
		@JsonProperty("PERSON_CODE")
		private String personCode;
		@JsonProperty("PERSON_NAME")
		private String personName;

	}

}
