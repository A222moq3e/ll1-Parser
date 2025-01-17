import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Map;
import java.util.Set;

/**
 * authors:
 * @author abdullah bin ammar
 * @author
 * @author
 * @author
 */

public class Main extends javax.swing.JFrame {
    private JButton runEx1Button;
    private JButton runEx2Button;
    private JLabel firstSetsLabel;
    private JLabel followSetsLabel;
    private JLabel parsingTableLabel;
    private JLabel grammar1Label;
    private JLabel grammar2Label;
    private JScrollPane grammar1ScrollPane;
    private JScrollPane firstSetsScrollPane;
    private JScrollPane followSetsScrollPane;
    private JScrollPane grammar2ScrollPane;
    private JScrollPane parsingTableScrollPane;
    private JTextArea grammar1TextArea;
    private JTextArea firstSetsTextArea;
    private JTextArea followSetsTextArea;
    private JTextArea parsingTableTextArea;
    private JTextArea grammar2TextArea;
    private JSeparator inputSeparator;
    private JSeparator outputSeparator;

    public Main() {
        initComponents();
        setPreferredSize(new Dimension(1200, 800)); 
        pack(); // To fit its content
        setLocationRelativeTo(null); // Center the window on the screen
        customizeComponents();
    }

    private void customizeComponents() {
        setTitle("Grammar Parser");
        getContentPane().setBackground(new Color(240, 240, 245));

        Font titleFont = new Font("Segoe UI", Font.BOLD, 14);
        Font contentFont = new Font("Segoe UI", Font.PLAIN, 13);

        firstSetsLabel.setFont(titleFont);
        followSetsLabel.setFont(titleFont);
        parsingTableLabel.setFont(titleFont);
        grammar1Label.setFont(titleFont);
        grammar2Label.setFont(titleFont);

        customizeTextArea(grammar1TextArea, contentFont);
        customizeTextArea(firstSetsTextArea, contentFont);
        customizeTextArea(followSetsTextArea, contentFont);
        customizeTextArea(parsingTableTextArea, contentFont);
        customizeTextArea(grammar2TextArea, contentFont);

        customizeButton(runEx1Button);
        customizeButton(runEx2Button);

        customizeScrollPane(grammar1ScrollPane, "Grammar 1");
        customizeScrollPane(firstSetsScrollPane, "first Sets");
        customizeScrollPane(followSetsScrollPane, "follow Sets");
        customizeScrollPane(grammar2ScrollPane, "Grammar 2");
        customizeScrollPane(parsingTableScrollPane, "Parsing Table");
    }

    private void customizeTextArea(JTextArea textArea, Font font) {
        textArea.setFont(font);
        textArea.setBackground(new Color(252, 252, 255));
        textArea.setBorder(new RoundedBorder(5, new Color(252, 252, 255)));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(5, new Color(70, 130, 180)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void customizeScrollPane(JScrollPane scrollPane, String title) {
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                new RoundedBorder(5, new Color(70, 130, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(70, 130, 180)
        ));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        grammar1ScrollPane = new JScrollPane();
        grammar1TextArea = new JTextArea();
        runEx1Button = new JButton();
        firstSetsScrollPane = new JScrollPane();
        firstSetsTextArea = new JTextArea();
        firstSetsLabel = new JLabel();
        followSetsScrollPane = new JScrollPane();
        followSetsTextArea = new JTextArea();
        followSetsLabel = new JLabel();
        parsingTableLabel = new JLabel();
        parsingTableScrollPane = new JScrollPane();
        parsingTableTextArea = new JTextArea();
        grammar2ScrollPane = new JScrollPane();
        grammar2TextArea = new JTextArea();
        grammar1Label = new JLabel();
        runEx2Button = new JButton();
        grammar2Label = new JLabel();
        inputSeparator = new JSeparator();
        outputSeparator = new JSeparator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int colSize = 25;
        grammar1TextArea.setColumns(colSize);
        grammar1TextArea.setFont(new Font("Segoe UI", 0, 14));
        grammar1TextArea.setRows(5);
        grammar1TextArea.setText("S -> A C\nA -> a B | ε\nB -> b C | ε\nC -> ( c )");
        grammar1ScrollPane.setViewportView(grammar1TextArea);

        runEx1Button.setText("Run Input1");
        runEx1Button.addActionListener(evt -> runEx1ButtonActionPerformed(evt));

        firstSetsTextArea.setColumns(colSize);
        firstSetsTextArea.setFont(new Font("Segoe UI", 0, 14));
        firstSetsTextArea.setRows(5);
        firstSetsScrollPane.setViewportView(firstSetsTextArea);

        firstSetsLabel.setText("FIRST Sets:");

        followSetsTextArea.setColumns(colSize);
        followSetsTextArea.setFont(new Font("Segoe UI", 0, 14));
        followSetsTextArea.setRows(5);
        followSetsScrollPane.setViewportView(followSetsTextArea);

        followSetsLabel.setText("Follow Sets:");

        parsingTableLabel.setText("Parsing Table");

        parsingTableTextArea.setColumns(colSize);
        parsingTableTextArea.setRows(5);
        parsingTableScrollPane.setViewportView(parsingTableTextArea);

        grammar2TextArea.setColumns(colSize);
        grammar2TextArea.setFont(new Font("Segoe UI", 0, 14));
        grammar2TextArea.setRows(5);
        grammar2TextArea.setText("S -> ( L ) | a\nL -> L | a");
        grammar2ScrollPane.setViewportView(grammar2TextArea);

        grammar1Label.setText("Grammar#1");

        runEx2Button.setText("Run Input2");
        runEx2Button.addActionListener(evt -> runEx2ButtonActionPerformed(evt));

        grammar2Label.setText("Grammar#2");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(outputSeparator)
                                        .addComponent(inputSeparator)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(parsingTableLabel)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(parsingTableScrollPane, GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(firstSetsLabel)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(grammar1Label)
                                                                                .addGap(48, 48, 48)
                                                                                .addComponent(runEx1Button))
                                                                        .addComponent(grammar1ScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addComponent(firstSetsScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(followSetsScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addComponent(grammar2ScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(grammar2Label)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(runEx2Button))
                                                                        .addComponent(followSetsLabel))))
                                                .addGap(17, 17, 17))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(grammar1Label)
                                                        .addComponent(runEx2Button)))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(runEx1Button)
                                                        .addComponent(grammar2Label))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(grammar1ScrollPane, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(grammar2ScrollPane))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(firstSetsLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(firstSetsScrollPane, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(followSetsLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(followSetsScrollPane)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(outputSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(parsingTableLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(parsingTableScrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }

    private void runEx1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Button 1 clicked");
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();

        String grammar = grammar1TextArea.getText();

        parser.readGrammarFromString(grammar);
        parser.processGrammar();

        Map<String, Set<String>> firstSets = parser.getFirstSets();
        for (String nonTerminal : firstSets.keySet()) {
            firstSetsTextArea.append(nonTerminal + " : " + firstSets.get(nonTerminal) + "\n");
        }

        Map<String, Set<String>> followSets = parser.getFollowSets();
        for (String nonTerminal : followSets.keySet()) {
            followSetsTextArea.append(nonTerminal + " : " + followSets.get(nonTerminal) + "\n");
        }

        if (parser.isLL1Grammar()) {
            String parsingTableString = parser.getParsingTableAsString();
            parsingTableTextArea.setText(parsingTableString);
        } else {
            parsingTableTextArea.setText("Error: The grammar is ambiguous or it is inherently not a LL(1) grammar.");
        }

        parser.printAllResults();
        System.out.println("Button 1 processing completed");
    }

    private void runEx2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Button 2 clicked");
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();

        String grammar = grammar2TextArea.getText();

        parser.readGrammarFromString(grammar);
        parser.processGrammar();

        Map<String, Set<String>> firstSets = parser.getFirstSets();
        for (String nonTerminal : firstSets.keySet()) {
            firstSetsTextArea.append(nonTerminal + " : " + firstSets.get(nonTerminal) + "\n");
        }

        Map<String, Set<String>> followSets = parser.getFollowSets();
        for (String nonTerminal : followSets.keySet()) {
            followSetsTextArea.append(nonTerminal + " : " + followSets.get(nonTerminal) + "\n");
        }

        if (parser.isLL1Grammar()) {
            String parsingTableString = parser.getParsingTableAsString();
            parsingTableTextArea.setText(parsingTableString);
        } else {
            parsingTableTextArea.setText("Error: The grammar is ambiguous or it is inherently not a LL(1) grammar.");
        }

        parser.printAllResults();
        System.out.println("Button 2 processing completed");
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private static class RoundedBorder extends LineBorder {
        private final int radius;

        public RoundedBorder(int radius, Color color) {
            super(color, 1, true);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
