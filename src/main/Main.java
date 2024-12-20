package main;

import view.RegisterView;

public class Main {

    public static void main(String[] args) {
        //ini langsung ngejalanan halaman register
        javax.swing.SwingUtilities.invokeLater(() -> {
            //nanti ubah yang dibawah jadi LoginView
            new RegisterView().setVisible(true);
        });
    }
}
