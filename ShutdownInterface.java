import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShutdownInterface extends JFrame {
    private JRadioButton shutdownRadio;
    private JRadioButton restartRadio;
    private JTextField timeField;
    private JButton sendButton;
    private JButton cancelButton;

    public ShutdownInterface() {
        // Настройка основного окна
        setTitle("Контроллер компьютера");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Создание компонентов
        shutdownRadio = new JRadioButton("Выключение");
        restartRadio = new JRadioButton("Перезагрузка");
        timeField = new JTextField(10);
        sendButton = new JButton("Отправить запрос");
        cancelButton = new JButton("Отмена запроса");

        //Автоматически активная кнопка
        shutdownRadio.setSelected(true);

        // Убираем выделение при нажатии на кнопки и радиокнопки
        shutdownRadio.setFocusPainted(false);
        restartRadio.setFocusPainted(false);
        sendButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);

        // Группировка радиокнопок
        ButtonGroup group = new ButtonGroup();
        group.add(shutdownRadio);
        group.add(restartRadio);

        // Уменьшение размеров шрифта
        Font smallFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        shutdownRadio.setFont(smallFont);
        restartRadio.setFont(smallFont);
        timeField.setFont(smallFont);
        sendButton.setFont(smallFont);
        cancelButton.setFont(smallFont);

        // Добавление компонентов на панель
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5)); // Установка отступов между компонентами
        panel.add(shutdownRadio);
        panel.add(restartRadio);
        panel.add(new JLabel("Введите время: "));
        panel.add(timeField);
        panel.add(sendButton);
        panel.add(cancelButton);

        // Добавление панели на основное окно
        add(panel, BorderLayout.CENTER);

        // Настройка обработчика событий для кнопки "Отправить"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeField.getText().equals("")) {
                    JOptionPane.showMessageDialog(panel, "Ошибка: пустое поле");
                } else {
                    String command = shutdownRadio.isSelected() ? "shutdown" : "restart";
                    String time = String.valueOf(Integer.parseInt(timeField.getText()) * 60);
                    executeShutdownCommand(command, time);
                }
            }
        });

        // Настройка обработчика событий для кнопки "Отмена"
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelShutdownCommand();
            }
        });
        // Центрирование окна на экране
        setLocationRelativeTo(null);
    }

    private void executeShutdownCommand(String command, String time) {
        try {
            // Формирование команды
            String shutdownCommand = "cmd /c " + command + " /s /t " + time;

            // Выполнение команды
            Process process = Runtime.getRuntime().exec(shutdownCommand);

            // Ожидание завершения выполнения команды
            process.waitFor();

            // Вывод сообщения об успешном выполнении
            JOptionPane.showMessageDialog(this, "Компьютер будет " + command + " через " + time + " секунд.");
        } catch (Exception ex) {
            // Обработка возможных исключений
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка выполнения команды " + command);
        }
    }

    private void cancelShutdownCommand() {
        try {
            // Выполнение команды отмены
            Process process = Runtime.getRuntime().exec("cmd /c shutdown /a");

            // Ожидание завершения выполнения команды
            process.waitFor();

            // Вывод сообщения об успешной отмене
            JOptionPane.showMessageDialog(this, "Команда выключения/перезагрузки отменена.");
        } catch (Exception ex) {
            // Обработка возможных исключений
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка отмены команды выключения/перезагрузки.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShutdownInterface().setVisible(true);
            }
        });
    }
}
