package th.go.dxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DxcQmMoiLinkage2ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DxcQmMoiLinkage2ServiceApplication.class, args);
	}

}
