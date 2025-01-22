package com.example.restapi.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
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
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Acara",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailUiState.Success -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    DetailCard(acara = state.acara)
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
                DetailItem("ID Acara", acara.idacara)
                DetailItem("Nama Acara", acara.namaacara)
                DetailItem("Status", acara.statusacara, isStatus = true)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Deskripsi") {
                Text(
                    text = acara.deskripsiacara,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Waktu Pelaksanaan") {
                DetailItem("Tanggal Mulai", acara.tanggalmulai)
                DetailItem("Tanggal Berakhir", acara.tanggalberakhir)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Informasi Tambahan") {
                DetailItem("ID Lokasi", acara.idlokasi)
                DetailItem("ID Klien", acara.idklien)
            }
        }
    }
}

@Composable
fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
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
fun DetailItem(
    label: String,
    value: String,
    isStatus: Boolean = false
) {
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
        if (isStatus) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when (value.lowercase()) {
                        "aktif" -> Color(0xFF4CAF50)
                        "selesai" -> Color(0xFF2196F3)
                        else -> Color(0xFFFFA000)
                    }
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = value,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}