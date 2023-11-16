package com.capstone.datamate.Entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="tbl_user", uniqueConstraints = {@UniqueConstraint(columnNames = { "username" })})
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	
	@Column(unique=true, length = 50)
	private String username;
	
	private String password;
	private String businessName;
	private String businessType;
	
	@Column(columnDefinition = "BYTEA")
	private byte[] userImage;


	private String verificationCode;
	
	@Column(name = "verification_code_expiration")
	private Instant verificationCodeExpiration;
	
	public UserEntity() {}

	public UserEntity(int userId, String firstName, String lastName, String email, String address, String username,
			String password, String businessName, String businessType, byte[] userImage, String vertificationcode, Instant verificationCodeExpiration) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.username = username;
		this.password = password;
		this.businessName = businessName;
		this.businessType = businessType;
		this.userImage = userImage;
		this.verificationCode = vertificationcode;
		this.verificationCodeExpiration = verificationCodeExpiration;
	}

	public int getUserId() {
		return userId;
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public byte[] getUserImage() {
		return userImage;
	}

	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}
	
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    
    public Instant getVerificationCodeExpiration() {
        return verificationCodeExpiration;
    }

    public void setVerificationCodeExpiration(Instant verificationCodeExpiration) {
        this.verificationCodeExpiration = verificationCodeExpiration;
    }


}
