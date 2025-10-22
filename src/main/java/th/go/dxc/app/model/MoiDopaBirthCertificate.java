package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลใบสูติบัตร")
public class MoiDopaBirthCertificate extends BaseMoiLinkage2 {
	
	@Schema(description = "รหัสสำนักทะเบียนที่แจ้งเกิด", example = "ท้องถิ่นเขตวัฒนา")
	private String authorityIssuing;
	@Schema(description = "ปีพ.ศ. เดือน วันเกิด", example = "25510623")
	private Integer dateOfBirth;
	@Schema(description = "ปีพ.ศ. เดือน วัน ที่แจ้งเกิด", example = "25510702")
	private Integer dateOfNotifying;
	@Schema(description = "ชื่อบิดา (คำนำหน้านาม#ชื่อตัว#=ชื่อรอง#ชื่อสกุล)", example = "ทดสอบ ทดสอบ")
	private String fatherName;
	@Schema(description = "สัญชาติบิดา", example = "ไทย")
	private String fatherNationalityDesc;
	@Schema(description = "เลขประจำตัวประชาชนบิดา", example = "1111111111111")
	private Long fatherPersonalID;
	@Schema(description = "ชื่อตัว", example = "ทดสอบ")
	private String firstName;
	@Schema(description = "เพศ", example = "ชาย")
	private String gender;
	@Schema(description = "ชื่อโรงพยาบาล", example = "บำรุงราษฎร์")
	private String hospitalName;
	@Schema(description = "ซอย (ที่เพิ่มชื่อเข้า)", example = "แจ่มจันทร์")
	private String houseRegistrationAlleyDesc;
	@Schema(description = "ตรอก (ที่เพิ่มชื่อเข้า)", example = "")
	private String houseRegistrationAlleyWayDesc;
	@Schema(description = "อำเภอ (ที่เพิ่มชื่อเข้า)", example = "เขตวัฒนา")
	private String houseRegistrationDistrictDesc;
	@Schema(description = "บ้านเลขที่ (ที่เพิ่มชื่อเข้า)", example = "ทะเบียนบ้านกลาง 123(บ้านกลาง)")
	private String houseRegistrationNo;
	@Schema(description = "จังหวัด (ที่เพิ่มชื่อเข้า)", example = "กรุงเทพมหานคร")
	private String houseRegistrationProvinceDesc;
	@Schema(description = "ถนน (ที่เพิ่มชื่อเข้า)", example = "")
	private String houseRegistrationRoadDesc;
	@Schema(description = "ตำบล (ที่เพิ่มชื่อเข้า)", example = "คลองตันเหนือ")
	private String houseRegistrationSubdistrictDesc;
	@Schema(description = "หมู่ที่ (ที่เพิ่มชื่อเข้า)", example = "0")
	private Integer houseRegistrationVillageNo;
	@Schema(description = "ชื่อสกุล", example = "ทดสอบ")
	private String lastName;
	@Schema(description = "ชื่อรอง", example = "")
	private String middleName;
	@Schema(description = "ชื่อมารดา (คำนำหน้านาม#ชื่อตัว[ปัจจุบัน]#ชื่อรอง#ชื่อสกุล[ก่อนสมรส])", example = "ทดสอบ ทดสอบ")
	private String motherName;
	@Schema(description = "สัญชาติมารดา", example = "ไทย")
	private String motherNationalityDesc;
	@Schema(description = "เลขประจำตัวประชาชนมารดา", example = "2222222222222")
	private Long motherPersonalID;
	@Schema(description = "สัญชาติ", example = "ไทย")
	private String nationality;
	@Schema(description = "ชื่อนายทะเบียนผู้รับแจ้งการเกิด", example = "นาง ทดสอบ ทดสอบ")
	private String officerName;
	@Schema(description = "อายุผู้แจ้งการเกิด", example = "53")
	private Integer personInformAge;
	@Schema(description = "ชื่อผู้แจ้งการเกิด", example = "นาย ทดสอบ ทดสอบ")
	private String personInformName;
	@Schema(description = "เลขประจำตัวประชาชนผู้แจ้งการเกิด", example = "3333333333333")
	private Long personInformPersonalID;
	@Schema(description = "ความสัมพันธ์ผู้แจ้งการเกิด", example = "ผู้อื่น")
	private String personInformRelation;
	@Schema(description = "เลขประจำตัวประชาชน", example = "44444444444444")
	private Long personalID;
	@Schema(description = "สถานที่เกิด (โรงพยาบาล, บ้าน, สถานีอนามัย ฯ)", example = "โรงพยาบาล")
	private String placeOfBirth;
	@Schema(description = "รหัสซอยที่เกิด", example = "3")
	private Integer placeOfBirthAlleyCode;
	@Schema(description = "ซอยที่เกิด", example = "สุขุมวิท 3(นานาเหนือ)")
	private String placeOfBirthAlleyDesc;
	@Schema(description = "รหัสตรอกที่เกิด", example = "0")
	private Integer placeOfBirthAlleyWayCode;
	@Schema(description = "ตรอกที่เกิด", example = "null")
	private String placeOfBirthAlleyWayDesc;
	@Schema(description = "รหัสอำเภอที่เกิด", example = "39")
	private Integer placeOfBirthDistrictCode;
	@Schema(description = "อำเภอที่เกิด", example = "เขตวัฒนา")
	private String placeOfBirthDistrictDesc;
	@Schema(description = "สถานที่เกิด", example = "33/3")
	private String placeOfBirthNo;
	@Schema(description = "รหัสจังหวัดที่เกิด", example = "10")
	private Integer placeOfBirthProvinceCode;
	@Schema(description = "จังหวัดที่เกิด", example = "กรุงเทพมหานคร")
	private String placeOfBirthProvinceDesc;
	@Schema(description = "รหัสถนนที่เกิด", example = "0")
	private Integer placeOfBirthRoadCode;
	@Schema(description = "ถนนที่เกิด", example = "null")
	private String placeOfBirthRoadDesc;
	@Schema(description = "รหัสตำบลที่เกิด", example = "1")
	private Integer placeOfBirthSubdistrictCode;
	@Schema(description = "ตำบลที่เกิด", example = "คลองเตยเหนือ")
	private String placeOfBirthSubdistrictDesc;
	@Schema(description = "รหัสหมู่บ้านที่เกิด", example = "0")
	private Integer placeOfBirthVillageNo;
	@Schema(description = "คำนำหน้านาม", example = "ด.ช.")
	private String titleDesc;
}
