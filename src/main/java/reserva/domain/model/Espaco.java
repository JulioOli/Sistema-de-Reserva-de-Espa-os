package reserva.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Espaco - Entidade central (agregado raiz). Representa um espaço físico,
 * contém reservas e chaves, e mantém referência a uma categoria
 * RF02.2.2 - O sistema deve permitir o cadastro de espaços
 * RF02.2.3 - Ao cadastrar um espaço, deve ser possível informar: tipo, tamanho,
 * equipamentos, ar condicionado, tipo de acesso e identificação
 */
public class Espaco {
    private String id;
    private String tipo; // sala, laboratório, anfiteatro, etc (RF02.2.3)
    private int capacidade; // tamanho/capacidade do espaço (RF02.2.3)
    private List<String> equipamentos; // equipamentos disponíveis (RF02.2.3)
    private boolean arCondicionado; // se possui ar condicionado (RF02.2.3)
    private TipoAcesso tipoAcesso; // acesso com chave ou digital (RF02.2.3)
    private Categoria categoria; // categoria do espaço (RF02.2.1)
    private String localizacao; // prédio e andar
    private boolean ativo;
    
    public enum TipoAcesso {
        CHAVE("Acesso apenas com chave"),
        DIGITAL("Acesso com digital previamente cadastrada");
        
        private final String descricao;
        
        TipoAcesso(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public Espaco() {
        this.equipamentos = new ArrayList<>();
        this.ativo = true;
    }
    
    public Espaco(String id, String tipo, int capacidade, TipoAcesso tipoAcesso, 
                  Categoria categoria, String localizacao) {
        this.id = id;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.tipoAcesso = tipoAcesso;
        this.categoria = categoria;
        this.localizacao = localizacao;
        this.equipamentos = new ArrayList<>();
        this.ativo = true;
    }
    
    public void adicionarEquipamento(String equipamento) {
        this.equipamentos.add(equipamento);
    }
    
    public void removerEquipamento(String equipamento) {
        this.equipamentos.remove(equipamento);
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getCapacidade() {
        return capacidade;
    }
    
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public List<String> getEquipamentos() {
        return new ArrayList<>(equipamentos);
    }
    
    public void setEquipamentos(List<String> equipamentos) {
        this.equipamentos = new ArrayList<>(equipamentos);
    }
    
    public boolean isArCondicionado() {
        return arCondicionado;
    }
    
    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }
    
    public TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }
    
    public void setTipoAcesso(TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espaco espaco = (Espaco) o;
        return Objects.equals(id, espaco.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Espaco{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", capacidade=" + capacidade +
                ", arCondicionado=" + arCondicionado +
                ", tipoAcesso=" + tipoAcesso +
                ", categoria=" + (categoria != null ? categoria.getNome() : "null") +
                ", localizacao='" + localizacao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}

