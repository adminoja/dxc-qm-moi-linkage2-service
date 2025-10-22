package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลทะเบียนบ้าน (บุคคลทุกประเภท)")
public class MoiDopaAddress extends BaseMoiLinkage2 {
	
	@Schema(description = "เลขรหัสซอย", example = "0")
	private Integer alleyCode;
	@Schema(description = "ชื่อซอย", example = "")
	private String alleyDesc;
	@Schema(description = "ชื่อซอยภาษาอังกฤษ", example = "")
	private String alleyEnglishDesc;
	@Schema(description = "เลขรหัสตรอก", example = "0")
	private Integer alleyWayCode;
	@Schema(description = "ชื่อตรอก", example = "")
	private String alleyWayDesc;
	@Schema(description = "ชื่อตรอกภาษาอังกฤษ", example = "")
	private String alleyWayEnglishDesc;
	@Schema(description = "วันที่จำหน่าย", example = "0")
	private Integer dateOfTerminate;
	@Schema(description = "เลขรหัสอำเภอ", example = "6")
	private Integer districtCode;
	@Schema(description = "ชื่ออำเภอ", example = "ปากเกร็ด")
	private String districtDesc;
	@Schema(description = "ชื่ออำเภอภาษาอังกฤษ", example = "Pak Kret")
	private String districtEnglishDesc;
	@Schema(description = "เลขรหัสประจำบ้าน", example = "12345678912")
	private Long houseID;
	@Schema(description = "บ้านเลขที่", example = "111/222")
	private String houseNo;
	@Schema(description = "เลขรหัสประเภทบ้าน", example = "1")
	private Integer houseType;
	@Schema(description = "ประเภทบ้าน", example = "บ้าน")
	private String houseTypeDesc;
	@Schema(description = "เลขรหัสจังหวัด", example = "12")
	private Integer provinceCode;
	@Schema(description = "ชื่อจังหวัด", example = "นนทบุรี")
	private String provinceDesc;
	@Schema(description = "ชื่อจังหวัดภาษาอังกฤษ", example = "Nonthaburi")
	private String provinceEnglishDesc;
	@Schema(description = "เลขรหัสสำนักทะเบียน", example = "1297")
	private String rcodeCode;
	@Schema(description = "ชื่อสำนักทะเบียน", example = "ท้องถิ่นเทศบาลนครปากเกร็ด")
	private String rcodeDesc;
	@Schema(description = "เลขรหัสถนน", example = "0")
	private Integer roadCode;
	@Schema(description = "ชื่อถนน", example = "")
	private String roadDesc;
	@Schema(description = "ชื่อถนนภาษาอังกฤษ", example = "")
	private String roadEnglishDesc;
	@Schema(description = "เลขรหัสตำบล", example = "4")
	private Integer subdistrictCode;
	@Schema(description = "ชื่อตำบล", example = "บางพูด")
	private String subdistrictDesc;
	@Schema(description = "ชื่อตำบลภาษาอังกฤษ", example = "Bang Phut")
	private String subdistrictEnglishDesc;
	@Schema(description = "เลขหมู่ที่", example = "9")
	private Integer villageNo;
}
