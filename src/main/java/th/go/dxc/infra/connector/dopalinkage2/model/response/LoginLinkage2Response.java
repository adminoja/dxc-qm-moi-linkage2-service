package th.go.dxc.infra.connector.dopalinkage2.model.response;

import java.util.List;

//import org.coj.services.dopalinkage2.service.DopaLinkage2ServiceImpl.Linkage2ErrorDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLinkage2Response {
	private List<Office> office;

	@Data
	public static class Office {
		private Long id;
		private String name;
	}

}
