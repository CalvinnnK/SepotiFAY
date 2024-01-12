package com.example.sepotifay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sepotifay.model.Song
import com.example.sepotifay.ui.theme.SepotiFAYTheme
import com.example.sepotifay.viewModel.SongViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SepotiFAYTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val viewModel = SongViewModel(applicationContext)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("SepotiFAY")
                    }
                )
            },

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                var currentSong = viewModel.currSong.collectAsState()
                var isPlaying = viewModel.isPlaying.collectAsState()

                SongListScreen(viewModel,currentSong, isPlaying)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))

                ) {
                    CurrentSongLayout(viewModel, currentSong, isPlaying)
                }

            }
        }

    }

    @Composable
    fun SongListScreen(
        viewModel: SongViewModel,
        currentSong: State<Song>,
        isPlaying: State<Boolean>
    ) {
        val songs = viewModel.loadSongs()

        Column(modifier = Modifier) {
            songs?.let { songList ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(songList) { song ->
                        SongItem(song = song, viewModel = viewModel, onItemClick = {
                            if(isPlaying.value) viewModel.stopSong()
                            viewModel.setCurrSong(song)
                            viewModel.playSong(song)
                        })
                    }
                }
            }
        }

    }

    @Composable
    fun SongItem(song: Song, viewModel: SongViewModel, onItemClick: (Song) -> Unit) {
        Card(

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(song) }
                .padding(5.dp),
            ) {

            Row(
                modifier = Modifier.padding(10.dp),

                ) {
                Image(painter = painterResource(song.image),
                    contentDescription = "Image Music",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = song.title, fontSize = 16.sp)
                    Text(text = song.artist, fontSize = 14.sp)
                }
            }
        }
    }

    @Composable
    fun CurrentSongLayout(
        viewModel: SongViewModel,
        currentSong: State<Song>,
        isPlaying: State<Boolean>
    ){
        viewModel.onPrepared()



        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = currentSong.value.image), contentDescription = "album_image",
                Modifier
                    .size(75.dp)
                    .padding(end = 10.dp))

            //Title and Artist
            Column() {
                Text(text = currentSong.value.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = currentSong.value.artist, fontSize = 12.sp, color = Color.White)
            }

            Spacer(Modifier.weight(2f))
            
            Row(modifier = Modifier
                .padding(end = 10.dp),
                ){
                //Controller Music
                IconButton(onClick = { viewModel.prevSong() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_prev), contentDescription = "", Modifier.size(100.dp))
                }

                IconButton(onClick = {
                    if(isPlaying.value){
                        viewModel.pauseSong()
                        viewModel.isPlayingFalse()
                    }
                    else{
                        viewModel.playSong(viewModel.getCurrSong())
                        viewModel.isPlayingTrue()
                    }
                } ){
                    if (isPlaying.value) Icon(painter = painterResource(id = R.drawable.ic_pause), contentDescription = "playing", Modifier.size(100.dp))
                    else Icon(painter = painterResource(id = R.drawable.ic_play), contentDescription = "pause", Modifier.size(100.dp))
                }

                IconButton(onClick = {
                    viewModel.nextSong()

                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_next), contentDescription = "", Modifier.size(100.dp))
                }
            }
        }
    }


    @Composable
    fun TrackSlider(
        value: Float,
        onValueChange: (newValue: Float) -> Unit,
        onValueChangeFinished: () -> Unit,
        songDuration: Float
    ) {
        Slider(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            onValueChangeFinished = {

                onValueChangeFinished()

            },
            valueRange = 0f..songDuration,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.DarkGray,
                inactiveTrackColor = Color.Gray,
            )
        )

    }


    @Preview(showBackground = true)
    @Composable
    fun MyPreview() {
        SepotiFAYTheme {
//            CurrentSongLayout()
        }
    }


}


