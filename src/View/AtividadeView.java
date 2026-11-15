package View;

import Model.InstituicaoParceira;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AtividadeView extends JFrame {

    // Campos do formulário
    private JTextField txtId = new JTextField();
    private JTextField txtTitulo = new JTextField();
    private JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Curso", "Palestra", "Voluntariado", "Estágio"});
    private JTextField txtHoras = new JTextField();
    private JComboBox<InstituicaoParceira> cbInstituicao = new JComboBox<>();

    // Botões
    private JButton btnSalvar = new JButton("Salvar");
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnExcluir = new JButton("Excluir");
    private JButton btnLimpar = new JButton("Limpar");

    // Tabela
    private DefaultTableModel modeloTabela;
    private JTable tabelaAtividades;

    public AtividadeView() {
        setTitle("Linka+ | Atividades");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        txtId.setEditable(false);

        // 1. Formulário
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(txtId);
        painelFormulario.add(new JLabel("Título:"));
        painelFormulario.add(txtTitulo);
        painelFormulario.add(new JLabel("Tipo:"));
        painelFormulario.add(cbTipo);
        painelFormulario.add(new JLabel("Horas complementares:"));
        painelFormulario.add(txtHoras);
        painelFormulario.add(new JLabel("Instituição parceira:"));
        painelFormulario.add(cbInstituicao);

        // 2. Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelFormulario, BorderLayout.CENTER);
        painelTopo.add(painelBotoes, BorderLayout.SOUTH);

        // 3. Tabela
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Título", "Tipo", "Horas", "Instituição"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        tabelaAtividades = new JTable(modeloTabela);

        add(painelTopo, BorderLayout.NORTH);
        add(new JScrollPane(tabelaAtividades), BorderLayout.CENTER);
    }

    // Getters usados pelo Controller
    public String getId() { return txtId.getText(); }
    public String getTitulo() { return txtTitulo.getText().trim(); }
    public String getTipo() { return (String) cbTipo.getSelectedItem(); }
    public String getHoras() { return txtHoras.getText().trim(); }
    public InstituicaoParceira getInstituicaoSelecionada() { return (InstituicaoParceira) cbInstituicao.getSelectedItem(); }

    public JComboBox<InstituicaoParceira> getCbInstituicao() { return cbInstituicao; }

    public JButton getBtnSalvar() { return btnSalvar; }
    public JButton getBtnAtualizar() { return btnAtualizar; }
    public JButton getBtnExcluir() { return btnExcluir; }
    public JButton getBtnLimpar() { return btnLimpar; }

    public JTable getTabelaAtividades() { return tabelaAtividades; }
    public DefaultTableModel getModeloTabela() { return modeloTabela; }

    // Preenche o formulário com a atividade clicada na tabela
    public void preencherFormulario(String id, String titulo, String tipo, String horas, int idInstituicao) {
        txtId.setText(id);
        txtTitulo.setText(titulo);
        cbTipo.setSelectedItem(tipo);
        txtHoras.setText(horas);

        // Procura na caixinha a instituição com o mesmo id e seleciona
        for (int i = 0; i < cbInstituicao.getItemCount(); i++) {
            if (cbInstituicao.getItemAt(i).getId() == idInstituicao) {
                cbInstituicao.setSelectedIndex(i);
                break;
            }
        }
    }

    public void limparFormulario() {
        txtId.setText("");
        txtTitulo.setText("");
        cbTipo.setSelectedIndex(0);
        txtHoras.setText("");
        if (cbInstituicao.getItemCount() > 0) {
            cbInstituicao.setSelectedIndex(0);
        }
        tabelaAtividades.clearSelection();
    }
}