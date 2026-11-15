package Controller;

import View.AtividadeView;
import View.InstituicaoParceiraView;
import View.MenuPrincipalView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalController {

    private MenuPrincipalView view;

    public MenuPrincipalController(MenuPrincipalView view) {
        this.view = view;

        // Abre a tela de instituições
        this.view.getBtnInstituicoes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstituicaoParceiraView telaInst = new InstituicaoParceiraView();
                new InstituicaoParceiraController(telaInst);
                telaInst.setVisible(true);
            }
        });

        // Abre a tela de atividades
        this.view.getBtnAtividades().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AtividadeView telaAtiv = new AtividadeView();
                new AtividadeController(telaAtiv);
                telaAtiv.setVisible(true);
            }
        });
    }
}