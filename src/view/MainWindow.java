package view;

import model.Direction;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    /**
     * A tábla példánya
     */
    private final Board board;
    /**
     * A sárkány mozgatásának időzítője
     */
    private final Timer stepTimer;
    /**
     * Az eltelt időt mutató timer
     */
    private final Timer elapsedTimer;
    /**
     * A játékos neve
     */
    private final String playerName;
    /**
     * Teljesített pályák számának JLabel-e
     */
    private final JLabel completedCount;
    /**
     * Eltelt idő JLabel-e
     */
    private final JLabel elapsedTime;
    /**
     * A pályaméretek menüje
     */
    private final JMenu tableSizeMenu;
    /**
     * A nehézségel menüje
     */
    private final JMenu difficultyMenu;
    /**
     * A nagyítás menüje
     */
    private final JMenu scaling;
    /**
     * Az eltelt másodpercek számlálója
     */
    private int sec;
    /**
     * A játék egy példánya
     */
    private Game game;
    /**
     * A pályaméret pontszorzója
     */
    private int mapSizeMul;
    /**
     * A nehézség pontszorzója
     */
    private int difficultyMul;


    /**
     * A játékablak példányosítása
     */
    public MainWindow() {
        setTitle("Labirintus");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        playerName = JOptionPane.showInputDialog("Kérlek add meg a nevedet!", "");
        game = new Game(8, playerName);
        mapSizeMul = 2;
        difficultyMul = 2;
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


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        JMenu menuGame = new JMenu("Játék");
        menuBar.add(menuGame);
        JMenuItem newGame = new JMenuItem("Új Játék");
        newGame.addActionListener((ActionEvent e) -> startNew());
        menuGame.add(newGame);
        JMenuItem newPlayer = new JMenuItem("Új Játékos");
        newPlayer.addActionListener((ActionEvent e) -> game.setPlayerName(JOptionPane.showInputDialog("Kérlek add meg a nevedet!", "")));
        menuGame.add(newPlayer);
        JMenuItem exit = new JMenuItem("Kilépés");
        exit.addActionListener((ActionEvent e) -> System.exit(0));
        menuGame.add(exit);


        JMenu menuSettings = new JMenu("Beállítások");
        tableSizeMenu = new JMenu("Pályaméret");

        createMapSize("Kicsi (13x13)", 6, 1);
        createMapSize("Közepes (17x17)", 8, 2);
        createMapSize("Nagy (21x21)", 10, 3);

        JMenuItem randomized = new JMenuItem("Változó");
        randomized.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            setMapSizeMul(3);
            game = new Game(playerName);
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            stepTimer.start();
            resize();
        });
        tableSizeMenu.add(randomized);
        menuSettings.add(tableSizeMenu);

        difficultyMenu = new JMenu("Nehézség");

        createDifficulty("Könnyű", 500, 1);
        createDifficulty("Közepes", 100, 2);
        createDifficulty("Nehéz", 50, 3);
        createDifficulty("Lehetetlen", 25, 4);
        menuSettings.add(difficultyMenu);

        scaling = new JMenu("Nagyítás");
        createScaling("Kicsi", 25);
        createScaling("Közepes", 40);
        createScaling("Nagy", 50);
        menuSettings.add(scaling);

        menuBar.add(menuSettings);


        JMenu menuTest = new JMenu("Tesztelés");

        JMenuItem dark = new JMenuItem("Sötétség");
        dark.addActionListener((ActionEvent e) -> board.toggleDark());
        menuTest.add(dark);
        Timer timerLoad = new Timer(1, evt -> {
            game = new Game(game.getGenerationSize(), playerName);
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

        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Dicsőség tábla") {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    new HighScoreWindow(game.getHighScores(), MainWindow.this);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuGame.add(menuHighScores);

        controls();

        stepTimer.start();
        elapsedTimer = new Timer(1000, evt -> elapsedTime.setText("Eltelt idő: " + (sec += 1)));
        elapsedTimer.start();
        setResizable(false);
        resize();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * A játékablak létrehozása
     * @param args argumentumok
     */
    public static void main(String[] args) {
        new MainWindow();
    }

    /**
     * A pályaméret szorzó beállítása
     * @param mapSizeMul az új szorzóérték
     */
    public void setMapSizeMul(int mapSizeMul) {
        this.mapSizeMul = mapSizeMul;
    }

    /**
     * A nehézség szorzó beállítása
     * @param difficultyMul az új szorzóérték
     */
    public void setDifficultyMul(int difficultyMul) {
        this.difficultyMul = difficultyMul;
    }

    /**
     * Metódus pályaméret opció létrehozásához
     * @param text a menüpont szövege
     * @param size a pályaméret
     * @param mapSizeMul a szorzó értéke
     */
    private void createMapSize(String text, int size, int mapSizeMul) {
        JMenuItem mapSize = new JMenuItem(text);
        mapSize.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            setMapSizeMul(mapSizeMul);
            game = new Game(size, playerName);
            board.newBoard(game);
            completedCount.setText(game.getCompletedCount());
            stepTimer.start();
            resetElapsedTime();
            resize();
        });
        tableSizeMenu.add(mapSize);
    }

    /**
     * Metódus nehézség opció létrehozásához
     * @param text a menüpont szövege
     * @param time a léptetés gyorsasága
     * @param difficultyMul a szorzó értéke
     */
    private void createDifficulty(String text, int time, int difficultyMul) {
        JMenuItem difficulty = new JMenuItem(text);
        difficulty.addActionListener((ActionEvent e) -> {
            stepTimer.stop();
            setDifficultyMul(difficultyMul);
            startNew();
            stepTimer.setDelay(time);
            stepTimer.start();
            resetElapsedTime();
        });
        difficultyMenu.add(difficulty);
    }

    /**
     * Metódus nagyítás opció létrehozásához
     * @param text a menüpont szövege
     * @param size a nagyítás mértéke
     */
    private void createScaling(String text, int size) {
        JMenuItem scalingVal = new JMenuItem(text);
        scalingVal.addActionListener((ActionEvent e) -> {
            board.setScale(size);
            resize();
        });
        scaling.add(scalingVal);
    }

    /**
     * Az irányítás létrehozása
     */
    private void controls() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke);
                int kk = ke.getKeyCode();
                if (!game.isEnded() && game.getPlayer().isCanMove()) {
                    Direction d = switch (kk) {
                        case KeyEvent.VK_LEFT -> Direction.LEFT;
                        case KeyEvent.VK_RIGHT -> Direction.RIGHT;
                        case KeyEvent.VK_UP -> Direction.UP;
                        case KeyEvent.VK_DOWN -> Direction.DOWN;
                        default -> null;
                    };


                    if (!game.isEnded() && d != null) {
                        game.movePlayer(d);
                    }

                    if (game.isCompleted()) {

                        stepTimer.stop();
                        game.newLevel();
                        board.newBoard(game);
                        game.addToCompletedCount();
                        completedCount.setText(game.getCompletedCount());
                        resize();
                        stepTimer.start();
                    }

                }
            }
        });
    }

    /**
     * Az átméretezés metódusa
     */
    private void resize() {
        board.setBoardSize();
        pack();
    }

    /**
     * Új játék indítása
     */
    private void startNew() {
        stepTimer.stop();
        game.saveScore(difficultyMul, mapSizeMul);
        game = new Game(game.getGenerationSize(), playerName);
        board.newBoard(game);
        completedCount.setText(game.getCompletedCount());
        stepTimer.start();
        resetElapsedTime();
        resize();
    }

    /**
     * Eltelt idő újraindítása
     */
    private void resetElapsedTime() {
        elapsedTime.setText("Eltelt idő: 0");
        sec = 0;
        elapsedTimer.restart();
    }

    /**
     * A sárkány mozgatásáért felelős timer létrehozása
     * @return a létrehozott timer
     */
    private Timer createTimer() {
        final Timer timer;
        timer = new Timer(100, evt -> {
            if (((Timer) evt.getSource()).getDelay() < 30)
                game.getDragon().setMoveSpeed(board.getScale() / 2);
            else
                game.getDragon().setMoveSpeed(5);
            if (game.isEnded()) {
                elapsedTimer.stop();
                ((Timer) evt.getSource()).stop();
                String msg = "Vége a játéknak! " + (game.getCompletedCountValue() * difficultyMul * mapSizeMul) + " pontot szereztél!";
                JOptionPane.showMessageDialog(MainWindow.this, msg, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
                game.saveScore(difficultyMul, mapSizeMul);
                startNew();
            } else {
                if (game.getDragon().isCanMove())
                    game.moveDragon();
            }

        });
        return timer;
    }
}
