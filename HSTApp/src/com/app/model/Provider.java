package com.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Provider implements Serializable{

	private String Doctor_Id;
	private String Medical_GroupName;
	private String FirstName;
	private String LastName;
	private String Address1;
	private String Address2;
	private String City;
	private String State;
	private String Zip;
	private String Phone;
	private String Hours_Open;
	private double Latitude;
	private double Longitude;
	private String Specialization;
	private String CategoryId;
	private String ProviderId;
	private String Gender;
	private String Languages;
	

	public String getDoctor_Id() {
		return Doctor_Id;
	}

	public void setDoctor_Id(String doctor_Id) {
		Doctor_Id = doctor_Id;
	}

	public String getMedical_GroupName() {
		return Medical_GroupName;
	}

	public void setMedical_GroupName(String medical_GroupName) {
		Medical_GroupName = medical_GroupName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getHours_Open() {
		return Hours_Open;
	}

	public void setHours_Open(String hours_Open) {
		Hours_Open = hours_Open;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public String getSpecialization() {
		return Specialization;
	}

	public void setSpecialization(String specialization) {
		Specialization = specialization;
	}

	public String getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}

	public String getProviderId() {
		return ProviderId;
	}

	public void setProviderId(String providerId) {
		ProviderId = providerId;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getLanguages() {
		return Languages;
	}

	public void setLanguages(String languages) {
		Languages = languages;
	}
	
	
}
