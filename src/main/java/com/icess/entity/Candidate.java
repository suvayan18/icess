package com.icess.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="icess_candidate")
public class Candidate {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "candidateid", unique = true, nullable = false)
	private int candidateId;
	
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "candidatename")
	private String candidateName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "regdate")
	private Date reg_date;
	
	@Column(name = "contactno1")
	private String contact_No1;
	
	@Column(name = "contactno2")
	private String contact_No2;
	
	@Column(name = "upload_file")
	private String uploadFile;
	
	@Column(name = "upload_path")
	private String uploadPath;
	
	@Column(name = "upload_img_file")
	private String upload_img_file;
	
	
	@Column(name = "candidate_status_flag")
	private Boolean candidatestatusFlag;
	
	
	@OneToMany(
	        mappedBy = "candidate", 
	        cascade = CascadeType.ALL)
	    private List<CandidateSkill> candidateskills = new ArrayList<>();


	
	
	public String getUpload_img_file() {
		return upload_img_file;
	}

	public void setUpload_img_file(String upload_img_file) {
		this.upload_img_file = upload_img_file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
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

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
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

	public List<CandidateSkill> getCandidateskills() {
		return candidateskills;
	}

	public void setCandidateskills(List<CandidateSkill> candidateskills) {
		this.candidateskills = candidateskills;
	}

	public Boolean getCandidatestatusFlag() {
		return candidatestatusFlag;
	}

	public void setCandidatestatusFlag(Boolean candidatestatusFlag) {
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
	
	
	
	
	
}
