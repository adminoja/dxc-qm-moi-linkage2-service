package th.go.dxc.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPersons {
	private String userNin;
	private String thaiNin;
	private String serviceId;
}
