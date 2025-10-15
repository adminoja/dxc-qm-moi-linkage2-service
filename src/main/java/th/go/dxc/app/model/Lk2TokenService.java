package th.go.dxc.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lk2TokenService {
	private Integer id;
	private String username;
	private String token;
	private LocalDateTime insertTime;
	private LocalDateTime expireTime;
	private String channel;
	private String sessionState;
	private LocalDateTime lastActiveTime;
	
}
