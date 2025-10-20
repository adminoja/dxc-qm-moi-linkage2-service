package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaBirthCertificateResponse {

	private String authorityIssuing;
	private Integer dateOfBirth;
	private Integer dateOfNotifying;
	private String fatherName;
	private String fatherNationalityDesc;
	private Long fatherPersonalID;
	private String firstName;
	private String gender;
	private String hospitalName;
	private String houseRegistrationAlleyDesc;
	private String houseRegistrationAlleyWayDesc;
	private String houseRegistrationDistrictDesc;
	private String houseRegistrationNo;
	private String houseRegistrationProvinceDesc;
	private String houseRegistrationRoadDesc;
	private String houseRegistrationSubdistrictDesc;
	private Integer houseRegistrationVillageNo;
	private String lastName;
	private String middleName;
	private String motherName;
	private String motherNationalityDesc;
	private Long motherPersonalID;
	private String nationality;
	private String officerName;
	private Integer personInformAge;
	private String personInformName;
	private Long personInformPersonalID;
	private String personInformRelation;
	private Long personalID;
	private String placeOfBirth;
	private Integer placeOfBirthAlleyCode;
	private String placeOfBirthAlleyDesc;
	private Integer placeOfBirthAlleyWayCode;
	private String placeOfBirthAlleyWayDesc;
	private Integer placeOfBirthDistrictCode;
	private String placeOfBirthDistrictDesc;
	private String placeOfBirthNo;
	private Integer placeOfBirthProvinceCode;
	private String placeOfBirthProvinceDesc;
	private Integer placeOfBirthRoadCode;
	private String placeOfBirthRoadDesc;
	private Integer placeOfBirthSubdistrictCode;
	private String placeOfBirthSubdistrictDesc;
	private Integer placeOfBirthVillageNo;
	private String titleDesc;
}
