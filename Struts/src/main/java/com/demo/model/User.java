package com.demo.model;

import java.util.Date;

public class User {
    private int psncd;         // Primary key
    private String userId;     // ID có thể khác psncd
    private String username;
    private String password;
    private boolean deleted;   // rename từ "delete" vì "delete" là keyword
    private Date dateUpdate;

    // Getter & Setter
    public int getPsncd() { return psncd; }
    public void setPsncd(int psncd) { this.psncd = psncd; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public Date getDateUpdate() { return dateUpdate; }
    public void setDateUpdate(Date dateUpdate) { this.dateUpdate = dateUpdate; }
}

