package th.go.dxc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลสิทธิประกันสุขภาพและการลงทะเบียนกับหน่วยบริการ")
public class MophNhsoHealthInsuranceRight {

	@Schema(description = "รหัสสิทธิหลักในการรับบริการ", example = "UCS")
	private String mainInscl;
	@Schema(description = "ชื่อสิทธิหลักในการรับบริการ", example = "สิทธิหลัก UCS")
	private String mainInsclName;
	@Schema(description = "เลขประจำตัวประชาชน", example = "1101700234567")
	private String personId;
	@Schema(description = "รหัสประเภทสิทธิย่อย", example = "01")
	private String subInscl;
	@Schema(description = "ชื่อประเภทสิทธิย่อย", example = "บัตรทอง")
	private String subInsclName;
	@Schema(description = "หมายเลขอ้างอิงบริการตรวจสอบข้อมูล", example = "WS123456")
	private String wsid;
	@Schema(description = "วันที่ร้องขอบริการตรวจสอบข้อมูล", example = "21")
	private Integer wsDatetimeDay;
	@Schema(description = "ชั่วโมงที่ร้องขอบริการตรวจสอบข้อมูล", example = "9")
	private Integer wsDatetimeHour;
	@Schema(description = "นาทีที่ร้องขอบริการตรวจสอบข้อมูล", example = "30")
	private Integer wsDatetimeMinute;
	@Schema(description = "เดือนที่ร้องขอบริการตรวจสอบข้อมูล", example = "10")
	private Integer wsDatetimeMonth;
	@Schema(description = "วินาทีที่ร้องขอบริการตรวจสอบข้อมูล", example = "0")
	private Integer wsDatetimeSecond;
	@Schema(description = "timezone ของการร้องขอบริการ", example = "7")
	private Integer wsDatetimeTimezone;
	@Schema(description = "ปีที่ร้องขอบริการตรวจสอบข้อมูล", example = "2025")
	private Integer wsDatetimeYear;
	@Schema(description = "สถานะการตรวจสอบข้อมูล", example = "SUCCESS")
	private String wsStatus;
	@Schema(description = "วันเริ่มใช้สิทธิ", example = "2025-01-01")
	private String startDate;
	@Schema(description = "ชื่อจังหวัดที่ลงทะเบียน", example = "กรุงเทพมหานคร")
	private String purchaseProvinceName;
	@Schema(description = "รหัสจังหวัดที่ลงทะเบียน", example = "10")
	private String purchaseProvince;
////	@Schema(description = "รูปแบบการให้บริการเพื่อรับการจัดสรรเงิน", example = "OPD")
////	private String paidModel;
////	@Schema(description = "รายละเอียดของประเภทการลงทะเบียนใหม่", example = "ลงทะเบียนใหม่")
////	private String newTypeRegisterDesc;
////	@Schema(description = "ประเภทการลงทะเบียนใหม่", example = "01")
////	private String newTypeRegister;
////	@Schema(description = "ชื่อประเภทสิทธิย่อยใหม่", example = "บัตรทอง")
////	private String newSubInsclName;
////	@Schema(description = "รหัสประเภทสิทธิย่อยใหม่", example = "01")
////	private String newSubInscl;
////	@Schema(description = "วันเริ่มใช้สิทธิใหม่", example = "2025-02-01")
////	private String newStartDate;
////	@Schema(description = "ชื่อเจ้าหน้าที่ประจำหน่วยทะเบียนใหม่", example = "นายสมชาย")
////	private String newStaffName;
////	@Schema(description = "ชื่อจังหวัดที่ขอเปลี่ยนหน่วยบริการใหม่", example = "กรุงเทพมหานคร")
////	private String newPurchaseProvinceName;
////	@Schema(description = "รหัสจังหวัดที่ขอเปลี่ยนหน่วยบริการใหม่", example = "10")
////	private String newPurchaseProvince;
////	@Schema(description = "รูปแบบการให้บริการเพื่อรับการจัดสรรเงินใหม่", example = "OPD")
////	private String newPaidModel;
////	@Schema(description = "รหัสเครือข่ายหน่วยบริการที่ลงทะเบียนใหม่", example = "MC123")
////	private String newMastercupId;
////	@Schema(description = "ชื่อสิทธิหลักในการรับบริการใหม่", example = "UCS")
////	private String newMainInsclName;
////	@Schema(description = "รหัสสิทธิหลักในการรับบริการใหม่", example = "UCS")
////	private String newMainInscl;
////	@Schema(description = "ชื่อหน่วยบริการปฐมภูมิใหม่", example = "รพ.สต.บางนา")
////	private String newHsubName;
////	@Schema(description = "รหัสหน่วยบริการปฐมภูมิใหม่", example = "HSUB01")
////	private String newHsub;
////	@Schema(description = "ชื่อหน่วยบริการประจำใหม่", example = "รพ.บางนา")
////	private String newHmainOpName;
////	@Schema(description = "รหัสหน่วยบริการประจำใหม่", example = "HMAINOP01")
////	private String newHmainOp;
////	@Schema(description = "ชื่อหน่วยบริการที่รับการส่งต่อใหม่", example = "รพ.ใหญ่")
////	private String newHmainName;
////	@Schema(description = "รหัสหน่วยบริการที่รับการส่งต่อใหม่", example = "HMAIN01")
////	private String newHmain;
////	@Schema(description = "วันหมดสิทธิใหม่", example = "2026-01-01")
////	private String newExpDate;
////	@Schema(description = "ปีที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "2025")
////	private Integer newDateRegisterYear;
////	@Schema(description = "เดือนที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "10")
////	private Integer newDateRegisterMonth;
////	@Schema(description = "วันที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "21")
////	private Integer newDateRegisterDay;
////	@Schema(description = "ชั่วโมงที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "9")
////	private Integer newDateRegisterHour;
////	@Schema(description = "นาทีที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "30")
////	private Integer newDateRegisterMinute;
////	@Schema(description = "วินาทีที่บันทึกข้อมูลการลงทะเบียนใหม่", example = "0")
////	private Integer newDateRegisterSecond;
////	@Schema(description = "timezone ของการบันทึกข้อมูลการลงทะเบียนใหม่", example = "7")
////	private Integer newDateRegisterTimezone;
////	@Schema(description = "รหัสเครือข่ายหน่วยบริการที่ลงทะเบียน", example = "MC001")
////	private String mastercupId;
////	@Schema(description = "ชื่อหน่วยบริการปฐมภูมิ", example = "รพ.สต.บางนา")
////	private String hsubName;
////	@Schema(description = "รหัสหน่วยบริการปฐมภูมิ", example = "HSUB01")
////	private String hsub;
////	@Schema(description = "ชื่อหน่วยบริการประจำ", example = "รพ.บางนา")
////	private String hmainOpName;
////	@Schema(description = "รหัสหน่วยบริการประจำ", example = "HMAINOP01")
////	private String hmainOp;
	@Schema(description = "ชื่อหน่วยบริการที่รับการส่งต่อ", example = "รพ.ใหญ่")
	private String hmainName;
	@Schema(description = "รหัสหน่วยบริการที่รับการส่งต่อ", example = "HMAIN01")
	private String hmain;
//	@Schema(description = "วันหมดสิทธิ", example = "2025-12-31")
////	private String expDate;
	@Schema(description = "รหัสบัตรประกันสุขภาพ", example = "1234567890123")
	private String cardId;
	
	@JsonIgnore
	@Schema(description = "คำอธิบายสถานะการตรวจสอบข้อมูล", example = "ไม่พบข้อมูลที่ค้นหา")
	private String wsStatusDesc;
	
//	private String mainInscl;
//	private String mainInsclName;
//	private String personId;
//	private String subInscl;
//	private String subInsclName;
//	private String wsid;
//	private String wsStatus;
//	private String startDate;
//	private String purchaseProvince;
//	private String purchaseProvinceName;
//	private String hmain;
//	private String hmainName;
//	private String cardId;

	// WsDatetime fields
//	private Integer wsDatetimeDay;
//	private Integer wsDatetimeHour;
//	private Integer wsDatetimeMinute;
//	private Integer wsDatetimeMonth;
//	private Integer wsDatetimeSecond;
//	private Integer wsDatetimeTimezone;
//	private Integer wsDatetimeYear;
}
