package com.example.projectakhir.ui.screens

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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.acara.InsertUiEvent
import com.example.projectakhir.ui.viewmodel.acara.InsertUiState
import com.example.projectakhir.ui.viewmodel.acara.InsertViewModel
import com.example.projectakhir.ui.viewmodel.widget.DynamicSelectedTextField

object DestinasiEntryAcara : DestinasiNavigasi {
    override val route = "insert_acara"
    override val titleRes = "Entry Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryAcara.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onAcaraValueChange = viewModel::updateInsertAcaraState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAcara()
                    navigateBack()
                }
            },
            viewModel = viewModel,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onAcaraValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    viewModel: InsertViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            viewModel = viewModel,
            onValueChange = onAcaraValueChange,
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
fun FormInput(
    insertUiEvent: InsertUiEvent,
    viewModel: InsertViewModel,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
) {
    val statusAcaraOption = listOf("Direncakan", "Berlangsung", "Selesai")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idacara,
            onValueChange = { onValueChange(insertUiEvent.copy(idacara = it)) },
            label = { Text("Id Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaacara,
            onValueChange = { onValueChange(insertUiEvent.copy(namaacara = it)) },
            label = { Text("Nama Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiacara,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiacara = it)) },
            label = { Text("Deskripsi Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalmulai,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalmulai = it)) },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalberakhir,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalberakhir = it)) },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idklien,
            options = viewModel.klienIds,
            label = "Pilih ID Klien",
            onValueChangedEvent = {
                onValueChange(insertUiEvent.copy(idklien = it))
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idlokasi,
            options = viewModel.lokasiIds,
            label = "Pilih ID Lokasi",
            onValueChangedEvent = {
                onValueChange(insertUiEvent.copy(idlokasi = it))
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
    Text(text = "Status Acara", style = MaterialTheme.typography.bodyMedium)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        statusAcaraOption.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 1.dp)
            ) {
                RadioButton(
                    selected = insertUiEvent.statusacara == status,
                    onClick = { onValueChange(insertUiEvent.copy(statusacara = status)) }
                )
                Text(text = status)
            }
        }
    }

    Divider(
        thickness = 8.dp,
        modifier = Modifier.padding(12.dp)
    )
}