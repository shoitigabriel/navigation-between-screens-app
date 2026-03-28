# CHECKPOINT 1 - ANDROID KOTLIN DEVELOPER 📱

## DESCRIÇÃO DO PROJETO

Este projeto é um aplicativo Android desenvolvido em Kotlin utilizando Jetpack Compose e Navigation Compose. O app demonstra, de forma didática, como funciona a troca de telas no Compose, incluindo:
- Navegação simples entre telas
- Passagem de parâmetros obrigatórios
- Passagem de parâmetros opcionais
- Tratamento de múltiplos parâmetros
- Leitura desses parâmetros dentro das telas

---------------------------------------------

## OBJETIVO DA PROVA

O objetivo da prova é validar a capacidade do aluno em:
- Aplicar conceitos de navegação no Jetpack Compose
- Implementar tanto parâmetros obrigatórios quanto opcionais
- Demonstrar passagem de múltiplos parâmetros
- Documentar cada etapa com clareza técnica
- Mostrar domínio do Navigation Compose e suas boas práticas

---------------------------------------------

## EXPLICAÇÃO DE CADA PASSO IMPLEMENTADO

### COMMIT 1 - Passagem de parâmetros obrigatórios na tela de Perfil

#### *MainActivity*

```diff
- composable(route = "perfil") {
- PerfilScreen(modifier = Modifier.padding(innerPadding), navController)
```

```kotlin

/*
A rota que antes era estática ("perfil") agora foi modificada para "perfil/{nome}".
Essa mudança indica ao Navigation Compose que a navegação para essa tela deve fornecer um valor para o parâmetro {nome}.
Isso permite que a tela Perfil seja carregada com informações dinâmicas vindas diretamente da navegação.
*/
composable(route = "perfil/{nome}") {

/*
Aqui é recuperado o valor enviado pela navegação.
O Navigation Compose interpreta automaticamente o trecho após "perfil/" como o valor do parâmetro {nome} e
o disponibiliza dentro de 'arguments'.
Caso algum valor inesperado seja recebido, o segundo parâmetro ("Usuário Genérico") funciona como fallback,
garantindo que sempre exista um texto válido.
*/
val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")

/*
O nome extraído da rota é repassado para a PerfilScreen.
Como o parâmetro foi definido como obrigatório, é utilizado o '!!' para indicar ao compilador que
esse valor não será nulo durante a navegação correta.
*/
PerfilScreen(modifier = Modifier.padding(innerPadding), navController, nome!!)
```

#### *MenuScreen*

```diff
- onClick = { navController.navigate("perfil") },
```

```kotlin
/*
Agora a navegação envia um parâmetro obrigatório ("Fulano de Tal") diretamente na rota.
Isso é necessário porque a rota "perfil/{nome}" exige que o valor de {nome} seja informado no momento da navegação.
Sem esse valor, a rota não seria encontrada e o Navigation Compose lançaria erro.
Aqui é garantido que a tela Perfil receba o nome que será exibido ao usuário
*/
onClick = { navController.navigate("perfil/Fulano de Tal") },
```

#### *PerfilScreen*

```diff
- fun PerfilScreen(modifier: Modifier = Modifier, navController: NavController) {
```

```kotlin
/*
A função PerfilScreen agora exige um terceiro parâmetro: nome: String.
Isso ocorre porque a rota "perfil/{nome}" envia um valor obrigatório, e a tela precisa ser capaz de
receber esse valor para exibir informações personalizadas.
Sem esse parâmetro aqui, a tela não teria acesso ao que foi passado pela navegação.
*/
fun PerfilScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    nome: String
) {
```

```diff
- text = "PERFIL",
```

```kotlin
/*
O texto antes era fixo ("PERFIL"), mas agora passa a ser dinâmico, integrando o valor recebido no parâmetro 'nome'.
Isso permite que a tela Perfil exiba informações diferentes com base em quem foi enviado na navegação, tornando a
interface personalizada para cada usuário.
*/
text = "PERFIL - $nome",
```

### COMMIT 2 - Passagem de parâmetros obrigatórios na tela de Perfil

#### *MainActivity*

```diff
- composable(route = "pedidos") {
- PedidosScreen(modifier = Modifier.padding(innerPadding), navController)
```

```kotlin
composable(
/*
A rota "pedidos" foi substituída por uma rota com um parâmetro opcional.
Diferente de parâmetros obrigatórios (ex: "perfil/{nome}"), é usado "pedidos?cliente={cliente}",
o que significa que o parâmetro 'cliente' pode ou NÃO ser enviado durante a navegação.
Caso o parâmetro não seja fornecido na navegação, o Navigation Compose utiliza o valor padrão definido em
navArgument (neste caso: "Cliente Genérico").
*/
route = "pedidos?cliente={cliente}",

/*
Configuração explícita do argumento 'cliente'.
Mesmo sendo opcional, é preciso declará-lo para que o Compose saiba qual parâmetro esperar e qual tipo manipular.
*/
arguments = listOf(navArgument("cliente") {

/*
Esse defaultValue garante que a navegação "pedidos" (sem parâmetro) continue
funcionando normalmente, sem causar erro de rota.
Isso é diferente do parâmetro obrigatório ({nome}), que causa erro se faltar.
*/
defaultValue = "Cliente Genérico"
})
) {


/*
Aqui, ao entrar na rota, é recuperado o valor enviado pela navegação.
Se nenhum valor foi enviado (ex.: navController.navigate("pedidos")),
então 'cliente' será igual ao defaultValue configurado acima.
O Navigation Compose entrega o valor via 'arguments', e usamos getString() para ler o parâmetro.
*/
PedidosScreen(modifier = Modifier.padding(innerPadding), navController, it.arguments?.getString("cliente"))
```

#### *PedidosScreen*

```diff
- fun PedidosScreen(modifier: Modifier = Modifier, navController: NavController) {
```

```kotlin
/*
A função PedidosScreen agora foi atualizada para receber um terceiro parâmetro:
'cliente', que representa o nome do cliente enviado pela navegação.
Como o parâmetro é OPCIONAL, ele é declarado como String? (nulo permitido),
pois a tela pode receber um valor real OU usar o valor padrão definido no NavHost.
*/
fun PedidosScreen(modifier: Modifier = Modifier, navController: NavController, cliente: String?) {
```

```diff
- text = "Voltar",
```

```kotlin
/*
Antes o texto era estático, mostrando apenas "PEDIDOS".
Agora ele se torna DINÂMICO, exibindo o nome do cliente enviado pela rota.
*/
text = "PEDIDOS - $cliente",
```

### COMMIT 3 - Inserindo valor ao parâmetro opcional na tela de Pedidos

#### *MenuScreen*

```diff
- onClick = { navController.navigate("pedidos") },
```

```kotlin
/*
É passado a enviar um valor explícito para o parâmetro opcional "cliente".
A rota "pedidos?cliente={cliente}" aceita que o valor seja enviado pela navegação.
Antes, ao chamar apenas "pedidos", a tela recebia o valor padrão definido no NavHost
("Cliente Genérico"). Já aqui, é enviado "Cliente XPTO", que substituirá o valor padrão.
Isso permite que a tela Pedidos exiba informações personalizadas com base no cliente informado.
*/
onClick = { navController.navigate("pedidos?cliente=Cliente XPTO") },
```

### COMMIT 4 - Passagem de múltiplos parâmetros

#### *MainActivity*

```diff
- composable(route = "perfil/{nome}") {
```

```kotlin
/*
A rota deixa de ter apenas um parâmetro obrigatório ({nome}) e passa a ter 2 parâmetros
obrigatórios ({nome} e {idade}), utilizando o formato: perfil/{nome}/{idade}.
Isso obriga qualquer navegação para essa rota a fornecer os dois valores,
caso contrário o Navigation Compose não encontrará uma rota válida.
No bloco 'arguments', cada parâmetro é declarado com seu tipo,
permitindo que o Navigation Compose faça a leitura correta.
A utilização do NavType garante que a conversão dos valores recebidos seja feita automaticamente
pelo framework, e também ajuda a evitar erros caso a idade não seja um número válido.
*/
composable(
route = "perfil/{nome}/{idade}",
arguments = listOf(
navArgument("nome") { type = NavType.StringType },
navArgument("idade") { type = NavType.IntType }
)
) {
```

```kotlin
/*
Dentro do composable recupera-se o segundo parâmetro.
Como foi declarado 'idade' com NavType.IntType, a função getInt() já entrega o valor convertido para inteiro.
O fallback '0' serve como valor padrão caso algo inesperado ocorra.
*/
val idade: Int? = it.arguments?.getInt("idade", 0)
```

```diff
- nome!!
```

```kotlin
/*
A chamada da PerfilScreen agora envia DOIS valores obrigatórios:
nome!! e idade!!
Isso reflete a nova estrutura da rota.
Ambos são enviados para que a tela Perfil possa utilizá‑los
e exibir informações completas e dinâmicas ao usuário.
O uso de '!!' é seguro aqui porque a rota só é acionada
quando os dois valores estão presentes, já que são obrigatórios.
*/
nome!!,
idade!!
```

#### *MenuScreen*

```diff
- onClick = { navController.navigate("perfil/Fulano de Tal") },
```

```kotlin
/*
Como a rota passou a exigir dois parâmetros obrigatórios,
a navegação precisa incluir 'nome' E 'idade'.
Caso qualquer um desses valores falte, a navegação falhará,
pois a rota "perfil/{nome}/{idade}" exige ambos.
*/
onClick = { navController.navigate("perfil/Fulano de Tal/27") },
```

#### *PerfilScreen*

```diff
-  nome: String
```

```kotlin
/*
A função PerfilScreen agora recebe dois parâmetros obrigatórios:
nome (String) e idade (Int).
Isso permite que a tela apresente informações mais completas
e deixa claro no nível da função que esses valores sempre existirão
quando a tela for aberta.
*/
nome: String,
idade: Int
```

```diff
-  text = "PERFIL - $nome",
```

```kotlin
/*
O texto exibido na interface passou a ser mais descritivo,
combinando ambos os valores recebidos pela navegação.
É demonstrado como a tela Perfil é totalmente dinâmica
e depende dos dados enviados pela rota ao ser chamada.
*/
text = "PERFIL - $nome tem $idade anos",
```



















  
