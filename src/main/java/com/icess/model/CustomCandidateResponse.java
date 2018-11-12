package com.icess.model;

import java.util.List;

public class CustomCandidateResponse {
	
	private String candidateId;
	private String candidateName;
	private String email;
	private String address;
	private String reg_date;
	private String contact_No1;
	private String contact_No2;
	private String candidatestatusFlag;
	private String uploadFile;
	private String uploadPath;
	private String upload_img_file;
	private String userId;
	private String userName;
	private String password;
	private String roleId;
	private String roleName;
	private List<CustomCandidateSkill> candidateskills;
	
	
	
	
	/*
	  ccr.setIsDeleted(String.valueOf(returnValue.get().getCandidateskills().get(0).getIsDeleted()));
      ccr.setCandidateskillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getCandidateskillId()));
      ccr.setSkillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getSkillId()));
      ccr.setSubskillId(String.valueOf(returnValue.get().getCandidateskills().get(0).getSubskillId()));
      ccr.setSkillName(returnValue.get().getCandidateskills().get(0).getSkillName());
      ccr.setSubSkillName(returnValue.get().getCandidateskills().get(0).getSubSkillName());*/
	
	public String getUpload_img_file() {
		return upload_img_file;
	}
	public void setUpload_img_file(String upload_img_file) {
		this.upload_img_file = upload_img_file;
	}
	public List<CustomCandidateSkill> getCandidateskills() {
		return candidateskills;
	}
	public void setCandidateskills(List<CustomCandidateSkill> candidateskills) {
		this.candidateskills = candidateskills;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getContact_No1() {
		return contact_No1;
	}
	public void setContact_No1(String contact_No1) {
		this.contact_No1 = contact_No1;
	}
	public String getContact_No2() {
		return contact_No2;
	}
	public void setContact_No2(String contact_No2) {
		this.contact_No2 = contact_No2;
	}
	public String getCandidatestatusFlag() {
		return candidatestatusFlag;
	}
	public void setCandidatestatusFlag(String candidatestatusFlag) {
		this.candidatestatusFlag = candidatestatusFlag;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	

}
