package View;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {

    private JButton btnInstituicoes = new JButton("Instituições Parceiras");
    private JButton btnAtividades = new JButton("Atividades");

    public MenuPrincipalView() {
        setTitle("Linka+");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Linka+ | Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        painelBotoes.add(btnInstituicoes);
        painelBotoes.add(btnAtividades);

        add(titulo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
    }

    public JButton getBtnInstituicoes() { return btnInstituicoes; }
    public JButton getBtnAtividades() { return btnAtividades; }
}