package reserva.presentation;

import reserva.application.*;
import reserva.domain.model.*;
import reserva.dto.*;
import java.util.List;
import java.util.Set;

/**
 * AdminController - Fornece a interface de administração do sistema
 * Permite a criação, atualização, inativação e exclusão de usuários, categorias
 * e níveis de acesso
 * Implementa RF01 (Gestão de Usuários) - Interface de apresentação
 */
public class AdminController {
    
    private final AdminUsuariosAppService usuariosService;
    private final AdminCategoriasAppService categoriasService;
    private final AdminNiveisAppService niveisService;
    
    public AdminController(AdminUsuariosAppService usuariosService,
                          AdminCategoriasAppService categoriasService,
                          AdminNiveisAppService niveisService) {
        this.usuariosService = usuariosService;
        this.categoriasService = categoriasService;
        this.niveisService = niveisService;
    }
    
    // ========== GESTÃO DE USUÁRIOS (RF01) ==========
    
    /**
     * RF01.1.5 - Cadastra um novo usuário
     */
    public boolean criarUsuario(UsuarioDTO dto) {
        try {
            Usuario usuario = usuariosService.cadastrarUsuario(
                dto.getEmail(), dto.getNome(), dto.getSenha(), 
                dto.getTelefone(), dto.getRamal(), dto.getNomeNivel(), dto.getNomeCategoria()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * RF01.1.7 - Atualiza dados do usuário
     */
    public boolean atualizarUsuario(UsuarioDTO dto) {
        try {
            usuariosService.atualizarUsuario(dto.getEmail(), dto.getNome(), 
                dto.getTelefone(), dto.getRamal(), dto.getSenha());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    /**
     * RF01.1.8 - Exclui um usuário
     */
    public boolean excluirUsuario(String email) {
        try {
            usuariosService.excluirUsuario(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * RF01.1.4 - Lista todos os usuários
     */
    public UsuarioDTO[] pesquisar(Object filtros) {
        List<Usuario> usuarios = usuariosService.listarTodosUsuarios();
        return usuarios.stream()
            .map(u -> new UsuarioDTO(u.getEmail(), u.getNome(), null, 
                u.getTelefone(), u.getRamal(), u.getNivelAcesso().getNome(), 
                u.getCategoria().getNome(), u.isAtivo()))
            .toArray(UsuarioDTO[]::new);
    }
    
    // ========== GESTÃO DE CATEGORIAS (RF01.1.3) ==========
    
    /**
     * RF01.1.3 - Cadastra uma categoria de usuário ou espaço
     */
    public boolean criarCategoria(CategoriaDTO dto) {
        try {
            categoriasService.cadastrarCategoria(dto.getNome(), dto.getDescricao(), dto.getTipo());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Atualiza uma categoria
     */
    public boolean atualizarCategoria(CategoriaDTO dto) {
        try {
            categoriasService.atualizarCategoria(dto.getNome(), dto.getDescricao());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Exclui uma categoria
     */
    public boolean excluirCategoria(String nome) {
        try {
            categoriasService.excluirCategoria(nome);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public CategoriaDTO[] listarCategorias() {
        List<Categoria> categorias = categoriasService.listarTodasCategorias();
        return categorias.stream()
            .map(c -> new CategoriaDTO(c.getNome(), c.getDescricao(), c.getTipo()))
            .toArray(CategoriaDTO[]::new);
    }
    
    // ========== GESTÃO DE NÍVEIS (RF01.1.2) ==========
    
    /**
     * RF01.1.2 - Cadastra um novo nível de acesso com permissões
     */
    public boolean criarNivelAcesso(NivelAcessoDTO dto) {
        try {
            niveisService.cadastrarNivel(dto.getNome(), dto.getPermissoes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Atualiza permissões de um nível
     */
    public boolean atualizarNivelAcesso(String nivel, Permissao[] permissoes) {
        try {
            Set<Permissao> permissoesSet = Set.of(permissoes);
            niveisService.atualizarPermissoes(nivel, permissoesSet);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Exclui um nível de acesso
     */
    public boolean excluirNivelAcesso(String nivel) {
        try {
            niveisService.excluirNivel(nivel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public NivelAcessoDTO[] listarNiveisAcesso() {
        List<NivelAcesso> niveis = niveisService.listarTodosNiveis();
        return niveis.stream()
            .map(n -> new NivelAcessoDTO(n.getNome(), n.getPermissoes()))
            .toArray(NivelAcessoDTO[]::new);
    }
    
    // ========== MÉTODOS DE COMPATIBILIDADE (para Main.java) ==========
    
    /**
     * Método de compatibilidade - Cadastra usuário (versão String)
     */
    public String cadastrarUsuario(String email, String nome, String senha, String telefone,
                                    String ramal, String nomeNivel, String nomeCategoria) {
        try {
            Usuario usuario = usuariosService.cadastrarUsuario(
                email, nome, senha, telefone, ramal, nomeNivel, nomeCategoria
            );
            return "Usuário cadastrado com sucesso: " + usuario.getEmail();
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar usuário: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Método de compatibilidade - Cadastra categoria (versão String)
     */
    public String cadastrarCategoria(String nome, String descricao, Categoria.TipoCategoria tipo) {
        try {
            Categoria categoria = categoriasService.cadastrarCategoria(nome, descricao, tipo);
            return "Categoria cadastrada com sucesso: " + categoria.getNome();
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar categoria: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Método de compatibilidade - Cadastra nível (versão String)
     */
    public String cadastrarNivel(String nome, Set<Permissao> permissoes) {
        try {
            NivelAcesso nivel = niveisService.cadastrarNivel(nome, permissoes);
            return "Nível cadastrado com sucesso: " + nivel.getNome();
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar nível: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * Método de compatibilidade - Lista usuários ativos
     */
    public List<Usuario> listarUsuariosAtivos() {
        return usuariosService.listarUsuariosAtivos();
    }
}


