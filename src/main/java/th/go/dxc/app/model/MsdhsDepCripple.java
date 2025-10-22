package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลคนพิการ")
public class MsdhsDepCripple extends BaseMoiLinkage2 {

	@Schema(description = "Error Message", example = "")
	private Object message;
	@Schema(description = "ผลการทำงาน 0=OK, 1=ERROR, 3= Can't connect", example = "0")
	private Integer status;
	@Schema(description = "วันเดือนปีเกิด", example = "")
	private String birthDate;
	@Schema(description = "วันหมดอายุบัตร", example = "")
	private Object cardExpireDate;
	@Schema(description = "วันออกบัตร", example = "")
	private Object cardIssueDate;
	@Schema(description = "ประเภทความพิการ", example = "")
	private String deformName;
	@Schema(description = "เลขประจำตัวประชาชน", example = "1111111111111")
	private String personCode;
	@Schema(description = "ชื่อ-สกุล", example = "ทดสอบ ทดสอบ")
	private String personName;
}
