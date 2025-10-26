# Sistema de Reserva de Espaços

Sistema desenvolvido em Java para gerenciamento e reserva de espaços físicos (salas, laboratórios, auditórios) seguindo rigorosamente a documentação de requisitos e arquitetura.

## Requisitos Funcionais Implementados

### RF01 - Gestão de Usuários
- **RF01.1.2**: Sistema com diferentes níveis de usuário, permitindo ao nível 1 cadastrar novos níveis com suas permissões (visualização de espaços, cadastro de espaços, cadastro de usuários, reserva de espaços)
- **RF01.1.3**: Cadastro de categorias de usuários (aluno, servidor, professor)
- **RF01.1.4**: Visualização de todas as informações do sistema pelo usuário nível 1
- **RF01.1.5**: Cadastro de novos usuários (nome, telefone, ramal, senha, nível, categoria)
- **RF01.1.7**: Edição de informações de cadastro pelos usuários
- **RF01.1.8**: Exclusão de usuários pelo nível 1
- **RF01.1.9**: Validação de senha (mínimo 6 caracteres, alfanuméricos, 2 letras maiúsculas)

### RF02 - Gestão de Espaços e Categorias
- **RF02.2.1**: Criação e gerenciamento de hierarquia de categorias para espaços
- **RF02.2.2**: Cadastro de espaços por usuários com permissão
- **RF02.2.3**: Cadastro completo com tipo, tamanho, equipamentos, ar condicionado, tipo de acesso e identificação
- Pesquisa de espaços por: tipo, capacidade, categoria, equipamentos, ar condicionado, localização

## Arquitetura

O projeto está seguindo a **arquitetura em camadas** especificada no documento "Descricao_Classes_Sistema.docx.pdf":

```
src/main/java/reserva/
├── Main.java                                    # Classe principal
├── LauncherSimples.java                         # Launcher com seletor GUI/Console
├── domain/                                      # Camada de Domínio
│   ├── model/                                  # Entidades
│   │   ├── Permissao.java                     # Enum de permissões
│   │   ├── NivelAcesso.java                   # Níveis de acesso
│   │   ├── Categoria.java                     # Categorias (usuário/espaço)
│   │   ├── Usuario.java                       # Entidade usuário
│   │   └── Espaco.java                        # Entidade espaço (agregado raiz)
│   └── repository/                             # Interfaces de repositórios
│       ├── UsuarioRepository.java
│       ├── CategoriaRepository.java
│       ├── NivelAcessoRepository.java
│       └── EspacoRepository.java
├── application/                                 # Camada de Aplicação
│   ├── AdminUsuariosAppService.java            # Gestão de usuários (RF01)
│   ├── AdminNiveisAppService.java              # Gestão de níveis (RF01)
│   ├── AdminCategoriasAppService.java          # Gestão de categorias (RF01/RF02)
│   ├── AdminEspacosAppService.java             # Gestão de espaços (RF02)
│   └── PesquisaEspacosAppService.java          # Pesquisa de espaços (RF02)
├── presentation/                                # Camada de Apresentação
│   ├── AdminController.java                    # Controller admin (RF01)
│   └── EspacoController.java                   # Controller espaços (RF02)
├── gui/                                         # Interface Gráfica
│   ├── MainWindowSimples.java                  # Janela principal
│   ├── UsuariosWindow.java                     # Gestão de usuários
│   ├── CategoriasWindow.java                   # Gestão de categorias
│   ├── NiveisWindow.java                       # Gestão de níveis
│   ├── GerenciarEspacosWindow.java             # Gestão de espaços
│   └── PesquisarEspacosWindow.java             # Pesquisa de espaços
├── dto/                                         # Data Transfer Objects
│   ├── UsuarioDTO.java                         # DTO para usuário
│   ├── CategoriaDTO.java                       # DTO para categoria
│   └── NivelAcessoDTO.java                     # DTO para nível de acesso
└── infrastructure/                              # Camada de Infraestrutura
    ├── UsuarioRepositoryMemoria.java           # Persistência em memória
    ├── CategoriaRepositoryMemoria.java
    ├── NivelAcessoRepositoryMemoria.java
    └── EspacoRepositoryMemoria.java
```

### Camadas da Arquitetura

1. **Domain (Domínio)**
   - Entidades, agregados e objetos de valor
   - Interfaces de repositórios
   - Lógica de negócio fundamental

2. **Application (Aplicação)**
   - Serviços de aplicação (AppServices)
   - Coordenação de casos de uso
   - DTOs e Views (quando necessário)

3. **Presentation (Apresentação/UI)**
   - Controllers que recebem requisições
   - Transformam dados em DTOs
   - Retornam resultados para UI

4. **Infrastructure (Infraestrutura)**
   - Implementações de repositórios
   - Persistência em arquivo/memória
   - Serialização e utilitários

5. **GUI (Interface Gráfica)**
   - Janelas Swing para interação
   - Formulários com validação
   - Tabelas para listagem de dados
   - Componentes visuais intuitivos

6. **DTO (Data Transfer Objects)**
   - Objetos para transferência de dados
   - Isolamento entre camadas
   - Validação de entrada

## Como Compilar

```bash
# Navegar até o diretório do código
cd codigo/

# Compilar usando o script (recomendado)
./compilar.sh

# Ou compilar manualmente
javac -d bin -sourcepath src/main/java src/main/java/reserva/**/*.java
```

## Como Executar

### Opções de Execução

#### 1. Seletor Interativo (Recomendado)
```bash
./executar.sh
```
Abre uma janela para escolher entre Interface Gráfica ou Modo Console.

#### 2. Interface Gráfica Direta
```bash
./executar-gui.sh
```
Executa diretamente a interface gráfica completa com todas as funcionalidades.

#### 3. Modo Console Direto
```bash
./executar-console.sh
```
Executa a demonstração original no console.

#### 4. Execução Manual
```bash
# Interface gráfica
java -cp bin reserva.LauncherSimples --gui

# Modo console
java -cp bin reserva.LauncherSimples --console

# Ajuda
java -cp bin reserva.LauncherSimples --help
```

## Funcionalidades Disponíveis

### Interface Gráfica Completa (Swing)
- **🎯 Seletor de Modo**: Escolha elegante entre GUI e Console
- **👥 Gestão de Usuários**: Interface completa para RF01
  - Cadastro, edição e exclusão de usuários
  - Gestão de níveis de acesso com permissões
  - Gestão de categorias de usuários
- **🏢 Gestão de Espaços**: Interface completa para RF02
  - Cadastro completo de espaços com equipamentos
  - Pesquisa avançada com múltiplos filtros
  - Gestão de categorias de espaços
- **✅ Validações em Tempo Real**: Feedback visual imediato
- **📊 Dados Iniciais**: Sistema pré-populado para demonstração
- **🎨 Design Intuitivo**: Interface moderna com cores temáticas

### Demonstração Console
O programa `Main.java` executa uma demonstração automática das funcionalidades:

### RF01 - Gestão de Usuários
1. Cadastro de níveis de acesso (Nível 1, Usuário Geral)
2. Cadastro de categorias de usuários (Professor, Aluno, Servidor)
3. Cadastro de usuários com validação de senha
4. Listagem de usuários

### RF02 - Gestão de Espaços
1. Cadastro de categorias de espaços (Sala de Aula, Laboratório, Auditório)
2. Cadastro de espaços com todas as informações (tipo, capacidade, equipamentos, ar condicionado, etc.)
3. Listagem de espaços
4. Buscas por: tipo, capacidade, ar condicionado, categoria

## Validações Implementadas

### RF01 - Usuários
- E-mail único e obrigatório
- Nome obrigatório
- Telefone obrigatório
- Senha: mínimo 6 caracteres, alfanuméricos, 2 letras maiúsculas
- Nível de acesso e categoria devem existir

### RF02 - Espaços
- ID único e obrigatório
- Tipo obrigatório
- Capacidade deve ser maior que zero
- Tipo de acesso obrigatório
- Localização obrigatória
- Categoria deve ser do tipo ESPACO

## Tecnologias e Padrões

- **Java 8+**: Linguagem principal
- **Swing**: Interface gráfica nativa
- **Collections Framework**: Gerenciamento de coleções
- **Stream API**: Operações funcionais
- **Repository Pattern**: Abstração de persistência
- **Layered Architecture**: Separação clara de responsabilidades
- **Domain-Driven Design**: Foco no domínio do problema
- **DTO Pattern**: Transferência de dados entre camadas
- **MVC Pattern**: Separação de apresentação e lógica

## Documentos de Referência

- `Documento de requisitos de sistema corrigido.pdf`: Requisitos funcionais
- `Descricao_Classes_Sistema.docx.pdf`: Arquitetura e descrição das classes
- `Casos de Uso Expandidos.pdf`: Casos de uso detalhados

## Funcionalidades Implementadas

### ✅ RF01 - Gestão de Usuários (Completo)
- Interface gráfica completa para gestão
- Validações de senha implementadas
- Gestão de níveis e permissões
- Gestão de categorias de usuários

### ✅ RF02 - Gestão de Espaços (Completo)
- Interface gráfica completa para gestão
- Pesquisa avançada com filtros
- Gestão de categorias de espaços
- Cadastro completo de espaços

### 🔄 Próximos Passos
- Implementar RF03 (Gestão de Reservas)
- Implementar autenticação (RF01.1.6)
- Adicionar persistência em arquivo
- Implementar gestão de chaves

## Autores

Desenvolvido para a disciplina de Engenharia de Software 2
**Autores**: Julio Oliveira Santana, Ricardo Kuroiwa e Igor Martin
Baseado na documentação: Luiz Henrique Cruz dos Santos e Sara R. de Albuquerque

