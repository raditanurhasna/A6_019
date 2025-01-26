package com.example.projectakhir.ui.view.Vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.vendor.UpdateUiEvent
import com.example.projectakhir.ui.viewmodel.vendor.UpdateUiState
import com.example.projectakhir.ui.viewmodel.vendor.UpdateViewModelVendor

object DestinasiUpdateVendor : DestinasiNavigasi {
    override val route = "update_vendor"
    override val titleRes = "Update Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenVendor(
    idVendor: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idVendor) {
        viewModel.loadVendorData(idVendor)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateVendor.titleRes,
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
                    val vendor = (uiState as UpdateUiState.Success).vendor
                    UpdateForm(
                        idVendor = vendor.idVendor,
                        namaVendor = vendor.namaVendor,
                        jenisVendor = vendor.jenisVendor,
                        kontakVendor = vendor.kontakVendor,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateVendor()
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
    idVendor: String,
    namaVendor: String,
    jenisVendor: String,
    kontakVendor: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var idVendor by remember { mutableStateOf(idVendor) }
    var namaVendor by remember { mutableStateOf(namaVendor) }
    var selectedJenisVendor by remember { mutableStateOf(jenisVendor) }
    var kontakVendor by remember { mutableStateOf(kontakVendor) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = idVendor,
            onValueChange = { idVendor = it },
            label = { Text("Nama Vendor") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = namaVendor,
            onValueChange = { namaVendor = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Radio Button untuk Jenis Vendor
        Text("Jenis Vendor:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedJenisVendor == "Catering",
                onClick = { selectedJenisVendor = "Catering" }
            )
            Text("Catering")

            RadioButton(
                selected = selectedJenisVendor == "Dekorasi",
                onClick = { selectedJenisVendor = "Dekorasi" }
            )
            Text("Dekorasi")

            RadioButton(
                selected = selectedJenisVendor == "Audio",
                onClick = { selectedJenisVendor = "Audio" }
            )
            Text("Audio")
        }

        OutlinedTextField(
            value = kontakVendor,
            onValueChange = { kontakVendor = it },
            label = { Text("Kontak Vendor") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idVendor = idVendor,
                        namaVendor = namaVendor,
                        jenisVendor = jenisVendor,
                        kontakVendor = kontakVendor
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}

