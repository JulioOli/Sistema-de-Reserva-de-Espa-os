package reserva.gui;

import reserva.domain.model.Espaco;
import reserva.presentation.EspacoController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * PesquisarEspacosWindow - Interface para pesquisa de espaços (RF02)
 */
public class PesquisarEspacosWindow extends JDialog {
    
    private EspacoController espacoController;
    private JTable resultadosTable;
    private DefaultTableModel tableModel;
    private JTextField nomeField, localizacaoField, capacidadeMinField, categoriaField;
    private JCheckBox disponiveisCheckBox;
    
    public PesquisarEspacosWindow(Frame parent, EspacoController espacoController) {
        super(parent, "Pesquisar Espaços - RF02", true);
        this.espacoController = espacoController;
        initializeGUI();
        loadAllEspacos();
    }
    
    private void initializeGUI() {
        setSize(1000, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("🔍 Pesquisar Espaços Disponíveis", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Filtros
        JPanel filtrosPanel = createFiltrosPanel();
        mainPanel.add(filtrosPanel, BorderLayout.NORTH);
        
        // Resultados
        JPanel resultadosPanel = createResultadosPanel();
        mainPanel.add(resultadosPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFiltrosPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Filtros de Pesquisa"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        nomeField = new JTextField(15);
        nomeField.setToolTipText("Digite parte do nome do espaço");
        panel.add(nomeField, gbc);
        
        // Localização
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Localização:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        localizacaoField = new JTextField(15);
        localizacaoField.setToolTipText("Digite parte da localização");
        panel.add(localizacaoField, gbc);
        
        // Capacidade mínima
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Capacidade mín:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        capacidadeMinField = new JTextField(15);
        capacidadeMinField.setToolTipText("Capacidade mínima necessária");
        panel.add(capacidadeMinField, gbc);
        
        // Categoria
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        categoriaField = new JTextField(15);
        categoriaField.setToolTipText("Digite o nome da categoria");
        panel.add(categoriaField, gbc);
        
        // Apenas disponíveis
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        disponiveisCheckBox = new JCheckBox("Apenas espaços disponíveis");
        disponiveisCheckBox.setSelected(true);
        disponiveisCheckBox.setToolTipText("Mostrar apenas espaços que podem ser reservados");
        panel.add(disponiveisCheckBox, gbc);
        
        // Botões
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton pesquisarBtn = new JButton("🔍 Pesquisar");
        pesquisarBtn.setBackground(new Color(33, 150, 243));
        pesquisarBtn.setForeground(Color.WHITE);
        pesquisarBtn.addActionListener(e -> pesquisarEspacos());
        
        JButton limparBtn = new JButton("🗑️ Limpar Filtros");
        limparBtn.addActionListener(e -> limparFiltros());
        
        JButton todosBtn = new JButton("📋 Mostrar Todos");
        todosBtn.addActionListener(e -> loadAllEspacos());
        
        buttonPanel.add(pesquisarBtn);
        buttonPanel.add(limparBtn);
        buttonPanel.add(todosBtn);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createResultadosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resultados da Pesquisa"));
        
        // Tabela
        String[] columns = {"ID", "Nome", "Localização", "Capacidade", "Categoria", "Equipamentos"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        resultadosTable = new JTable(tableModel);
        resultadosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar largura das colunas
        resultadosTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        resultadosTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        resultadosTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        resultadosTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        resultadosTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        resultadosTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(resultadosTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Informações dos resultados
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Dica: Clique duas vezes em um espaço para ver detalhes completos");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        infoPanel.add(infoLabel);
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        // Double click para detalhes
        resultadosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showEspacoDetails();
                }
            }
        });
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Estatísticas à esquerda
        JLabel statsLabel = new JLabel("Total de espaços encontrados: 0");
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(statsLabel, BorderLayout.WEST);
        
        // Botão fechar à direita
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeBtn = new JButton("Fechar");
        closeBtn.addActionListener(e -> dispose());
        rightPanel.add(closeBtn);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void pesquisarEspacos() {
        String nome = nomeField.getText().trim();
        String localizacao = localizacaoField.getText().trim();
        String capacidadeStr = capacidadeMinField.getText().trim();
        String categoria = categoriaField.getText().trim();
        boolean apenasDisponiveis = disponiveisCheckBox.isSelected();
        
        List<Espaco> espacos;
        
        // Se todos os campos estão vazios, mostrar todos
        if (nome.isEmpty() && localizacao.isEmpty() && capacidadeStr.isEmpty() && categoria.isEmpty()) {
            espacos = espacoController.listarTodosEspacos();
        } else {
            // Aplicar filtros
            espacos = espacoController.listarTodosEspacos();
            
            // Filtrar por nome
            if (!nome.isEmpty()) {
                espacos = espacos.stream()
                    .filter(e -> e.getTipo().toLowerCase().contains(nome.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Filtrar por localização
            if (!localizacao.isEmpty()) {
                espacos = espacos.stream()
                    .filter(e -> e.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Filtrar por capacidade mínima
            if (!capacidadeStr.isEmpty()) {
                try {
                    int capacidadeMin = Integer.parseInt(capacidadeStr);
                    espacos = espacos.stream()
                        .filter(e -> e.getCapacidade() >= capacidadeMin)
                        .collect(java.util.stream.Collectors.toList());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Capacidade deve ser um número válido!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Filtrar por categoria
            if (!categoria.isEmpty()) {
                espacos = espacos.stream()
                    .filter(e -> e.getCategoria() != null && 
                        e.getCategoria().getNome().toLowerCase().contains(categoria.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
            }
        }
        
        // Aplicar filtro de disponibilidade (simulado)
        if (apenasDisponiveis) {
            // Por enquanto, todos os espaços são considerados disponíveis
            // Em uma implementação real, verificaria reservas existentes
        }
        
        displayResultados(espacos);
    }
    
    private void displayResultados(List<Espaco> espacos) {
        tableModel.setRowCount(0);
        
        for (Espaco espaco : espacos) {
            String equipamentos = "";
            if (espaco.getEquipamentos() != null && !espaco.getEquipamentos().isEmpty()) {
                equipamentos = String.join(", ", espaco.getEquipamentos());
                if (equipamentos.length() > 50) {
                    equipamentos = equipamentos.substring(0, 47) + "...";
                }
            }
            
            Object[] row = {
                espaco.getId(),
                espaco.getTipo(),
                espaco.getLocalizacao(),
                espaco.getCapacidade(),
                espaco.getCategoria() != null ? espaco.getCategoria().getNome() : "Sem categoria",
                equipamentos.isEmpty() ? "Nenhum" : equipamentos
            };
            tableModel.addRow(row);
        }
        
        // Atualizar estatísticas
        updateStats(espacos.size());
    }
    
    private void updateStats(int count) {
        JPanel footerPanel = (JPanel) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        JLabel statsLabel = (JLabel) ((BorderLayout) footerPanel.getLayout()).getLayoutComponent(BorderLayout.WEST);
        statsLabel.setText("Total de espaços encontrados: " + count);
    }
    
    private void loadAllEspacos() {
        List<Espaco> espacos = espacoController.listarTodosEspacos();
        displayResultados(espacos);
    }
    
    private void limparFiltros() {
        nomeField.setText("");
        localizacaoField.setText("");
        capacidadeMinField.setText("");
        categoriaField.setText("");
        disponiveisCheckBox.setSelected(true);
    }
    
    private void showEspacoDetails() {
        int selectedRow = resultadosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            
            // Buscar espaço completo
            List<Espaco> espacos = espacoController.listarTodosEspacos();
            for (Espaco espaco : espacos) {
                if (espaco.getId().equals(id)) {
                    StringBuilder details = new StringBuilder();
                    details.append("═══ DETALHES DO ESPAÇO ═══\n\n");
                    details.append("ID: ").append(espaco.getId()).append("\n");
                    details.append("Tipo: ").append(espaco.getTipo()).append("\n");
                    details.append("Localização: ").append(espaco.getLocalizacao()).append("\n");
                    details.append("Capacidade: ").append(espaco.getCapacidade()).append(" pessoas\n");
                    details.append("Categoria: ").append(espaco.getCategoria() != null ? 
                        espaco.getCategoria().getNome() : "Sem categoria").append("\n\n");
                    
                    if (espaco.getEquipamentos() != null && !espaco.getEquipamentos().isEmpty()) {
                        details.append("EQUIPAMENTOS DISPONÍVEIS:\n");
                        for (String equipamento : espaco.getEquipamentos()) {
                            details.append("  • ").append(equipamento).append("\n");
                        }
                    } else {
                        details.append("EQUIPAMENTOS: Nenhum equipamento cadastrado\n");
                    }
                    
                    details.append("\nSTATUS: Disponível para reserva");
                    
                    JTextArea textArea = new JTextArea(details.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    
                    JOptionPane.showMessageDialog(this, scrollPane, 
                        "Detalhes do Espaço - " + espaco.getTipo(), 
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }
}
