import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tela extends JFrame implements ActionListener {
    int linhas;
    int colunas;
    int minas;
    ArrayList<Campo> todosCampos = new ArrayList<>();
    ArrayList<Integer> sorteio = new ArrayList<>();

    JFrame tela = new JFrame();
    JPanel painel = new JPanel();
    JLabel tempo;

    public Tela() {

    }

    JPanel painelPrincipal;

    public void criarTela(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        // Menu superior
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Dificuldade");
        JMenu credito = new JMenu("Créditos");
        JMenuItem verCredito = new JMenuItem("Desenvolvedor");
        credito.add(verCredito);
        verCredito.addActionListener(this);
        JMenuItem item1 = new JMenuItem("Fácil");
        item1.addActionListener(this);
        JMenuItem item2 = new JMenuItem("Médio");
        item2.addActionListener(this);
        JMenuItem item3 = new JMenuItem("Difícil");
        item3.addActionListener(this);
        JMenuItem item4 = new JMenuItem("Personalizado");
        item4.addActionListener(this);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menuBar.add(menu);
        menuBar.add(credito);
        menuBar.setPreferredSize(new Dimension(385, 30));
        menuBar.setMaximumSize(new Dimension(900, 30));
        menuBar.setMinimumSize(new Dimension(300, 30));
        tela.setJMenuBar(menuBar);
        // Menu superior

        // este painel será o container principal da tela
        painelPrincipal = new JPanel();
        tela.setContentPane(painelPrincipal);
        BoxLayout box = new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS);
        painelPrincipal.setLayout(box);
        // botoes opcoes
        GridBagLayout tempoLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel painelTempo = new JPanel(tempoLayout);
        painelTempo.setPreferredSize(new Dimension(200, 100));
        painelTempo.setMaximumSize(new Dimension(400, 100));
        painelTempo.setMinimumSize(new Dimension(365, 100));
        painelTempo.setBackground(new Color(187, 187, 187));
        painelTempo.setBorder(BorderFactory.createLoweredBevelBorder());
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton mudo = new JButton("Mudo");
        mudo.addActionListener(this);
        mudo.setPreferredSize(new Dimension(65, 30));
        c.insets = new Insets(0, 5, 0, 10);
        c.ipadx = 3;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        painelTempo.add(mudo, c);
        JButton reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setPreferredSize(new Dimension(50, 45));
        c.insets = new Insets(0, 40, 0, 40);
        c.weightx = 1.5;
        c.gridx = 1;
        c.gridy = 0;
        painelTempo.add(reset, c);
        tempo = new JLabel("");
        tempo.setPreferredSize(new Dimension(65, 30));
        c.insets = new Insets(0, 10, 0, 5);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        painelTempo.add(tempo, c);
        painelPrincipal.add(painelTempo);
        // botoes opcoes

        // borda tabuleiro
        JPanel quadroTabuleiro = new JPanel();
        BorderLayout border = new BorderLayout(0, 0);
        quadroTabuleiro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        quadroTabuleiro.setLayout(border);
        painelPrincipal.add(quadroTabuleiro);
        // borda tabuleiro

        // TABULEIRO
        GridLayout layout = new GridLayout(linhas, colunas);
        painel.setLayout(layout);
        quadroTabuleiro.add(painel);
        // TABULEIRO

        // chamada TELA final
        painelPrincipal.setPreferredSize(new Dimension(385, 430));
        tela.setVisible(true);
        tela.setDefaultCloseOperation(EXIT_ON_CLOSE);
        criarCampos(painel);
        associarCampos();
        tela.pack();
        tela.setLocationRelativeTo(null);
        // chamada final
    }

    int contarCampoId = -1;

    public void criarCampos(JPanel painel) {
        aleatorio(minas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                contarCampoId++;
                if (sorteio.contains(contarCampoId)) {
                    Campo campo = new Campo(contarCampoId, false, true, false, i, j);
                    painel.add(campo);
                    todosCampos.add(campo);
                } else {
                    Campo campo = new Campo(contarCampoId, false, false, false, i, j);
                    painel.add(campo);
                    todosCampos.add(campo);
                }
            }
        }
    }

    public void associarCampos() {
        for (Campo campo1 : todosCampos) {
            for (Campo campo2 : todosCampos) {
                campo1.adicinarVizinhos(campo2);
            }
        }
    }

    public void abrir(int id) {
        todosCampos.parallelStream().filter(c -> c.getId() == id)
                .findFirst()
                .ifPresent(c -> c.abrirCampo());

    }

    public void aleatorio(int qtdMinas) {
        do {
            int numeroMina = (int) (Math.random() * (linhas * colunas));
            if (!sorteio.contains(numeroMina)) {
                sorteio.add(numeroMina);
            }
        } while (sorteio.size() < minas);
    }

    public void derrota() {
        App app = new App();
        app.derrota();
    }

    public void condicaoVitoria() {
        App app = new App();
        app.condicaoVitoria();
    }

    String mode = "Fácil";

    @Override
    public void actionPerformed(ActionEvent e) {
        App app = new App();


        if (e.getSource() instanceof JMenuItem) {
            JMenuItem selecionar = (JMenuItem) e.getSource();
            if (selecionar.getText().equals("Fácil")) {
                JOptionPane.showMessageDialog(null, "Modo Fácil");
                tela.setPreferredSize(new Dimension(401, 499));
                tela.revalidate();
                app.modoJogo("facil");
                app.resetTempo();
                mode = "Fácil";

            } else if (selecionar.getText().equals("Médio")) {
                JOptionPane.showMessageDialog(null, "Modo Médio");
                tela.setPreferredSize(new Dimension(600, 700));
                tela.revalidate();
                app.modoJogo("medio");
                app.resetTempo();
                mode = "Médio";
            } else if (selecionar.getText().equals("Difícil")) {
                JOptionPane.showMessageDialog(null, "Modo Díficil");
                tela.setPreferredSize(new Dimension(1150, 900));
                tela.revalidate();
                app.modoJogo("dificil");
                app.resetTempo();
                mode = "Difícil";
            } else if (selecionar.getText().equals("Personalizado")) {

                tela.setPreferredSize(new Dimension(1800, 1000));
                tela.revalidate();
                app.modoJogo("personalizado");
                app.resetTempo();
                mode = "Personalizado";
            } else if(selecionar.getText().equals("Desenvolvedor")){
                Creditos creditos = new Creditos();
                creditos.criarTela();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton resetar = (JButton) e.getSource();
            if (resetar.getText().equals("Reset")) {
                resetar.setText(mode);
                app.resetar(mode);
            } else if (resetar.getText().equals("Som")) {
                app.comecarMusica();
                resetar.setText("Mudo");
            } else if (resetar.getText().equals("Mudo")) {
                app.pausarMusica();
                resetar.setText("Som");
                
            }

        }

    }

}
