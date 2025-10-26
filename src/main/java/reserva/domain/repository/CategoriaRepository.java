package reserva.domain.repository;

import reserva.domain.model.Categoria;
import java.util.List;
import java.util.Optional;

/**
 * CategoriaRepository - Interface de repositório para categorias
 * Define operações de busca por nome, listagem e salvamento
 * Implementa persistência para RF01.1.3 e RF02.2.1
 */
public interface CategoriaRepository {
    
    /**
     * Busca uma categoria por nome
     * @param nome Nome da categoria
     * @return Optional com a categoria se encontrada
     */
    Optional<Categoria> buscarPorNome(String nome);
    
    /**
     * Lista todas as categorias
     * @return Lista de todas as categorias
     */
    List<Categoria> listarTodas();
    
    /**
     * Lista categorias por tipo (USUARIO ou ESPACO)
     * @param tipo Tipo da categoria
     * @return Lista de categorias do tipo especificado
     */
    List<Categoria> listarPorTipo(Categoria.TipoCategoria tipo);
    
    /**
     * Salva ou atualiza uma categoria
     * @param categoria Categoria a ser salva
     * @return Categoria salva
     */
    Categoria salvar(Categoria categoria);
    
    /**
     * Remove uma categoria
     * @param nome Nome da categoria
     * @return true se removida com sucesso
     */
    boolean remover(String nome);
    
    /**
     * Verifica se existe categoria com o nome
     * @param nome Nome a verificar
     * @return true se existe
     */
    boolean existe(String nome);
}

