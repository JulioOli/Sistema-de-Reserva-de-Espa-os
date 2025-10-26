# Plano de Reorganização - Arquitetura em Camadas

## Estrutura Alvo (Conforme Diagrama de Classes)

```
src/main/java/reserva/
├── Main.java / LauncherSimples.java (raiz)
│
├── presentation/           # CAMADA DE APRESENTAÇÃO
│   ├── dto/               # Data Transfer Objects
│   │   ├── ReservaDTO.java
│   │   ├── CancelamentoDTO.java
│   │   ├── EspacoDTO.java
│   │   ├── UsuarioDTO.java
│   │   ├── UsuarioDeptoDTO.java
│   │   ├── CategoriaDTO.java
│   │   └── NivelAcessoDTO.java
│   └── view/              # Views
│       ├── ReservaView.java
│       └── EspacoView.java
│
├── application/           # CAMADA DE APLICAÇÃO (App Services)
│   ├── ReservaAppService.java
│   ├── PesquisaEspacosAppService.java
│   ├── AdminEspacosAppService.java
│   ├── AdminUsuariosAppService.java
│   ├── AdminCategoriasAppService.java
│   └── AdminNiveisAppService.java
│
├── domain/                # CAMADA DE DOMÍNIO
│   ├── model/            # Entidades de Domínio
│   │   ├── Categoria.java
│   │   ├── Espaco.java
│   │   ├── Usuario.java
│   │   ├── NivelAcesso.java
│   │   ├── Permissao.java (enum)
│   │   ├── Reserva.java
│   │   ├── Chave.java
│   │   └── TipoMovimentacao.java (enum)
│   └── repository/       # Interfaces dos Repositories
│       ├── CategoriaRepository.java
│       ├── EspacoRepository.java
│       ├── UsuarioRepository.java
│       └── NivelAcessoRepository.java
│
├── infrastructure/        # CAMADA DE INFRAESTRUTURA
│   └── persistence/      # Implementações de Persistência
│       ├── CategoriaRepositoryMemoria.java
│       ├── EspacoRepositoryMemoria.java
│       ├── UsuarioRepositoryMemoria.java
│       └── NivelAcessoRepositoryMemoria.java
│
└── gui/                   # Interface Gráfica (separado da arquitetura)
    ├── MainWindowSimples.java
    ├── UsuariosWindow.java
    ├── CategoriasWindow.java
    ├── NiveisWindow.java
    ├── GerenciarEspacosWindow.java
    └── PesquisarEspacosWindow.java
```

## Ações:
1. Mover DTOs para presentation/dto/
2. Mover Views para presentation/view/
3. Limpar pastas duplicadas (controller/, service/, model/, repository/, dto/, view/)
4. Mover repositories para infrastructure/persistence/
5. Atualizar imports em todos os arquivos
