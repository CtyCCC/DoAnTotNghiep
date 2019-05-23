package com.candidate.form;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.entity.Candidate;

public class FormEditProfile extends Candidate{
	
	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public FormEditProfile(String idCan, String nameCan, String cmnd, String email, String phone, boolean gender,
			String dob, String linkCV, String namePos, String dateImport, String workExp, String avatar,
			Map<String, Object> rate, String status, Map<String, Object> interview, Map<String, Object> offer,
			Map<String, Object> probation, MultipartFile file) {
		super(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport, workExp, avatar, rate,
				status, interview, offer, probation);
		this.file = file;
	}

	public FormEditProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FormEditProfile(String idCan, String nameCan, String cmnd, String email, String phone, boolean gender,
			String dob, String linkCV, String namePos, String dateImport, String workExp, String avatar,
			Map<String, Object> rate, String status, Map<String, Object> interview, Map<String, Object> offer,
			Map<String, Object> probation) {
		super(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport, workExp, avatar, rate, status,
				interview, offer, probation);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
