package reserva.view;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservaView - View Object para visualização de Reserva
 * Usado para apresentar dados de reserva na camada de apresentação
 * Conforme especificado no diagrama de classes
 */
public class ReservaView {
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String protocolo;
    private String codigoEspaco;
    private String nomeEspaco;
    private String tipoEspaco;
    private String status;  // Ex: "CONFIRMADA", "CANCELADA", "PENDENTE"
    
    // Construtor vazio
    public ReservaView() {}
    
    // Construtor completo
    public ReservaView(LocalDate data, LocalTime horaInicio, LocalTime horaFim, 
                      String protocolo, String codigoEspaco, String nomeEspaco, 
                      String tipoEspaco, String status) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.protocolo = protocolo;
        this.codigoEspaco = codigoEspaco;
        this.nomeEspaco = nomeEspaco;
        this.tipoEspaco = tipoEspaco;
        this.status = status;
    }
    
    // Getters e Setters
    public LocalDate getData() { 
        return data; 
    }
    
    public void setData(LocalDate data) { 
        this.data = data; 
    }
    
    public LocalTime getHoraInicio() { 
        return horaInicio; 
    }
    
    public void setHoraInicio(LocalTime horaInicio) { 
        this.horaInicio = horaInicio; 
    }
    
    public LocalTime getHoraFim() { 
        return horaFim; 
    }
    
    public void setHoraFim(LocalTime horaFim) { 
        this.horaFim = horaFim; 
    }
    
    public String getProtocolo() { 
        return protocolo; 
    }
    
    public void setProtocolo(String protocolo) { 
        this.protocolo = protocolo; 
    }
    
    public String getCodigoEspaco() { 
        return codigoEspaco; 
    }
    
    public void setCodigoEspaco(String codigoEspaco) { 
        this.codigoEspaco = codigoEspaco; 
    }
    
    public String getNomeEspaco() { 
        return nomeEspaco; 
    }
    
    public void setNomeEspaco(String nomeEspaco) { 
        this.nomeEspaco = nomeEspaco; 
    }
    
    public String getTipoEspaco() { 
        return tipoEspaco; 
    }
    
    public void setTipoEspaco(String tipoEspaco) { 
        this.tipoEspaco = tipoEspaco; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    @Override
    public String toString() {
        return "ReservaView{" +
                "data=" + data +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", protocolo='" + protocolo + '\'' +
                ", codigoEspaco='" + codigoEspaco + '\'' +
                ", nomeEspaco='" + nomeEspaco + '\'' +
                ", tipoEspaco='" + tipoEspaco + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
    /**
     * Método auxiliar para exibir a reserva de forma formatada
     */
    public String exibir() {
        return String.format(
            "[%s] %s - %s às %s | Espaço: %s (%s) | Status: %s",
            protocolo, data, horaInicio, horaFim, nomeEspaco, codigoEspaco, status
        );
    }
}
