import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class GridFrameTest extends JFrame {
    private JButton[][] buttons;
    private int[][] buttonStates;
    public boolean isSavePressed = false;
    private int rows;
    private int cols;

    public GridFrameTest(int cols, int rows) {
        super("Grid Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout gridLayout = new GridLayout(rows, cols);
        setLayout(gridLayout);

        buttons = new JButton[rows][cols];
        buttonStates = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                // button.setPreferredSize(new Dimension(50, 50));
                button.addActionListener(new ButtonClickListener(i, j));
                buttons[i][j] = button;
                add(button);
                int randomValue = 0;
                buttonStates[i][j] = randomValue;
                button.setBackground(randomValue == 0 ? Color.WHITE : Color.BLACK);
            }
        }

        int panelWidth = 50;
        int panelHeight = 50;
        int frameWidth = cols * panelWidth;
        int frameHeight = rows * panelHeight;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(frameWidth < screenWidth ? frameWidth : screenWidth, frameHeight < screenHeight ? frameHeight : screenHeight);

        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        this.rows = rows;
        this.cols = cols;
        setVisible(true);
    }

    public int[][] getFinalCustomPosition() {
        return buttonStates;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = buttons[row][col];
            int currentState = buttonStates[row][col];
            int newState = currentState == 0 ? 1 : 0;
            buttonStates[row][col] = newState;
            button.setBackground(newState == 0 ? Color.WHITE : Color.BLACK);
        }
    }

    public int[][] saveToFile() {
        System.out.println("Grid state:");
        for (int i = 0; i < buttonStates.length; i++) {
            for (int j = 0; j < buttonStates[0].length; j++) {
                System.out.print(buttonStates[i][j] + " ");
            }
            System.out.println();
        }
        isSavePressed = true;
        System.out.println(buttonStates.length);
        JOptionPane.showMessageDialog(this, "Grid state printed to console.");
        dispose();
        return buttonStates;
    }
    // This will be set in the constructor once implemented
    public static void main(String[] args) {
        int rows = 40;
        int cols = 5;
        GridFrameTest frame = new GridFrameTest(cols, rows);
        while (!frame.isSavePressed) {
            Thread.onSpinWait();
        }
        int[][] position = new int[rows][cols];
    }

    public static int[][] autofillArray(int rows, int cols) {
        int[][] array = new int[rows][cols];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = random.nextInt(2);
            }
        }
        return array;
    }
}
