package com.example.projectakhir.ui.view.Acara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.acara.UpdateUiEvent
import com.example.projectakhir.ui.viewmodel.acara.UpdateUiState
import com.example.projectakhir.ui.viewmodel.acara.UpdateViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.ui.viewmodel.widget.DynamicSelectedTextField

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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
                        },
                        viewModel = viewModel
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
    viewModel: UpdateViewModel,
    modifier: Modifier = Modifier
) {
    var nama by remember { mutableStateOf(namaAcara) }
    var deskripsi by remember { mutableStateOf(deskripsiAcara) }
    var tanggalMulaiState by remember { mutableStateOf(tanggalMulai) }
    var tanggalBerakhirState by remember { mutableStateOf(tanggalBerakhir) }
    var lokasi by remember { mutableStateOf(idLokasi) }
    var klien by remember { mutableStateOf(idKlien) }
    var selectedStatus by remember { mutableStateOf(statusAcara) }

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

        DynamicSelectedTextField(
            selectedValue = klien,
            options = viewModel.klienIds,
            label = "Pilih ID Klien",
            onValueChangedEvent = { klien = it },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectedTextField(
            selectedValue = lokasi,
            options = viewModel.lokasiIds,
            label = "Pilih ID Lokasi",
            onValueChangedEvent = { lokasi = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Radio Button untuk Status Acara
        Text("Status Acara", style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            listOf("Direncanakan", "Berlangsung", "Selesai").forEach { status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 1.dp)
                ) {
                    RadioButton(
                        selected = selectedStatus == status,
                        onClick = { selectedStatus = status }
                    )
                    Text(text = status)
                }
            }
        }

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idacara = idAcara,
                        namaacara = nama,
                        deskripsiacara = deskripsi,
                        tanggalmulai = tanggalMulaiState,
                        tanggalberakhir = tanggalBerakhirState,
                        idlokasi = lokasi,
                        idklien = klien,
                        statusacara = selectedStatus
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
