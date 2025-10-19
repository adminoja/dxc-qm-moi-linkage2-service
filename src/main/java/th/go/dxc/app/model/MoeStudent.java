package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoeStudent {
	@Schema(description = "ปีการศึกษา", example = "")
	private String academicYear;
	@Schema(description = "สาขางาน", example = "")
	private String courseName;
	@Schema(description = "หลักสูตร", example = "")
	private String curriculumName;
	@Schema(description = "หน่วยงานต้นสังกัด", example = "")
	private String departmentNameThai;
	@Schema(description = "ระดับการศึกษา", example = "")
	private String educationLevelName;
	@Schema(description = "ชื่อ", example = "")
	private String firstName;
	@Schema(description = "ผลการเรียนเฉลียสะสม", example = "")
	private String gpax;
	@Schema(description = "ชั้นปี", example = "")
	private String gradeLevelName;
	@Schema(description = "นามสกุล", example = "")
	private String lastName;
	@Schema(description = "ประเภทวิชา", example = "")
	private String majorName;
	@Schema(description = "เลขประจำตัวประชาชน", example = "")
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
	@Schema(description = "สถานะนักเรียน", example = "")
	private String studentStatusName;
}
