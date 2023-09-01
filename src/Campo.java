import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Campo extends JButton implements MouseListener {
    int id;
    boolean aberto = false;
    boolean minado;
    boolean status;
    int linha;
    int coluna;

    public Campo() {

    }

    public Campo(int id, boolean aberto, boolean minado, boolean status, int linha, int coluna) {
        this.id = id;
        this.aberto = aberto;
        this.minado = minado;
        this.linha = linha;
        this.coluna = coluna;
        this.status = status;
        setBackground(Color.GRAY);
        setSize(50, 50);
        addMouseListener(this);
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    ArrayList<Campo> vizinhos = new ArrayList<>();

    public boolean adicinarVizinhos(Campo campo) {
        boolean linhaDif = linha != campo.linha;
        boolean colunaDif = coluna != campo.coluna;
        boolean diagonal = linhaDif && colunaDif;

        int deltaLinha = Math.abs(linha - campo.linha);
        int deltaColuna = Math.abs(coluna - campo.coluna);
        int delta = deltaLinha + deltaColuna;

        if (delta == 1 && !diagonal) {
            vizinhos.add(campo);
            return true;
        } else if (delta == 2 && diagonal) {
            vizinhos.add(campo);
            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(c -> c.minado);
    }

    public long contarMinas() {
        return vizinhos.stream().filter(c -> c.minado).count();
    }

    public boolean abrirCampo() {
        if (!aberto && !status) {
            setAberto(true);
            if (vizinhancaSegura()) {
                vizinhos.forEach(c -> c.abrirCampo());
            }
            return true;
        } else {
            return false;
        }
    }

    public void derrota() {
        Tela tela = new Tela();
        tela.derrota();

    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;

        if (aberto) {
            setBackground(new Color(150, 150, 151));
            if (contarMinas() == 0) {
                setText("");
                setBorder(BorderFactory.createLoweredBevelBorder());
            } else {
                setText("" + contarMinas());
                switch ("" + contarMinas()) {
                    case "1":
                        setForeground(Color.BLUE);
                        break;
                    case "2":
                        setForeground(Color.GREEN);
                        break;
                    case "3":
                        setForeground(Color.ORANGE);
                        break;
                    case "4", "5":
                        setForeground(Color.PINK);
                        break;
                    case "6", "7":
                        setForeground(Color.RED);
                        break;
                    case "8":
                        setForeground(Color.BLACK);
                        break;

                    default:
                        break;
                }
                setBorder(BorderFactory.createLoweredSoftBevelBorder());
            }
        }
    }

    public boolean isMinado() {
        return minado;
    }

    public void setMinado(boolean minado) {
        this.minado = minado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tela tela = new Tela();
        if (e.getSource() instanceof Campo) {
            Campo campo = (Campo) e.getSource();
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (campo.minado && !campo.status) {
                    Som som = new Som("explosion.wav");
                    som.start();
                    derrota();
                    som.stop();
                } else {
                    campo.abrirCampo();
                    tela.condicaoVitoria();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (campo.isStatus() == false) {
                    campo.setStatus(true);
                    campo.setBackground(Color.PINK);
                    tela.condicaoVitoria();
                } else {
                    campo.setStatus(false);
                    campo.setBackground(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
