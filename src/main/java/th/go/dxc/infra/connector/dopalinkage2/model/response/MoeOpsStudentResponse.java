package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoeOpsStudentResponse {
	
	private String academicYear;
	private String courseName;
	private String curriculumName;
	private String departmentNameThai;
	private String educationLevelName;
	private String firstName;
	private String gpax;
	private String gradeLevelName;
	private String lastName;
	private String majorName;
	private String personID;
	private String prefixName;
	private String programName;
	private String schoolID;
	private String schoolName;
	private String semester;
	private String studentStatusName;
}
