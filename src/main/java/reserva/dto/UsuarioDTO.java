package reserva.dto;

/**
 * UsuarioDTO - Data Transfer Object para Usuario
 * Usado para transferir dados de usuário entre camadas
 */
public class UsuarioDTO {
    private String email;
    private String nome;
    private String senha;
    private String telefone;
    private String ramal;
    private String nomeNivel;
    private String nomeCategoria;
    private boolean ativo;
    
    // Construtor vazio
    public UsuarioDTO() {}
    
    // Construtor completo
    public UsuarioDTO(String email, String nome, String senha, String telefone, 
                     String ramal, String nomeNivel, String nomeCategoria, boolean ativo) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.ramal = ramal;
        this.nomeNivel = nomeNivel;
        this.nomeCategoria = nomeCategoria;
        this.ativo = ativo;
    }
    
    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getRamal() { return ramal; }
    public void setRamal(String ramal) { this.ramal = ramal; }
    
    public String getNomeNivel() { return nomeNivel; }
    public void setNomeNivel(String nomeNivel) { this.nomeNivel = nomeNivel; }
    
    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", ramal='" + ramal + '\'' +
                ", nomeNivel='" + nomeNivel + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
