package com.example.projectakhir.ui.view.Lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.DetailUiState
import com.example.projectakhir.ui.viewmodel.lokasi.DetailViewModelLokasi2


object DestinasiDetailLokasi : DestinasiNavigasi {
    override val route = "detail_lokasi"
    override val titleRes = "Detail Lokasi" // Ubah judul sesuai dengan konteks
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewLokasi(
    idLokasi: String,
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: DetailViewModelLokasi2 = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idLokasi) {
        viewModel.getLokasiById(idLokasi)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailLokasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idLokasi) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Lokasi")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState.value) {
                is DetailUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())
                is DetailUiState.Success -> DetailCard(
                    lokasi = state.lokasi,
                    modifier = Modifier
                        .padding(16.dp)
                )
                is DetailUiState.Error -> OnError(
                    retryAction = { viewModel.getLokasiById(idLokasi) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun DetailCard(lokasi: Lokasi, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailSection(title = "Informasi Umum") {
                DetailItem("ID Lokasi", lokasi.idLokasi)
                DetailItem("Nama Lokasi", lokasi.namaLokasi)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Alamat") {
                DetailItem("Alamat Lokasi", lokasi.alamatLokasi)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Kapasitas") {
                DetailItem("Kapasitas", lokasi.kapasitas)
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DetailSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        content()
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.errorconnection),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
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