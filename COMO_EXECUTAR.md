# 🚀 Como Executar o Sistema de Reserva de Espaços

## 📋 **Pré-requisitos**

### **Java Development Kit (JDK)**
- **Versão mínima**: JDK 8 ou superior
- **Recomendado**: JDK 11 ou JDK 17

#### **Verificar se o Java está instalado:**
```bash
java -version
javac -version
```

#### **Se não tiver Java instalado:**

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

**Windows:**
- Baixar do site oficial: https://www.oracle.com/java/technologies/downloads/
- Ou usar OpenJDK: https://adoptium.net/

**macOS:**
```bash
brew install openjdk@11
```

---

## 📁 **Estrutura do Projeto**

```
Projeto4-es2-reserva-salas/
├── src/main/java/          # Código fonte
├── bin/                    # Arquivos compilados (criado automaticamente)
├── compilar.sh            # Script de compilação
├── executar.sh            # Launcher com seletor
├── executar-gui.sh        # Interface gráfica direta
├── executar-console.sh    # Modo console direto
└── README.md              # Documentação principal
```

---

## ⚡ **Execução Rápida (3 passos)**

### **1. Compilar o projeto**
```bash
./compilar.sh
```

### **2. Executar o sistema**
```bash
./executar.sh
```

### **3. Escolher o modo**
- Clique em **"🖥️ Interface Gráfica"** para usar a GUI completa
- Clique em **"⌨️ Modo Console"** para ver a demonstração no terminal

---

## 🎯 **Opções de Execução Detalhadas**

### **Opção 1: Seletor Interativo (Recomendado)**
```bash
./executar.sh
```
- Abre uma janela elegante para escolher entre GUI ou Console
- **Melhor para demonstrações**

### **Opção 2: Interface Gráfica Direta**
```bash
./executar-gui.sh
```
- Vai direto para a interface gráfica
- **Melhor para uso prático**

### **Opção 3: Modo Console Direto**
```bash
./executar-console.sh
```
- Executa a demonstração automática no terminal
- **Melhor para ver todas as funcionalidades rapidamente**

### **Opção 4: Execução Manual**
```bash
# Compilar manualmente
javac -d bin -sourcepath src/main/java src/main/java/reserva/**/*.java

# Interface gráfica
java -cp bin reserva.LauncherSimples --gui

# Modo console
java -cp bin reserva.LauncherSimples --console

# Ver ajuda
java -cp bin reserva.LauncherSimples --help
```

---

## 🖥️ **Interface Gráfica - Funcionalidades**

### **RF01 - Gestão de Usuários**
1. **Gerenciar Usuários**: Cadastro, edição, exclusão
2. **Gerenciar Categorias**: Criar categorias para usuários e espaços
3. **Gerenciar Níveis**: Configurar permissões de acesso

### **RF02 - Gestão de Espaços**
1. **Gerenciar Espaços**: Cadastro completo com equipamentos
2. **Pesquisar Espaços**: Filtros avançados para encontrar espaços

### **Como usar a Interface:**
1. **Janela Principal**: Escolha entre RF01 ou RF02
2. **Formulários**: Preencha os campos e clique "Salvar"
3. **Tabelas**: Clique em uma linha para editar
4. **Pesquisa**: Use os filtros para encontrar dados específicos

---

## ⌨️ **Modo Console - Demonstração Automática**

O modo console executa automaticamente:

1. **Criação de Níveis de Acesso**
2. **Criação de Categorias** (usuários e espaços)
3. **Cadastro de Usuários** com validação
4. **Cadastro de Espaços** com equipamentos
5. **Demonstração de Pesquisas**

**Duração**: ~30 segundos de demonstração completa

---

## 🔧 **Resolução de Problemas**

### **Erro: "java: command not found"**
```bash
# Verificar se Java está no PATH
echo $JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### **Erro: "Permission denied"**
```bash
# Dar permissão aos scripts
chmod +x *.sh
```

### **Erro de compilação**
```bash
# Limpar e recompilar
rm -rf bin/
./compilar.sh
```

### **Interface gráfica não abre**
```bash
# Usar modo console como alternativa
./executar-console.sh

# Ou executar manualmente
java -cp bin reserva.Main
```

### **Problemas no Windows**
```cmd
# Usar comandos equivalentes
javac -d bin -sourcepath src/main/java src/main/java/reserva/**/*.java
java -cp bin reserva.LauncherSimples
```

---

## 📚 **Documentação Adicional**

- **`README.md`**: Documentação técnica completa
- **`INTERFACE_GRAFICA.md`**: Guia detalhado da GUI
- **`CORRECOES_ADMIN_CONTROLLER.md`**: Correções técnicas implementadas

---

## 🎯 **Para Professores/Avaliadores**

### **Demonstração Rápida (5 minutos)**
1. Execute `./executar-console.sh` para ver todas as funcionalidades
2. Execute `./executar-gui.sh` para testar a interface interativa
3. Teste cadastro de usuários e espaços na GUI

### **Funcionalidades Implementadas**
- ✅ **RF01**: Gestão completa de usuários, categorias e níveis
- ✅ **RF02**: Gestão completa de espaços e pesquisa avançada
- ✅ **Arquitetura**: Camadas bem definidas (Domain, Application, Presentation, Infrastructure)
- ✅ **Interface**: GUI completa + modo console
- ✅ **Validações**: Regras de negócio implementadas
- ✅ **Padrões**: Repository, DTO, MVC

### **Pontos de Destaque**
- **Conformidade**: Código segue exatamente o diagrama de classes
- **Usabilidade**: Interface intuitiva com validações
- **Flexibilidade**: Múltiplas formas de execução
- **Qualidade**: Código bem estruturado e documentado

---

## 📞 **Suporte**

Se houver problemas na execução:

1. **Verificar Java**: `java -version` deve mostrar versão 8+
2. **Verificar permissões**: `ls -la *.sh` deve mostrar `-rwxr-xr-x`
3. **Recompilar**: `./compilar.sh` deve mostrar "✓ Compilação concluída"
4. **Modo seguro**: `./executar-console.sh` sempre funciona

**O sistema está pronto para uso e demonstração!** 🎉
