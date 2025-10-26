package reserva.controller;

import reserva.model.Espaco;
import reserva.service.EspacoService;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar operações com Espaços
 * Implementa RF01 e RF02
 */
public class EspacoController {
    
    private final EspacoService service;
    
    public EspacoController(EspacoService service) {
        this.service = service;
    }
    
    /**
     * RF01 - Cadastra um novo espaço
     * @param codigo Código do espaço
     * @param nome Nome do espaço
     * @param tipo Tipo do espaço
     * @param capacidade Capacidade do espaço
     * @param localizacao Localização do espaço
     * @param descricao Descrição do espaço
     * @return Mensagem de sucesso ou erro
     */
    public String cadastrarEspaco(String codigo, String nome, String tipo, 
                                   int capacidade, String localizacao, String descricao) {
        try {
            Espaco espaco = new Espaco(codigo, nome, tipo, capacidade, localizacao);
            espaco.setDescricao(descricao);
            
            service.cadastrarEspaco(espaco);
            return "Espaço cadastrado com sucesso!";
            
        } catch (IllegalArgumentException e) {
            return "Erro ao cadastrar espaço: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * RF01 - Atualiza um espaço existente
     * @param espaco Espaço com dados atualizados
     * @return Mensagem de sucesso ou erro
     */
    public String atualizarEspaco(Espaco espaco) {
        try {
            service.atualizarEspaco(espaco);
            return "Espaço atualizado com sucesso!";
            
        } catch (IllegalArgumentException e) {
            return "Erro ao atualizar espaço: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * RF01 - Remove um espaço
     * @param codigo Código do espaço
     * @return Mensagem de sucesso ou erro
     */
    public String removerEspaco(String codigo) {
        try {
            service.removerEspaco(codigo);
            return "Espaço removido com sucesso!";
            
        } catch (IllegalArgumentException e) {
            return "Erro ao remover espaço: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    /**
     * RF02 - Consulta espaço por código
     * @param codigo Código do espaço
     * @return Espaço encontrado ou null
     */
    public Espaco consultarEspaco(String codigo) {
        Optional<Espaco> espaco = service.consultarPorCodigo(codigo);
        return espaco.orElse(null);
    }
    
    /**
     * RF02 - Lista todos os espaços ativos
     * @return Lista de espaços ativos
     */
    public List<Espaco> listarEspacosAtivos() {
        return service.listarEspacosAtivos();
    }
    
    /**
     * RF02 - Busca espaços por tipo
     * @param tipo Tipo do espaço
     * @return Lista de espaços do tipo especificado
     */
    public List<Espaco> buscarPorTipo(String tipo) {
        try {
            return service.buscarPorTipo(tipo);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * RF02 - Busca espaços por capacidade mínima
     * @param capacidadeMinima Capacidade mínima desejada
     * @return Lista de espaços que atendem a capacidade
     */
    public List<Espaco> buscarPorCapacidade(int capacidadeMinima) {
        try {
            return service.buscarPorCapacidade(capacidadeMinima);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }
}

