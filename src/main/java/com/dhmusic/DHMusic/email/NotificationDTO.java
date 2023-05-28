package com.dhmusic.DHMusic.email;


public class NotificationDTO {
    private String contactId;
    private String title;
    private String text;


    public NotificationDTO() {

    }

    public NotificationDTO(String contactId, String title, String text) {
        this.contactId = contactId;
        this.title = title;
        this.text = text;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
