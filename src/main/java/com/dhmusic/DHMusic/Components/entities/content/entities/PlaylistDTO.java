package com.dhmusic.DHMusic.Components.entities.content.entities;



public class PlaylistDTO {

    private Long id;

    private String title;

    private Long userId;


    public PlaylistDTO() {
    }

    public PlaylistDTO(Long id, String title, Long userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
