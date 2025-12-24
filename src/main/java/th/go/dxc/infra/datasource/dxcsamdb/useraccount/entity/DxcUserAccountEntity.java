package th.go.dxc.infra.datasource.dxcsamdb.useraccount.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= DxcUserAccountEntity.ENTITY_TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DxcUserAccountEntity {
	
	public static final String ENTITY_TABLE_NAME = "USER_ACCOUNT";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="[officernumber]")
	private String officerNumber;
	@Column(name="[citizencardnumber]")
	private String citizenCardNumber;
	@Column(name="[departmentcode]")
	private String departmentCode;
	@Column(name="[username]")
	private String username;
	@Column(name="[password]")
	private String password;
	@Column(name="[accounttype]")
	private Integer accountType;
	@Column(name="[activationcode]")
	private String activationCode;
	@Column(name="[registerdate]")
	private Date registerDate;
	@Column(name="[lastaccessdate]")
	private Timestamp lastAccessDate;
	@Column(name="[createdate]")
	private Timestamp createDate;
	@Column(name="[isblock]")
	private String isBlock;
	@Column(name="[status]")
	private String status;
	@Column(name="[substatus]")
	private String subStatus;
	@Column(name="[exprdate]")
	private Timestamp exprDate;
	@Column(name="[email]")
	private String email;
	@Column(name="[permissions]")
	private String permissions;
	@Column(name="[countlogin]")
	private Integer countLogin;
	@Column(name="[countsearch]")
	private String countSearch;
	@Column(name="[prefix]")
	private String prefix;
	@Column(name="[firstname]")
	private String firstName;
	@Column(name="[lastname]")
	private String lastName;
	@Column(name="[address]")
	private String address;
	@Column(name="[phonenumber]")
	private String phoneNumber;
	@Column(name="[hadtoken]")
	private String hadToken;
	@Column(name="[adaccount]")
	private String adAccount;
	@Column(name="[groupid]")
	private Integer groupId;
	@Column(name="[firstnameen]")
	private String firstNameEn;
	@Column(name="[lastnameen]")
	private String lastNameEn;
	@Column(name="[position]")
	private String position;
	@Column(name="[createrid]")
	private Integer createrId;
	@Column(name="[creatername]")
	private String createrName;
	@Column(name="[apikey]")
	private String apikey;
	@Column(name="[isallowserviceapi]")
	private String isAllowServiceAPI;
	@Column(name="[apienabled]")
	private Boolean apiEnabled;
	@Column(name="[drmaccount]")
	private String drmAccount;
	@Column(name="[drmaccountid]")
	private String drmAccountID;
	@Column(name="[drmaccountstatus]")
	private String drmAccountStatus;
	@Column(name="[countloginfail]")
	private Integer countLoginFail;
	@Column(name="[deleteddatetime]")
	private LocalDateTime deletedDatetime;
	@Column(name="[deleteusername]")
	private String deleteUsername;
	@Column(name="[deleteemail]")
	private String deleteEmail;
	@Column(name="[deletecitizencardnumber]")
	private String deleteCitizenCardNumber;
	@Column(name="[deleteadminid]")
	private Integer deleteAdminId;
	@Column(name="[deleted]")
	private String deleted;
	@Column(name="[profilecompleteddatetime]")
	private Timestamp profileCompletedDateTime;
	@Column(name="[sys_update_registerdate_byscript]")
	private Timestamp sys_update_registerDate_byscript;
	@Column(name="[sys_update_lastaccessdate_byscript]")
	private Timestamp sys_update_lastAccessDate_byscript;
	@Column(name="[refid]")
	private String refId;
	@Column(name="[hideonreport]")
	private String hideOnReport;
	@Column(name="[approvalstatus]")
	private String approvalStatus;
	@Column(name="[approvalstatusby]")
	private Integer approvalStatusBy;
	@Column(name="[approvalremark]")
	private String approvalRemark;
	@Column(name="[approvaldate]")
	private Timestamp approvalDate;
	@Column(name="[province]")
	private String province; 
	@Column(name="[subordinate]")
	private String subordinate;
	@Column(name="[mainsignature]")
	private String mainsignature;
	@Column(name="[ministry]")
	private String ministry;
	@Column(name="[responsiblework]")
	private String responsiblework;
	@Column(name="[governmentofficialstype]")
	private String governmentofficialstype;
	@Column(name="[governmentnum]")
	private String governmentNum;
	@Column(name="[commanderid]")
	private String commanderId;
	@Column(name="[adminid]")
	private String adminId;
	@Column(name="[sscsubstatus]")
	private String sscSubStatus;
	
}
