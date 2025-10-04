package th.go.dxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
})
public class DxcQmMoiLinkage2ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DxcQmMoiLinkage2ServiceApplication.class, args);
	}

}
