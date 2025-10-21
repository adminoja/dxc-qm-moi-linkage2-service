package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaPersonFirstnameLastnameResponse {
	
	private Integer titleCode;
	private String titleDesc;
	private String titleName;
	private Integer titleSex;
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer genderCode;
	private Integer dateOfBirth;
	private Integer nationalityCode;
	private String nationalityDesc;
	private String ownerStatusDesc;
	private Integer statusOfPersonCode;
	private String statusOfPersonDesc;
	private Integer dateOfMoveIn;
	private Integer age;
	private Long fatherPersonalID;
	private String fatherName;
	private Integer fatherNationalityCode;
	private String fatherNationalityDesc;
	private Long motherPersonalID;
	private String motherName;
	private Integer motherNationalityCode;
	private String motherNationalityDesc;
	private String fullnameAndRank;
	private String genderDesc;
	private String englishTitleDesc;
	private String englishFirstName;
	private String englishMiddleName;
	private String englishLastName;
	private Long pid;
	private Integer totalRecord;
	private Integer recordNumber;
}
