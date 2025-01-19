package com.example.projectakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectakhir.ui.screens.EntryAcaraScreen
import com.example.projectakhir.ui.view.Acara.DestinasiHome
import com.example.projectakhir.ui.view.Acara.HomeScreen
import com.example.projectakhir.ui.view.Acara.DetailAcaraScreen

object DestinasiEntry {
    const val route = "entry"
}

object DestinasiDetail {
    const val route = "detail"
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
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    // Navigasi ke halaman detail dengan itemId
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryAcaraScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Menambahkan composable untuk DetailAcaraScreen
        composable("${DestinasiDetail.route}/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            if (itemId != null) {
                DetailAcaraScreen(
                    id = itemId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
