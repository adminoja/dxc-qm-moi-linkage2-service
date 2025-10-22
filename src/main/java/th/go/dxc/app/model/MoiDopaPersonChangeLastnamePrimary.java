package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลการจดทะเบียนเปลี่ยนชื่อสกุล")
public class MoiDopaPersonChangeLastnamePrimary extends BaseMoiLinkage2 {
	@Schema(description = "อายุผู้ขอใบอนุญาต ณ วันที่ออกใบอนุญาต", example = "39")
	private Integer age;
	@Schema(description = "ชื่ออำเภอ ตามที่อยู่", example = "อำเภอเมืองนนทบุรี")
	private String amphorDesc;
	@Schema(description = "สาเหตุการเปลี่ยนชื่อสกุล", example = "การสมรส")
	private String causeOfChange;
	@Schema(description = "ปี เดือน วันเกิดของผู้ขอใบอนุญาต", example = "25180513")
	private Integer dateOfBirth;
	@Schema(description = "ชื่อตำบล ตามที่อยู่", example = "บางกร่าง")
	private String districtDesc;
	@Schema(description = "ปีพ.ศ. เดือน วันที่ ออกหนังสืออนุญาต", example = "25180513")
	private Integer docDate;
	@Schema(description = "เลขที่หนังสือ (9หลักแรกเป็น docno 4 หลักหลังเป็นปี)", example = "1234567")
	private Integer docID;
	@Schema(description = "รหัสสนท. ที่ออกหนังสืออนุญาต", example = "1011")
	private String docPlace;
	@Schema(description = "สนท.ที่ออกหนังสืออนุญาต", example = "ท้องถิ่นเขตประเวศ")
	private String docPlaceDesc;
	@Schema(description = "จังหวัดที่ออกหนังสืออนุญาต", example = "นนทบุรี")
	private String docPlaceProvince;
	@Schema(description = "ชื่อบิดา", example = "ทดสอบ")
	private String fatherFirstName;
	@Schema(description = "ชื่อตัวของผู้ขอใบอนุญาต", example = "ทดสอบ")
	private String firstName;
	@Schema(description = "ชื่อเต็มผู้ขอใบอนุญาต", example = "นายทดสอบ ทดสอบ")
	private String fullNameAndRank;
	@Schema(description = "รหัสเพศผู้ขอใบอนุญาต", example = "1")
	private Integer genderCode;
	@Schema(description = "Descrption เพศผู้ขอใบอนุญาต", example = "ชาย")
	private String genderDesc;
	@Schema(description = "บ้านเลขที่", example = "11")
	private String hno;
	@Schema(description = "รหัสสนท. ตามที่อยู่", example = "1234")
	private String hrcode;
	@Schema(description = "Description สนท. ตามที่อยู่", example = "ทดสอบทดสอบทดสอบ")
	private String hrcodeDesc;
	@Schema(description = "ชื่อสกุลของผู้ขอใบอนุญาต", example = "ทดสอบ")
	private String lastName;
	@Schema(description = "ชื่อกลาง ผู้ขอใบอนุญาต", example = "")
	private String middleName;
	@Schema(description = "ชื่อมารดา", example = "ทดสอบ")
	private String motherFirstName;
	@Schema(description = "รหัสสัญชาติของผู้ขอใบอนุญาต", example = "99")
	private Integer nationalityCode;
	@Schema(description = "Description สัญชาติของผู้ขอใบอนุญาต", example = "ไทย")
	private String nationalityDesc;
	@Schema(description = "ชื่อสกุลที่แจ้งเปลี่ยน", example = "ทดสอบ")
	private String newName;
	@Schema(description = "เลขประจำตัวประชาชนผู้ขอใบอนุญาต", example = "1234567890123")
	private Long pid;
	@Schema(description = "ชื่อจังหวัด ตามที่อยู่", example = "นนทบุรี")
	private String provinceDesc;
	@Schema(description = "ปี เดือน วันของใบคำขอ (ช.๑) (YYYYMMDD)", example = "25180513")
	private Integer requestDate;
	@Schema(description = "เลขที่ใบคำขอช.๑ (เลขรับ)", example = "1234")
	private Integer requestID;
	@Schema(description = "ปีของเลขที่ใบคำขอช.๑(พ.ศ.YYYY)", example = "2555")
	private Integer requestYear;
	@Schema(description = "ชื่อซอย ตามที่อยู่", example = "เฉลิมพระเกียรติร.๙ซ.5")
	private String soiDesc;
	@Schema(description = "ชื่อถนน ตามที่อยู่", example = "")
	private String thanonDesc;
	@Schema(description = "รหัสคำนำหน้านามผู้ขอใบอนุญาต", example = "1")
	private Integer titleCode;
	@Schema(description = "Description คำนำหน้านามผู้ขอใบอนุญาต", example = "นาย")
	private String titleDesc;
	@Schema(description = "ชื่อตรอก ตามที่อยู่", example = "")
	private String trokDesc;
	@Schema(description = "จำนวนรายการ ทะเบียนการเปลี่ยนชื่อตัว (ช.5) ทั้งหมด", example = "10")
	private Integer total;
}
