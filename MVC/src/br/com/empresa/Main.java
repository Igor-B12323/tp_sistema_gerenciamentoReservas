package br.com.empresa;

import br.com.empresa.view.FramePrincipal;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    FramePrincipal form = new FramePrincipal();
                    form.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    form.setVisible(true);
                });
    }
}
