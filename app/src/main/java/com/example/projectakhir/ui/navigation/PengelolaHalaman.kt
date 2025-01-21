package com.example.projectakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectakhir.ui.screens.EntryAcaraScreen
import com.example.projectakhir.ui.view.Acara.*
import com.example.projectakhir.ui.view.Lokasi.DestinasiEntryLokasi
import com.example.projectakhir.ui.view.Lokasi.EntryLokasiScreen
import com.example.projectakhir.ui.view.Lokasi.HomeScreenLokasi
import com.example.restapi.ui.view.DetailView

object DestinasiEntry {
    const val route = "entry"
}

object DestinasiDetail {
    const val route = "detail"
}

object DestinasiUpdate {
    const val route = "update"
}

object DestinasiHomeLokasi {
    const val route = "homeLokasi"
}

object DestinasiHome {
    const val route = "home"
}

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier,
    ) {
        // Halaman utama
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToLokasi = { navController.navigate(DestinasiHomeLokasi.route) },
                navigateToKlien = {},
                navigateToVendor = {},
                onLokasiClick = { navController.navigate(DestinasiHomeLokasi.route) },
                onKlienClick = {},
                onVendorClick = {},
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        // Halaman input acara
        composable(DestinasiEntry.route) {
            EntryAcaraScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Halaman input lokasi
        composable(DestinasiEntryLokasi.route) {
            EntryLokasiScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Halaman detail (item atau lokasi)
        composable("${DestinasiDetail.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                DetailView(
                    id = id,
                    onNavigateBack = { navController.popBackStack() },
                    onEditClick = {}
                )
            }
        }



        // Halaman edit/update acara
        composable("${DestinasiUpdate.route}/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            if (itemId != null) {
                UpdateScreen(
                    id = itemId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        // Halaman lokasi
        composable(DestinasiHomeLokasi.route) {
            HomeScreenLokasi(
                navigateToLokasiEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = { idLokasi ->
                    navController.navigate("${DestinasiDetail.route}/$idLokasi")
                },
                onBack = {
                    // Menggunakan popBackStack untuk kembali ke halaman sebelumnya
                    navController.popBackStack()
                }
            )
        }


    }
}
