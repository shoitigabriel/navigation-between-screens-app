package shoitigabriel.com.github.navigationbetweenscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import shoitigabriel.com.github.navigationbetweenscreen.screens.LoginScreen
import shoitigabriel.com.github.navigationbetweenscreen.screens.MenuScreen
import shoitigabriel.com.github.navigationbetweenscreen.screens.PedidosScreen
import shoitigabriel.com.github.navigationbetweenscreen.screens.PerfilScreen
import shoitigabriel.com.github.navigationbetweenscreen.ui.theme.NavigationBetweenScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationBetweenScreenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(route = "login") {
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(
                            //passagem de parâmetro opcional cliente para a tela Pedidos.
                            route = "pedidos?cliente={cliente}",
                            //A rota fixa pedidos foi substituída pela rota pedidos?cliente={cliente} com argumento declarado no navArgument.
                            arguments = listOf(navArgument("cliente") {
                                //Como cliente tem valor padrão, a navegação ocorre mesmo sem envio do parâmetro.
                                defaultValue = "Cliente Genérico"
                            })
                        ) {
                            PedidosScreen(modifier = Modifier.padding(innerPadding), navController, it.arguments?.getString("cliente"))
                        }
                        composable(route = "perfil/{nome}") {
                            val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
                            PerfilScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                nome!!
                            )
                        }
                    }

                }
            }
        }
    }
}
