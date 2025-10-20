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
public class MolDsdWorkforceDevelopmentResponse {
	@JsonProperty("CERTIFICATE_DATE")
	private String certificateDate;
	@JsonProperty("CERTIFICATE_NO")
	private String certificateNo;
	@JsonProperty("CER_EXPIRE")
	private String cerExpire;
	@JsonProperty("COURSE")
	private String course;
	@JsonProperty("E_MAIL")
	private String email;
	@JsonProperty("FORM_ID")
	private String formId;
	@JsonProperty("NAMES")
	private String names;
	@JsonProperty("PATH_CER")
	private String pathCer;
	@JsonProperty("PERSONAL_ID")
	private String personalId;
	@JsonProperty("SITE")
	private String site;
	@JsonProperty("TEL_NO")
	private String telNo;
	@JsonProperty("TYPEOFTRAIN")
	private String typeOfTrain;
	@JsonProperty("T_ID")
	private String tId;
	@JsonProperty("YEAR")
	private String year;
}
