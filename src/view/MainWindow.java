package view;

import model.Direction;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private final Board board;
    private Timer timer;
    private Game game;

    public MainWindow() {
        game = new Game(8);
        setTitle("Labirintus");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel statusPanel = new JPanel();
        JLabel completedCount = new JLabel();
        completedCount.setText(game.getCompletedCount());
        statusPanel.add(completedCount);
        add(statusPanel, BorderLayout.NORTH);

        add(board = new Board(game), BorderLayout.CENTER);
        timer = createTimer(100);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuGame = new JMenu("Játék");
        menuBar.add(menuGame);
        JMenuItem newGame = new JMenuItem("Új Játék");
        newGame.addActionListener((ActionEvent e) -> {
            timer.stop();
            game = new Game(game.getGenerationSize());
            setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            timer.start();
            pack();

        });
        menuGame.add(newGame);
        JMenuItem exit = new JMenuItem("Kilépés");
        exit.addActionListener((ActionEvent e) -> System.exit(0));
        menuGame.add(exit);

        JMenuItem dark = new JMenuItem("Sötétség");
        dark.addActionListener((ActionEvent e) -> board.toggleDark());
        menuGame.add(dark);

        JMenu tableSizeMenu = new JMenu("Pályaméret");
        JMenuItem small = new JMenuItem("Kicsi (13x13)");
        small.addActionListener((ActionEvent e) -> {
            timer.stop();
            game = new Game(6);
            board.newBoard(game);
            setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
            completedCount.setText(game.getCompletedCount());
            timer.start();
            pack();


        });

        tableSizeMenu.add(small);
        JMenuItem medium = new JMenuItem("Közepes (17x17)");
        medium.addActionListener((ActionEvent e) -> {
            timer.stop();
            game = new Game(8);
            board.newBoard(game);
            setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
            completedCount.setText(game.getCompletedCount());
            timer.start();
            pack();


        });
        tableSizeMenu.add(medium);
        JMenuItem large = new JMenuItem("Nagy (21x21)");
        large.addActionListener((ActionEvent e) -> {
            timer.stop();
            game = new Game(10);

            board.newBoard(game);
            setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
            completedCount.setText(game.getCompletedCount());
            timer.start();
            pack();

        });
        tableSizeMenu.add(large);
        JMenuItem randomized = new JMenuItem("Változó");
        randomized.addActionListener((ActionEvent e) -> {
            timer.stop();
            game = new Game();
            board.newBoard(game);
            setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
            completedCount.setText(game.getCompletedCount());
            timer.start();
            pack();
        });
        tableSizeMenu.add(randomized);

        JMenuItem loadTest = new JMenuItem("LoadTest");
        loadTest.addActionListener((ActionEvent e) -> {
            timer.stop();
            Timer timerLoad = new Timer(1, evt -> {
                game = new Game(game.getGenerationSize());
                board.newBoard(game);
                setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));

                completedCount.setText(game.getCompletedCount());
                timer.start();
                pack();
            });
            timerLoad.start();
        });
        menuGame.add(loadTest);
        menuBar.add(tableSizeMenu);

        JMenu difficultyMenu = new JMenu("Nehézség");
        JMenuItem easyDif = new JMenuItem("Könnyű");
        easyDif.addActionListener((ActionEvent e) -> {
            timer.stop();
            timer = createTimer(1000);
            timer.start();
        });
        difficultyMenu.add(easyDif);

        JMenuItem mediumDif = new JMenuItem("Közepes");
        mediumDif.addActionListener((ActionEvent e) -> {
            timer.stop();
            timer = createTimer(100);
            timer.start();
        });
        difficultyMenu.add(mediumDif);

        JMenuItem hardDif = new JMenuItem("Nehéz");
        hardDif.addActionListener((ActionEvent e) -> {
            timer.stop();
            timer = createTimer(10);
            timer.start();
        });
        difficultyMenu.add(hardDif);

        JMenuItem insaneDif = new JMenuItem("Lehetetlen");
        insaneDif.addActionListener((ActionEvent e) -> {
            timer.stop();
            timer = createTimer(10);
            timer.start();
        });
        difficultyMenu.add(insaneDif);

        menuBar.add(difficultyMenu);


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke);
                int kk = ke.getKeyCode();
                Direction d = switch (kk) {
                    case KeyEvent.VK_LEFT -> Direction.LEFT;
                    case KeyEvent.VK_RIGHT -> Direction.RIGHT;
                    case KeyEvent.VK_UP -> Direction.UP;
                    case KeyEvent.VK_DOWN -> Direction.DOWN;
                    default -> null;
                };
                if (!game.isEnded() && d != null)
                    game.movePlayer(d);

                if (game.isCompleted()) {
                    timer.stop();
                    game.newLevel();
                    board.newBoard(game);
                    game.addToCompletedCount();
                    completedCount.setText(game.getCompletedCount());
                    setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
                    pack();
                    timer.start();
                }

                board.revalidate();
                board.repaint();


            }
        });

        timer.start();
        setResizable(false);
        setPreferredSize(new Dimension(game.getLevel().getMapSize() * 50 + 100 + 15, game.getLevel().getMapSize() * 50 + 185));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Timer createTimer(int speed) {
        final Timer timer;
        timer = new Timer(speed, evt -> {
            game.moveDragon();
            repaint();
            if (game.isEnded()) {
                String msg = "Meghaltál!";
                JOptionPane.showMessageDialog(MainWindow.this, msg, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
                ((Timer) evt.getSource()).stop();
            }
        });
        return timer;
    }

    public static void main(String[] args) {

        new MainWindow();

    }
}
