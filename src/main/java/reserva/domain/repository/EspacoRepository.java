package reserva.domain.repository;

import reserva.domain.model.Espaco;
import java.util.List;
import java.util.Optional;

/**
 * EspacoRepository - Interface de repositório do agregado Espaco
 * Define operações para buscar por id, listar e salvar um espaço
 * Implementa persistência para RF02 (Gestão de Espaços)
 */
public interface EspacoRepository {
    
    /**
     * Busca um espaço por ID
     * @param id ID do espaço
     * @return Optional com o espaço se encontrado
     */
    Optional<Espaco> buscarPorId(String id);
    
    /**
     * Lista todos os espaços cadastrados
     * @return Lista de todos os espaços
     */
    List<Espaco> listarTodos();
    
    /**
     * Lista apenas espaços ativos
     * @return Lista de espaços ativos
     */
    List<Espaco> listarAtivos();
    
    /**
     * Lista espaços por tipo (RF02.2.3 - sala, laboratório, anfiteatro, etc)
     * @param tipo Tipo do espaço
     * @return Lista de espaços do tipo especificado
     */
    List<Espaco> buscarPorTipo(String tipo);
    
    /**
     * Lista espaços com capacidade mínima
     * @param capacidadeMinima Capacidade mínima desejada
     * @return Lista de espaços que atendem a capacidade
     */
    List<Espaco> buscarPorCapacidade(int capacidadeMinima);
    
    /**
     * Lista espaços por categoria (RF02.2.1)
     * @param nomeCategoria Nome da categoria
     * @return Lista de espaços da categoria
     */
    List<Espaco> buscarPorCategoria(String nomeCategoria);
    
    /**
     * Salva ou atualiza um espaço (RF02.2.2 - cadastro)
     * @param espaco Espaço a ser salvo (incluindo suas reservas e chaves)
     * @return Espaço salvo
     */
    Espaco salvar(Espaco espaco);
    
    /**
     * Remove um espaço
     * @param id ID do espaço
     * @return true se removido com sucesso
     */
    boolean remover(String id);
    
    /**
     * Verifica se existe espaço com o ID
     * @param id ID a verificar
     * @return true se existe
     */
    boolean existe(String id);
}

