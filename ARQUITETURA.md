# Arquitetura em Camadas - Sistema de Reserva de Espaços

## 📐 Estrutura do Projeto (Conforme Diagrama de Classes)

```
src/main/java/reserva/
│
├── 📱 presentation/              # CAMADA DE APRESENTAÇÃO
│   ├── dto/                      # Data Transfer Objects
│   │   ├── ReservaDTO.java       # DTO para reservas
│   │   ├── CancelamentoDTO.java  # DTO para cancelamentos
│   │   ├── EspacoDTO.java        # DTO para espaços
│   │   ├── UsuarioDTO.java       # DTO para usuários
│   │   ├── UsuarioDeptoDTO.java  # DTO para usuário com departamento
│   │   ├── CategoriaDTO.java     # DTO para categorias
│   │   └── NivelAcessoDTO.java   # DTO para níveis de acesso
│   │
│   ├── view/                     # Views para apresentação
│   │   ├── ReservaView.java      # View de reservas
│   │   └── EspacoView.java       # View de espaços
│   │
│   ├── AdminController.java      # Controller de administração
│   └── EspacoController.java     # Controller de espaços
│
├── 🔧 application/                # CAMADA DE APLICAÇÃO
│   ├── ReservaAppService.java             # Serviço de reservas
│   ├── PesquisaEspacosAppService.java     # Serviço de pesquisa
│   ├── AdminEspacosAppService.java        # Serviço admin de espaços
│   ├── AdminUsuariosAppService.java       # Serviço admin de usuários
│   ├── AdminCategoriasAppService.java     # Serviço admin de categorias
│   └── AdminNiveisAppService.java         # Serviço admin de níveis
│
├── 🎯 domain/                     # CAMADA DE DOMÍNIO
│   ├── model/                    # Entidades de Negócio
│   │   ├── Categoria.java        # Entidade categoria
│   │   ├── Espaco.java           # Entidade espaço (Agregado Raiz)
│   │   ├── Usuario.java          # Entidade usuário
│   │   ├── NivelAcesso.java      # Entidade nível de acesso
│   │   ├── Permissao.java        # Enum de permissões
│   │   ├── Reserva.java          # Entidade reserva
│   │   ├── Chave.java            # Entidade chave
│   │   └── TipoMovimentacao.java # Enum tipo de movimentação
│   │
│   └── repository/               # Interfaces dos Repositórios
│       ├── CategoriaRepository.java      # Interface repository categoria
│       ├── EspacoRepository.java         # Interface repository espaço
│       ├── UsuarioRepository.java        # Interface repository usuário
│       └── NivelAcessoRepository.java    # Interface repository nível
│
├── 🏗️ infrastructure/            # CAMADA DE INFRAESTRUTURA
│   └── persistence/              # Persistência de Dados
│       │
│       ├── FileStorage.java               # Operações de arquivo
│       ├── Serializer.java                # Interface de serialização
│       ├── JsonSerializer.java            # Implementação JSON
│       │
│       ├── EspacoRepositoryMemoria.java   # Repository em memória
│       ├── UsuarioRepositoryMemoria.java  # Repository em memória
│       ├── CategoriaRepositoryMemoria.java# Repository em memória
│       ├── NivelAcessoRepositoryMemoria.java # Repository em memória
│       │
│       ├── EspacoFileRepository.java      # Repository em arquivo
│       ├── UsuarioFileRepository.java     # Repository em arquivo
│       ├── CategoriaFileRepository.java   # Repository em arquivo
│       └── NivelAcessoFileRepository.java # Repository em arquivo
│
├── 🖥️ gui/                       # Interface Gráfica (UI)
│   ├── MainWindowSimples.java             # Janela principal
│   ├── UsuariosWindow.java                # Janela de usuários
│   ├── CategoriasWindow.java              # Janela de categorias
│   ├── NiveisWindow.java                  # Janela de níveis
│   ├── GerenciarEspacosWindow.java        # Janela de espaços
│   └── PesquisarEspacosWindow.java        # Janela de pesquisa
│
├── Main.java                     # Ponto de entrada (console)
└── LauncherSimples.java          # Ponto de entrada (GUI)
```

## 📊 Descrição das Camadas

### 1. 📱 Presentation (Apresentação)
**Responsabilidade:** Interface com o usuário e transformação de dados

- **DTOs (Data Transfer Objects)**: Objetos simples para transferência de dados entre camadas
- **Views**: Classes para formatação e apresentação de dados
- **Controllers**: Recebem requisições e coordenam a interação com a camada de aplicação

**Fluxo:**
```
Usuário → Controller → AppService → Domain
```

---

### 2. 🔧 Application (Aplicação)
**Responsabilidade:** Orquestração de casos de uso e lógica de aplicação

- **AppServices**: Implementam casos de uso do sistema
- Coordenam chamadas aos repositórios
- Aplicam regras de validação de nível de aplicação
- Transformam entidades em DTOs

**Exemplos de Casos de Uso:**
- `AdminUsuariosAppService`: Cadastrar, editar, excluir usuários
- `PesquisaEspacosAppService`: Buscar espaços por diversos critérios
- `ReservaAppService`: Realizar e cancelar reservas

---

### 3. 🎯 Domain (Domínio)
**Responsabilidade:** Lógica de negócio e regras fundamentais

- **Models**: Entidades que representam conceitos do negócio
- **Repositories (interfaces)**: Contratos para persistência
- Regras de negócio fundamentais (validações, cálculos)

**Características:**
- ✅ Independente de frameworks
- ✅ Regras de negócio puras
- ✅ Entidades com comportamento
- ✅ Imutabilidade onde possível

---

### 4. 🏗️ Infrastructure (Infraestrutura)
**Responsabilidade:** Implementações técnicas e persistência

#### **Persistence (Persistência)**

**FileStorage**
- Operações de I/O em arquivos
- Métodos: `read()`, `write()`, `lock()`

**Serializer (Interface)**
- Contrato para serialização/deserialização
- Métodos: `serialize()`, `deserialize()`

**JsonSerializer**
- Implementação de Serializer usando JSON
- Converte objetos em bytes e vice-versa

**Repositories**
- **Em Memória (`*RepositoryMemoria`)**: Para desenvolvimento e testes
  - Dados armazenados em HashMap
  - Volátil (perde ao fechar)
  
- **Em Arquivo (`*FileRepository`)**: Para persistência real
  - Usa FileStorage e Serializer
  - Persiste em arquivos no diretório `data/`

---

## 🔄 Fluxo de Dados

### Exemplo: Cadastrar um Espaço

```
1. GUI/Console
   ↓
2. EspacoController (Presentation)
   ↓
3. AdminEspacosAppService (Application)
   - Valida dados
   - Busca categoria
   - Cria entidade Espaco
   ↓
4. Espaco (Domain Model)
   - Aplica regras de negócio
   ↓
5. EspacoRepository (Domain Interface)
   ↓
6. EspacoFileRepository (Infrastructure)
   - Usa Serializer para converter
   - Usa FileStorage para salvar
   ↓
7. FileStorage (Infrastructure)
   - Grava bytes no arquivo
```

---

## 🎨 Princípios Arquiteturais

### ✅ Separação de Responsabilidades
Cada camada tem uma responsabilidade clara e bem definida.

### ✅ Dependency Inversion (SOLID)
- Infrastructure depende de Domain
- Domain não depende de nada
- Application depende de Domain

### ✅ Repository Pattern
Abstração da persistência através de interfaces no Domain.

### ✅ DTO Pattern
Separação entre entidades de domínio e objetos de transferência.

### ✅ Service Layer
Lógica de aplicação isolada em AppServices.

---

## 📦 Dependências entre Camadas

```
┌─────────────────┐
│  Presentation   │
│   (UI/DTOs)     │
└────────┬────────┘
         │ usa
         ↓
┌─────────────────┐
│   Application   │
│  (AppServices)  │
└────────┬────────┘
         │ usa
         ↓
┌─────────────────┐     ┌─────────────────┐
│     Domain      │←────│ Infrastructure  │
│ (Models/Repos)  │impl │  (Persistence)  │
└─────────────────┘     └─────────────────┘
```

**Regra:** Camadas superiores dependem de inferiores, nunca o contrário.

---

## 🚀 Como Usar

### Usando Repository em Memória (desenvolvimento)
```java
EspacoRepository repository = new EspacoRepositoryMemoria();
AdminEspacosAppService service = new AdminEspacosAppService(repository, categoriaRepo);
```

### Usando Repository em Arquivo (produção)
```java
EspacoRepository repository = new EspacoFileRepository();
AdminEspacosAppService service = new AdminEspacosAppService(repository, categoriaRepo);
```

A camada de aplicação não precisa saber qual implementação está sendo usada!

---

## 📝 Benefícios da Arquitetura

✅ **Testabilidade**: Cada camada pode ser testada isoladamente  
✅ **Manutenibilidade**: Mudanças localizadas em camadas específicas  
✅ **Flexibilidade**: Fácil trocar implementações (memória → arquivo → banco)  
✅ **Escalabilidade**: Arquitetura preparada para crescimento  
✅ **Clareza**: Código organizado e fácil de entender  

---

## 🔍 Comparação: Antes vs Depois

### ❌ Antes (Estrutura Confusa)
```
reserva/
├── dto/ (misturado)
├── view/ (misturado)
├── controller/ (duplicado)
├── model/ (duplicado)
├── service/ (duplicado)
├── repository/ (duplicado)
├── application/
├── domain/
├── infrastructure/
└── presentation/
```

### ✅ Depois (Arquitetura Limpa)
```
reserva/
├── presentation/
│   ├── dto/
│   └── view/
├── application/
├── domain/
│   ├── model/
│   └── repository/
├── infrastructure/
│   └── persistence/
└── gui/
```

---

## 📚 Referências

- **Domain-Driven Design (DDD)** - Eric Evans
- **Clean Architecture** - Robert C. Martin
- **Patterns of Enterprise Application Architecture** - Martin Fowler

---

**Data de Reorganização:** 26 de outubro de 2025  
**Versão:** 2.0 - Arquitetura em Camadas Completa
