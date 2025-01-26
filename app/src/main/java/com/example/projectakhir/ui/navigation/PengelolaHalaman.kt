package com.example.projectakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir.ui.screens.DestinasiEntryAcara
import com.example.projectakhir.ui.screens.EntryAcaraScreen
import com.example.projectakhir.ui.view.Acara.*
import com.example.projectakhir.ui.view.DestinasiDetailAcara
import com.example.projectakhir.ui.view.DestinasiEntryKlien
import com.example.projectakhir.ui.view.DetailViewAcara
import com.example.projectakhir.ui.view.EntryKlienScreen
import com.example.projectakhir.ui.view.Klien.DestinasiDetailKlien
import com.example.projectakhir.ui.view.Klien.DestinasiHomeKlien
import com.example.projectakhir.ui.view.Klien.DestinasiUpdateKlien
import com.example.projectakhir.ui.view.Klien.DetailViewKlien
import com.example.projectakhir.ui.view.Klien.HomeScreenKlien
import com.example.projectakhir.ui.view.Klien.UpdateScreenKlien
import com.example.projectakhir.ui.view.Lokasi.DestinasiDetailLokasi
import com.example.projectakhir.ui.view.Lokasi.DestinasiEntryLokasi
import com.example.projectakhir.ui.view.Lokasi.DestinasiHomeLokasi
import com.example.projectakhir.ui.view.Lokasi.DestinasiUpdateLokasi
import com.example.projectakhir.ui.view.Lokasi.DetailViewLokasi
import com.example.projectakhir.ui.view.Lokasi.EntryLokasiScreen
import com.example.projectakhir.ui.view.Lokasi.HomeLokasi
import com.example.projectakhir.ui.view.Lokasi.UpdateScreenLokasi
import com.example.projectakhir.ui.view.Vendor.DestinasiDetailVendor
import com.example.projectakhir.ui.view.Vendor.DestinasiEntryVendor
import com.example.projectakhir.ui.view.Vendor.DestinasiHomeVendor
import com.example.projectakhir.ui.view.Vendor.DestinasiUpdateVendor
import com.example.projectakhir.ui.view.Vendor.DetailViewVendor
import com.example.projectakhir.ui.view.Vendor.EntryVendorScreen
import com.example.projectakhir.ui.view.Vendor.HomeScreenVendor
import com.example.projectakhir.ui.view.Vendor.UpdateScreenVendor



@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAcara.route,  // Ubah start destination
        modifier = Modifier,
    ) {
        // ACARA
        composable(DestinasiHomeAcara.route) {
            HomeScreenAcara(
                navigateToItemEntry = { navController.navigate(DestinasiEntryAcara.route) },
                navigateToKlien = { navController.navigate(DestinasiHomeKlien.route)},
                navigateToLokasi = { navController.navigate(DestinasiHomeLokasi.route)},
                navigateToVendor = { navController.navigate(DestinasiHomeVendor.route) },
                onDetailClick = { idAcara ->
                    navController.navigate("${DestinasiDetailAcara.route}/$idAcara")
                }
            )
        }

        // Entry Acara Screen
        composable(DestinasiEntryAcara.route) {
            EntryAcaraScreen(
                navigateBack = { navController.navigateUp() },
            )
        }

        // Detail Acara
        composable(
            route = "${DestinasiDetailAcara.route}/{idAcara}",
            arguments = listOf(navArgument("idAcara") { type = NavType.StringType })
        ) { backStackEntry ->
            val idAcara = backStackEntry.arguments?.getString("idAcara") ?: ""
            DetailViewAcara(
                idAcara = idAcara,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { idAcara ->
                    navController.navigate("${DestinasiUpdateAcara.route}/$idAcara")
                },
            )
        }

        // Edit Acara
        composable(
            route = "${DestinasiUpdateAcara.route}/{idAcara}",
            arguments = listOf(navArgument("idAcara") { type = NavType.StringType })
        ) { backStackEntry ->
            val idAcara = backStackEntry.arguments?.getString("idAcara") ?: ""
            UpdateScreenAcara(
                idAcara = idAcara,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // LOKASI HOME
        composable(DestinasiHomeLokasi.route) {
            HomeLokasi(
                navigateToLokasiEntry = { navController.navigate(DestinasiEntryLokasi.route) },
                onDetailClick = { idLokasi ->
                    navController.navigate("${DestinasiDetailLokasi.route}/$idLokasi")
                },
                navigateBack = { navController.navigate(DestinasiHomeAcara.route) }
            )
        }

        // Entry Lokasi Screen
        composable(DestinasiEntryLokasi.route) {
            EntryLokasiScreen(
                navigateBack = { navController.navigateUp() },
            )
        }

        // Detail Lokasi
        composable(
            route = "${DestinasiDetailLokasi.route}/{idLokasi}",
            arguments = listOf(navArgument("idLokasi") { type = NavType.StringType })
        ) { backStackEntry ->
            val idLokasi = backStackEntry.arguments?.getString("idLokasi") ?: ""
            DetailViewLokasi(
                idLokasi = idLokasi,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { idLokasi ->
                    navController.navigate("${DestinasiUpdateLokasi.route}/$idLokasi")
                },
            )
        }

        // Edit Lokasi
        composable(
            route = "${DestinasiUpdateLokasi.route}/{idLokasi}",
            arguments = listOf(navArgument("idLokasi") { type = NavType.StringType })
        ) { backStackEntry ->
            val idLokasi = backStackEntry.arguments?.getString("idLokasi") ?: ""
            UpdateScreenLokasi(
                idLokasi = idLokasi,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // KLIEN HOME
        composable(DestinasiHomeKlien.route) {
            HomeScreenKlien(
                navigateToKlienEntry = { navController.navigate(DestinasiEntryKlien.route) },
                onDetailClick = { idKlien ->
                    navController.navigate("${DestinasiDetailKlien.route}/$idKlien")
                },
                navigateBack = { navController.navigate(DestinasiHomeAcara.route) }
            )
        }

        // Entry Klien Screen
        composable(DestinasiEntryKlien.route) {
            EntryKlienScreen(
                navigateBack = { navController.navigateUp() },
            )
        }

        // Detail Klient
        composable(
            route = "${DestinasiDetailKlien.route}/{idKlien}",
            arguments = listOf(navArgument("idKlien") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKlien = backStackEntry.arguments?.getString("idKlien") ?: ""
            DetailViewKlien(
                idKlien = idKlien,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { idLokasi ->
                    navController.navigate("${DestinasiUpdateKlien.route}/$idKlien")
                },
            )
        }

        // Edit Klien
        composable(
            route = "${DestinasiUpdateKlien.route}/{idKlien}",
            arguments = listOf(navArgument("idKlien") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKlien = backStackEntry.arguments?.getString("idKlien") ?: ""
            UpdateScreenKlien(
                idKlien = idKlien,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // VENDORS HOME
        composable(DestinasiHomeVendor.route) {
            HomeScreenVendor(
                navigateToVendorEntry = { navController.navigate(DestinasiEntryVendor.route) },
                onDetailClick = { idVendor ->
                    navController.navigate("${DestinasiDetailVendor.route}/$idVendor")
                },
                navigateBack = { navController.navigate(DestinasiHomeAcara.route) }
            )
        }

        // Entry Vendor Screen
        composable(DestinasiEntryVendor.route) {
            EntryVendorScreen(
                navigateBack = { navController.navigateUp() },
            )
        }

        // Detail Vendor
        composable(
            route = "${DestinasiDetailVendor.route}/{idVendor}",
            arguments = listOf(navArgument("idVendor") { type = NavType.StringType })
        ) { backStackEntry ->
            val idVendor = backStackEntry.arguments?.getString("idVendor") ?: ""
            DetailViewVendor(
                idVendor = idVendor,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { idVendor ->
                    navController.navigate("${DestinasiUpdateVendor.route}/$idVendor")
                },
            )
        }

        // Edit Vendor
        composable(
            route = "${DestinasiUpdateVendor.route}/{idVendor}",
            arguments = listOf(navArgument("idVendor") { type = NavType.StringType })
        ) { backStackEntry ->
            val idVendor = backStackEntry.arguments?.getString("idVendor") ?: ""
            UpdateScreenVendor(
                idVendor = idVendor,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
