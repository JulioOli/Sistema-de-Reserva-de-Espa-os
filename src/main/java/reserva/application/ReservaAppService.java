package reserva.application;

import reserva.dto.ReservaDTO;
import reserva.dto.CancelamentoDTO;
import reserva.view.ReservaView;

/**
 * ReservaAppService - Gerencia operações de reserva de espaços
 * Permite fazer reservas, cancelar reservas e consultar reservas do usuário
 * Implementa RF03 (Gestão de Reservas)
 * 
 * Conforme especificado no diagrama de classes do sistema
 */
public class ReservaAppService {
    
    // Repositórios e dependências serão injetados quando o RF03 for implementado
    // private final ReservaRepository reservaRepository;
    // private final EspacoRepository espacoRepository;
    // private final UsuarioRepository usuarioRepository;
    
    public ReservaAppService() {
        // Construtor padrão
        // Quando RF03 for implementado, injetar dependências aqui
    }
    
    /**
     * RF03 - Realiza uma nova reserva de espaço
     * 
     * @param dto ReservaDTO contendo os dados da reserva (data, horaInicio, horaFim, espaco)
     * @return String com mensagem de confirmação ou código da reserva
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se o espaço não estiver disponível
     */
    public String fazerReserva(ReservaDTO dto) {
        // Validações básicas
        if (dto == null) {
            throw new IllegalArgumentException("Dados da reserva são obrigatórios");
        }
        
        if (dto.getData() == null) {
            throw new IllegalArgumentException("Data da reserva é obrigatória");
        }
        
        if (dto.getHoraInicio() == null || dto.getHoraFim() == null) {
            throw new IllegalArgumentException("Horário de início e fim são obrigatórios");
        }
        
        if (dto.getEspaco() == null || dto.getEspaco().isEmpty()) {
            throw new IllegalArgumentException("Espaço é obrigatório");
        }
        
        // TODO: Implementar quando RF03 for desenvolvido
        // 1. Validar se o espaço existe e está ativo
        // 2. Validar se o horário está disponível (não há conflito)
        // 3. Validar se o usuário tem permissão para reservar
        // 4. Criar entidade Reserva
        // 5. Salvar no repositório
        // 6. Retornar código da reserva
        
        throw new UnsupportedOperationException(
            "Funcionalidade de fazer reserva será implementada no RF03"
        );
    }
    
    /**
     * RF03 - Cancela uma reserva existente
     * 
     * @param dto CancelamentoDTO contendo código do espaço, email do usuário e protocolo
     * @return boolean true se cancelamento foi bem-sucedido, false caso contrário
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se a reserva não puder ser cancelada
     */
    public boolean cancelar(CancelamentoDTO dto) {
        // Validações básicas
        if (dto == null) {
            throw new IllegalArgumentException("Dados do cancelamento são obrigatórios");
        }
        
        if (dto.getCodigoEspaco() == null || dto.getCodigoEspaco().isEmpty()) {
            throw new IllegalArgumentException("Código do espaço é obrigatório");
        }
        
        if (dto.getEmailUsuario() == null || dto.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário é obrigatório");
        }
        
        if (dto.getProtocolo() == null || dto.getProtocolo().isEmpty()) {
            throw new IllegalArgumentException("Protocolo da reserva é obrigatório");
        }
        
        // TODO: Implementar quando RF03 for desenvolvido
        // 1. Buscar reserva pelo protocolo
        // 2. Validar se a reserva pertence ao usuário
        // 3. Validar se a reserva pode ser cancelada (regras de negócio)
        // 4. Marcar reserva como cancelada
        // 5. Salvar alterações no repositório
        
        throw new UnsupportedOperationException(
            "Funcionalidade de cancelar reserva será implementada no RF03"
        );
    }
    
    /**
     * RF03 - Consulta as reservas de um usuário específico
     * 
     * @param emailUsuario Email do usuário para buscar suas reservas
     * @param filtros Objeto contendo filtros opcionais (data, status, etc)
     * @return Array de ReservaView com as reservas encontradas
     * @throws IllegalArgumentException se o email for inválido
     */
    public ReservaView[] minhasReservas(String emailUsuario, Object filtros) {
        // Validações básicas
        if (emailUsuario == null || emailUsuario.isEmpty()) {
            throw new IllegalArgumentException("Email do usuário é obrigatório");
        }
        
        // TODO: Implementar quando RF03 for desenvolvido
        // 1. Validar se o usuário existe
        // 2. Buscar reservas do usuário no repositório
        // 3. Aplicar filtros se fornecidos
        // 4. Converter entidades Reserva para ReservaView
        // 5. Retornar array de views
        
        // Retorno temporário vazio até implementação do RF03
        return new ReservaView[0];
    }
    
    /**
     * Método auxiliar para buscar todas as reservas de um usuário sem filtros
     * 
     * @param emailUsuario Email do usuário
     * @return Array de ReservaView
     */
    public ReservaView[] minhasReservas(String emailUsuario) {
        return minhasReservas(emailUsuario, null);
    }
}
