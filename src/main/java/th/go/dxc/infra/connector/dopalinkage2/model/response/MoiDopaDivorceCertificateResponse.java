package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaDivorceCertificateResponse {
	
	private Integer divorceDate;
	private Long divorceID;
	private String divorcePlace;
	private String divorcePlaceDesc;
	private String divorcePlaceProvince;
	private Integer divorceTime;
	private String divorceType;
	private Integer femaleAge;
	private Integer femaleDateOfBirth;
	private String femaleFirstName;
	private String femaleFullnameAndRank;
	private String femaleLastName;
	private String femaleMiddleName;
	private Integer femaleNationalityCode;
	private String femaleNationalityDesc;
	private String femaleOtherDocID;
	private Long femalePID;
	private Integer femaleTitleCode;
	private String femaleTitleDesc;
	private Integer maleAge;
	private Integer maleDateOfBirth;
	private String maleFirstName;
	private String maleFullnameAndRank;
	private String maleLastName;
	private String maleMiddleName;
	private Integer maleNationalityCode;
	private String maleNationalityDesc;
	private String maleOtherDocID;
	private Long malePID;
	private Integer maleTitleCode;
	private String maleTitleDesc;
	private Integer marryDate;
	private Integer marryID;
	private String marryPlace;
	private String marryPlaceDesc;
	private String marryPlaceProvince;
	private String marryTime;
	private String marryType;
}
