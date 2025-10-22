package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลรายชื่อบุคคลที่ถูกยึดหรืออายัดทรัพย์สิน (HR-02)")
public class AmloAssetFreezePerson extends BaseMoiLinkage2 {

	@Schema(description = "ที่อยู่ (ตามทะเบียนบ้าน)", example = "999 หมู่ 9")
	private String address;
	@Schema(description = "ที่อยู่ (ที่ติดต่อได้)", example = "")
	private String address2;
	@Schema(description = "ชื่ออื่น/ฉายา [1]", example = "")
	private String alias1;
	@Schema(description = "ชื่ออื่น/ฉายา [2]", example = "")
	private String alias2;
	@Schema(description = "ชื่ออื่น/ฉายา [3]", example = "")
	private String alias3;
	@Schema(description = "วันที่ประกาศ (yyyy-mm-dd) (ปี ค.ศ.)", example = "2025-01-31")
	private String announceDate;
	@Schema(description = "วันที่สิ้นสุดคำสั่ง (yyyy-mm-dd) (ปี ค.ศ.)", example = "2025-01-31")
	private String announceDateEnd;
	@Schema(description = "จังหวัด/รัฐ/เมืองที่เกิด", example = "")
	private String birthCity;
	@Schema(description = "ประเทศที่เกิด", example = "")
	private String birthCountry;
	@Schema(description = "วันเดือนปีที่เกิด (yyyy-mm-dd) (ปี พ.ศ.)", example = "1980-01-31")
	private String birthday;
	@Schema(description = "เลขทะเบียนนิติบุคคล", example = "0000000000000")
	private String coRegisNo;
	@Schema(description = "เลขที่คำสั่ง", example = "ย.999/2568")
	private String commandNo;
	@Schema(description = "ประเทศ (ตามทะเบียนบ้าน)", example = "TH")
	private String country;
	@Schema(description = "ประเทศ (ที่ติดต่อได้)", example = "")
	private String country2;
	@Schema(description = "วันเดือนปีที่สร้างข้อมูล", example = "2025-01-31")
	private String createDate;
	@Schema(description = "อำเภอ (ตามทะเบียนบ้าน)", example = "ปทุมวัน")
	private String district;
	@Schema(description = "อำเภอ (ที่ติดต่อได้)", example = "")
	private String district2;
	@Schema(description = "ประเภทบุคคล (ENTITY = นิติบุคคล, PERSON = บุคคลธรรมดา)", example = "PERSON")
	private String entityType;
	@Schema(description = "ชื่อภาษาอังกฤษ", example = "")
	private String firstNameEn;
	@Schema(description = "ชื่อภาษาไทย", example = "ป้องกัน")
	private String firstNameTh;
	@Schema(description = "เพศ", example = "FEMALE")
	private String gender;
	@Schema(description = "กลุ่มของรายชื่อ", example = "ไม่ระบุ")
	private String groupName;
	@Schema(description = "อาชีพ", example = "")
	private String job;
	@Schema(description = "นามสกุลภาษาอังกฤษ", example = "")
	private String lastNameEn;
	@Schema(description = "นามสกุลภาษาไทย", example = "ปราบปราม")
	private String lastNameTh;
	@Schema(description = "ชื่อกลาง", example = "")
	private String middleName;
	@Schema(description = "ชื่อสี่", example = "")
	private String nameFour;
	@Schema(description = "สัญชาติ", example = "ไทย")
	private String nationality;
	@Schema(description = "ชื่อ(ตัวอักษรต้นแบบ)ระบุรูปแบบภาษาอังกฤษ", example = "")
	private String originalName;
	@Schema(description = "ชื่ออื่นที่อาจใช้ระบุตัวตนได้ [1]", example = "")
	private String otherName1;
	@Schema(description = "ชื่ออื่นที่อาจใช้ระบุตัวตนได้ [2]", example = "")
	private String otherName2;
	@Schema(description = "ชื่ออื่นที่อาจใช้ระบุตัวตนได้ [3]", example = "")
	private String otherName3;
	@Schema(description = "เลขที่หนังสือเดินทาง", example = "A00000000")
	private String passportNo;
	@Schema(description = "เลขที่หนังสือเดินทาง [2]", example = "A00000000")
	private String passportNo2;
	@Schema(description = "เลขที่หนังสือเดินทาง [3]", example = "A00000000")
	private String passportNo3;
	@Schema(description = "เลขที่หนังสือเดินทาง [4]", example = "A00000000")
	private String passportNo4;
	@Schema(description = "เลขที่หนังสือเดินทาง [5]", example = "A00000000")
	private String passportNo5;
	@Schema(description = "รหัสประจำตัวประชาชน", example = "0000000000000")
	private String personalID;
	@Schema(description = "ตำแหน่ง", example = "")
	private String position;
	@Schema(description = "คำอธิบายชนิดของเลขที่หลักฐานแสดงตนอื่น ๆ", example = "ผู้กระทำความผิดมูลฐาน")
	private String proofDesc;
	@Schema(description = "เลขที่ของหลักฐานแสดงตนอื่น ๆ", example = "")
	private String proofNo;
	@Schema(description = "จังหวัด/รัฐ/เมือง (ตามทะเบียนบ้าน)", example = "กรุงเทพมหานคร")
	private String province;
	@Schema(description = "จังหวัด/รัฐ/เมือง (ที่ติดต่อได้)", example = "")
	private String province2;
	@Schema(description = "ตำบล (ตามทะเบียนบ้าน)", example = "วังใหม่")
	private String subdistrict;
	@Schema(description = "ตำบล (ที่ติดต่อได้)", example = "")
	private String subdistrict2;
	@Schema(description = "เลขประจำตัวผู้เสียภาษี", example = "")
	private String taxNo;
	@Schema(description = "คำนำหน้าชื่อภาษาอังกฤษ", example = "Miss , Ms.")
	private String titleEn;
	@Schema(description = "คำนำหน้าชื่อภาษาไทย", example = "นางสาว")
	private String titleTh;
	@Schema(description = "รหัสบุคคล", example = "")
	private String uid;
	@Schema(description = "วันเดือนปีที่ปรับปรุงข้อมูล", example = "2025-01-31")
	private String updateDate;
	@Schema(description = "สถานะของบุคคล/นิติบุคคล (Y = บุคคลที่มีความเสี่ยง, N = เพิกถอนรายชื่อ)", example = "null")
	private String yOrn;
}
