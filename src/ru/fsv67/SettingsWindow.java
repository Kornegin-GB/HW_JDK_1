package ru.fsv67;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Окно настроек игры
 */
public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    private static int SIZE_GAME_WINDOW = 3;

    private static final String SET_FIELD_SIZE = "Установленный размер поля: ";
    private static final String SET_WINNING_SIZE = "Установленная длинна для победы: ";

    protected static int mode = 0;
    protected static int sizeX = 3;
    protected static int sizeY = 3;
    protected static int winLength = 3;

    JButton buttonStart = new JButton("Start new game");

    SettingsWindow(GameWindow gameWindow) {
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Задание на семинаре 1
        setLayout(new GridLayout(10, 1));
        add(new JLabel("Выберите режим игры"));

        JRadioButton pvp = new JRadioButton("Человек против человека");
        JRadioButton pve = new JRadioButton("Человек против компьютера", true);
        ButtonGroup groupButton = new ButtonGroup();
        groupButton.add(pve);
        groupButton.add(pvp);
        add(pvp);
        add(pve);

        add(new JLabel("Выберите размеры поля"));

        JLabel labelField = new JLabel(SET_FIELD_SIZE + SIZE_GAME_WINDOW);
        add(labelField);
        JSlider sizeWindow = new JSlider(3, 10, 3);
        sizeWindow.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SIZE_GAME_WINDOW = sizeWindow.getValue();
                labelField.setText(SET_FIELD_SIZE + SIZE_GAME_WINDOW);
            }
        });
        add(sizeWindow);

        add(new JLabel("Выбирете длинну для победы"));
        JLabel labelWinning = new JLabel(SET_WINNING_SIZE + winLength);
        add(labelWinning);
        JSlider lengthWin = new JSlider(3, 10, 3);
        lengthWin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                winLength = lengthWin.getValue();
                labelWinning.setText(SET_WINNING_SIZE + winLength);
            }
        });
        add(lengthWin);

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pvp.isSelected()) {
                    mode = 1;
                }
                sizeX = sizeWindow.getValue();
                sizeY = sizeWindow.getValue();
                winLength = lengthWin.getValue();
                gameWindow.startNewGame(mode, sizeX, sizeY, winLength);
                setVisible(false);
            }
        });

        add(buttonStart);
        setVisible(false);

    }
}
