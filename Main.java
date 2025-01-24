import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Map;
import java.util.Set;

/**
 * authors:
 * @author Abdullah Khalid Bin Ammar
 * @author Abdullah Altamimi
 * @author Abdulrahim alfaifi
 * @author
 */
/**
 * Requriments:
 * Implement LL(1) parser and print LL(1) parsing table. 
    •	The input shall be a simple grammar that is free from left recursion, left factoring.
    •	Compute the First, Follow for all variables. (3 Marks)
    •	Print LL(1) parsing table (3 Marks)
    •	If the resulting parsing table contains multiply defined entries in any cell then print  “The grammar is ambiguous or it is inherently not a LL(1) grammar” (2 Marks)
    •	Include the full code in the report (1 Mark)
    •	Test your program for at least two different examples and print the results in the report. (1 Mark)
 */

public class Main extends javax.swing.JFrame {
    private JButton runInputButton;
    private JButton runEx1Button;
    private JButton runEx2Button;
    private JScrollPane grammarScrollPane;
    private JScrollPane firstSetsScrollPane;
    private JScrollPane followSetsScrollPane;
    private JScrollPane parsingTableScrollPane;
    private JTextArea grammarTextArea;
    private JTextArea firstSetsTextArea;
    private JTextArea followSetsTextArea;
    private JTextArea parsingTableTextArea;
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

        Font contentFont = new Font("Tahoma", Font.PLAIN, 13);

        customizeTextArea(grammarTextArea, contentFont);
        customizeTextArea(firstSetsTextArea, contentFont);
        customizeTextArea(followSetsTextArea, contentFont);
        customizeTextArea(parsingTableTextArea, contentFont);

        customizeButton(runInputButton);
        customizeButton(runEx1Button);
        customizeButton(runEx2Button);

        customizeScrollPane(grammarScrollPane, "Grammar");
        customizeScrollPane(firstSetsScrollPane, "First Sets");
        customizeScrollPane(followSetsScrollPane, "Follow Sets");
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
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        // button.setBorder(new RoundedBorder(5, new Color(70, 130, 180)));
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
                new Font("Tahoma", Font.BOLD, 12),
                new Color(70, 130, 180)
        ));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        grammarScrollPane = new JScrollPane();
        grammarTextArea = new JTextArea();
        runInputButton = new JButton();
        runEx1Button = new JButton(); //  for Example Normal LL1 Grammar
        runEx2Button = new JButton();//  for Example Ambiguous Grammar
        firstSetsScrollPane = new JScrollPane();
        firstSetsTextArea = new JTextArea();
        followSetsScrollPane = new JScrollPane();
        followSetsTextArea = new JTextArea();
        parsingTableScrollPane = new JScrollPane();
        parsingTableTextArea = new JTextArea();
        inputSeparator = new JSeparator();
        outputSeparator = new JSeparator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int colSize = 25;
        grammarTextArea.setColumns(colSize);
        grammarTextArea.setFont(new Font("Tahoma", 0, 14));
        grammarTextArea.setRows(5);
        grammarTextArea.setText("S -> A C\nA -> a B | ε\nB -> b C | ε\nC -> ( c )"); // Default Grammar
        grammarScrollPane.setViewportView(grammarTextArea);

        runInputButton.setText("Run Input");
        runInputButton.addActionListener(evt -> runInputButtonActionPerformed(evt));

        runEx1Button.setText("Run Ex1 (LL1 Grammar)");
        runEx1Button.addActionListener(evt -> runEx1ButtonActionPerformed(evt));

        runEx2Button.setText("Run Ex2 (ambiguous Grammar)");
        runEx2Button.addActionListener(evt -> runEx2ButtonActionPerformed(evt));

        firstSetsTextArea.setColumns(colSize);
        firstSetsTextArea.setFont(new Font("Tahoma", 0, 14));
        firstSetsTextArea.setRows(5);
        firstSetsScrollPane.setViewportView(firstSetsTextArea);

        followSetsTextArea.setColumns(colSize);
        followSetsTextArea.setFont(new Font("Tahoma", 0, 14));
        followSetsTextArea.setRows(5);
        followSetsScrollPane.setViewportView(followSetsTextArea);

        parsingTableTextArea.setColumns(colSize);
        parsingTableTextArea.setRows(5);
        parsingTableScrollPane.setViewportView(parsingTableTextArea);

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
                                                .addComponent(grammarScrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(runInputButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(runEx1Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(runEx2Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(parsingTableScrollPane, GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(firstSetsScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(followSetsScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                                .addGap(17, 17, 17))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(parsingTableScrollPane)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(grammarScrollPane, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(runInputButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(runEx1Button)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(runEx2Button)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(firstSetsScrollPane, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(followSetsScrollPane))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(outputSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(parsingTableScrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }

    private void runInputButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Run Input button clicked");
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();

        String grammar = grammarTextArea.getText();

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
        System.out.println("Run Input button processing completed");
    }

    private void runEx1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Run Ex1 button clicked");
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();

        String grammar = "E -> T E'\nE' -> + T E' | ε\nT -> F T'\nT' -> * F T' | ε\nF -> ( E ) | id";
        grammarTextArea.setText(grammar);

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
        System.out.println("Run Ex1 button processing completed");
    }

    private void runEx2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Run Ex2 button clicked");
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();

        String grammar = "S -> ( L ) | a\nL -> L L'\nL' -> ) S L' |  ε";
        grammarTextArea.setText(grammar);

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
        System.out.println("Run Ex2 button processing completed");
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
