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
    private final Timer stepTimer;
    private final Timer elapsedTimer;
    private int sec;
    JLabel completedCount;
    JLabel elapsedTime;
    JMenuBar menuBar;
    JMenu menuGame;
    JMenu menuSettings;
    JMenu tableSizeMenu;
    JMenu difficultyMenu;
    JMenu scaling;
    JMenu menuTest;
    private Game game;

    public MainWindow() {
        game = new Game(8);
        setTitle("Labirintus");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel statusPanel = new JPanel();

        completedCount = new JLabel();
        completedCount.setText(game.getCompletedCount());
        elapsedTime = new JLabel();
        elapsedTime.setText("Eltelt idő: 0");
        statusPanel.add(completedCount);
        statusPanel.add(elapsedTime);

        add(statusPanel, BorderLayout.NORTH);

        add(board = new Board(game), BorderLayout.CENTER);
        stepTimer = createTimer();

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        menuGame = new JMenu("Játék");
        menuBar.add(menuGame);
        JMenuItem newGame = new JMenuItem("Új Játék");
        newGame.addActionListener((ActionEvent e) -> startNew(completedCount));
        menuGame.add(newGame);
        JMenuItem exit = new JMenuItem("Kilépés");
        exit.addActionListener((ActionEvent e) -> System.exit(0));
        menuGame.add(exit);


        menuSettings = new JMenu("Beállítások");
        tableSizeMenu = new JMenu("Pályaméret");

        createMapSize("Kicsi (13x13)", 6);
        createMapSize("Közepes (17x17)", 8);
        createMapSize("Nagy (21x21)", 10);

        JMenuItem randomized = new JMenuItem("Változó");
        randomized.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            game = new Game();
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            stepTimer.start();
            resize();
        });
        tableSizeMenu.add(randomized);
        menuSettings.add(tableSizeMenu);


        difficultyMenu = new JMenu("Nehézség");

        createDifficulty("Könnyű", 500);
        createDifficulty("Közepes", 100);
        createDifficulty("Nehéz", 10);
        createDifficulty("Lehetetlen", 1);
        menuSettings.add(difficultyMenu);


        scaling = new JMenu("Nagyítás");
        createScaling("Kicsi", 25);
        createScaling("Közepes", 42);
        createScaling("Nagy", 50);
        menuSettings.add(scaling);

        menuBar.add(menuSettings);


        menuTest = new JMenu("Tesztelés");

        JMenuItem dark = new JMenuItem("Sötétség");
        dark.addActionListener((ActionEvent e) -> board.toggleDark());
        menuTest.add(dark);
        Timer timerLoad = new Timer(1, evt -> {
            game = new Game(game.getGenerationSize());
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            stepTimer.start();
            resize();
        });
        JMenuItem loadTest = new JMenuItem("LoadTest");
        loadTest.addActionListener((ActionEvent e) -> {
            if (timerLoad.isRunning()) {
                timerLoad.stop();
                stepTimer.start();
            } else {
                stepTimer.stop();
                timerLoad.start();
            }
        });
        menuTest.add(loadTest);
        menuBar.add(menuTest);

        Controls(completedCount);

        stepTimer.start();
        elapsedTimer = new Timer(1000,evt ->{
            elapsedTime.setText("Eltelt idő: "+ String.valueOf(sec+=1));
        });
        elapsedTimer.start();
        setResizable(false);
        resize();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private void createMapSize(String text, int size) {
        JMenuItem mapSize = new JMenuItem(text);
        mapSize.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            game = new Game(size);
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            stepTimer.start();
            resetElapsedTime();
            resize();
        });
        tableSizeMenu.add(mapSize);
    }

    private void createDifficulty(String text, int time) {
        JMenuItem difficulty = new JMenuItem(text);
        difficulty.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            startNew(completedCount);
            stepTimer.setDelay(time);
            stepTimer.start();
            resetElapsedTime();
        });
        difficultyMenu.add(difficulty);
    }

    private void createScaling(String text, int size) {
        JMenuItem scalingVal = new JMenuItem(text);
        scalingVal.addActionListener((ActionEvent e) -> {
            board.setScale(size);
            resize();
        });
        scaling.add(scalingVal);
    }

    private void Controls(JLabel completedCount) {
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
                    stepTimer.stop();
                    game.newLevel();
                    board.newBoard(game);
                    game.addToCompletedCount();
                    completedCount.setText(game.getCompletedCount());
                    resize();
                    stepTimer.start();
                }

                board.revalidate();
                board.repaint();
            }
        });
    }

    private void resize() {
        board.setBoardSize();
        pack();
    }

    private void startNew(JLabel completedCount) {
        stepTimer.stop();
        game = new Game(game.getGenerationSize());
        board.newBoard(game);
        completedCount.setText(game.getCompletedCount());
        stepTimer.start();
        resetElapsedTime();
        resize();
    }

    private void resetElapsedTime() {
        elapsedTime.setText("Eltelt idő: 0");
        sec = 0;
        elapsedTimer.restart();
    }

    private Timer createTimer() {
        final Timer timer;
        timer = new Timer(100, evt -> {
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
}
