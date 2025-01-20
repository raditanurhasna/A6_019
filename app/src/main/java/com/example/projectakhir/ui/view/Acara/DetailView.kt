package com.example.restapi.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Acara
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.view.Acara.OnError
import com.example.projectakhir.ui.view.Acara.OnLoading
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.restapi.ui.viewmodel.DetailAcaraViewModel
import com.example.restapi.ui.viewmodel.DetailUiState


object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    id: String,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: DetailAcaraViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id) {
        viewModel.getAcaraById(id)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Acara")
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailCard(acara = state.acara)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailUiState.Error -> OnError(retryAction = { viewModel.getAcaraById(id) })
        }
    }
}

@Composable
fun DetailCard(acara: Acara) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Id Acara: ${acara.idacara}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nama Acara: ${acara.namaacara}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Deskripsi Acara: ${acara.deskripsiacara}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Mulai: ${acara.tanggalmulai}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Berakhir: ${acara.tanggalberakhir}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Id Lokasi: ${acara.idlokasi}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Id Klien: ${acara.idklien}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Status Acara: ${acara.statusacara}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
