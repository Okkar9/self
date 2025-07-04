import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class RussianRouletteGUI extends JFrame {
    // Language interface and implementations
    interface Language {
        String welcome();
        String shootPrompt();
        String loseMsg();
        String surviveMsg();
        String playAgainPrompt();
        String yes();
        String no();
        String invalidInput();
        String playerTurn(String playerName);
    }

    static class English implements Language {
        public String welcome() { return "Welcome to Russian Roulette!"; }
        public String shootPrompt() { return "Pull the trigger?"; }
        public String loseMsg() { return "Bang! You lost."; }
        public String surviveMsg() { return "Click... You survived."; }
        public String playAgainPrompt() { return "Play again?"; }
        public String yes() { return "Yes"; }
        public String no() { return "No"; }
        public String invalidInput() { return "Invalid input."; }
        public String playerTurn(String playerName) { return playerName + "'s turn."; }
    }

    static class Japanese implements Language {
        public String welcome() { return "ロシアンルーレットへようこそ！"; }
        public String shootPrompt() { return "引き金を引きますか？"; }
        public String loseMsg() { return "バン！あなたは負けました。"; }
        public String surviveMsg() { return "カチッ…生き残りました。"; }
        public String playAgainPrompt() { return "もう一度遊びますか？"; }
        public String yes() { return "はい"; }
        public String no() { return "いいえ"; }
        public String invalidInput() { return "無効な入力です。"; }
        public String playerTurn(String playerName) { return playerName + "のターン"; }
    }

    private Language lang;
    private int bulletPos;
    private int triggerPos;
    private Random rand = new Random();
    private int numPlayers;
    private String[] players;
    private int currentPlayer = 0;

    private JButton shootBtn = new JButton();
    private JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

    public RussianRouletteGUI(Language lang) {
        this.lang = lang;
        setupGame();
        setupUI();
        resetGame();
    }

    private void setupGame() {
        // Prompt for number of players
        numPlayers = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of players (2-4):"));
        players = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":");
        }
    }

    private void setupUI() {
        setTitle("Russian Roulette");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Status label for displaying current player and results
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(statusLabel, BorderLayout.CENTER);

        // Shoot button to trigger the shot
        shootBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        shootBtn.addActionListener(e -> shoot());
        add(shootBtn, BorderLayout.SOUTH);

        updateTexts();
    }

    private void updateTexts() {
        shootBtn.setText(lang.shootPrompt());
        statusLabel.setText(lang.playerTurn(players[currentPlayer]));
    }

    private void resetGame() {
        bulletPos = rand.nextInt(6); // Randomly position the bullet in one of the 6 chambers
        triggerPos = 0;
        shootBtn.setEnabled(true);
        updateTexts();
    }

    private void shoot() {
        // Check if the current player gets shot
        if (triggerPos == bulletPos) {
            statusLabel.setText(lang.loseMsg());
            shootBtn.setEnabled(false);
            askPlayAgain();
        } else {
            statusLabel.setText(lang.surviveMsg());
            triggerPos = (triggerPos + 1) % 6; // Rotate the revolver after each shot
            currentPlayer = (currentPlayer + 1) % numPlayers; // Switch to the next player
            updateTexts();
        }
    }

    private void askPlayAgain() {
        int option = JOptionPane.showOptionDialog(this, lang.playAgainPrompt(), "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[] {lang.yes(), lang.no()}, lang.yes());

        if (option == JOptionPane.YES_OPTION) {
            resetGame(); // Restart the game
        } else {
            dispose(); // Exit the game
        }
    }

    public static void main(String[] args) {
        // Language selection dialog before launching main GUI
        String[] options = {"English", "日本語"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select Language / 言語を選択してください",
                "Language Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        Language lang = (choice == 1) ? new Japanese() : new English();

        // Launch the game in a separate thread for smooth UI loading
        SwingUtilities.invokeLater(() -> {
            RussianRouletteGUI game = new RussianRouletteGUI(lang);
            game.setVisible(true);
        });
    }
}
