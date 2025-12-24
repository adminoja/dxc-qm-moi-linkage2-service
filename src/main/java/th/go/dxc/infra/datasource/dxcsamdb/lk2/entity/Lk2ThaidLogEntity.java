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
@Table(name = Lk2ThaidLogEntity.ENTITY_TABLE_NAME)
public class Lk2ThaidLogEntity {
	public static final String ENTITY_TABLE_NAME = "LK2_THAID_LOG";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	@Column(name = "access_token")
	private String accessToken;
	private String pid;
	private String firstname;
	private String lastname;
	private Boolean status;
	@Column(name = "expires_in")
	private LocalDateTime expiresIn;
	@Column(name = "refresh_token")
	private String refreshToken;
	@Column(name = "issued_at")
	private LocalDateTime issuedAt;
	private String type;
	@Column(name = "session_state_keycloak")
	private String sessionStateKeycloak;
	
//	@CreatedDate
//	@Column(name = "login_datetime", nullable = false, updatable = false)
	@Column(name = "login_datetime")
	private LocalDateTime loginDatetime;
}
