import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Creditos extends JFrame {

    ImageIcon imagem = new ImageIcon("./img/GustavoGarciaSantanaCreditos.png", "GuguNiD");
    JLabel label = new JLabel();

    public Creditos() {

    }

    public void criarTela() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(515, 537);
        setLocationRelativeTo(null);
        label.setIcon(imagem);
        label.setHorizontalAlignment(0);
        add(label);
    }

    

}
