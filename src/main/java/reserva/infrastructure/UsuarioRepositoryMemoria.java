package reserva.infrastructure;

import reserva.domain.model.Usuario;
import reserva.domain.repository.UsuarioRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação em memória do UsuarioRepository
 * Para testes e desenvolvimento - pode ser substituída por UsuarioFileRepository
 */
public class UsuarioRepositoryMemoria implements UsuarioRepository {
    
    private final Map<String, Usuario> usuarios;
    
    public UsuarioRepositoryMemoria() {
        this.usuarios = new HashMap<>();
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(usuarios.get(email));
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }
    
    @Override
    public List<Usuario> listarAtivos() {
        return usuarios.values().stream()
                .filter(Usuario::isAtivo)
                .collect(Collectors.toList());
    }
    
    @Override
    public Usuario salvar(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Usuário ou e-mail inválido");
        }
        usuarios.put(usuario.getEmail(), usuario);
        return usuario;
    }
    
    @Override
    public boolean remover(String email) {
        return usuarios.remove(email) != null;
    }
    
    @Override
    public boolean existe(String email) {
        return usuarios.containsKey(email);
    }
}

