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
 * @author Moath Alshehri
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
    // all GUI components
    private JButton runInputButton;
    private JButton runEx1Button;
    private JButton runEx2Button;
    private JScrollPane grammarConainer;
    private JScrollPane firstSetsContainer;
    private JScrollPane followSetsContainer;
    private JScrollPane parsingTableContainer;
    private JTextArea grammarTextArea;
    private JTextArea firstSetsTextArea;
    private JTextArea followSetsTextArea;
    private JTextArea parsingTableTextArea;
    private JSeparator inputSeparator;
    private JSeparator outputSeparator;
    Color mainColor = new Color(70, 130, 180);
    Color textAreaBGColor = new Color(248, 248, 255);
    String MainFont = "Tahoma";

    public Main() {
        System.out.println("Running APP...");
        initComponents(); // Initialize all components
        setPreferredSize(new Dimension(1200, 800));  // Set the window size
        pack(); // To fit its content
        setLocationRelativeTo(null); // Center the window on the screen
        customizeComponents();
        System.out.println("Running Completed, Enjoy LL1 Parser :)");
    }

    private void customizeComponents() {
        // some customizations for the components
        setTitle("Grammar Parser");
        getContentPane().setBackground(new Color(240, 240, 245));


        customizeTextArea(grammarTextArea);
        customizeTextArea(firstSetsTextArea);
        customizeTextArea(followSetsTextArea);
        customizeTextArea(parsingTableTextArea);

        customizeButton(runInputButton);
        customizeButton(runEx1Button);
        customizeButton(runEx2Button);

        customizeContainer(grammarConainer, "Grammar");
        customizeContainer(firstSetsContainer, "First Sets");
        customizeContainer(followSetsContainer, "Follow Sets");
        customizeContainer(parsingTableContainer, "Parsing Table");
    }

    private void customizeTextArea(JTextArea textArea) {
        Font contentFont = new Font(MainFont, Font.PLAIN, 14);
        textArea.setFont(contentFont);
        textArea.setBackground(textAreaBGColor);
        textArea.setBorder(new RoundedBorder(5, new Color(252, 252, 255))); // only for radus
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font(MainFont, Font.BOLD, 12));
        button.setBackground(mainColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(mainColor);
            }
        });
    }

    private void customizeContainer(JScrollPane scrollPane, String title) {
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                new RoundedBorder(5, mainColor),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font(MainFont, Font.BOLD, 12),
                mainColor
        ));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // start building the GUI
        grammarConainer = new JScrollPane(); // Input Grammar Pane
        grammarTextArea = new JTextArea(); // Input Grammar Text Area
        runInputButton = new JButton();
        runEx1Button = new JButton(); //  for Example Normal LL1 Grammar
        runEx2Button = new JButton();//  for Example Ambiguous Grammar
        firstSetsContainer = new JScrollPane();
        firstSetsTextArea = new JTextArea();
        followSetsContainer = new JScrollPane();
        followSetsTextArea = new JTextArea();
        parsingTableContainer = new JScrollPane();
        parsingTableTextArea = new JTextArea();
        inputSeparator = new JSeparator();
        outputSeparator = new JSeparator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int colSize = 25;
        // Input Grammar Text Area
        grammarTextArea.setColumns(colSize);
        grammarTextArea.setRows(5);
        grammarTextArea.setText("S -> A C\nA -> a B | ε\nB -> b C | ε\nC -> ( c )"); // Default Grammar
        grammarConainer.setViewportView(grammarTextArea);
        // First Sets Output Text Area
        firstSetsTextArea.setColumns(colSize);
        firstSetsTextArea.setRows(5);
        firstSetsContainer.setViewportView(firstSetsTextArea);
        
        // Output Follow Sets Output Text Area
        followSetsTextArea.setColumns(colSize);
        followSetsTextArea.setRows(5);
        followSetsContainer.setViewportView(followSetsTextArea);
        // Output Parsing Table Text Area
        parsingTableTextArea.setColumns(colSize);
        parsingTableTextArea.setRows(5);
        parsingTableContainer.setViewportView(parsingTableTextArea);

        // Run Input Button
        runInputButton.setText("Run Input");
        runInputButton.addActionListener(evt -> runInputButtonActionPerformed(evt));
        // Run Ex1 Button
        runEx1Button.setText("Run Ex1 (LL1 Grammar)");
        runEx1Button.addActionListener(evt -> runEx1ButtonActionPerformed(evt));
        // Run Ex2 Button
        runEx2Button.setText("Run Ex2 (ambiguous Grammar)");
        runEx2Button.addActionListener(evt -> runEx2ButtonActionPerformed(evt));

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
                                                .addComponent(grammarConainer, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(runInputButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(runEx1Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(runEx2Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(parsingTableContainer, GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(firstSetsContainer, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(followSetsContainer, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                                .addGap(17, 17, 17))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(parsingTableContainer)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(grammarConainer, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(firstSetsContainer, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(followSetsContainer))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(outputSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(parsingTableContainer, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }
    private void ButtonAction(String inputGrammar, String nameButton){
        System.out.println("Run"+nameButton+" button clicked");
        grammarTextArea.setText(inputGrammar);
        firstSetsTextArea.setText("");
        followSetsTextArea.setText("");
        parsingTableTextArea.setText("");
        LL1P parser = new LL1P();
        // Get String grammar from input text area
        String grammar = inputGrammar;

        parser.readGrammarFromString(grammar);
        parser.processGrammar();
        // Output First Sets in the text area
        Map<String, Set<String>> firstSets = parser.getFirstSets();
        for (String nonTerminal : firstSets.keySet()) {
            firstSetsTextArea.append(nonTerminal + " : " + firstSets.get(nonTerminal) + "\n");
        }
        
        // Output Follow Sets in the text area
        Map<String, Set<String>> followSets = parser.getFollowSets();
        for (String nonTerminal : followSets.keySet()) {
            followSetsTextArea.append(nonTerminal + " : " + followSets.get(nonTerminal) + "\n");
        }
        // Output Parsing Table in the text area Of Parsing Table
        if (parser.isLL1Grammar()) {
            System.out.println("The grammar is LL(1)");
            String parsingTableString = parser.getParsingTableAsString();
            parsingTableTextArea.setText(parsingTableString);
        } else {
            System.out.println("The grammar is ambiguous or it is not a LL(1) grammar :(");
            parsingTableTextArea.setText("Error: The grammar is ambiguous or it is not a LL(1) grammar :(");
        }
        // Print All Results in Terminal
        parser.printAllResults();
        System.out.println("Run "+nameButton+" button processing completed");
    }

    private void runInputButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nameButton = evt.getActionCommand();
        ButtonAction(grammarTextArea.getText(), nameButton);
    }

    private void runEx1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nameButton = evt.getActionCommand();
        ButtonAction("S -> A C\nA -> a B | ε\nB -> b C | ε\nC -> ( c )",nameButton);
    }
    
    private void runEx2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nameButton = evt.getActionCommand();
        ButtonAction("S -> ( Q ) | a\nQ -> Q Q'\nQ' -> ) S A B D U L L A H L' |  ε", nameButton);
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
