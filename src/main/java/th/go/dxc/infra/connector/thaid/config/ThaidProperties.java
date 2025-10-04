package th.go.dxc.infra.connector.thaid.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "infra.connector.thaid")
public class ThaidProperties {
	private String baseUrl;
	private String authorization;
	private String authorizationCode;
	private String redirectUri;
	private boolean enableWiretap; // เปิดใช้งานการดักฟัง
}
