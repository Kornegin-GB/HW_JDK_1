package ru.fsv67;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Основное окно
 */
public class GameWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    JButton buttonStart = new JButton("New Game");
    JButton buttonExit = new JButton("Exit");

    Map map;
    SettingsWindow settingsWindow;

    /**
     * Конструктор основного окна
     */
    GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("TicTacToe");
        setResizable(false);

        map = new Map();
        settingsWindow = new SettingsWindow(this);
        settingsWindow.setVisible(false);

        buttonExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);

            }
        });

        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(buttonStart);
        panelBottom.add(buttonExit);
        add(panelBottom, BorderLayout.SOUTH);
        add(map);

        setVisible(true);
    }

    /**
     * Параметры старта игры
     * 
     * @param mode      режим игры
     * @param sizeX     размер игрового поля по оси X
     * @param sizeY     размер игрового поля по оси Y
     * @param winLength размер выиграшной комбинации
     */
    void startNewGame(int mode, int sizeX, int sizeY, int winLength) {
        map.startNewGame(mode, sizeX, sizeY, winLength);
    }

}
