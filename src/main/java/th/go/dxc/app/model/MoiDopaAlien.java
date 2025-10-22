package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลทะเบียนบุคคลต่างด้าว")
public class MoiDopaAlien extends BaseMoiLinkage2 {

	@Schema(description = "เลขรหัสประจำบ้านที่อาศัยอยู่", example = "10010111111")
	private Long houseID;
	@Schema(description = "คำนำหน้านาม", example = "น.ส.")
	private String titleDesc;
	@Schema(description = "ชื่อตัว", example = "มารี")
	private String firstName;
	@Schema(description = "ชื่อสกุล", example = "ทดสอบ")
	private String lastName;
	@Schema(description = "เพศ", example = "หญิง")
	private String genderDesc;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ที่เกิด", example = "25391010")
	private Integer dateOfBirth;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ที่เข้ามาในประเทศไทย", example = "0")
	private Integer dateInThai;
	@Schema(description = "สัญชาติ", example = "กัมพูชา")
	private String nationalityDesc;
	@Schema(description = "หมู่เลือด", example = "ไม่ทราบ")
	private String bloodType;
	@Schema(description = "ศาสนา", example = "ไม่ทราบ")
	private String religion;
	@Schema(description = "สถานภาพสมรส", example = "โสด")
	private String marryStatus;
	@Schema(description = "ชื่อคู่สมรส", example = "")
	private String spouseName;
	@Schema(description = "สถานภาพบุคคล", example = "ปกติ")
	private String statusOfPersonDesc;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ที่ทำการเพิ่มคนต่างด้าว", example = "25630316")
	private Integer personAddDate;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ที่ทำการแก้ไขคนต่างด้าว", example = "25630316")
	private Integer personUpdDate;
	@Schema(description = "สถานะการเพิ่มคนต่างด้าว", example = "เพิ่มด้วยการลงทะเบียนปี พ.ศ. 2562 ถือบัตรหมดอายุ 31 ม.ค. 2563")
	private String statusAdded;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ที่จำหน่ายบุคคล", example = "0")
	private String terminateDate;
	@Schema(description = "เลขอ้างอิงจากกรมการจัดหางาน", example = "0010011111111")
	private String doeNumber;

	// --- Father ---
	@Schema(description = "เลขประจำตัวประชาชนบิดา", example = "0")
	private Long fatherPersonalID;
	@Schema(description = "ชื่อ-ชื่อสกุลบิดา", example = "เวียร")
	private String fatherName;
	@Schema(description = "สัญชาติบิดา", example = "-")
	private String fatherNationalityDesc;

	// --- Mother ---
	@Schema(description = "เลขประจำตัวประชาชนมารดา", example = "0")
	private Long motherPersonalID;
	@Schema(description = "ชื่อ-ชื่อสกุลมารดา", example = "มาลี")
	private String motherName;
	@Schema(description = "สัญชาติมารดา", example = "-")
	private String motherNationalityDesc;

	// --- Passport ---
	@Schema(description = "ประเภทหนังสือเดินทาง", example = "เอกสารใช้แทนหนังสือเดินทาง Travel Document")
	private String passportDocumentType;
	@Schema(description = "เลขที่หนังสือเดินทาง", example = "T0111111")
	private String passportDocumentNo;
	@Schema(description = "สถานที่ออกหนังสือเดินทาง", example = "ไทย")
	private String passportIssuePlace;
	@Schema(description = "วันที่ออกหนังสือเดินทาง", example = "25630316")
	private Integer passportIssueDate;
	@Schema(description = "วันที่หมดอายุหนังสือเดินทาง", example = "25631224")
	private Integer passportExpireDate;

	// --- Visa ---
	@Schema(description = "เลขวีซ่า", example = "")
	private String visaDocumentNo;
	@Schema(description = "วันที่ออกวีซ่า", example = "25630316")
	private Integer visaIssueDate;
	@Schema(description = "วันที่หมดอายุวีซ่า", example = "25631224")
	private Integer visaExpireDate;
	@Schema(description = "สถานที่ออกวีซ่า", example = "")
	private String visaIssuePlace;
	@Schema(description = "ประเภทวีซ่า", example = "NON L-A")
	private String visaType;
	@Schema(description = "ประเภทคำร้องขอออกวีซ่า", example = "ไม่ระบุ")
	private String visaRequestType;
}
