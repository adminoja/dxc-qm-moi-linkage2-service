package th.go.dxc.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "เงื่อนไขการค้นหาข้อมูล")
public class Lk2TokenServiceFilter {
	@Parameter(description = "รหัสข้อมูล")
	private Integer id;
	@Parameter(description = "ชื่อผู้ใช้งาน (เลขบัตรประจำตัวประชาชน)")
	private String username;
	@Parameter(description = "Token การเข้าถึง Linkage2")
	private String token;
	@JsonIgnore
	@Parameter(description = "วันเวลาใช้งาน Linkage2")
	private LocalDateTime insertTime;
	@JsonIgnore
	@Parameter(description = "วันเวลาหมดอายุใช้งาน Linkage2")
	private LocalDateTime expireTime;
	@Parameter(description = "ช่องทางในการ Login")
	private String channel;
	@Parameter(description = "Session เข้าใช้งาน Keycloak")
	private String sessionState;
	@JsonIgnore
	@Parameter(description = "วันเวลาใช้งานล่าสุด")
	private LocalDateTime lastActiveTime;
	
}
