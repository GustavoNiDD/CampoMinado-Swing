import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class App {
    static Tela tela;
    String mode;
    int i = 1;
    static Som som;

    public App() {
    }

    public static void main(String[] args) throws Exception {
        tela = new Tela();
        tela.criarTela(5, 5, 5);
        tarefa1();
        som = new Som("musica.wav");
        som.start();

    }

    public void pausarMusica() {
        som.stop();
    }

    public void comecarMusica(){
        som.start();
    }


    public void derrota() {
        tela.todosCampos.stream().forEach(c -> c.setEnabled(false));
        // tela.todosCampos.stream().forEach(c -> c.setText("" + c.id)); para testes
        tela.todosCampos.stream().filter(c -> c.minado).forEach(c -> c.setBackground(Color.RED));
        JOptionPane.showMessageDialog(null, "Boom xD, reset para um novo jogo");

    }

    public void novoJogo(int linha, int coluna, int mina) {
        tela.contarCampoId = -1;
        tela.todosCampos.clear();
        tela.sorteio.clear();
        tela.painel.removeAll();
        tela.painel.validate();
        tela.criarTela(linha, coluna, mina);
        // tela.todosCampos.stream().forEach(c -> c.setText("" + c.id)); //para testes
        // tela.todosCampos.stream().filter(c -> c.minado).forEach(c ->
        // c.setBackground(Color.RED)); //para testes
    }

    public void modoJogo(String modo) {
        if (modo.equals("facil")) {
            novoJogo(5, 5, 5);
        } else if (modo.equals("medio")) {
            novoJogo(10, 10, 20);
        } else if (modo.equals("dificil")) {
            novoJogo(20, 20, 180);
        } else if (modo.equals("personalizado")) {
            String line = JOptionPane.showInputDialog("Digite o número de linhas (Max: 30)", "20");
            while (Integer.parseInt(line) > 30) {
                line = JOptionPane.showInputDialog("Número excedido -> Digite o número de linhas (Max: 30)");
            }
            int vLineInt = Integer.parseInt(line);
            String colu = JOptionPane.showInputDialog("Digite o número de colunas (Max: 30)", "20");
            while (Integer.parseInt(colu) > 30) {
                colu = JOptionPane.showInputDialog("Número excedido -> Digite o número de colunas (Max: 30)");
            }
            int vColuInt = Integer.parseInt(colu);
            int linhaxcoluna = (vLineInt * vColuInt) - 1;
            String mine = JOptionPane.showInputDialog("Digite o número de minas (Max: " + linhaxcoluna + ")", "20");
            while (Integer.parseInt(mine) > linhaxcoluna) {
                mine = JOptionPane
                        .showInputDialog("Número excedido -> Digite o número de minas (Max: " + linhaxcoluna + ")");
            }
            int vMineInt = Integer.parseInt(mine);
            tela.setExtendedState(6);
            tela.revalidate();
            novoJogo(vLineInt, vColuInt, vMineInt);

        }
    }

    public void resetar(String mode) {
        if (mode.equals("Fácil")) {
            modoJogo("facil");
        } else if (mode.equals("Médio")) {
            modoJogo("medio");
        } else if (mode.equals("Difícil")) {
            modoJogo("dificil");
        } else if (mode.equals("Personalizado")) {
            modoJogo("personalizado");
        }
        resetTempo();
    }

    public void resetTempo() {
        segundosInt = 0;
        minutosInt = 0;
    }

    public boolean condicaoVitoria() {
        if (tela.todosCampos.stream().filter(c -> !c.minado).allMatch(c -> c.aberto)
                && tela.todosCampos.stream().filter(c -> c.minado).allMatch(c -> c.status)) {
            vitoria();
            return true;
        } else {
            return false;
        }
    }

    public boolean vitoria() {
        JOptionPane.showMessageDialog(null, "Parabéns você venceu");
        tela.todosCampos.stream().forEach(c -> c.setEnabled(false));
        return true;
    }

    static Timer timer1 = new Timer();
    Timer timer2 = new Timer();
    static int segundosInt = 0;
    static int minutosInt = 0;

    public static void tarefa1() {
        int delay = 000; // delay de 2 seg.
        int interval = 1000; // intervalo de 1 seg.

        timer1.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                segundosInt++;
                if (segundosInt == 60) {
                    minutosInt++;
                    segundosInt = 0;
                }
                tela.tempo.setText(minutosInt + " : " + segundosInt);

            }
        }, delay, interval);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}
