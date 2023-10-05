package chat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow extends JFrame {

    FileLogo fileLogo = new FileLogo();

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_POSX = 700;
    private static final int WINDOW_POSY = 200;

    private static boolean isConnect = false;

    JButton buttonConnect = new JButton("Connect");
    JButton buttonExit = new JButton("Exit");

    JTextArea fieldChat;

    /**
     * Конструктор основного окна
     * 
     */
    ChatWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat");
        setResizable(false);

        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConnect = true;
                if (isConnect) {
                    historyLoader();
                }
            }
        });

        buttonExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        JPanel panelBottom = new JPanel(new GridLayout(5, 2));
        JTextField loginField = new JTextField("login");
        JTextField passField = new JTextField("pass");
        JTextField ipField = new JTextField("ip");
        JTextField portField = new JTextField("port");
        panelBottom.add(loginField);
        panelBottom.add(passField);
        panelBottom.add(ipField);
        panelBottom.add(portField);

        panelBottom.add(buttonConnect);
        panelBottom.add(buttonExit);

        add(panelBottom, BorderLayout.NORTH);

        JPanel panelMid = new JPanel(new GridLayout(1, 2));

        fieldChat = new JTextArea();
        fieldChat.setEditable(false);
        JList<String> listUser = new JList<>();
        String users[] = { "Dima", "Katya", "Sasha", "Vasya", "Ignat" };
        listUser.setListData(users);
        panelMid.add(fieldChat);
        panelMid.add(listUser);

        add(panelMid, BorderLayout.CENTER);

        JPanel panelChat = new JPanel(new GridLayout(2, 1));
        JTextField fieldMessage = new JTextField("message");

        fieldMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fieldChat.append(fieldMessage.getText() + "\n");
                    fileLogo.readLogo(fieldMessage.getText());
                }
            }
        });
        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChat.append(fieldMessage.getText() + "\n");
                fileLogo.readLogo(fieldMessage.getText());
            }
        });

        panelChat.add(fieldMessage);
        panelChat.add(btnSend);

        add(panelChat, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void historyLoader() {
        String str = "";
        try (FileReader reader = new FileReader("logo.txt")) {

            int c;
            while ((c = reader.read()) != -1) {

                str += ((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        fieldChat.append(str);
    }
}
