package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaAddressResponse {
	
	private Integer alleyCode;
	private String alleyDesc;
	private String alleyEnglishDesc;
	private Integer alleyWayCode;
	private String alleyWayDesc;
	private String alleyWayEnglishDesc;
	private Integer dateOfTerminate;
	private Integer districtCode;
	private String districtDesc;
	private String districtEnglishDesc;
	private Long houseID;
	private String houseNo;
	private Integer houseType;
	private String houseTypeDesc;
	private Integer provinceCode;
	private String provinceDesc;
	private String provinceEnglishDesc;
	private String rcodeCode;
	private String rcodeDesc;
	private Integer roadCode;
	private String roadDesc;
	private String roadEnglishDesc;
	private Integer subdistrictCode;
	private String subdistrictDesc;
	private String subdistrictEnglishDesc;
	private Integer villageNo;
}
