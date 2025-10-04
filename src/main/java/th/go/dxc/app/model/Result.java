package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "ผลลัพธ์ของข้อมูล")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
	private String result;
}
