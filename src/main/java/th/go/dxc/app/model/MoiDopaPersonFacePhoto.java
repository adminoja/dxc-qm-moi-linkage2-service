package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลใบสูติบัตร")
public class MoiDopaPersonFacePhoto extends BaseMoiLinkage2 {
	@Schema(description = "เลขบัตรประจำตัวประชาชน", example = "1234567890123")
	private String personalID;
	@Schema(description = "ภาพใบหน้า (อยู่ในรูปแบบ base64string)", example = "/9j/4AAQSkZJRgABAgAAAQ......")
	private String image;
	@Schema(description = "ชนิดไฟล์รูปภาพ", example = "data:image/jpeg;base64")
	private String mineType;
}
