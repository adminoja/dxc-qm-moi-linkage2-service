package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaThaiIdCardResponse {
	@ApiModelProperty(notes = "เลขคำขอมีบัตร (บป.1)", example = "12345678901")
	private String documentNumber;
	@ApiModelProperty(notes = "เลขบัตรประจำตัวประชาชน", example = "1234567890123")
	private Long personalID;

	@ApiModelProperty(notes = "หมู่โลหิต", example = "B")
	private String blood;
	@ApiModelProperty(notes = "วันเดือนปี เกิด (พ.ศ. YYYYMMDD)", example = "25230605")
	private Integer birthDate;
	@ApiModelProperty(notes = "ศาสนา", example = "พุทธ")
	private String religion;
	@ApiModelProperty(notes = "ศาสนา(อื่นๆ)", example = "")
	private String religionOther;
	@ApiModelProperty(notes = "เพศ", example = "ชาย")
	private String sex;
	@ApiModelProperty(notes = "สาเหตุการยกเลิกบัตร", example = "")
	private String cancelCause;
	@ApiModelProperty(notes = "วันเดือนปี ที่ออกบัตร", example = "25630703")
	private Integer issueDate;
	@ApiModelProperty(notes = "เวลา ที่ออกบัตร", example = "9280455")
	private Integer issueTime;
	@ApiModelProperty(notes = "ประเทศ (กรณีอยู่ต่างประเทศ)", example = "")
	private String foreignCountry;
	@ApiModelProperty(notes = "เมือง (กรณีอยู่ต่างประเทศ)", example = "")
	private String foreignCountryCity;
	@ApiModelProperty(notes = "วันเดือนปี บัตรหมดอายุ (พ.ศ. YYYYMMDD)", example = "25630703")
	private String expireDate;
	@ApiModelProperty(notes = "เบอร์โทรศัพท์", example = "0812345698")
	private String phoneNumber;

	private Document document;
	private NameEN nameEN;
	private NameTH nameTH;
	private Address address;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Document {
		@ApiModelProperty(notes = "ประเทศ", example = "ประเทศไทย")
		private String countryDesc;
		@ApiModelProperty(notes = "อำเภอ", example = "อำเภอปากเกร็ด")
		private String districtDesc;
		@ApiModelProperty(notes = "จังหวัด", example = "จังหวัดนนทบุรี")
		private String provinceDesc;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NameEN {
		@ApiModelProperty(notes = "ชื่อตัว (ภาษาอังกฤษ)", example = "test")
		private String firstName;
		@ApiModelProperty(notes = "ชื่อสกุล (ภาษาอังกฤษ)", example = "test")
		private String lastName;
		@ApiModelProperty(notes = "ชื่อกลาง (ภาษาอังกฤษ)", example = "")
		private String middleName;
		@ApiModelProperty(notes = "คำนำหน้านาม (ภาษาอังกฤษ)", example = "Mr.")
		private String title;
	}

	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NameTH {
		@ApiModelProperty(notes = "ชื่อตัว-ชื่อสกุล (ภาษาไทย)", example = "ทดสอบ")
		private String fullName;
		@ApiModelProperty(notes = "ชื่อตัว (ภาษาไทย)", example = "ทดสอบ")
		private String firstName;
		@ApiModelProperty(notes = "ชื่อสกุล (ภาษาไทย)", example = "ทดสอบ")
		private String lastName;
		@ApiModelProperty(notes = "ชื่อกลาง (ภาษาไทย)", example = "")
		private String middleName;
		@ApiModelProperty(notes = "คำนำหน้านาม (ภาษาไทย)", example = "นาย")
		private String title;
	}

	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Address {
		@ApiModelProperty(notes = "ซอย (ณ วันทําบัตร)", example = "")
		private String alleyDesc;
		@ApiModelProperty(notes = "ตรอก (ณ วันทําบัตร)", example = "")
		private String alleyWayDesc;
		@ApiModelProperty(notes = "อําเภอ (ณ วันทําบัตร)", example = "ทดสอบ")
		private String districtDesc;
		@ApiModelProperty(notes = "บ้านเลขที่ (ณ วันทําบัตร)", example = "11/11")
		private String houseNo;
		@ApiModelProperty(notes = "จังหวัด (ณ วันทําบัตร)", example = "จังหวัดทดสอบ")
		private String provinceDesc;
		@ApiModelProperty(notes = "ถนน (ณ วันทําบัตร)", example = "ถนนทดสอบ")
		private String roadDesc;
		@ApiModelProperty(notes = "ตําบล (ณ วันทําบัตร)", example = "ตำบลทดสอบ")
		private String subdistrictDesc;
		@ApiModelProperty(notes = "หมู่ที่ (ณ วันทําบัตร)", example = "1")
		private String villageNo;
	}

}
