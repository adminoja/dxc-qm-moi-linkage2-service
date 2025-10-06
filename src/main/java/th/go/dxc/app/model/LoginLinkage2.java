package th.go.dxc.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLinkage2 {
	private List<Office> office;
	
	@Data
	public static class Office {
		private Long id;
		private String name;
	}
}
