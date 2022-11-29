import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Objects;

public class GamePlay extends JPanel implements ActionListener, Serializable {

    boolean play = false;
    int score = 0;
    int totalBricks = 21;

    Timer timer;

    int delay = 8;

    int playerX = 310;

    int ballposX = 120;
    int ballposY = 350;

    int ballXdir = -1;
    int ballYdir = -2;

    private MapGenerator mapVar;

    private MapGenerator2 mapVar2;

    JButton easy, medium, hard, go, restart, select;

    JComboBox<String> selectMap;
    JPanel jPanel1;

    String speed = "";

    String[] Level = {"Easy", "Medium", "Hard"};

    GamePlay() {
        this.setPreferredSize(new Dimension(700, 600));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        //  this.setFocusTraversalKeysEnabled(false);
        inItComponent();

    }

    public void inItComponent() {
        restart = new JButton("Restart");
        restart.setForeground(Color.yellow);
        restart.setBackground(Color.black);
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                restartActionPerformed(evt);
            }
        });
        select = new JButton("Select");
        select.setForeground(Color.yellow);
        select.setBackground(Color.black);
        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });

        JLabel jLabel1 = new JLabel("Select the Maps");
        jLabel1.setForeground(Color.white);
        jLabel1.setVisible(true);
        jLabel1.setBounds(260, 0, 200, 210);
        jLabel1.setFont(new Font("Verdana", Font.BOLD, 20));

        selectMap = new JComboBox<>(Level);
        selectMap.setBounds(270, 130, 150, 60);
        selectMap.setForeground(Color.black);
        selectMap.setBackground(Color.white);
        selectMap.setVisible(true);
        selectMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectMapActionPerformed(evt);
            }
        });

        jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(700, 600));
        //  jPanel1.setBounds(0, 0, 700, 600);
        jPanel1.setBackground(Color.red);
        jPanel1.setFocusable(true);
        jPanel1.setLayout(null);

        JLabel jLabel2 = new JLabel("Select the Speed");
        jLabel2.setForeground(Color.white);
        jLabel2.setVisible(true);
        jLabel2.setBounds(250, 180, 200, 210);
        jLabel2.setFont(new Font("Verdana", Font.BOLD, 20));

        //easy level button
        easy = new JButton("Easy");
        easy.setBounds(150, 330, 100, 60);
        easy.setVisible(true);
        easy.setBackground(Color.LIGHT_GRAY);
        easy.setForeground(Color.white);

        //medium level button
        medium = new JButton("Medium");
        medium.setBounds(300, 330, 100, 60);
        medium.setVisible(true);
        medium.setBackground(Color.cyan);
        medium.setForeground(Color.white);

        //hard level button
        hard = new JButton("Hard");
        hard.setBounds(450, 330, 100, 60);
        hard.setVisible(true);
        hard.setBackground(Color.red);
        hard.setForeground(Color.white);

        //go button to start the game and hide the jPanel
        go = new JButton("GO");
        go.setBounds(270, 480, 150, 60);
        go.setBackground(Color.white);
        go.setForeground(Color.black);
        go.setVisible(true);

        JLabel pic = new JLabel(new ImageIcon("brick.png"));
        pic.setBounds(0, 0, 700, 600);
        pic.setVisible(true);

        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(easy);
        jPanel1.add(medium);
        jPanel1.add(hard);
        jPanel1.add(go);
        jPanel1.add(selectMap);
        jPanel1.add(pic);
        jPanel1.setVisible(true);

        this.add(jPanel1);
        this.add(restart);
        this.add(select);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        go.addActionListener(this);
    }

    private void selectActionPerformed(ActionEvent evt) {
        jPanel1.setVisible(true);
    }

    private void selectMapActionPerformed(ActionEvent evt) {

    }

    private void restartActionPerformed(ActionEvent evt) {
        play = true;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;
        playerX = 310;
        score = 0;
        totalBricks = 21;
        startGame();
    }

    public void startGame() {
        restart.setVisible(false);
        select.setVisible(false);
        if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Easy")) {
            mapVar = new MapGenerator(3, 7);
        } else if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Medium")) {
            mapVar2 = new MapGenerator2(3, 7);
        }
        play = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        paintChildren(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (play) {
            //background
            g.setColor(Color.black);
            g.fillRect(1, 1, 692, 592);

            //drawing map
            if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Easy")) {
                mapVar.drawE((Graphics2D) g);
            } else if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Medium")) {
                mapVar2.drawM((Graphics2D) g);
            }

            //border
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 3, 592);
            g.fillRect(0, 0, 692, 3);
            g.fillRect(691, 0, 3, 592);

            //Scores
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("" + score, 560, 30);

            //the paddle
            g.setColor(Color.orange);
            g.fillRect(playerX, 550, 100, 10);

            //the ball
            g.setColor(Color.yellow);
            g.fillOval(ballposX, ballposY, 20, 20);

            //checking the bottom collision
            checkBottomCollision(g);

            //checking if bricks are 0 or not
            won(g);
        }
        g.dispose();
    }

    public void move() {
        ballposX += ballXdir;
        ballposY += ballYdir;
        if (ballposX < 0) {
            ballXdir = -ballXdir;
        }
        if (ballposY < 0) {
            ballYdir = -ballYdir;
        }
        if (ballposX > 670) {
            ballXdir = -ballXdir;
        }
    }

    public void checkBallToPaddleCollision() {
        if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 10))) {
            ballYdir = -ballYdir;
        }
    }

    public void checkBallToBrickCollision() {
        if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Easy")) {
            for (int i = 0; i < mapVar.map.length; i++) {
                for (int j = 0; j < mapVar.map[0].length; j++) {
                    if (mapVar.map[i][j] > 0) {
                        int brickX = j * mapVar.brickWidth + 80;
                        int brickY = i * mapVar.brickHeight + 50;
                        int brickWidth = mapVar.brickWidth;
                        int brickHeight = mapVar.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);


                        if (ballRect.intersects(brickRect)) {
                            mapVar.setBrickValueE(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            return;
                        }
                    }
                }
            }
        } else if (Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Medium")) {
            for (int i = 0; i < mapVar2.map2.length; i++) {
                for (int j = 0; j < mapVar2.map2[0].length; j++) {
                    if (mapVar2.map2[i][j] == 1) {
                        int brickX = j * mapVar2.brickWidth + 80;
                        int brickY = i * mapVar2.brickHeight + 50;
                        int brickWidth = mapVar2.brickWidth;
                        int brickHeight = mapVar2.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);


                        if (ballRect.intersects(brickRect)) {
                            mapVar2.setBrickValueM(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            return;
                        }
                    }
                    if (mapVar2.map2[i][j] == 2) {
                        int brickX = j * mapVar2.brickWidth + 80;
                        int brickY = i * mapVar2.brickHeight + 50;
                        int brickWidth = mapVar2.brickWidth;
                        int brickHeight = mapVar2.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }
        }
    }

    public void won(Graphics g) {
        if (totalBricks <= 0 && Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Easy")) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON ", 260, 300);

            restart.setVisible(true);
            select.setVisible(true);
        } else if (totalBricks <= 7 && Objects.requireNonNull(selectMap.getSelectedItem()).toString().equals("Medium")) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON ", 260, 300);

            restart.setVisible(true);
            select.setVisible(true);
        }
        if (!play) timer.stop();
    }

    public void checkBottomCollision(Graphics g) {
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GameOver,Scores: " + score, 190, 300);


            restart.setVisible(true);
            select.setVisible(true);
        }
        if (!play) timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            move();
            checkBallToBrickCollision();
            checkBallToPaddleCollision();
        }
        if (e.getSource() == easy) {
            speed = "easy";
            delay = 8;
            easy.setBackground(Color.green);
            medium.setBackground(Color.cyan);
            hard.setBackground(Color.red);
        }
        if (e.getSource() == medium) {
            speed = "medium";
            delay = 5;
            medium.setBackground(Color.green);
            easy.setBackground(Color.LIGHT_GRAY);
            hard.setBackground(Color.red);
        }
        if (e.getSource() == hard) {
            speed = "hard";
            delay = 2;
            hard.setBackground(Color.green);
            easy.setBackground(Color.LIGHT_GRAY);
            medium.setBackground(Color.cyan);
        }
        if (e.getSource() == go) {
            go.setBackground(Color.green);
            jPanel1.setVisible(false);
            ballposX = 120;
            ballposY = 350;
            ballXdir = -1;
            ballYdir = -2;
            playerX = 310;
            score = 0;
            totalBricks = 21;
            startGame();
        }
        repaint();
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT: {
                    if (playerX >= 600) {
                        playerX = 600;
                    } else {
                        moveRight();
                    }
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    if (playerX < 10) {
                        playerX = 10;
                    } else {
                        moveLeft();
                    }
                    break;
                }
            }
        }


        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
