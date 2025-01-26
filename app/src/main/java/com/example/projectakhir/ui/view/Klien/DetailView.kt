package com.example.projectakhir.ui.view.Klien

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.example.projectakhir.model.Klien
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.Klien.DetailViewModelKlien
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.ui.view.DetailSection
import com.example.projectakhir.ui.viewmodel.Klien.DetailUiState


object DestinasiDetailKlien : DestinasiNavigasi {
    override val route = "detail_klien"
    override val titleRes = "Detail Klien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewKlien(
    idKlien: String,
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: DetailViewModelKlien = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idKlien) {
        viewModel.getKlienById(idKlien)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKlien.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onEditClick(idKlien)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Klien")
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
                    DetailCard(klien = state.klien)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailUiState.Error -> OnError(
                retryAction = { viewModel.getKlienById(idKlien) }
            )
        }
    }
}

@Composable
fun DetailCard(klien: Klien) {
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
                DetailItem("ID Klien", klien.idKlien)
                DetailItem("Nama Klien", klien.namaKlien)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            DetailSection(title = "Kontak") {
                DetailItem("Telepon", klien.kontakKlien)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
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

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}