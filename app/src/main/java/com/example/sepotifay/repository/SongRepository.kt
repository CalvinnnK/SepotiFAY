package com.example.sepotifay.repository

import com.example.sepotifay.R
import com.example.sepotifay.model.Song

class SongRepository {
    fun getAllData(): List<Song> {
        return listOf(
            Song(0, "Soul and Mind", "Jammy Jams", "Album 4", "res/raw/soul_and_mind.mp3", R.raw.soul_and_mind, R.drawable.soul_and_mind_cover),
            Song(1, "Cocktail Hour", "Aaron Kenny", "Album 1", "res/raw/cocktail_hour.mp3", R.raw.cocktail_hour, R.drawable.cocktail_hour_cover),
            Song(2, "Bet On It", "Silent Partner", "Album 2", "res/raw/bet_on_it.mp3", R.raw.bet_on_it, R.drawable.bet_on_it_cover),
            Song(3, "Bluesy Vibes", "Doug Maxwell", "Album 3", "res/raw/bluesy_vibes.mp3", R.raw.bluesy_vibes, R.drawable.bluesy_vibes_cover),
            Song(4, "Soul and Mind", "Jammy Jams", "Album 4", "res/raw/soul_and_mind.mp3", R.raw.soul_and_mind, R.drawable.soul_and_mind_cover),
            Song(5, "Cocktail Hour", "Aaron Kenny", "Album 1", "res/raw/cocktail_hour.mp3", R.raw.cocktail_hour, R.drawable.cocktail_hour_cover),
            Song(6, "Bet On It", "Silent Partner", "Album 2", "res/raw/bet_on_it.mp3", R.raw.bet_on_it, R.drawable.bet_on_it_cover),
            Song(7, "Bluesy Vibes", "Doug Maxwell", "Album 3", "res/raw/bluesy_vibes.mp3", R.raw.bluesy_vibes, R.drawable.bluesy_vibes_cover),
            Song(8, "Soul and Mind", "Jammy Jams", "Album 4", "res/raw/soul_and_mind.mp3", R.raw.soul_and_mind, R.drawable.soul_and_mind_cover),
            Song(9, "Cocktail Hour", "Aaron Kenny", "Album 1", "res/raw/cocktail_hour.mp3", R.raw.cocktail_hour, R.drawable.cocktail_hour_cover),
            Song(10, "Bet On It", "Silent Partner", "Album 2", "res/raw/bet_on_it.mp3", R.raw.bet_on_it, R.drawable.bet_on_it_cover),
            Song(11, "Bluesy Vibes", "Doug Maxwell", "Album 3", "res/raw/bluesy_vibes.mp3", R.raw.bluesy_vibes, R.drawable.bluesy_vibes_cover),
        )
    }

}