package th.go.dxc.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lk2TokenServiceFilter {
	private Integer id;
	private String username;
	private String token;
	@JsonIgnore
	private LocalDateTime insertTime;
	@JsonIgnore
	private LocalDateTime expireTime;
	private String channel;
	private String sessionState;
	@JsonIgnore
	private LocalDateTime lastActiveTime;
	
}
