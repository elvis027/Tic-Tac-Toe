package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class TicTacToeServer {
    JFrame f;
    JPanel cardPane;
    startPane SP;
    playPane PP;
    endPane EP;
    CardLayout card;

    btnListener btls = new btnListener();
    JButton btnPlay, btnExit, btnExit2;
    JButton[][] btn;
    int[][] grid = new int[3][3];
    boolean player1F = true;
    int result = -1;

    Socket socketServer;
    boolean socketBtn = false;

    public static void main(String[] args) {

        TicTacToeServer TServer = new TicTacToeServer();
        TServer.receive();

    }

    TicTacToeServer() {
        try {
            processSocket();

            f = new JFrame();
            f.setTitle("TicTacToe - Server - Player1");
            f.setBounds(1000, 120, 500, 700);
            f.setResizable(false);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setAlwaysOnTop(true);

            f.setContentPane(getCardPane());

            f.setVisible(true);
        }catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect!", "TTTServer", JOptionPane.INFORMATION_MESSAGE);
            try {
                socketServer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        }
    }

    void processSocket() throws IOException {
            ServerSocket server = new ServerSocket(7777);
               InetAddress a = InetAddress.getLocalHost();
            System.out.println("Server IP is " + a.getHostAddress());
            System.out.println("Server Port is " + server.getLocalPort());
            System.out.println("Waiting for connection...");
            socketServer = server.accept();
            server.close();
            System.out.println("Connected!");
    }

    void receive() {
        do {
            try {
                BufferedReader brInFromClient = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));

                String str = brInFromClient.readLine();

                if(str.length() == 1) {
                    int i = 0;
                    int j = Character.getNumericValue(str.charAt(0));
                    socketBtn = true;
                    btn[i][j].doClick();
                }else if(str.length() == 2) {
                    int i = Character.getNumericValue(str.charAt(0));
                    int j = Character.getNumericValue(str.charAt(1));
                    socketBtn = true;
                    btn[i][j].doClick();
                }
            }catch(IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect!", "TTTServer", JOptionPane.INFORMATION_MESSAGE);
                try {
                    socketServer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        }while(result == -1);

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
            btnExit2 = new JButton(new ImageIcon(getClass().getResource("/img/TTTExitBtn.png")));

            btnExit2.setContentAreaFilled(false);
            btnExit2.setFocusPainted(false);
            btnExit2.setBorderPainted(false);

            btnExit2.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnExit2.addActionListener(btls);

            setLayout(null);
            btnExit2.setBounds(190, 590, 120, 60);
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
            if(e.getSource() == btnPlay) {
                card.next(cardPane);
            }else if(e.getSource() == btnExit || e.getSource() == btnExit2) {
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
                                send(String.valueOf(i * 10 + j) + '\n');
                                btn[i][j].setIcon(new ImageIcon(getClass().getResource("/img/TTTOBtn.png")));
                                grid[i][j] = 1;
                                judge();
                                player1F = !player1F;
                                PP.turn();
                            }
                            return;
                        }else if(!player1F && socketBtn) {
                            if(btn[i][j].getIcon() == null) {
                                btn[i][j].setCursor(null);
                                btn[i][j].setIcon(new ImageIcon(getClass().getResource("/img/TTTXBtn.png")));
                                grid[i][j] = 2;
                                judge();
                                player1F = !player1F;
                                PP.turn();
                                socketBtn = false;
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

        void send(String str) {
            try {
                DataOutputStream  dosOutToClient = new DataOutputStream(socketServer.getOutputStream());
                dosOutToClient.writeBytes(str);
            }catch(IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect!", "TTTServer", JOptionPane.INFORMATION_MESSAGE);
                try {
                    socketServer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }

        }

    }

}
