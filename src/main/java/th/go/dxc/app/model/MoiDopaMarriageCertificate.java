package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลทะเบียนสมรส")
public class MoiDopaMarriageCertificate {

	@Schema(description = "อายุฝ่ายหญิง", example = "30")
	private Integer femaleAge;
	@Schema(description = "ปี เดือน วันเกิดของฝ่ายหญิง", example = "25360719")
	private Integer femaleDateOfBirth;
	@Schema(description = "ชื่อตัวของฝ่ายหญิง", example = "25320404")
	private String femaleFirstName;
	@Schema(description = "ชื่อเต็มฝ่ายหญิง", example = "ทดสอบ")
	private String femaleFullnameAndRank;
	@Schema(description = "ชื่อสกุลของฝ่ายหญิง", example = "น.ส.ทดสอบ ทดสอบ")
	private String femaleLastName;
	@Schema(description = "ชื่อกลาง ฝ่ายหญิง", example = "")
	private String femaleMiddleName;
	@Schema(description = "รหัสสัญชาติของฝ่ายหญิง", example = "99")
	private Integer femaleNationalityCode;
	@Schema(description = "สัญชาติของฝ่ายหญิง", example = "ไทย")
	private String femaleNationalityDesc;
	@Schema(description = "เลขที่เอกสารอื่น ๆ ของฝ่ายหญิง", example = "")
	private String femaleOtherDocID;
	@Schema(description = "เลขประจำตัวประชาชนฝ่ายหญิง", example = "0123456789123")
	private Long femalePID;
	@Schema(description = "รหัสคำนำหน้านามฝ่ายหญิง", example = "4")
	private Integer femaleTitleCode;
	@Schema(description = "คำนำหน้านามฝ่ายหญิง", example = "น.ส.")
	private String femaleTitleDesc;
	@Schema(description = "อายุฝ่ายชาย", example = "39")
	private Integer maleAge;
	@Schema(description = "ปี เดือน วันเกิดของฝ่ายชาย", example = "25320101")
	private Integer maleDateOfBirth;
	@Schema(description = "ชื่อตัวของฝ่ายชาย", example = "25320101")
	private String maleFirstName;
	@Schema(description = "ชื่อเต็มฝ่ายชาย", example = "ทดสอบ")
	private String maleFullnameAndRank;
	@Schema(description = "ชื่อสกุลของฝ่ายชาย", example = "นายทดสอบ ทดสอบ")
	private String maleLastName;
	@Schema(description = "ชื่อกลาง ฝ่ายชาย", example = "ทดสอบ")
	private String maleMiddleName;
	@Schema(description = "รหัสสัญชาติของฝ่ายชาย", example = "99")
	private Integer maleNationalityCode;
	@Schema(description = "สัญชาติของฝ่ายชาย", example = "ไทย")
	private String maleNationalityDesc;
	@Schema(description = "เลขที่เอกสารอื่น ๆ ของฝ่ายชาย", example = "ทดสอบ")
	private String maleOtherDocID;
	@Schema(description = "เลขประจำตัวประชาชนฝ่ายชาย", example = "0123456789123")
	private Long malePID;
	@Schema(description = "รหัสคำนำหน้านามฝ่ายชาย", example = "3")
	private Integer maleTitleCode;
	@Schema(description = "คำนำหน้านามฝ่ายชาย", example = "นาย")
	private String maleTitleDesc;
	@Schema(description = "ปี เดือน วันที่จดทะเบียนสมรส", example = "25630320")
	private Integer marryDate;
	@Schema(description = "เลขทะเบียนในการจดทะเบียนสมรส", example = "5460102100")
	private Long marryID;
	@Schema(description = "รหัสสถานที่จดทะเบียนสมรส", example = "1201")
	private String marryPlace;
	@Schema(description = "สนท.ที่จดทะเบียนสมรส", example = "อำเภอเมืองนนทบุรี")
	private String marryPlaceDesc;
	@Schema(description = "จังหวัดที่จดทะเบียนสมรส", example = "จังหวัดนนทบุรี")
	private String marryPlaceProvince;
	@Schema(description = "เวลาจดทะเบียนสมรส", example = "91900")
	private Integer marryTime;
	@Schema(description = "ประเภทของการสมรส ( ก, ข, ค, ง=เล่มที่, ท=เคลื่อนที่, พ=พิพากษา, ร=คำสั่ง รมต. )", example = "")
	private String marryType;
}
