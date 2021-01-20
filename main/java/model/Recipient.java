package model;

import java.io.Serializable;

public class Recipient implements Serializable {

    private static final long serialVersionUID = 10L;
    private String recipientName;
    private String recipientSurname;
    private String recipientCellNo;
    private String recipientEmail;
    private String recipientMessage;
    private int nameId;

    public Recipient(String name, String surname, String cellNo, String email, String message, int id){

        recipientName = name;
        recipientSurname = surname;
        recipientCellNo = cellNo;
        recipientEmail = email;
        recipientMessage = message;
        nameId = id;
    }

    public Recipient(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }

    public String getRecipientCellNo() {
        return recipientCellNo;
    }

    public void setRecipientCellNo(String recipientCellNo) {
        this.recipientCellNo = recipientCellNo;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientMessage() {
        return recipientMessage;
    }

    public void setRecipientMessage(String recipientMessage) {
        this.recipientMessage = recipientMessage;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }
}
