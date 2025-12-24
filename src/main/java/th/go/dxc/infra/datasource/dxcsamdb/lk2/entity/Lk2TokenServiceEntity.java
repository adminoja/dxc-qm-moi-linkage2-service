package th.go.dxc.infra.datasource.dxcsamdb.lk2.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Lk2TokenServiceEntity.ENTITY_TABLE_NAME)
public class Lk2TokenServiceEntity {
	
	public static final String ENTITY_TABLE_NAME = "LK2_TOKEN_SERVICE";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String token;
	private LocalDateTime insertTime;
	@Column(name = "expireTime", insertable = false, updatable = false)
	private LocalDateTime expireTime;
	private String channel;
	private String sessionState;
	private LocalDateTime lastActiveTime;
	
}
