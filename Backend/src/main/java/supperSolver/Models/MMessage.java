package supperSolver.Models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "tbl_message")
@Data
public class MMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();

    @Column
    private String destUser;

    @Column
    private int destGroupID;

    public MMessage() {};

    public MMessage(String userName, String content, String destUser, int destGroupID) {
        this.userName = userName;
        this.content = content;
        this.destUser = destUser;
        this.destGroupID = destGroupID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public void setDestUser(String destUser) {this.destUser = destUser;}

    public String getDestUser(){return this.destUser;}

    public void setDestGroupID(int destGroupID){this.destGroupID = destGroupID;}

    public int getDestGroupID(){return this.destGroupID;}


}