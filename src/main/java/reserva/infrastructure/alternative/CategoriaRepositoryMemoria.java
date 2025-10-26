package reserva.infrastructure.alternative;

import reserva.domain.model.Categoria;
import reserva.domain.repository.CategoriaRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação em memória do CategoriaRepository
 */
public class CategoriaRepositoryMemoria implements CategoriaRepository {
    
    private final Map<String, Categoria> categorias;
    
    public CategoriaRepositoryMemoria() {
        this.categorias = new HashMap<>();
    }
    
    @Override
    public Optional<Categoria> buscarPorNome(String nome) {
        return Optional.ofNullable(categorias.get(nome));
    }
    
    @Override
    public List<Categoria> listarTodas() {
        return new ArrayList<>(categorias.values());
    }
    
    @Override
    public List<Categoria> listarPorTipo(Categoria.TipoCategoria tipo) {
        return categorias.values().stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }
    
    @Override
    public Categoria salvar(Categoria categoria) {
        if (categoria == null || categoria.getNome() == null) {
            throw new IllegalArgumentException("Categoria ou nome inválido");
        }
        categorias.put(categoria.getNome(), categoria);
        return categoria;
    }
    
    @Override
    public boolean remover(String nome) {
        return categorias.remove(nome) != null;
    }
    
    @Override
    public boolean existe(String nome) {
        return categorias.containsKey(nome);
    }
}

