package th.go.dxc.infra.connector.dopalinkage2.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaPersonChangeLastnamePrimaryResponse {
	
	private List<AllName> allName;
	private Integer total;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AllName {
		private Integer age;
		private String amphorDesc;
		private String causeOfChange;
		private Integer dateOfBirth;
		private String districtDesc;
		private Integer docDate;
		private Integer docID;
		private String docPlace;
		private String docPlaceDesc;
		private String docPlaceProvince;
		private String fatherFirstName;
		private String firstName;
		private String fullNameAndRank;
		private Integer genderCode;
		private String genderDesc;
		private String hno;
		private String hrcode;
		private String hrcodeDesc;
		private String lastName;
		private String middleName;
		private String motherFirstName;
		private Integer nationalityCode;
		private String nationalityDesc;
		private String newName;
		private Long pid;
		private String provinceDesc;
		private Integer requestDate;
		private Integer requestID;
		private Integer requestYear;
		private String soiDesc;
		private String thanonDesc;
		private Integer titleCode;
		private String titleDesc;
		private String trokDesc;
	}
}
