package th.go.dxc.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ฐานข้อมูลใบอนุญาตป.4")
public class MoiDopaPor4License extends BaseMoiLinkage2 {

	@Schema(description = "อำเภอ ตามที่อยู่", example = "")
	private String amphorDesc;
	@Schema(description = "อำเภอ ตามที่อยู่ คนที่ 2", example = "")
	private String amphorDesc2;
	@Schema(description = "ประเภทผู้ยื่นคำร้อง (1 : นิติบุคคล, 2 : บุคคลธรรมดา)", example = "")
	private Integer applicantType;
	@Schema(description = "ชื่อนิติบุคคล", example = "")
	private String businessName;
	@Schema(description = "ประเภทนิติบุคคล", example = "")
	private String businessType;
	@Schema(description = "ตำบล ตามที่อยู่", example = "")
	private String districtDesc;
	@Schema(description = "ตำบล ตามที่อยู่ คนที่ 2", example = "")
	private String districtDesc2;
	@Schema(description = "ปี เดือน วันที่ออกใบอนุญาต", example = "")
	private Integer docDate;
	@Schema(description = "เลขที่ใบอนุญาต (เช่น 1/2551:255100000001)", example = "")
	private String docID;
	@Schema(description = "รหัสสำนักทะเบียนที่ออกใบอนุญาต", example = "")
	private String docPlace;
	@Schema(description = "สำนักทะเบียนที่ออกใบอนุญาต / ใบแทน", example = "")
	private String docPlaceDesc;
	@Schema(description = "จังหวัดที่ออกใบอนุญาต / ใบแทน", example = "")
	private String docPlaceProvince;
	@Schema(description = "ปี เดือน วันที่ใบอนุญาตสิ้นอายุ", example = "")
	private Integer expireDate;
	@Schema(description = "ชื่อตัว", example = "")
	private String firstName;
	@Schema(description = "ชื่อตัว คนที่ 2", example = "")
	private String firstName2;
	@Schema(description = "ชื่อเต็มผู้ขอใบอนุญาต", example = "")
	private String fullNameAndRank;
	@Schema(description = "ชื่อเต็มผู้รับใบอนุญาต คนที่ 2", example = "")
	private String fullNameAndRank2;
	@Schema(description = "รหัสเพศ", example = "")
	private Integer genderCode;
	@Schema(description = "รหัสเพศ ผู้ได้รับอนุญาต คนที่ 2", example = "")
	private Integer genderCode2;
	@Schema(description = "คำอธิบายเพศผู้ขอใบอนุญาต", example = "")
	private String genderDesc;
	@Schema(description = "คำอธิบายเพศผู้รับใบอนุญาต คนที่ 2", example = "")
	private String genderDesc2;
	@Schema(description = "ชนิดอาวุธปืน", example = "")
	private String gunCharacteristic;
	@Schema(description = "ผู้ผลิตอาวุธปืน", example = "")
	private String gunProduct;
	@Schema(description = "เครื่องหมายทะเบียนปืน", example = "")
	private String gunRegistrationId;
	@Schema(description = "เลขหมายประจำปืน", example = "")
	private String gunSerialNo;
	@Schema(description = "ขนาดอาวุธปืน", example = "")
	private String gunSize;
	@Schema(description = "ประเภทอาวุธปืน", example = "")
	private String gunType;
	@Schema(description = "เลขรหัสประจำบ้านผู้รับใบอนุญาต", example = "")
	private Long hid;
	@Schema(description = "เลขรหัสประจำบ้านผู้รับใบอนุญาต คนที่ 2", example = "")
	private Long hid2;
	@Schema(description = "รหัสสำนักทะเบียน ตามที่อยู่", example = "")
	private Long hidRcodeCode;
	@Schema(description = "รหัสสำนักทะเบียน ตามที่อยู่ ผู้ได้รับอนุญาต คนที่ 2", example = "")
	private Long hidRcodeCode2;
	@Schema(description = "ชื่อสำนักทะเบียน ตามที่อยู่", example = "")
	private String hidRcodeDesc;
	@Schema(description = "ชื่อสำนักทะเบียน ตามที่อยู่ คนที่ 2", example = "")
	private String hidRcodeDesc2;
	@Schema(description = "บ้านเลขที่ ตามที่อยู่", example = "")
	private String hno;
	@Schema(description = "บ้านเลขที่ ตามที่อยู่ คนที่ 2", example = "")
	private String hno2;
	@Schema(description = "ชื่อสกุล", example = "")
	private String lastName;
	@Schema(description = "ชื่อสกุล คนที่ 2", example = "")
	private String lastName2;
	@Schema(description = "ชื่อกลาง", example = "")
	private String middleName;
	@Schema(description = "ชื่อกลาง คนที่ 2", example = "")
	private String middleName2;
	@Schema(description = "เลขประจำตัวประชาชนผู้รับใบอนุญาต / ตัวแทนนิติบุคคล คนที่ 1", example = "")
	private Long personalId;
	@Schema(description = "เลขประจำตัวประชาชนตัวแทนนิติบุคคล คนที่ 2 (กรณีเป็นนิติบุคคล)", example = "")
	private Long personalId2;
	@Schema(description = "จังหวัด ตามที่อยู่", example = "")
	private String provinceDesc;
	@Schema(description = "จังหวัด ตามที่อยู่ คนที่ 2", example = "")
	private String provinceDesc2;
	@Schema(description = "ชื่อนามสกุลผู้ลงนามใบอนุญาต", example = "")
	private String signFullName;
	@Schema(description = "คำนำหน้านามผู้ลงนามใบอนุญาต", example = "")
	private String signTitleDesc;
	@Schema(description = "ซอย ตามที่อยู่", example = "")
	private String soi;
	@Schema(description = "ซอย ตามที่อยู่ คนที่ 2", example = "")
	private String soi2;
	@Schema(description = "ถนน ตามที่อยู่", example = "")
	private String thanon;
	@Schema(description = "ถนน ตามที่อยู่ คนที่ 2", example = "")
	private String thanon2;
	@Schema(description = "รหัสคำนำหน้านาม", example = "")
	private Integer titleCode;
	@Schema(description = "รหัสคำนำหน้านาม ผู้ได้รับอนุญาต คนที่ 2", example = "")
	private Integer titleCode2;
	@Schema(description = "รายละเอียดคำนำหน้านาม", example = "")
	private String titleDesc;
	@Schema(description = "รายละเอียดคำนำหน้านาม คนที่ 2", example = "")
	private String titleDesc2;
	@Schema(description = "ตรอก ตามที่อยู่", example = "")
	private String trok;
	@Schema(description = "ตรอก ตามที่อยู่ คนที่ 2", example = "")
	private String trok2;
	@Schema(description = "วันที่และเวลาที่ขอข้อมูล (timestamp)", example = "")
	private Long processTimestamp;
	@Schema(description = "หมายเหตุ", example = "")
	private String remark;
	@Schema(description = "จำนวนรายการ", example = "")
	private Integer total;

}
