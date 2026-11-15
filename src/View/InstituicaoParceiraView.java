package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InstituicaoParceiraView extends JFrame {

    // Campos do formulário
    private JTextField txtId = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtCnpj = new JTextField();
    private JTextField txtEmail = new JTextField();

    // Botões
    private JButton btnSalvar = new JButton("Salvar");
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnExcluir = new JButton("Excluir");
    private JButton btnLimpar = new JButton("Limpar");

    // Tabela
    private DefaultTableModel modeloTabela;
    private JTable tabelaInstituicoes;

    public InstituicaoParceiraView() {
        setTitle("Linka+ | Instituições Parceiras");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // abre no centro da tela
        setLayout(new BorderLayout(10, 10));

        // O id só aparece, não pode ser digitado (o banco gera sozinho)
        txtId.setEditable(false);

        // 1. Formulário (parte de cima)
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(txtId);
        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(txtNome);
        painelFormulario.add(new JLabel("CNPJ:"));
        painelFormulario.add(txtCnpj);
        painelFormulario.add(new JLabel("E-mail:"));
        painelFormulario.add(txtEmail);

        // 2. Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelFormulario, BorderLayout.CENTER);
        painelTopo.add(painelBotoes, BorderLayout.SOUTH);

        // 3. Tabela (parte de baixo) - as células não podem ser editadas direto
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "CNPJ", "E-mail"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        tabelaInstituicoes = new JTable(modeloTabela);

        add(painelTopo, BorderLayout.NORTH);
        add(new JScrollPane(tabelaInstituicoes), BorderLayout.CENTER);
    }

    // Getters usados pelo Controller
    public String getId() { return txtId.getText(); }
    public String getNome() { return txtNome.getText().trim(); }
    public String getCnpj() { return txtCnpj.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }

    public JButton getBtnSalvar() { return btnSalvar; }
    public JButton getBtnAtualizar() { return btnAtualizar; }
    public JButton getBtnExcluir() { return btnExcluir; }
    public JButton getBtnLimpar() { return btnLimpar; }

    public JTable getTabelaInstituicoes() { return tabelaInstituicoes; }
    public DefaultTableModel getModeloTabela() { return modeloTabela; }

    // Preenche o formulário quando o usuário clica numa linha da tabela
    public void preencherFormulario(String id, String nome, String cnpj, String email) {
        txtId.setText(id);
        txtNome.setText(nome);
        txtCnpj.setText(cnpj);
        txtEmail.setText(email);
    }

    // Esvazia o formulário
    public void limparFormulario() {
        preencherFormulario("", "", "", "");
        tabelaInstituicoes.clearSelection();
    }
}