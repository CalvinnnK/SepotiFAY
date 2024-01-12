package com.example.sepotifay.viewModel



import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sepotifay.R
import com.example.sepotifay.model.Song
import com.example.sepotifay.repository.SongRepository
import kotlinx.coroutines.flow.MutableStateFlow


class SongViewModel(applicationContext: Context) : ViewModel() {

    val context = applicationContext

    private var player: MediaPlayer = MediaPlayer()

    var currSong = MutableStateFlow<Song>(loadSongs().first())
    var isPlaying = MutableStateFlow<Boolean>(isPlaying())

    fun onPrepared(){
        player = MediaPlayer.create(context, loadSongs().first().uri)
    }

    fun loadSongs() :List<Song>{
        val repo = SongRepository();
        return repo.getAllData()
    }
    fun playSong(song: Song) {
        if(isPlaying.value){
            stopSong()
            setCurrSong(song)
        }
        player.start()
        isPlaying.value = true

        player.setOnCompletionListener {
            nextSong()
        }

    }

    fun pauseSong(){
        player.pause()
        isPlaying.value = false
    }

    fun stopSong(){
        player.stop()
        isPlaying.value = false
    }

    fun nextSong(){
        val curr = getCurrSong()
        val nextId = (curr.id + 1) % totalSong()

        stopSong()
        setCurrSong(loadSongs()[nextId])
        playSong(loadSongs()[nextId])
    }

    fun prevSong(){
        val curr = getCurrSong()
        val prevId = (curr.id - 1 + totalSong()) % totalSong()

        stopSong()
        setCurrSong(loadSongs()[prevId])
        playSong(loadSongs()[prevId])
    }

    fun setCurrSong(song: Song){
        player = MediaPlayer.create(context,song.uri)
        currSong.value = song
    }


    fun getCurrSong(): Song{
        return currSong.value
    }

    fun isPlaying(): Boolean{
        return player.isPlaying
    }

    fun isPlayingTrue(){
        isPlaying.value = true
    }
    fun isPlayingFalse(){
        isPlaying.value = false
    }

    fun totalSong(): Int{
        return loadSongs().last().id
    }





}