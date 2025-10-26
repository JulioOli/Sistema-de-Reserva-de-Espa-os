package reserva.repository;

import reserva.model.Espaco;
import java.util.List;
import java.util.Optional;

/**
 * Interface para o repositório de Espaços
 * Define as operações de persistência para RF01 e RF02
 */
public interface EspacoRepository {
    
    /**
     * RF01 - Salva um novo espaço ou atualiza existente
     * @param espaco Espaço a ser salvo
     * @return Espaço salvo
     */
    Espaco salvar(Espaco espaco);
    
    /**
     * RF02 - Busca espaço por código
     * @param codigo Código do espaço
     * @return Optional com o espaço se encontrado
     */
    Optional<Espaco> buscarPorCodigo(String codigo);
    
    /**
     * RF02 - Lista todos os espaços
     * @return Lista de todos os espaços
     */
    List<Espaco> listarTodos();
    
    /**
     * RF02 - Lista apenas espaços ativos
     * @return Lista de espaços ativos
     */
    List<Espaco> listarAtivos();
    
    /**
     * RF02 - Busca espaços por tipo
     * @param tipo Tipo do espaço
     * @return Lista de espaços do tipo especificado
     */
    List<Espaco> buscarPorTipo(String tipo);
    
    /**
     * RF02 - Busca espaços com capacidade mínima
     * @param capacidadeMinima Capacidade mínima desejada
     * @return Lista de espaços que atendem a capacidade
     */
    List<Espaco> buscarPorCapacidade(int capacidadeMinima);
    
    /**
     * RF01 - Remove um espaço (desativa)
     * @param codigo Código do espaço a ser removido
     * @return true se removido com sucesso
     */
    boolean remover(String codigo);
    
    /**
     * Verifica se existe espaço com o código
     * @param codigo Código a verificar
     * @return true se existe
     */
    boolean existe(String codigo);
}

