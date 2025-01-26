package com.example.projectakhir.ui.view.Lokasi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.view.Acara.OnError
import com.example.projectakhir.ui.view.Acara.OnLoading
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.HomeViewModelLokasi
import com.example.projectakhir.ui.viewmodel.lokasi.LokasiUiState

object DestinasiHomeLokasi : DestinasiNavigasi {
    override val route = "homeLokasi"
    override val titleRes = "Daftar Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLokasi(
    navigateToLokasiEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory)

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiHomeLokasi.titleRes,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { viewModel.getLokasi() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToLokasiEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Lokasi")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            lokasiUiState = viewModel.lokasiUiState,
            retryAction = { viewModel.getLokasi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteLokasi(it.idLokasi)
                viewModel.getLokasi()
            }
        )
    }
}

@Composable
fun HomeStatus(
    lokasiUiState: LokasiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (lokasiUiState) {
        is LokasiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is LokasiUiState.Success ->
            if (lokasiUiState.lokasi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Lokasi")
                }
            } else {
                LokasiLayout(
                    lokasi = lokasiUiState.lokasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idLokasi)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is LokasiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LokasiLayout(
    lokasi: List<Lokasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Lokasi) -> Unit,
    onDeleteClick: (Lokasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Mengurangi jarak antar card
    ) {
        items(lokasi) { lokasiItem ->
            LokasiCard(
                lokasi = lokasiItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(lokasiItem) },
                onDeleteClick = {
                    onDeleteClick(lokasiItem)
                }
            )
        }
    }
}

@Composable
fun LokasiCard(
    lokasi: Lokasi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lokasi.namaLokasi,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(lokasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = lokasi.idLokasi,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = lokasi.alamatLokasi,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
