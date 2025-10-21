package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaAlienResponse {
	
	private Long houseID;
	private String titleDesc;
	private String firstName;
	private String lastName;
	private String genderDesc;
	private Integer dateOfBirth;
	private Integer dateInThai;
	private String nationalityDesc;
	private String bloodType;
	private String religion;
	private String marryStatus;
	private String spouseName;
	private String statusOfPersonDesc;
	private Integer personAddDate;
	private Integer personUpdDate;
	private String statusAdded;
	private String terminateDate;

	private Father father;
	private Mother mother;
	private Passport passport;
	private Visa visa;

	private String doeNumber;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Father {
		private Long personalID;
		private String name;
		private String nationalityDesc;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Mother {
		private Long personalID;
		private String name;
		private String nationalityDesc;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Passport {
		private String documentType;
		private String documentNo;
		private String documentIssuePlace;
		private Integer issueDate;
		private Integer expireDate;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Visa {
		private String documentNo;
		private Integer issueDate;
		private Integer expireDate;
		private String documentIssuePlace;
		private String visaType;
		private String visaRequestType;
	}
}
