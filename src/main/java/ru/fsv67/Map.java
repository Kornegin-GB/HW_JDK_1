package ru.fsv67;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Окно игрового поля
 */
public class Map extends JPanel {
    private static final int DOT_PADDING = 5;
    private static final Random RANDOM = new Random();

    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private boolean isGameOver;
    private boolean isInitialized;

    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int fieldSizeY;
    private int fieldSizeX;
    private char[][] field;
    private int winLength;

    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;

    /**
     * Конструктор игрового поля
     */
    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        isInitialized = false;
    }

    /**
     * Метод заполнения игрового поля
     * 
     * @param e - ячейка игрового поля
     */
    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized)
            return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY))
            return;
        field[cellY][cellX] = HUMAN_DOT;

        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN))
            return;
        aiTurn();
        repaint();
        if (checkEndGame(AI_DOT, STATE_WIN_AI))
            return;

        System.out.printf("x=%d, y=%d\n", cellX, cellY);
        repaint();
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }
        if (isMapFull()) {
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
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
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d", mode, sizeX, sizeY, winLength);
        initMap();
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    /**
     * Отрисовка игрового поля
     * 
     * @param g графика
     */
    private void render(Graphics g) {
        if (!isInitialized)
            return;
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellHeight = panelHeight / SettingsWindow.sizeX;
        cellWidth = panelWidth / SettingsWindow.sizeY;

        g.setColor(Color.BLACK);
        for (int h = 0; h < SettingsWindow.sizeX; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, panelWidth, y);

        }
        for (int w = 0; w < SettingsWindow.sizeY; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPTY_DOT)
                    continue;

                if (field[y][x] == HUMAN_DOT) {
                    // Вид отображения знака хода игрока
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING, y * cellHeight + DOT_PADDING, cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == AI_DOT) {
                    // Вид отображения знака хода компьютера
                    g.setColor(new Color(0xff0000));
                    g.fillOval(x * cellWidth + DOT_PADDING, y * cellHeight + DOT_PADDING, cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Unexpected value " + field[y][x] + " in cell: x=" + x + " y=" + y);
                }
            }
        }
        if (isGameOver)
            showMessageGameOver(g);
    }

    /**
     * Сообщение игроку о результате игры
     * 
     * @param g - холст игрового поля
     */
    private void showMessageGameOver(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Time new roman", Font.BOLD, 40));
        switch (gameOverType) {
        case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2);
        case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 20, getHeight() / 2);
        case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 70, getHeight() / 2);
        default -> throw new RuntimeException("Unexpected gameOver state: " + gameOverType);
        }
    }

    /**
     * Инициализация игрового поля
     */
    private void initMap() {
        fieldSizeY = SettingsWindow.sizeY;
        fieldSizeX = SettingsWindow.sizeX;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    /**
     * Проверка попал ли игрок или компьютер в клетку игрового поля
     * 
     * @param x координата по X
     * @param y координата по Y
     * @return результат проверки (true или false)
     */
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка пуста ли выбранная клетка игрового поля
     * 
     * @param x координата по X
     * @param y координата по Y
     * @return результат проверки (true или false)
     */
    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    /**
     * Ход компьютера
     */
    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    /**
     * Проверка выиграшной длинны
     * 
     * @param x
     * @param y
     * @param vx
     * @param vy
     * @param len
     * @param c
     * @return
     */
    private boolean checkLine(int x, int y, int vx, int vy, int len, int c) {
        final int far_x = x + (len - 1) * vx;
        final int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y))
            return false;
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != c)
                return false;
        }
        return true;
    }

    /**
     * Проверка выиграшной комбинации
     * 
     * @param c - проверяет крестик или нолик стоит в ячейке
     * @return результат проверки (true - выиграл или false - проиграл)
     */
    private boolean checkWin(int c) {
        winLength = SettingsWindow.winLength;
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLength, c))
                    return true;
                if (checkLine(i, j, 1, 1, winLength, c))
                    return true;
                if (checkLine(i, j, 0, 1, winLength, c))
                    return true;
                if (checkLine(i, j, 1, -1, winLength, c))
                    return true;
            }
        }
        return false;
    }

    /**
     * Проверка на заполненность игрового поля (если нет победителя то нечья)
     * 
     * @return результат проверки
     */
    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT)
                    return false;
            }
        }
        return true;
    }
}
