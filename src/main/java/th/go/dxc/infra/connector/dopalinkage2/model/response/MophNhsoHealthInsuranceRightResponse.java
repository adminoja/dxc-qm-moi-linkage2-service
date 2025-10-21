package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MophNhsoHealthInsuranceRightResponse {

	@JsonProperty("MAININSCL")
	private String mainInscl;
	@JsonProperty("MAININSCL_NAME")
	private String mainInsclName;
	@JsonProperty("PERSON_ID")
	private String personId;
	@JsonProperty("SUBINSCL")
	private String subInscl;
	@JsonProperty("SUBINSCL_NAME")
	private String subInsclName;
	@JsonProperty("WSID")
	private String wsid;
	@JsonProperty("WS_DATETIME")
	private WsDatetime wsDatetime;
	@JsonProperty("WS_STATUS")
	private String wsStatus;
	@JsonProperty("STARTDATE")
	private String startDate;
	@JsonProperty("PURCHASEPROVINCE_NAME")
	private String purchaseProvinceName;
	@JsonProperty("PURCHASEPROVINCE")
	private String purchaseProvince;
//	@JsonProperty("PAID_MODEL")
//	private String paidModel;
//	@JsonProperty("NEW_TYPE_REGISTER_DESC")
//	private String newTypeRegisterDesc;
//	@JsonProperty("NEW_TYPE_REGISTER")
//	private String newTypeRegister;
//	@JsonProperty("NEW_SUBINSCL_NAME")
//	private String newSubInsclName;
//	@JsonProperty("NEW_SUBINSCL")
//	private String newSubInscl;
//	@JsonProperty("NEW_STARTDATE")
//	private String newStartDate;
//	@JsonProperty("NEW_STAFFNAME")
//	private String newStaffName;
//	@JsonProperty("NEW_PURCHASEPROVINCE_NAME")
//	private String newPurchaseProvinceName;
//	@JsonProperty("NEW_PURCHASEPROVINCE")
//	private String newPurchaseProvince;
//	@JsonProperty("NEW_PAID_MODEL")
//	private String newPaidModel;
//	@JsonProperty("NEW_MASTERCUP_ID")
//	private String newMastercupId;
//	@JsonProperty("NEW_MAININSCL_NAME")
//	private String newMainInsclName;
//	@JsonProperty("NEW_MAININSCL")
//	private String newMainInscl;
//	@JsonProperty("NEW_HSUB_NAME")
//	private String newHsubName;
//	@JsonProperty("NEW_HSUB")
//	private String newHsub;
//	@JsonProperty("NEW_HMAIN_OP_NAME")
//	private String newHmainOpName;
//	@JsonProperty("NEW_HMAIN_OP")
//	private String newHmainOp;
//	@JsonProperty("NEW_HMAIN_NAME")
//	private String newHmainName;
//	@JsonProperty("NEW_HMAIN")
//	private String newHmain;
//	@JsonProperty("NEW_EXPDATE")
//	private String newExpDate;
//	@JsonProperty("NEW_DATE_REGISTER")
//	private NewDateRegister newDateRegister;
//	@JsonProperty("MASTERCUP_ID")
//	private String mastercupId;
//	@JsonProperty("HSUB_NAME")
//	private String hsubName;
//	@JsonProperty("HSUB")
//	private String hsub;
//	@JsonProperty("HMAIN_OP_NAME")
//	private String hmainOpName;
//	@JsonProperty("HMAIN_OP")
//	private String hmainOp;
	@JsonProperty("HMAIN_NAME")
	private String hmainName;
	@JsonProperty("HMAIN")
	private String hmain;
//	@JsonProperty("EXPDATE")
//	private String expDate;
	@JsonProperty("CARDID")
	private String cardId;

	@JsonProperty("WS_STATUS_DESC")
	private String wsStatusDesc;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class WsDatetime {
		private Integer day;
		private Integer hour;
		private Integer minute;
		private Integer month;
		private Integer second;
		private Integer timezone;
		private Integer year;
	}

//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	@JsonIgnoreProperties(ignoreUnknown = true)
//	public static class NewDateRegister {
//		private Integer day;
//		private Integer hour;
//		private Integer minute;
//		private Integer month;
//		private Integer second;
//		private Integer timezone;
//		private Integer year;
//	}
}
