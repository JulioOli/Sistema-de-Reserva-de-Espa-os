package reserva.application;

import reserva.domain.model.*;
import reserva.domain.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * AdminUsuariosAppService - Gerencia o ciclo de vida de usuários (CRUD)
 * Assegura unicidade de e-mail, atribuição correta de níveis de acesso 
 * e persistência no UsuarioRepository
 * Implementa RF01 (Gestão de Usuários)
 */
public class AdminUsuariosAppService {
    
    private final UsuarioRepository usuarioRepository;
    private final NivelAcessoRepository nivelAcessoRepository;
    private final CategoriaRepository categoriaRepository;
    
    public AdminUsuariosAppService(UsuarioRepository usuarioRepository,
                                   NivelAcessoRepository nivelAcessoRepository,
                                   CategoriaRepository categoriaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.nivelAcessoRepository = nivelAcessoRepository;
        this.categoriaRepository = categoriaRepository;
    }
    
    /**
     * RF01.1.5 - Cadastra um novo usuário
     * Valida: e-mail único, senha válida, nível e categoria existentes
     */
    public Usuario cadastrarUsuario(String email, String nome, String senha, String telefone,
                                     String ramal, String nomeNivel, String nomeCategoria) {
        // Validações
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }
        
        if (usuarioRepository.existe(email)) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
        }
        
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        // RF01.1.9 - Valida senha
        if (!Usuario.validarSenha(senha)) {
            throw new IllegalArgumentException(
                "Senha inválida. Deve ter no mínimo 6 caracteres, " +
                "mistura de caracteres alfanuméricos e pelo menos 2 letras maiúsculas"
            );
        }
        
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        
        // Busca nível de acesso
        NivelAcesso nivel = nivelAcessoRepository.buscarPorNome(nomeNivel)
                .orElseThrow(() -> new IllegalArgumentException("Nível de acesso não encontrado: " + nomeNivel));
        
        // Busca categoria
        Categoria categoria = categoriaRepository.buscarPorNome(nomeCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + nomeCategoria));
        
        if (categoria.getTipo() != Categoria.TipoCategoria.USUARIO) {
            throw new IllegalArgumentException("Categoria deve ser do tipo USUARIO");
        }
        
        // Cria e salva usuário
        Usuario usuario = new Usuario(email, nome, senha, telefone, nivel, categoria);
        usuario.setRamal(ramal); // Opcional, obrigatório apenas para funcionários
        
        return usuarioRepository.salvar(usuario);
    }
    
    /**
     * RF01.1.7 - Atualiza dados do usuário
     * Usuário pode editar suas próprias informações, exceto nível e categoria
     */
    public Usuario atualizarUsuario(String email, String nome, String telefone, String ramal, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        if (nome != null && !nome.trim().isEmpty()) {
            usuario.setNome(nome);
        }
        
        if (telefone != null && !telefone.trim().isEmpty()) {
            usuario.setTelefone(telefone);
        }
        
        if (ramal != null) {
            usuario.setRamal(ramal);
        }
        
        if (senha != null && !senha.trim().isEmpty()) {
            if (!Usuario.validarSenha(senha)) {
                throw new IllegalArgumentException("Senha inválida");
            }
            usuario.setSenha(senha);
        }
        
        return usuarioRepository.salvar(usuario);
    }
    
    /**
     * RF01.1.7 - Permite usuário de nível 1 alterar nível e categoria de outro usuário
     */
    public Usuario alterarNivelECategoria(String email, String nomeNivel, String nomeCategoria) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        if (nomeNivel != null) {
            NivelAcesso nivel = nivelAcessoRepository.buscarPorNome(nomeNivel)
                    .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
            usuario.setNivelAcesso(nivel);
        }
        
        if (nomeCategoria != null) {
            Categoria categoria = categoriaRepository.buscarPorNome(nomeCategoria)
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            if (categoria.getTipo() != Categoria.TipoCategoria.USUARIO) {
                throw new IllegalArgumentException("Categoria deve ser do tipo USUARIO");
            }
            usuario.setCategoria(categoria);
        }
        
        return usuarioRepository.salvar(usuario);
    }
    
    /**
     * RF01.1.8 - Exclui um usuário (apenas nível 1)
     */
    public boolean excluirUsuario(String email) {
        if (!usuarioRepository.existe(email)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuarioRepository.remover(email);
    }
    
    /**
     * RF01.1.4 - Lista todos os usuários (apenas nível 1)
     */
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.listarTodos();
    }
    
    /**
     * Lista apenas usuários ativos
     */
    public List<Usuario> listarUsuariosAtivos() {
        return usuarioRepository.listarAtivos();
    }
    
    /**
     * Busca usuário por e-mail
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }
}

