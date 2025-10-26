package reserva.dto;

/**
 * CancelamentoDTO - Data Transfer Object para Cancelamento de Reserva
 * Usado para transferir dados de cancelamento entre camadas
 * Conforme especificado no diagrama de classes
 */
public class CancelamentoDTO {
    private String codigoEspaco;
    private String emailUsuario;
    private String protocolo;
    
    // Construtor vazio
    public CancelamentoDTO() {}
    
    // Construtor completo
    public CancelamentoDTO(String codigoEspaco, String emailUsuario, String protocolo) {
        this.codigoEspaco = codigoEspaco;
        this.emailUsuario = emailUsuario;
        this.protocolo = protocolo;
    }
    
    // Getters e Setters
    public String getCodigoEspaco() { 
        return codigoEspaco; 
    }
    
    public void setCodigoEspaco(String codigoEspaco) { 
        this.codigoEspaco = codigoEspaco; 
    }
    
    public String getEmailUsuario() { 
        return emailUsuario; 
    }
    
    public void setEmailUsuario(String emailUsuario) { 
        this.emailUsuario = emailUsuario; 
    }
    
    public String getProtocolo() { 
        return protocolo; 
    }
    
    public void setProtocolo(String protocolo) { 
        this.protocolo = protocolo; 
    }
    
    @Override
    public String toString() {
        return "CancelamentoDTO{" +
                "codigoEspaco='" + codigoEspaco + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", protocolo='" + protocolo + '\'' +
                '}';
    }
}
