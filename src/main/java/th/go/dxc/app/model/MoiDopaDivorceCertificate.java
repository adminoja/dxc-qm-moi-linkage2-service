package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลทะเบียนการหย่า")
public class MoiDopaDivorceCertificate extends BaseMoiLinkage2 {
	
	@Schema(description = "ปี เดือน วันที่จดทะเบียนการหย่า", example = "25581117")
	private Integer divorceDate;
	@Schema(description = "เลขทะเบียนในการจดทะเบียนการหย่า", example = "5560037547")
	private Long divorceID;
	@Schema(description = "รหัสสถานที่จดทะเบียนการหย่า", example = "1005")
	private String divorcePlace;
	@Schema(description = "สนท.ที่จดทะเบียนการหย่า", example = "ท้องถิ่นเขตบางเขน")
	private String divorcePlaceDesc;
	@Schema(description = "จังหวัดที่จดทะเบียนการหย่า", example = "กรุงเทพมหานคร")
	private String divorcePlaceProvince;
	@Schema(description = "เวลาจดทะเบียนการหย่า", example = "82500")
	private Integer divorceTime;
	@Schema(description = "ประเภทของการหย่า", example = "")
	private String divorceType;
	@Schema(description = "อายุฝ่ายหญิง", example = "31")
	private Integer femaleAge;
	@Schema(description = "ปี เดือน วันเกิดของฝ่ายหญิง", example = "25270812")
	private Integer femaleDateOfBirth;
	@Schema(description = "ชื่อตัวของฝ่ายหญิง", example = "ทดสอบ")
	private String femaleFirstName;
	@Schema(description = "ชื่อเต็มฝ่ายหญิง", example = "น.ส.ทดสอบ ทดสอบ")
	private String femaleFullnameAndRank;
	@Schema(description = "ชื่อสกุลของฝ่ายหญิง", example = "ทดสอบ")
	private String femaleLastName;
	@Schema(description = "ชื่อกลาง ฝ่ายหญิง", example = "")
	private String femaleMiddleName;
	@Schema(description = "รหัสสัญชาติของฝ่ายหญิง", example = "99")
	private Integer femaleNationalityCode;
	@Schema(description = "สัญชาติของฝ่ายหญิง", example = "ไทย")
	private String femaleNationalityDesc;
	@Schema(description = "เลขที่เอกสารอื่น ๆ ของฝ่ายหญิง", example = "")
	private String femaleOtherDocID;
	@Schema(description = "เลขประจำตัวประชาชนฝ่ายหญิง", example = "1234567890123")
	private Long femalePID;
	@Schema(description = "รหัสคำนำหน้านามฝ่ายหญิง", example = "4")
	private Integer femaleTitleCode;
	@Schema(description = "คำนำหน้านามฝ่ายหญิง", example = "น.ส.")
	private String femaleTitleDesc;
	@Schema(description = "อายุฝ่ายชาย", example = "30")
	private Integer maleAge;
	@Schema(description = "ปี เดือน วันเกิดของฝ่ายชาย", example = "25280904")
	private Integer maleDateOfBirth;
	@Schema(description = "ชื่อตัวของฝ่ายชาย", example = "ทดสอบ")
	private String maleFirstName;
	@Schema(description = "ชื่อเต็มฝ่ายชาย", example = "นายทดสอบ ทดสอบ")
	private String maleFullnameAndRank;
	@Schema(description = "ชื่อสกุลของฝ่ายชาย", example = "ทดสอบ")
	private String maleLastName;
	@Schema(description = "ชื่อกลาง ฝ่ายชาย", example = "")
	private String maleMiddleName;
	@Schema(description = "รหัสสัญชาติของฝ่ายชาย", example = "99")
	private Integer maleNationalityCode;
	@Schema(description = "สัญชาติของฝ่ายชาย", example = "ไทย")
	private String maleNationalityDesc;
	@Schema(description = "เลขที่เอกสารอื่น ๆ ของฝ่ายชาย", example = "01")
	private String maleOtherDocID;
	@Schema(description = "เลขประจำตัวประชาชนฝ่ายชาย", example = "9876543210987")
	private Long malePID;
	@Schema(description = "รหัสคำนำหน้านามฝ่ายชาย", example = "3")
	private Integer maleTitleCode;
	@Schema(description = "คำนำหน้านามฝ่ายชาย", example = "นาย")
	private String maleTitleDesc;
	@Schema(description = "ปี เดือน วันที่จดทะเบียนสมรส", example = "25560714")
	private Integer marryDate;
	@Schema(description = "เลขทะเบียนในการจดทะเบียนสมรส", example = "100000810")
	private Integer marryID;
	@Schema(description = "รหัสสถานที่จดทะเบียนสมรส", example = "4001")
	private String marryPlace;
	@Schema(description = "สนท.ที่จดทะเบียนสมรส", example = "อำเภอเมืองขอนแก่น")
	private String marryPlaceDesc;
	@Schema(description = "จังหวัดที่จดทะเบียนสมรส", example = "จังหวัดขอนแก่น")
	private String marryPlaceProvince;
	@Schema(description = "เวลาจดทะเบียนสมรส", example = "90900")
	private String marryTime;
	@Schema(description = "ประเภทของการสมรส", example = "")
	private String marryType;
}
