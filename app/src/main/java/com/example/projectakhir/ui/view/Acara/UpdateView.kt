package com.example.projectakhir.ui.view.Acara

import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.acara.UpdateUiEvent
import com.example.projectakhir.ui.viewmodel.acara.UpdateUiState
import com.example.projectakhir.ui.viewmodel.acara.UpdateViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

object DestinasiUpdateAcara : DestinasiNavigasi {
    override val route = "update_acara"
    override val titleRes = "Update Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenAcara(
    idAcara: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idAcara) {
        viewModel.loadAcaraData(idAcara)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAcara.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateUiState.Loading -> CircularProgressIndicator()
                is UpdateUiState.Success -> {
                    val acara = (uiState as UpdateUiState.Success).acara
                    UpdateForm(
                        idAcara = acara.idacara,
                        namaAcara = acara.namaacara,
                        deskripsiAcara = acara.deskripsiacara,
                        tanggalMulai = acara.tanggalmulai,
                        tanggalBerakhir = acara.tanggalberakhir,
                        idLokasi = acara.idlokasi,
                        idKlien = acara.idklien,
                        statusAcara = acara.statusacara,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateAcara()
                            onNavigateBack()
                        }
                    )
                }
                is UpdateUiState.Error -> {
                    Text("Error: ${(uiState as UpdateUiState.Error).message}")
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateForm(
    idAcara: String,
    namaAcara: String,
    deskripsiAcara: String,
    tanggalMulai: String,
    tanggalBerakhir: String,
    idLokasi: String,
    idKlien: String,
    statusAcara: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var nama by remember { mutableStateOf(namaAcara) }
    var deskripsi by remember { mutableStateOf(deskripsiAcara) }
    var tanggalMulaiState by remember { mutableStateOf(tanggalMulai) }
    var tanggalBerakhirState by remember { mutableStateOf(tanggalBerakhir) }
    var lokasi by remember { mutableStateOf(idLokasi) }
    var klien by remember { mutableStateOf(idKlien) }
    var status by remember { mutableStateOf(statusAcara) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Acara") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = { Text("Deskripsi Acara") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = tanggalMulaiState,
            onValueChange = { tanggalMulaiState = it },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = tanggalBerakhirState,
            onValueChange = { tanggalBerakhirState = it },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = lokasi,
            onValueChange = { lokasi = it },
            label = { Text("ID Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = klien,
            onValueChange = { klien = it },
            label = { Text("ID Klien") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status Acara") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idacara = idAcara,
                        namaacara = namaAcara,
                        deskripsiacara = deskripsiAcara,
                        tanggalmulai = tanggalMulaiState,
                        tanggalberakhir = tanggalBerakhirState,
                        idlokasi = idLokasi,
                        idklien = idKlien,
                        statusacara = statusAcara
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
