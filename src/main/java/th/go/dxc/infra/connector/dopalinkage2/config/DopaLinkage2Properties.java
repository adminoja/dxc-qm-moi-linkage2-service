package th.go.dxc.infra.connector.dopalinkage2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "infra.connector.dopalinkage2")
public class DopaLinkage2Properties {
	private String baseUrl;
	private boolean enableWiretap; // เปิดใช้งานการดักฟัง
}
