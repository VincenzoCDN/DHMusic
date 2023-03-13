package com.dhmusic.DHMusic.service;

import org.springframework.stereotype.Service;

@Service
public class SongService {


    public String song(String name){
        return "la canzone scelta è "+name;
    }


}
