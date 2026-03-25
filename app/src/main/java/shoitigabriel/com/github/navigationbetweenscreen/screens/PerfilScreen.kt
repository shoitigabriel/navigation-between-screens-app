package shoitigabriel.com.github.navigationbetweenscreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
//A função PerfilScreen passou a exigir um argumento chamado nome: String. A tela precisa receber e usar esse parâmetro.
fun PerfilScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    nome: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF329F6B))
            .padding(32.dp)
    ) {
        Text(
            //O texto exibido na tela é dinâmico. A tela de Perfil se adapta ao nome enviado pela navegação.
            text = "PERFIL - $nome",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Button(
            onClick = { navController.navigate("menu") },
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "Voltar",
                fontSize = 20.sp,
                color = Color.Blue
            )
        }
    }
}