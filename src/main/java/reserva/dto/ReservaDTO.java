package reserva.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservaDTO - Data Transfer Object para Reserva
 * Usado para transferir dados de reserva entre camadas
 * Conforme especificado no diagrama de classes
 */
public class ReservaDTO {
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String espaco;      // Código do espaço
    private String protocolo;   // Protocolo/ID da reserva (para retorno)
    
    // Construtor vazio
    public ReservaDTO() {}
    
    // Construtor completo
    public ReservaDTO(LocalDate data, LocalTime horaInicio, LocalTime horaFim, String espaco) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.espaco = espaco;
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
    
    public String getEspaco() { 
        return espaco; 
    }
    
    public void setEspaco(String espaco) { 
        this.espaco = espaco; 
    }
    
    public String getProtocolo() { 
        return protocolo; 
    }
    
    public void setProtocolo(String protocolo) { 
        this.protocolo = protocolo; 
    }
    
    @Override
    public String toString() {
        return "ReservaDTO{" +
                "data=" + data +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", espaco='" + espaco + '\'' +
                ", protocolo='" + protocolo + '\'' +
                '}';
    }
}
