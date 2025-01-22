package com.example.projectakhir.ui.view.Acara

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Acara
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.acara.HomeUiState
import com.example.projectakhir.ui.viewmodel.acara.HomeViewModel

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToLokasi: () -> Unit,
    navigateToKlien: () -> Unit,
    navigateToVendor: () -> Unit,
    onLokasiClick: () -> Unit,
    onKlienClick: () -> Unit,
    onVendorClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAcara()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Acara")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp) // Menambahkan padding global
        ) {
            // Baris Tombol
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Memberikan jarak ke elemen di bawahnya
            ) {
                Button(
                    onClick = onLokasiClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lokasi", color = Color.White)
                }
                Button(
                    onClick = onKlienClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Klien", color = Color.White)
                }
                Button(
                    onClick = onVendorClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Vendor", color = Color.White)
                }
            }

            // Daftar Acara
            HomeStatus(
                acaraUiState = viewModel.acaraUiState,
                retryAction = { viewModel.getAcara() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onLokasiClick = navigateToLokasi,
                onDeleteClick = {
                    viewModel.deleteAcara(it.idacara)
                    viewModel.getAcara()
                }
            )
        }
    }
}



@Composable
fun HomeStatus(
    acaraUiState: HomeUiState,  // Ubah dari AcaraUiState menjadi HomeUiState
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onLokasiClick: () -> Unit = {},
    onKlienClick: () -> Unit = {},
    onVendorClick: () -> Unit = {},
    onDeleteClick: (Acara) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (acaraUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->  // Ganti AcaraUiState.Success menjadi HomeUiState.Success
            if (acaraUiState.acara.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Acara")
                }
            } else {
                AcaraLayout(
                    acara = acaraUiState.acara,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idacara) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())  // Ganti AcaraUiState.Error menjadi HomeUiState.Error
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.errorconnection),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AcaraLayout(
    acara: List<Acara>,
    modifier: Modifier = Modifier,
    onDetailClick: (Acara) -> Unit,
    onDeleteClick: (Acara) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(acara) { item ->
            AcaraCard(
                acara = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun AcaraCard(
    acara: Acara,
    modifier: Modifier = Modifier,
    onDeleteClick: (Acara) -> Unit = {}
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
                    text = acara.idacara,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(acara) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = acara.idacara,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = acara.namaacara,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = acara.deskripsiacara,
                style = MaterialTheme.typography.titleMedium
            )


        }
    }
}
