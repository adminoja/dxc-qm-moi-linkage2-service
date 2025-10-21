package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmloAssetFreezePersonResponse {

	private String address;
	private String address2;
	private String alias1;
	private String alias2;
	private String alias3;
	private String announceDate;
	private String announceDateEnd;
	private String birthCity;
	private String birthCountry;
	private String birthday;
	private String coRegisNo;
	private String commandNo;
	private String country;
	private String country2;
	private String createDate;
	private String district;
	private String district2;
	private String entityType;
	private String firstNameEn;
	private String firstNameTh;
	private String gender;
	private String groupName;
	private String job;
	private String lastNameEn;
	private String lastNameTh;
	private String middleName;
	private String nameFour;
	private String nationality;
	private String originalName;
	private String otherName1;
	private String otherName2;
	private String otherName3;
	private String passportNo;
	private String passportNo2;
	private String passportNo3;
	private String passportNo4;
	private String passportNo5;
	private String personalID;
	private String position;
	private String proofDesc;
	private String proofNo;
	private String province;
	private String province2;
	private String subdistrict;
	private String subdistrict2;
	private String taxNo;
	private String titleEn;
	private String titleTh;
	private String uid;
	private String updateDate;
	private String yOrn;
}
