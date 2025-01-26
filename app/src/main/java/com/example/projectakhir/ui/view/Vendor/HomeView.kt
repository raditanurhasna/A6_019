package com.example.projectakhir.ui.view.Vendor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.ui.navigation.DestinasiNavigasi
import com.example.projectakhir.ui.viewmodel.PenyediaViewModel
import com.example.projectakhir.ui.viewmodel.vendor.HomeUiState
import com.example.projectakhir.ui.viewmodel.vendor.HomeViewModelVendor

object DestinasiHomeVendor : DestinasiNavigasi {
    override val route = "home_vendor"
    override val titleRes = "Daftar Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenVendor(
    navigateToVendorEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiHomeVendor.titleRes,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { viewModel.getVendors() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToVendorEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vendor")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.vendorUiState,
            retryAction = { viewModel.getVendors() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteVendor(it.idVendor)
                viewModel.getVendors()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.vendors.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Vendor")
                }
            } else {
                VendorLayout(
                    vendors = homeUiState.vendors,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idVendor) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }

        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.errorconnection), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun VendorLayout(
    vendors: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailClick: (Vendor) -> Unit,
    onDeleteClick: (Vendor) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendors) { vendor ->
            VendorCard(
                vendor = vendor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(vendor) },
                onDeleteClick = { onDeleteClick(vendor) }
            )
        }
    }
}

@Composable
fun VendorCard(
    vendor: Vendor,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vendor.namaVendor,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(vendor) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = vendor.idVendor,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = vendor.jenisVendor,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = vendor.kontakVendor,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
