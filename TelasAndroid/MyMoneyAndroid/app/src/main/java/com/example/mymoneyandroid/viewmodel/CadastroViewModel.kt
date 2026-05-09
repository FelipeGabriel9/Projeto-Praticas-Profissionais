class CadastroViewModel : ViewModel() {
    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000/") // Use o IP do emulador e a porta da sua API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(UsuarioApiService::class.java)

    fun realizarCadastro(nome: String, cpf: String, email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null
            try {
                val request = RegistroRequest(nome, email, cpf, senha)
                apiService.cadastrarUsuario(request)
                onSuccess() // Se deu certo, navega pra outra tela
            } catch (e: Exception) {
                mensagemErro = "Erro ao cadastrar: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }
}