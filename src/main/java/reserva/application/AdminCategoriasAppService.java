package reserva.application;

import reserva.domain.model.Categoria;
import reserva.domain.repository.CategoriaRepository;
import reserva.domain.repository.EspacoRepository;
import java.util.List;
import java.util.Optional;

/**
 * AdminCategoriasAppService - Gerencia categorias do sistema
 * Cria, atualiza e exclui categorias, bloqueando a exclusão quando 
 * ainda vinculadas a espaços
 * RF01.1.3 - Cadastro de categorias de usuários
 * RF02.2.1 - Gestão de hierarquia de categorias para espaços
 */
public class AdminCategoriasAppService {
    
    private final CategoriaRepository categoriaRepository;
    private final EspacoRepository espacoRepository;
    
    public AdminCategoriasAppService(CategoriaRepository categoriaRepository,
                                     EspacoRepository espacoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.espacoRepository = espacoRepository;
    }
    
    /**
     * RF01.1.3 ou RF02.2.1 - Cadastra uma nova categoria
     */
    public Categoria cadastrarCategoria(String nome, String descricao, Categoria.TipoCategoria tipo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo da categoria é obrigatório");
        }
        
        if (categoriaRepository.existe(nome)) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome");
        }
        
        Categoria categoria = new Categoria(nome, descricao, tipo);
        return categoriaRepository.salvar(categoria);
    }
    
    /**
     * Atualiza uma categoria existente
     */
    public Categoria atualizarCategoria(String nome, String novaDescricao) {
        Categoria categoria = categoriaRepository.buscarPorNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
        
        if (novaDescricao != null) {
            categoria.setDescricao(novaDescricao);
        }
        
        return categoriaRepository.salvar(categoria);
    }
    
    /**
     * Exclui uma categoria
     * Bloqueia exclusão se houver espaços vinculados
     */
    public boolean excluirCategoria(String nome) {
        if (!categoriaRepository.existe(nome)) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        
        // Verifica se há espaços vinculados
        List espacosVinculados = espacoRepository.buscarPorCategoria(nome);
        if (!espacosVinculados.isEmpty()) {
            throw new IllegalStateException(
                "Não é possível excluir a categoria. Existem " + 
                espacosVinculados.size() + " espaço(s) vinculado(s)"
            );
        }
        
        return categoriaRepository.remover(nome);
    }
    
    /**
     * Lista todas as categorias
     */
    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.listarTodas();
    }
    
    /**
     * Lista categorias por tipo (USUARIO ou ESPACO)
     */
    public List<Categoria> listarCategoriasPorTipo(Categoria.TipoCategoria tipo) {
        return categoriaRepository.listarPorTipo(tipo);
    }
    
    /**
     * Busca categoria por nome
     */
    public Optional<Categoria> buscarCategoriaPorNome(String nome) {
        return categoriaRepository.buscarPorNome(nome);
    }
}

