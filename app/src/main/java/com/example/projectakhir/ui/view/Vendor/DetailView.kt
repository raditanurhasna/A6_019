package com.example.projectakhir.ui.view.Vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.ui.customewidget.CostumeTopAppBar
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.vendor.DetailUiState
import com.example.projectakhir.ui.viewmodel.vendor.DetailViewModelVendor

object DestinasiDetailVendor : DestinasiNavigasi {
    override val route = "detail_vendor"
    override val titleRes = "Detail Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewVendor(
    idVendor: String,
    onNavigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: DetailViewModelVendor = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idVendor) {
        viewModel.getVendorById(idVendor)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailVendor.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onEditClick(idVendor)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Vendor")
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
                    DetailCard(vendor = state.vendor)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailUiState.Error -> OnError(retryAction = { viewModel.getVendorById(idVendor) })
        }
    }
}

@Composable
fun DetailCard(vendor: Vendor) {
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
            // Informasi Umum
            DetailSection(title = "Informasi Umum") {
                DetailItem("ID Vendor", vendor.idVendor)
                DetailItem("Nama Vendor", vendor.namaVendor)
                DetailItem("Jenis Vendor", vendor.jenisVendor)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Kontak
            DetailSection(title = "Kontak") {
                DetailItem("Kontak Vendor", vendor.kontakVendor)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
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

