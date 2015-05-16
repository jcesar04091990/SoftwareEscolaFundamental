package br.com.Classes;

import br.com.Interface.TelaLogin;
import br.com.Interface.TelaPrincipal1;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutarProjeto {
    
    public static void main(String[] args) {
   TelaLogin tl;
        try {
            tl = new TelaLogin();
            tl.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExecutarProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    
    }
}
