package com.example.movie.screen.actor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie.R
import androidx.compose.runtime.getValue
import coil.compose.AsyncImage
import com.example.model.model.PersonDetail
import com.example.model.response.CastCreditResponse
import com.example.model.response.CreditMoviesResponse
import com.example.movie.utils.Constant

@Composable
fun PersonDetailScreen(vm: PersonDetailVM = hiltViewModel(), onNavigateToMovieDetail: (Int) -> Unit) {

    val personState by vm.uiState.collectAsStateWithLifecycle()

    val knownForState by vm.uiKnownForUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        when (personState) {
            is PersonDetailUiState.Loading -> CircularProgressIndicator()
            is PersonDetailUiState.Error -> CircularProgressIndicator()
            is PersonDetailUiState.Success -> ActorDetailScreenSuccess((personState as PersonDetailUiState.Success).data, knownForState, onNavigateToMovieDetail)
        }
    }
}


@Composable
private fun ActorDetailScreenSuccess(person: PersonDetail, knownForUiState: KnownForUiState, onNavigateToMovieDetail: (Int) -> Unit) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            InfoSection(person)
        }
        person.biography?.let {
            item {
                BioSection(it)
            }
        }
        item {
            when (knownForUiState) {
                is KnownForUiState.Loading -> CircularProgressIndicator()
                is KnownForUiState.Error -> CircularProgressIndicator()
                is KnownForUiState.Success -> {
                    KnownForSection(knownForUiState.data, onNavigateToMovieDetail)
                }
            }
        }
    }
}

@Composable
private fun BioSection(bio: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Biography", fontSize = 16.sp, fontStyle = FontStyle.Italic)
        Text(
            bio,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            softWrap = true
        )
    }
}

@Composable
private fun KnownForSection(movies: CreditMoviesResponse, onNavigateToMovieDetail: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            "Known For",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(movies.cast ?: emptyList()) {
                MovieItem(it, onNavigateToMovieDetail)
            }
        }
    }
}

@Composable
private fun MovieItem(movie: CastCreditResponse, onItemClicked: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage (
            model = Constant.IMAGE_URL + movie.posterPath,
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(height = 160.dp, width = 90.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onItemClicked(movie.id)
                }
        )
        Text(
            movie.title,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
    }
}
@Composable
private fun InfoSection(person: PersonDetail) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AsyncImage(
            model = Constant.IMAGE_URL + person.profilePath,
            null,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .weight(2f),
        )
        Column(
            modifier = Modifier.weight(3f)
        ) {
            Text(
                person.name ?: "Unknown",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(Modifier.height(15.dp))
            Text(
                "Personal Info",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Column (
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InfoItem("Known For", person.knownFor ?: "")
                InfoItem("Known Credits", "115")
                InfoItem("Gender", person.getGenderName())
                InfoItem("Birthday", person.birthday ?: "")
                InfoItem("Place of birth", person.placeOfBirth ?: "")
            }
        }
    }
}

@Composable
private fun InfoItem(
    title: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(title, fontStyle = FontStyle.Italic, fontSize = 11.sp)
        Text(value, fontStyle = FontStyle.Italic, fontSize = 11.sp)
    }
}