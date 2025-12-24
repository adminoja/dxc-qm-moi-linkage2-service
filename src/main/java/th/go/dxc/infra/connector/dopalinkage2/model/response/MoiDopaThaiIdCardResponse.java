package th.go.dxc.infra.connector.dopalinkage2.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoiDopaThaiIdCardResponse {

  @Schema(description = "เลขคำขอมีบัตร (บป.1)", example = "12345678901")
  private String documentNumber;

  @Schema(description = "เลขบัตรประจำตัวประชาชน", example = "1234567890123")
  private Long personalID;

  @Schema(description = "หมู่โลหิต", example = "B")
  private String blood;

  @Schema(description = "วันเดือนปี เกิด (พ.ศ. YYYYMMDD)", example = "25230605")
  private Integer birthDate;

  @Schema(description = "ศาสนา", example = "พุทธ")
  private String religion;

  @Schema(description = "ศาสนา(อื่นๆ)", example = "")
  private String religionOther;

  @Schema(description = "เพศ", example = "ชาย")
  private String sex;

  @Schema(description = "สาเหตุการยกเลิกบัตร", example = "")
  private String cancelCause;

  @Schema(description = "วันเดือนปี ที่ออกบัตร", example = "25630703")
  private Integer issueDate;

  @Schema(description = "เวลา ที่ออกบัตร", example = "9280455")
  private Integer issueTime;

  @Schema(description = "ประเทศ (กรณีอยู่ต่างประเทศ)", example = "")
  private String foreignCountry;

  @Schema(description = "เมือง (กรณีอยู่ต่างประเทศ)", example = "")
  private String foreignCountryCity;

  @Schema(description = "วันเดือนปี บัตรหมดอายุ (พ.ศ. YYYYMMDD)", example = "25630703")
  private String expireDate;

  @Schema(description = "เบอร์โทรศัพท์", example = "0812345698")
  private String phoneNumber;

  private Document document;
  private NameEN nameEN;
  private NameTH nameTH;
  private Address address;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Document {

    @Schema(description = "ประเทศ", example = "ประเทศไทย")
    private String countryDesc;

    @Schema(description = "อำเภอ", example = "อำเภอปากเกร็ด")
    private String districtDesc;

    @Schema(description = "จังหวัด", example = "จังหวัดนนทบุรี")
    private String provinceDesc;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class NameEN {

    @Schema(description = "ชื่อตัว (ภาษาอังกฤษ)", example = "test")
    private String firstName;

    @Schema(description = "ชื่อสกุล (ภาษาอังกฤษ)", example = "test")
    private String lastName;

    @Schema(description = "ชื่อกลาง (ภาษาอังกฤษ)", example = "")
    private String middleName;

    @Schema(description = "คำนำหน้านาม (ภาษาอังกฤษ)", example = "Mr.")
    private String title;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class NameTH {

    @Schema(description = "ชื่อตัว-ชื่อสกุล (ภาษาไทย)", example = "ทดสอบ")
    private String fullName;

    @Schema(description = "ชื่อตัว (ภาษาไทย)", example = "ทดสอบ")
    private String firstName;

    @Schema(description = "ชื่อสกุล (ภาษาไทย)", example = "ทดสอบ")
    private String lastName;

    @Schema(description = "ชื่อกลาง (ภาษาไทย)", example = "")
    private String middleName;

    @Schema(description = "คำนำหน้านาม (ภาษาไทย)", example = "นาย")
    private String title;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Address {

    @Schema(description = "ซอย (ณ วันทําบัตร)", example = "")
    private String alleyDesc;

    @Schema(description = "ตรอก (ณ วันทําบัตร)", example = "")
    private String alleyWayDesc;

    @Schema(description = "อําเภอ (ณ วันทําบัตร)", example = "ทดสอบ")
    private String districtDesc;

    @Schema(description = "บ้านเลขที่ (ณ วันทําบัตร)", example = "11/11")
    private String houseNo;

    @Schema(description = "จังหวัด (ณ วันทําบัตร)", example = "จังหวัดทดสอบ")
    private String provinceDesc;

    @Schema(description = "ถนน (ณ วันทําบัตร)", example = "ถนนทดสอบ")
    private String roadDesc;

    @Schema(description = "ตําบล (ณ วันทําบัตร)", example = "ตำบลทดสอบ")
    private String subdistrictDesc;

    @Schema(description = "หมู่ที่ (ณ วันทําบัตร)", example = "1")
    private String villageNo;
  }
}
