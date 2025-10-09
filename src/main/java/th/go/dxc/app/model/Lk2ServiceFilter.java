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
public class Lk2ServiceFilter {
	@Parameter(description = "รหัสข้อมูล")
	private Integer id;
	@Parameter(description = "ชื่อหน่วยงาน")
	private String department;
	@Parameter(description = "ชื่อฐานข้อมูล")
	private String serviceName;
	@Parameter(description = "ชื่อฐานข้อมูล DXC")
	private String serviceNameUnderDXC;
	@Parameter(description = "ชื่อ Job")
	private String jobName;
	@Parameter(description = "รหัสฐานข้อมูล")
	private String serviceId;
	@Schema(description = "รหัสหน่วยงาน Job")
	private String departmentJob;
	@JsonIgnore
	@Parameter(description = "IP proxy")
	private String ipproxy;
	@JsonIgnore
	@Parameter(description = "วันเวลาล่าสุด")
	private LocalDateTime lastUpdate;
	@Parameter(description = "รหัส Job")
	private String jobId;
}
