package th.go.dxc.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lk2ThaidLogFilter {
	@Parameter(description = "รหัสข้อมูล")
	private Integer id;
	@Parameter(description = "ชื่อผู้ใช้งาน (เลขบัตรประจำตัวประชาชน)")
	private String username;
	@Parameter(description = "Token การเข้าถึง")
	private String accessToken;
	@Parameter(description = "เลขบัตรประจำตัวประชาชน")
	private String pid;
	@Parameter(description = "ชื่อจริง")
	private String firstname;
	@Parameter(description = "สกุลจริง")
	private String lastname;
	@Parameter(description = "สถานะ")
	private Boolean status;
	@JsonIgnore
	@Parameter(description = "วันเวลาหมดอายุใช้งาน ThaID")
	private LocalDateTime expiresIn;
	@Parameter(description = "Token ต่ออายุ")
	private String refreshToken;
	@JsonIgnore
	@Parameter(description = "วันเวลาเข้าใช้งาน ThaID")
	private LocalDateTime issuedAt;
	@Parameter(description = "ประเภท")
	private String type;
	@Parameter(description = "Session เข้าใช้งาน Keycloak")
	private String sessionStateKeycloak;
	@JsonIgnore
	@Parameter(description = "วันเวลาเข้าใช้งาน")
	private LocalDateTime loginDatetime;
}
