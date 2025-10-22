package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseMoiLinkage2 {
	
	@Schema(description = "เลขประจำตัวประชาชน", example = "1111111111111")
	private String citizenCardNumber;
}
