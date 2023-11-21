package com.capstone.datamate.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_filelog")
public class FileActivityLogEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @ManyToOne
    @JoinColumn(name = "fileId")
	@OnDelete(action = OnDeleteAction.CASCADE)
    private FileEntity file;

    @ManyToOne
    @JoinColumn(name = "userId")
	@OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column
    private String activity;

    @Column
    private LocalDateTime timestamp;
    
    
    public FileActivityLogEntity() {
    }

    public FileActivityLogEntity(FileEntity file, UserEntity user, String activity) {
        this.file = file;
        this.user = user;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public FileEntity getFile() {
		return file;
	}

	public void setFile(FileEntity file) {
		this.file = file;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    

}
