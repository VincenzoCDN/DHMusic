package com.dhmusic.DHMusic.Controllers;

import com.dhmusic.DHMusic.Entities.content.Song;
import com.dhmusic.DHMusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private SongService songService;

    /*@GetMapping("/")
    public String musicPlay(@RequestParam(required = false) String nameSong){
        if(nameSong == null){

            return "avvio canzone casuale";
        }

        return songService.song(nameSong);
    }

     */

    @GetMapping("/")
    public Song musicPlay(@RequestParam(required = false) Song title){

        return title;
    }



    @GetMapping("/skip")
    public String skip(){
        return "vado alla canzone successiva";
    }

    @GetMapping("/stop")
    public String stop(){
        return "ho stoppato la canzone";
    }

    @GetMapping("back")
    public String back(){
        return "vado alla canzone precedente";
    }

}
