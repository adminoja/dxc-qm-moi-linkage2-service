package th.go.dxc.infra.datasource.dxcsamdb.lk2.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Lk2ThaidLogEntityFilter {
	private Integer id;
	private String username;
	private String accessToken;
	private String pid;
	private String firstname;
	private String lastname;
	private Boolean status;
	private LocalDateTime expiresIn;
	private String refreshToken;
	private LocalDateTime issuedAt;
	private String type;
	private String sessionStateKeycloak;
	private LocalDateTime loginDatetime;
}
