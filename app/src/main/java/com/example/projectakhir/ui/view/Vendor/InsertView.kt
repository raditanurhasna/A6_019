package com.example.projectakhir.ui.view.Vendor

import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.vendor.InsertUiEvent
import com.example.projectakhir.ui.viewmodel.vendor.InsertUiState
import com.example.projectakhir.ui.viewmodel.vendor.InsertViewModelVendor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

object DestinasiEntryVendor : DestinasiNavigasi {
    override val route = "vendor_insert"
    override val titleRes = "Daftar Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryVendorScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryVendor.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryVendorBody(
            insertUiState = viewModel.uiState,
            onVendorValueChange = viewModel::updateInsertVendorState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertVendor()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryVendorBody(
    insertUiState: InsertUiState,
    onVendorValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        VendorFormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorFormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val jenisVendorOption = listOf("Catering", "Dekorasi", "Audio")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(idVendor = it)) },
            label = { Text("ID Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(namaVendor = it)) },
            label = { Text("Nama Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(text = "Jenis Vendor", style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            jenisVendorOption.forEach { status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    RadioButton(
                        selected = insertUiEvent.jenisVendor == status,
                        onClick = { onValueChange(insertUiEvent.copy(jenisVendor = status)) }
                    )
                    Text(text = status)
                }
            }
        }
        OutlinedTextField(
            value = insertUiEvent.kontakVendor,
            onValueChange = { onValueChange(insertUiEvent.copy(kontakVendor = it)) },
            label = { Text("Kontak Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
