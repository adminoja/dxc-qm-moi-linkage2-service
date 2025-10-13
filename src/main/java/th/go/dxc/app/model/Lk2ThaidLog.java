package th.go.dxc.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lk2ThaidLog {
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
