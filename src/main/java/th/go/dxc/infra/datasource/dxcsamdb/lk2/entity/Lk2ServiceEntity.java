package th.go.dxc.infra.datasource.dxcsamdb.lk2.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Lk2ServiceEntity.ENTITY_TABLE_NAME)
public class Lk2ServiceEntity {
	public static final String ENTITY_TABLE_NAME = "LK2_SERVICE";

	@Id
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
