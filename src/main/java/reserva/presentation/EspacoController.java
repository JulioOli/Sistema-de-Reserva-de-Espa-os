package reserva.presentation;

import reserva.application.AdminEspacosAppService;
import reserva.application.PesquisaEspacosAppService;
import reserva.domain.model.Espaco;
import java.util.List;

/**
 * EspacoController - Controlador da camada de apresentação para Espaços
 * Faz a ponte entre a GUI e os Application Services
 * Implementa RF02 (Gestão de Espaços)
 */
public class EspacoController {
    
    private final AdminEspacosAppService adminService;
    private final PesquisaEspacosAppService pesquisaService;
    
    public EspacoController(AdminEspacosAppService adminService, 
                           PesquisaEspacosAppService pesquisaService) {
        this.adminService = adminService;
        this.pesquisaService = pesquisaService;
    }
    
    /**
     * Lista todos os espaços disponíveis
     */
    public List<Espaco> listarEspacos() {
        return pesquisaService.listarEspacosDisponiveis();
    }
    
    /**
     * Lista todos os espaços ativos (alias para listarEspacos)
     */
    public List<Espaco> listarEspacosAtivos() {
        return listarEspacos();
    }
    
    /**
     * Lista todos os espaços (alias para listarEspacos)
     */
    public List<Espaco> listarTodosEspacos() {
        return listarEspacos();
    }
    
    /**
     * Busca espaços por tipo
     */
    public List<Espaco> buscarPorTipo(String tipo) {
        try {
            return pesquisaService.buscarPorTipo(tipo);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao buscar por tipo: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca espaços por capacidade mínima
     */
    public List<Espaco> buscarPorCapacidade(int capacidade) {
        try {
            return pesquisaService.buscarPorCapacidade(capacidade);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao buscar por capacidade: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca espaços por categoria
     */
    public List<Espaco> buscarPorCategoria(String categoria) {
        try {
            return pesquisaService.buscarPorCategoria(categoria);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao buscar por categoria: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Busca espaços com ar condicionado
     */
    public List<Espaco> buscarComArCondicionado() {
        return pesquisaService.buscarComArCondicionado();
    }
    
    /**
     * Cadastra um novo espaço
     */
    public String cadastrarEspaco(String id, String tipo, int capacidade,
                                 List<String> equipamentos, boolean arCondicionado,
                                 Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                 String localizacao) {
        try {
            adminService.cadastrarEspaco(id, tipo, capacidade, equipamentos, 
                                       arCondicionado, tipoAcesso, nomeCategoria, localizacao);
            return "Espaço cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar espaço: " + e.getMessage();
        }
    }
    
    /**
     * Atualiza um espaço existente
     */
    public String atualizarEspaco(String id, String tipo, Integer capacidade,
                                 List<String> equipamentos, Boolean arCondicionado,
                                 Espaco.TipoAcesso tipoAcesso, String nomeCategoria,
                                 String localizacao) {
        try {
            adminService.atualizarEspaco(id, tipo, capacidade, equipamentos,
                                       arCondicionado, tipoAcesso, nomeCategoria, localizacao);
            return "Espaço atualizado com sucesso!";
        } catch (Exception e) {
            return "Erro ao atualizar espaço: " + e.getMessage();
        }
    }
    
    /**
     * Exclui um espaço
     */
    public String excluirEspaco(String id) {
        try {
            boolean sucesso = adminService.excluirEspaco(id);
            if (sucesso) {
                return "Espaço excluído com sucesso!";
            } else {
                return "Erro ao excluir espaço";
            }
        } catch (Exception e) {
            return "Erro ao excluir espaço: " + e.getMessage();
        }
    }
}
