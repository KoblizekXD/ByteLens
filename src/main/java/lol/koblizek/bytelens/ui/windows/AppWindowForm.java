package lol.koblizek.bytelens.ui.windows;

import com.formdev.flatlaf.icons.FlatFileViewComputerIcon;
import lol.koblizek.bytelens.ui.Form;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppWindowForm extends Form {

    public static final Font JETBRAINS_MONO;

    static {
        try {
            JETBRAINS_MONO = Font.createFont(Font.TRUETYPE_FONT, AppWindowForm.class.getResourceAsStream("/fonts/ttf/JetBrainsMono-Regular.ttf"))
                    .deriveFont(18f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initComponents() {
        setLayout(new BorderLayout());
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setSize(840, 480);
        setTitle("ByteLens - Main Menu");
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        createScheme(textArea);
        textArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setBorder(null);
        add(sp, BorderLayout.CENTER);
        add(createLeftBar(), BorderLayout.LINE_START);
    }

    private JPanel createLeftBar() {
        JPanel leftPanel = new JPanel();
        JToolBar leftBar = new JToolBar();
        leftBar.setOrientation(JToolBar.VERTICAL);
        leftBar.setFloatable(false);
        var ico = new JButton(icon("projectDirectory_dark"));
        ico.setPreferredSize(new Dimension(32, 32));
        leftBar.add(ico);
        leftPanel.add(leftBar);
        return leftPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu projectName = new JMenu("Test Project");
        projectName.setEnabled(false);
        menuBar.add(projectName);
        JMenu fileMenu = new JMenu("File");
        fileMenu.add("New");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu toolsMenu = new JMenu("Tools");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    private void createScheme(RSyntaxTextArea textArea) {
        textArea.setFont(JETBRAINS_MONO);
        textArea.setBackground(UIManager.getColor("TextArea.background"));
        textArea.setForeground(UIManager.getColor("TextArea.foreground"));
        textArea.setCurrentLineHighlightColor(new Color(0, 0, 0, 50));
        textArea.setSelectionColor(new Color(255, 255, 255, 40));
        textArea.setMatchedBracketBGColor(new Color(255, 255, 255, 70));
        textArea.setMatchedBracketBorderColor(null);
        textArea.setTabLineColor(Color.decode("0x35513e"));
        textArea.setTabSize(4);
        textArea.setTabsEmulated(true);
        textArea.setPaintTabLines(true);
        SyntaxScheme syntaxScheme = textArea.getSyntaxScheme();
        Style style = syntaxScheme.getStyle(Token.RESERVED_WORD);
        style.foreground = Color.decode("0xc477db");
        style.font = JETBRAINS_MONO.deriveFont(Font.ITALIC);
        syntaxScheme.getStyle(Token.LITERAL_BOOLEAN).foreground = Color.decode("0xc477db");
        syntaxScheme.getStyle(Token.RESERVED_WORD_2).foreground = Color.decode("0xc477db");
        syntaxScheme.getStyle(Token.IDENTIFIER).foreground = Color.decode("0xdfbd79");
        syntaxScheme.getStyle(Token.FUNCTION).foreground = Color.decode("0xdfbd79");
        syntaxScheme.getStyle(Token.DATA_TYPE).foreground = Color.decode("0x3f8be9");
        syntaxScheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.decode("0x78be74");
        syntaxScheme.getStyle(Token.LITERAL_BACKQUOTE).foreground = Color.decode("0x78be74");
        syntaxScheme.getStyle(Token.COMMENT_EOL).foreground = Color.decode("0x5a6061");
        syntaxScheme.getStyle(Token.COMMENT_MULTILINE).foreground = Color.decode("0x5a6061");
        syntaxScheme.getStyle(Token.ANNOTATION).foreground = Color.decode("0xe3be7a");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.SEPARATOR).foreground = Color.decode("0x3f9456");
        syntaxScheme.getStyle(Token.VARIABLE).foreground = Color.decode("0x3f9456");
        textArea.setSyntaxScheme(syntaxScheme);
    }
}
