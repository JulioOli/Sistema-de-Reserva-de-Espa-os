package reserva.domain.model;

/**
 * Enum que define as permissões do sistema (RF01)
 * Conforme especificação: visualizar espaços, cadastrar espaços, 
 * cadastrar usuários e reservar espaços
 */
public enum Permissao {
    VISUALIZAR_ESPACOS("Visualizar Espaços"),
    CADASTRAR_ESPACOS("Cadastrar Espaços"),
    CADASTRAR_USUARIOS("Cadastrar Usuários"),
    RESERVAR_ESPACOS("Reservar Espaços");
    
    private final String descricao;
    
    Permissao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

