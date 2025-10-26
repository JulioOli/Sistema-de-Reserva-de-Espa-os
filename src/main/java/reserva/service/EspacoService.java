package reserva.service;

import reserva.model.Espaco;
import reserva.repository.EspacoRepository;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de Espaços
 * Implementa a lógica de negócio para RF01 e RF02
 */
public class EspacoService {
    
    private final EspacoRepository repository;
    
    public EspacoService(EspacoRepository repository) {
        this.repository = repository;
    }
    
    /**
     * RF01 - Cadastra um novo espaço
     * @param espaco Espaço a ser cadastrado
     * @return Espaço cadastrado
     * @throws IllegalArgumentException se dados inválidos
     */
    public Espaco cadastrarEspaco(Espaco espaco) {
        validarEspaco(espaco);
        
        if (repository.existe(espaco.getCodigo())) {
            throw new IllegalArgumentException("Já existe um espaço com o código: " + espaco.getCodigo());
        }
        
        return repository.salvar(espaco);
    }
    
    /**
     * RF01 - Atualiza um espaço existente
     * @param espaco Espaço com dados atualizados
     * @return Espaço atualizado
     * @throws IllegalArgumentException se espaço não existe
     */
    public Espaco atualizarEspaco(Espaco espaco) {
        validarEspaco(espaco);
        
        if (!repository.existe(espaco.getCodigo())) {
            throw new IllegalArgumentException("Espaço não encontrado: " + espaco.getCodigo());
        }
        
        return repository.salvar(espaco);
    }
    
    /**
     * RF01 - Remove (desativa) um espaço
     * @param codigo Código do espaço
     * @return true se removido com sucesso
     */
    public boolean removerEspaco(String codigo) {
        if (!repository.existe(codigo)) {
            throw new IllegalArgumentException("Espaço não encontrado: " + codigo);
        }
        return repository.remover(codigo);
    }
    
    /**
     * RF02 - Consulta espaço por código
     * @param codigo Código do espaço
     * @return Optional com o espaço se encontrado
     */
    public Optional<Espaco> consultarPorCodigo(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }
    
    /**
     * RF02 - Lista todos os espaços ativos
     * @return Lista de espaços ativos
     */
    public List<Espaco> listarEspacosAtivos() {
        return repository.listarAtivos();
    }
    
    /**
     * RF02 - Busca espaços por tipo
     * @param tipo Tipo do espaço
     * @return Lista de espaços do tipo especificado
     */
    public List<Espaco> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }
        return repository.buscarPorTipo(tipo);
    }
    
    /**
     * RF02 - Busca espaços por capacidade mínima
     * @param capacidadeMinima Capacidade mínima desejada
     * @return Lista de espaços que atendem a capacidade
     */
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        if (capacidadeMinima < 0) {
            throw new IllegalArgumentException("Capacidade não pode ser negativa");
        }
        return repository.buscarPorCapacidade(capacidadeMinima);
    }
    
    /**
     * Valida os dados do espaço
     * @param espaco Espaço a ser validado
     * @throws IllegalArgumentException se dados inválidos
     */
    private void validarEspaco(Espaco espaco) {
        if (espaco == null) {
            throw new IllegalArgumentException("Espaço não pode ser nulo");
        }
        
        if (espaco.getCodigo() == null || espaco.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do espaço é obrigatório");
        }
        
        if (espaco.getNome() == null || espaco.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do espaço é obrigatório");
        }
        
        if (espaco.getTipo() == null || espaco.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do espaço é obrigatório");
        }
        
        if (espaco.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        
        if (espaco.getLocalizacao() == null || espaco.getLocalizacao().trim().isEmpty()) {
            throw new IllegalArgumentException("Localização do espaço é obrigatória");
        }
    }
}

