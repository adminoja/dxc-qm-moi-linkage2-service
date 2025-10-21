package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลทะเบียนราษฎร (ค้นหาด้วยชื่อตัว-ชื่อสกุล)")
public class MoiDopaPersonFirstnameLastname {

	@Schema(description = "รหัสคำนำหน้า", example = "3")
	private Integer titleCode;
	@Schema(description = "คำนำหน้านาม", example = "นาย")
	private String titleDesc;
	@Schema(description = "คำนำหน้านามเดิม", example = "นาย")
	private String titleName;
	@Schema(description = "รหัสตรวจสอบคำนำหน้านาม", example = "1")
	private Integer titleSex;
	@Schema(description = "ชื่อตัว", example = "ทดสอบ")
	private String firstName;
	@Schema(description = "ชื่อกลาง", example = "ทดสอบ")
	private String middleName;
	@Schema(description = "ชื่อสกุล", example = "ทดสอบ")
	private String lastName;
	@Schema(description = "รหัสเพศ", example = "1")
	private Integer genderCode;
	@Schema(description = "วันเดือนปี เกิด", example = "25360719")
	private Integer dateOfBirth;
	@Schema(description = "รหัสสัญชาติ", example = "99")
	private Integer nationalityCode;
	@Schema(description = "สัญชาติ", example = "ไทย")
	private String nationalityDesc;
	@Schema(description = "สถานะภาพเจ้าบ้าน", example = "ผู้อาศัย")
	private String ownerStatusDesc;
	@Schema(description = "รหัสสถานะภาพบุคคล", example = "0")
	private Integer statusOfPersonCode;
	@Schema(description = "สถานภาพบุคคล", example = "บุคคลนี้มีภูมิลำเนาอยู่ในบ้านนี้")
	private String statusOfPersonDesc;
	@Schema(description = "วันเดือนปี ที่ย้ายเข้ามาในบ้าน", example = "25560319")
	private Integer dateOfMoveIn;
	@Schema(description = "อายุ", example = "27")
	private Integer age;
	@Schema(description = "เลขประจำตัวประชาชน บิดา", example = "1101501217312")
	private Long fatherPersonalID;
	@Schema(description = "ชื่อบิดา", example = "ทดสอบ")
	private String fatherName;
	@Schema(description = "รหัสสัญชาติ บิดา", example = "99")
	private Integer fatherNationalityCode;
	@Schema(description = "สัญชาติ บิดา", example = "ไทย")
	private String fatherNationalityDesc;
	@Schema(description = "เลขประจำตัวประชาชน มารดา", example = "1101501217312")
	private Long motherPersonalID;
	@Schema(description = "ชื่อมารดา", example = "ทดสอบ")
	private String motherName;
	@Schema(description = "รหัสสัญาติ มารดา", example = "99")
	private Integer motherNationalityCode;
	@Schema(description = "สัญชาติ มารดา", example = "ไทย")
	private String motherNationalityDesc;
	@Schema(description = "คำนำหน้านาม/ยศ ชื่อตัว-สกุล", example = "นายทดสอบ ทดสอบ1")
	private String fullnameAndRank;
	@Schema(description = "เพศ", example = "ชาย")
	private String genderDesc;
	@Schema(description = "คำนำหน้านาม (ภาษาอังกฤษ)", example = "MR.")
	private String englishTitleDesc;
	@Schema(description = "ชื่อตัว (ภาษาอังกฤษ)", example = "Test")
	private String englishFirstName;
	@Schema(description = "ชื่อกลาง (ภาษาอังกฤษ)", example = "Test")
	private String englishMiddleName;
	@Schema(description = "ชื่อสกุล (ภาษาอังกฤษ)", example = "Test")
	private String englishLastName;
	@Schema(description = "รหัสบัตรประชาชน", example = "1101501217311")
	private Long pid;
	@Schema(description = "จำนวนข้อมูลทั้งหมด", example = "5")
	private Integer totalRecord;
	@Schema(description = "เลขที่ข้อมูล", example = "1")
	private Integer recordNumber;
}
