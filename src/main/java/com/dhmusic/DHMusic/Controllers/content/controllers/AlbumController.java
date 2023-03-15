package com.dhmusic.DHMusic.Controllers.content.controllers;

import com.dhmusic.DHMusic.entities.content.entities.Album;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    //inserisce un nuovo Album nel database
    @PostMapping("/create-Album")
    public Album createAlbum(@RequestBody Album newAlbum){
        //logica di creazione dell'Album
        return newAlbum;
    }

    //elimina un Album nel database
    @DeleteMapping("/delete-Album")
    public void deleteAlbum(@RequestBody Album newAlbum){
        //logica di rimozione dell'Album
    }

    //Aggiorna un Album nel database
    @PutMapping("/update-Album")
    public Album updateAlbum(@RequestBody Album newAlbum){
        //logica di aggiornamento dell'Album (usando solo questo metodo
        //oppure farne uno per singolo attributo tipo (/update-Album-name), (/update-Album-surname)
        return newAlbum;
    }

    //Seleziona gli Album nel database
    @GetMapping("/get-all-Albums")
    public Album getAllAlbum(){
        //logica di restituzione degli Album
        return null;
    }

    //Seleziona un Album nel database
    @GetMapping("/get-Album-by-id")
    public Album getAlbumById(){
        //logica di restituzione dell Album tramite id
        return null;
    }

}
