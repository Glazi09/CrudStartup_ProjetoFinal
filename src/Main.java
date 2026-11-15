import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import View.MenuPrincipalView;
import Controller.MenuPrincipalController;

public class Main {

    public static void main(String[] args) {

        // Deixa a janela com o visual do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível carregar o visual nativo do sistema.");
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipalView view = new MenuPrincipalView();
                new MenuPrincipalController(view);
                view.setVisible(true);
            }
        });
    }
}