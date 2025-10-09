package th.go.dxc.infra.datasource.dxcsamdb.lk2.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Lk2ServiceEntityFilter {
	private Integer id;
	private String department;
	private String serviceName;
	private String serviceNameUnderDXC;
	private String jobName;
	private String serviceId;
	private String departmentJob;
	private String ipproxy;
	private LocalDateTime lastUpdate;
	private String jobId;
}
