package reserva.application;

import reserva.domain.model.Categoria;
import reserva.domain.model.Espaco;
import reserva.domain.repository.CategoriaRepository;
import reserva.domain.repository.EspacoRepository;
import java.util.List;
import java.util.Optional;

/**
 * AdminEspacosAppService - Gerencia as operações de manutenção de espaços (CRUD)
 * Garante consistência dos dados (como categoria válida) e persiste alterações
 * através do EspacoRepository
 * Implementa RF02 (Gestão de Espaços e Categorias)
 */
public class AdminEspacosAppService {
    
    private final EspacoRepository espacoRepository;
    private final CategoriaRepository categoriaRepository;
    
    public AdminEspacosAppService(EspacoRepository espacoRepository,
                                  CategoriaRepository categoriaRepository) {
        this.espacoRepository = espacoRepository;
        this.categoriaRepository = categoriaRepository;
    }
    
    /**
     * RF02.2.2 - Cadastra um novo espaço
     * RF02.2.3 - Com informações: tipo, tamanho, equipamentos, ar condicionado, tipo de acesso, identificação
     */
    public Espaco cadastrarEspaco(String id, String tipo, int capacidade,
                                   List<String> equipamentos, boolean arCondicionado,
                                   Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                   String localizacao) {
        // Validações
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do espaço é obrigatório");
        }
        
        if (espacoRepository.existe(id)) {
            throw new IllegalArgumentException("Já existe um espaço com este ID");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do espaço é obrigatório");
        }
        
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        
        if (tipoAcesso == null) {
            throw new IllegalArgumentException("Tipo de acesso é obrigatório");
        }
        
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Localização é obrigatória");
        }
        
        // RF02.2.1 - Busca categoria válida
        Categoria categoria = categoriaRepository.buscarPorNome(nomeCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + nomeCategoria));
        
        if (categoria.getTipo() != Categoria.TipoCategoria.ESPACO) {
            throw new IllegalArgumentException("Categoria deve ser do tipo ESPACO");
        }
        
        // Cria e salva espaço
        Espaco espaco = new Espaco(id, tipo, capacidade, tipoAcesso, categoria, localizacao);
        espaco.setArCondicionado(arCondicionado);
        if (equipamentos != null) {
            espaco.setEquipamentos(equipamentos);
        }
        
        return espacoRepository.salvar(espaco);
    }
    
    /**
     * Atualiza um espaço existente
     */
    public Espaco atualizarEspaco(String id, String tipo, Integer capacidade,
                                   List<String> equipamentos, Boolean arCondicionado,
                                   Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                   String localizacao) {
        Espaco espaco = espacoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Espaço não encontrado"));
        
        if (tipo != null && !tipo.trim().isEmpty()) {
            espaco.setTipo(tipo);
        }
        
        if (capacidade != null && capacidade > 0) {
            espaco.setCapacidade(capacidade);
        }
        
        if (equipamentos != null) {
            espaco.setEquipamentos(equipamentos);
        }
        
        if (arCondicionado != null) {
            espaco.setArCondicionado(arCondicionado);
        }
        
        if (tipoAcesso != null) {
            espaco.setTipoAcesso(tipoAcesso);
        }
        
        if (nomeCategoria != null) {
            Categoria categoria = categoriaRepository.buscarPorNome(nomeCategoria)
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            if (categoria.getTipo() != Categoria.TipoCategoria.ESPACO) {
                throw new IllegalArgumentException("Categoria deve ser do tipo ESPACO");
            }
            espaco.setCategoria(categoria);
        }
        
        if (localizacao != null && !localizacao.trim().isEmpty()) {
            espaco.setLocalizacao(localizacao);
        }
        
        return espacoRepository.salvar(espaco);
    }
    
    /**
     * Inativa um espaço
     */
    public Espaco inativarEspaco(String id) {
        Espaco espaco = espacoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Espaço não encontrado"));
        
        espaco.setAtivo(false);
        return espacoRepository.salvar(espaco);
    }
    
    /**
     * Exclui um espaço permanentemente
     */
    public boolean excluirEspaco(String id) {
        if (!espacoRepository.existe(id)) {
            throw new IllegalArgumentException("Espaço não encontrado");
        }
        return espacoRepository.remover(id);
    }
    
    /**
     * Lista todos os espaços
     */
    public List<Espaco> listarTodosEspacos() {
        return espacoRepository.listarTodos();
    }
    
    /**
     * Lista apenas espaços ativos
     */
    public List<Espaco> listarEspacosAtivos() {
        return espacoRepository.listarAtivos();
    }
    
    /**
     * Busca espaço por ID
     */
    public Optional<Espaco> buscarEspacoPorId(String id) {
        return espacoRepository.buscarPorId(id);
    }
}

