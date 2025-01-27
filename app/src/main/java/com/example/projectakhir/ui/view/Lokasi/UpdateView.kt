package com.example.projectakhir.ui.view.Lokasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.lokasi.UpdateUiEvent
import com.example.projectakhir.ui.viewmodel.lokasi.UpdateUiState
import com.example.projectakhir.ui.viewmodel.lokasi.UpdateViewModellokasi

object DestinasiUpdateLokasi : DestinasiNavigasi {
    override val route = "update_lokasi"
    override val titleRes = "Update Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenLokasi(
    idLokasi: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModellokasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(idLokasi) {
        viewModel.loadLokasiData(idLokasi)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateLokasi.titleRes,
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
                    val lokasi = (uiState as UpdateUiState.Success).lokasi
                    UpdateForm(
                        idLokasi = lokasi.idLokasi,
                        namaLokasi = lokasi.namaLokasi,
                        alamatLokasi = lokasi.alamatLokasi,
                        kapasitas = lokasi.kapasitas,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateLokasi()
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
    idLokasi: String,
    namaLokasi: String,
    alamatLokasi: String,
    kapasitas: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idLokasi by remember { mutableStateOf(idLokasi) }
    var nama by remember { mutableStateOf(namaLokasi) }
    var alamat by remember { mutableStateOf(alamatLokasi) }
    var kapasitas by remember { mutableStateOf(kapasitas) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Alamat Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kapasitas,
            onValueChange = { kapasitas = it },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idLokasi = idLokasi,
                        namaLokasi = nama,
                        alamatLokasi = alamat,
                        kapasitas = kapasitas
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
