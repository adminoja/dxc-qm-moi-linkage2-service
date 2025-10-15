package th.go.dxc.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lk2Service {
	private Integer id;
	private String department;
	private String serviceName;
	private String serviceNameUnderDXC;
	private String jobName;
	private String serviceId;
	private String departmentCode;
	private String ipProxy;
	private LocalDateTime lastUpdate;
	private String jobId;
	private String officeId;
}
