package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลผู้สำเร็จการศึกษา")
public class MoeOpsGraduate extends BaseMoiLinkage2 {
	@Schema(description = "ปีที่สำเร็จการศึกษา", example = "")
	private String academicYear;
	@Schema(description = "สาขางาน", example = "")
	private String courseName;
	@Schema(description = "วุฒิการศึกษา", example = "")
	private String degreeName;
	@Schema(description = "หน่วยงานต้นสังกัด", example = "")
	private String departmentNameThai;
	@Schema(description = "ระดับการศึกษา", example = "")
	private String educationLevelName;
	@Schema(description = "ชื่อ", example = "")
	private String firstName;
	@Schema(description = "ผลการเรียนเฉลี่ยสะสม", example = "")
	private String gpax;
	@Schema(description = "วันที่สำเร็จการศึกษา", example = "")
	private String graduateDate;
	@Schema(description = "นามสกุล", example = "")
	private String lastName;
	@Schema(description = "ประเภทวิชา", example = "")
	private String majorName;
	@Schema(description = "เลขบัตรประจำตัวประชาชน", example = "")
	private String personID;
	@Schema(description = "คำนำหน้าชื่อ", example = "")
	private String prefixName;
	@Schema(description = "สาขาวิชา", example = "")
	private String programName;
	@Schema(description = "รหัสสถานศึกษา", example = "")
	private String schoolID;
	@Schema(description = "ชื่อสถานศึกษา", example = "")
	private String schoolName;
	@Schema(description = "ภาคการศึกษา", example = "")
	private String semester;
}
