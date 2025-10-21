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
public class MoiDopaPor4LicenseResponse {

	private List<AllName> allName;
	private Long processTimestamp;
	private String remark;
	private Integer total;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AllName {
		private String amphorDesc;
		private String amphorDesc2;
		private Integer applicantType;
		private String businessName;
		private String businessType;
		private String districtDesc;
		private String districtDesc2;
		private Integer docDate;
		private String docID;
		private String docPlace;
		private String docPlaceDesc;
		private String docPlaceProvince;
		private Integer expireDate;
		private String firstName;
		private String firstName2;
		private String fullNameAndRank;
		private String fullNameAndRank2;
		private Integer genderCode;
		private Integer genderCode2;
		private String genderDesc;
		private String genderDesc2;
		private String gunCharacteristic;
		private String gunProduct;
		private String gunRegistrationId;
		private String gunSerialNo;
		private String gunSize;
		private String gunType;
		private Long hid;
		private Long hid2;
		private Long hidRcodeCode;
		private Long hidRcodeCode2;
		private String hidRcodeDesc;
		private String hidRcodeDesc2;
		private String hno;
		private String hno2;
		private String lastName;
		private String lastName2;
		private String middleName;
		private String middleName2;
		private Long personalId;
		private Long personalId2;
		private String provinceDesc;
		private String provinceDesc2;
		private String signFullName;
		private String signTitleDesc;
		private String soi;
		private String soi2;
		private String thanon;
		private String thanon2;
		private Integer titleCode;
		private Integer titleCode2;
		private String titleDesc;
		private String titleDesc2;
		private String trok;
		private String trok2;
	}
}
