package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลการพัฒนาฝีมือแรงงาน")
public class MolDsdWorkforceDevelopment {
	@Schema(description = "วันที่ออกหนังสือรับรอง/วุฒิบัตร", example = "")
	private String certificateDate;
	@Schema(description = "หมายเลขของหนังสือรับรอง/วุฒิบัตร", example = "")
	private String certificateNo;
	@Schema(description = "-", example = "")
	private String cerExpire;
	@Schema(description = "สาขาของการฝึกอบรม/สาขาการทดสอบมาตรฐานฝีมือแรงงาน/สาขาการรับรองความรู้ความสามารถ ซึ่งขึ้นอยู่กับฟิลด์ TYPES ว่าเป็นแบบใด", example = "")
	private String course;
	@Schema(description = "-", example = "")
	private String email;
	@Schema(description = "-", example = "")
	private String formId;
	@Schema(description = "ชื่อ-สกุล", example = "")
	private String names;
	@Schema(description = "-", example = "")
	private String pathCer;
	@Schema(description = "เลขประจำตัวประชาชน 13 หลัก", example = "")
	private String personalId;
	@Schema(description = "หน่วยงานที่ออกหนังสือรับรอง/วุฒิบัตร", example = "")
	private String site;
	@Schema(description = "-", example = "")
	private String telNo;
	@Schema(description = "-", example = "")
	private String typeOfTrain;
	@Schema(description = "-", example = "")
	private String tId;
	@Schema(description = "-", example = "")
	private String year;
}
