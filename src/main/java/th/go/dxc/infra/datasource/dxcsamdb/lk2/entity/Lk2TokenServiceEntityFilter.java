package th.go.dxc.infra.datasource.dxcsamdb.lk2.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Lk2TokenServiceEntityFilter {
	private Integer id;
	private String username;
	private String token;
	private LocalDateTime insertTime;
	private LocalDateTime expireTime;
	private String channel;
	private String sessionState;
	private LocalDateTime lastActiveTime;
	
}
