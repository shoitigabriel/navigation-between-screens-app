package shoitigabriel.com.github.navigationbetweenscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
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
                            route = "pedidos?cliente={cliente}",
                            arguments = listOf(navArgument("cliente") {
                                defaultValue = "Cliente Genérico"
                            })
                        ) {
                            PedidosScreen(modifier = Modifier.padding(innerPadding), navController, it.arguments?.getString("cliente"))
                        }
                        //A rota deixa de ter 1 parâmetro e passa a ter 2 parâmetros obrigatórios. Foram declarados os tipos dos parâmetros usando navArgument: nome(String), idade (Int)
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument("nome") { type = NavType.StringType },
                                navArgument("idade") { type = NavType.IntType }
                            )
                        ) {
                            val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
                            //Dentro do composable, foi adicionado o código para recuperar a idade
                            val idade: Int? = it.arguments?.getInt("idade", 0)
                            PerfilScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                //A chamada da PerfilScreen passa a enviar nome e idade.
                                nome!!,
                                idade!!
                            )
                        }
                    }

                }
            }
        }
    }
}
