package tictactoesingle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeSingle {
    JFrame f;
    JPanel cardPane;
    startPane SP;
    playPane PP;
    endPane EP;
    CardLayout card;

    btnListener btls = new btnListener();
    JButton btnPlay, btnExit, btnBackToMenu, btnExit2;
    JButton[][] btn;
    int[][] grid = new int[3][3];
    boolean player1F = true;
    int result = -1;

    public static void main(String[] args) {
        new TicTacToeSingle();
    }

    TicTacToeSingle() {
        f = new JFrame();
        f.setTitle("TicTacToe - Single Vision");
        f.setBounds(500, 80, 500, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setAlwaysOnTop(true);

        f.setContentPane(getCardPane());

        f.setVisible(true);
    }

    JPanel getCardPane() {
        cardPane = new JPanel();
        card = new CardLayout();

        cardPane.setLayout(card);

        SP = new startPane();
        PP = new playPane();

        cardPane.add(SP);
        cardPane.add(PP);

        return cardPane;
    }

    class startPane extends JPanel {

        startPane() {
            btnPlay = new JButton(new ImageIcon(getClass().getResource("/img/TTTPlayBtn.png")));
            btnExit = new JButton(new ImageIcon(getClass().getResource("/img/TTTExitBtn.png")));

            btnPlay.setContentAreaFilled(false);
            btnPlay.setFocusPainted(false);
            btnPlay.setBorderPainted(false);

            btnExit.setContentAreaFilled(false);
            btnExit.setFocusPainted(false);
            btnExit.setBorderPainted(false);

            btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnPlay.addActionListener(btls);
            btnExit.addActionListener(btls);

            setLayout(null);
            btnPlay.setBounds(200, 375, 120, 60);
            btnExit.setBounds(200, 465, 120, 60);
            add(btnPlay);
            add(btnExit);

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(new ImageIcon(getClass().getResource("/img/TTTStartPane.png")).getImage(), 0, 0, f.getSize().width,f.getSize().height,f);

        }
    }

    class playPane extends JPanel {
        JLabel turn;

        playPane() {
            setLayout(null);
            btn = new JButton[3][3];

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {

                    btn[i][j] = new JButton();
                    btn[i][j].setBackground(Color.BLACK);

                    btn[i][j].setContentAreaFilled(false);
                    btn[i][j].setFocusPainted(false);
                    btn[i][j].setBorderPainted(false);

                    btn[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));

                    btn[i][j].addActionListener(btls);

                    btn[i][j].setBounds(125 * i + 67, 125 * j + 205, 115, 115);

                    add(btn[i][j]);
                }
            }

            turn = new JLabel(new ImageIcon(getClass().getResource("/img/TTTPlayer1Turn.png")));
            turn.setBounds(205, 115, 90, 50);
            add(turn);

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(new ImageIcon(getClass().getResource("/img/TTTPlayPane.png")).getImage(), 0, 0, f.getSize().width,f.getSize().height,f);

        }

        public void turn() {
            if(result == -1) {
                if(player1F) turn.setIcon(new ImageIcon(getClass().getResource("/img/TTTPlayer1Turn.png")));
                else turn.setIcon(new ImageIcon(getClass().getResource("/img/TTTPlayer2Turn.png")));
            }
        }

    }

    class endPane extends JPanel {

        endPane(int Aux, int Flag) {

            //btnBackToMenu = new JButton(new ImageIcon("btnPlay.png"));
            btnExit2 = new JButton(new ImageIcon(getClass().getResource("/img/TTTExitBtn.png")));

            //btnBackToMenu.setContentAreaFilled(false);
            //btnBackToMenu.setFocusPainted(false);
            //btnBackToMenu.setBorderPainted(false);

            btnExit2.setContentAreaFilled(false);
            btnExit2.setFocusPainted(false);
            btnExit2.setBorderPainted(false);

            btnExit2.setCursor(new Cursor(Cursor.HAND_CURSOR));

            //btnBackToMenu.addActionListener(btls);
            btnExit2.addActionListener(btls);

            setLayout(null);
            //btnBackToMenu.setBounds(190, 380, 105, 70);
            btnExit2.setBounds(190, 590, 120, 60);
            //add(btnBackToMenu);
            add(btnExit2);

            switch(Flag) {
            case 1:
                for(int i = 0; i < 3; i++) {
                    if(result == 1) btn[Aux][i].setIcon(new ImageIcon(getClass().getResource("/img/TTTORedBtn.png")));
                    else btn[Aux][i].setIcon(new ImageIcon(getClass().getResource("/img/TTTXRedBtn.png")));
                }
                break;
            case 2:
                for(int i = 0; i < 3; i++) {
                    if(result == 1) btn[i][Aux].setIcon(new ImageIcon(getClass().getResource("/img/TTTORedBtn.png")));
                    else btn[i][Aux].setIcon(new ImageIcon(getClass().getResource("/img/TTTXRedBtn.png")));
                }
                break;
            case 3:
                for(int i = 0; i < 3; i++) {
                    if(result == 1) btn[i][i].setIcon(new ImageIcon(getClass().getResource("/img/TTTORedBtn.png")));
                    else btn[i][i].setIcon(new ImageIcon(getClass().getResource("/img/TTTXRedBtn.png")));
                }
                break;
            case 4:
                for(int i = 0; i < 3; i++) {
                    if(result == 1) btn[i][2 - i].setIcon(new ImageIcon(getClass().getResource("/img/TTTORedBtn.png")));
                    else btn[i][2 - i].setIcon(new ImageIcon(getClass().getResource("/img/TTTXRedBtn.png")));
                }
                break;
            default:
                break;
            }

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    btn[i][j].removeActionListener(btls);
                    btn[i][j].setCursor(null);
                    add(btn[i][j]);
                }
            }

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(new ImageIcon(getClass().getResource("/img/TTTPlayPane.png")).getImage(), 0, 0, f.getSize().width,f.getSize().height,f);

            if(result == 0) {
                g.drawImage(new ImageIcon(getClass().getResource("/img/TTTEven.png")).getImage(), 200, 90, 100, 30, f);
            }else if(result == 1) {
                g.drawImage(new ImageIcon(getClass().getResource("/img/TTTWin.png")).getImage(), 40, 90, 100, 30, f);
                g.drawImage(new ImageIcon(getClass().getResource("/img/TTTLose.png")).getImage(), 350, 90, 100, 30, f);
            }else if(result == 2) {
                g.drawImage(new ImageIcon(getClass().getResource("/img/TTTWin.png")).getImage(), 350, 90, 100, 30, f);
                g.drawImage(new ImageIcon(getClass().getResource("/img/TTTLose.png")).getImage(), 40, 90, 100, 30, f);
            }

        }

    }

    class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnPlay ) {
                card.next(cardPane);
            }/*else if(e.getSource() == btnBackToMenu) {
                reset();
                card.next(cardPane);
            }*/else if(e.getSource() == btnExit || e.getSource() == btnExit2) {
                System.exit(0);
            }else {
                setBtn(e);
            }
        }

        public void setBtn(ActionEvent e) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(e.getSource() == btn[i][j]) {
                        if(player1F) {
                            if(btn[i][j].getIcon() == null) {
                                btn[i][j].setCursor(null);
                                btn[i][j].setIcon(new ImageIcon(getClass().getResource("/img/TTTOBtn.png")));
                                grid[i][j] = 1;
                                judge();
                                player1F = !player1F;
                                PP.turn();
                            }
                            return;
                        }else {
                            if(btn[i][j].getIcon() == null) {
                                btn[i][j].setCursor(null);
                                btn[i][j].setIcon(new ImageIcon(getClass().getResource("/img/TTTXBtn.png")));
                                grid[i][j] = 2;
                                judge();
                                player1F = !player1F;
                                PP.turn();
                            }
                            return;
                        }
                    }
                }
            }
        }

        public void judge() {
            for(int i = 0; i < 3; i++) {
                if(grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][0] != 0) {
                    if(player1F) result = 1;
                    else result = 2;
                    EP = new endPane(i, 1);
                    cardPane.add(EP);
                    card.next(cardPane);
                    return;
                }
                if(grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[0][i] != 0) {
                    if(player1F) result = 1;
                    else result = 2;
                    EP = new endPane(i, 2);
                    cardPane.add(EP);
                    card.next(cardPane);
                    return;
                }
            }

            if(grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[1][1] != 0) {
                if(player1F) result = 1;
                else result = 2;
                EP = new endPane(0, 3);
                cardPane.add(EP);
                card.next(cardPane);
                return;
            }
            if(grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[1][1] != 0) {
                if(player1F) result = 1;
                else result = 2;
                EP = new endPane(0, 4);
                cardPane.add(EP);
                card.next(cardPane);
                return;
            }

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(grid[i][j] == 0) return;
                }
            }
            result = 0;
            EP = new endPane(0, 5);
            cardPane.add(EP);
            card.next(cardPane);
        }
        /*
        public void reset() {

        }
        */
    }
}
