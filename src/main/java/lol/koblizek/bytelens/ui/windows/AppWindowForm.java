package lol.koblizek.bytelens.ui.windows;

import com.formdev.flatlaf.ui.FlatSplitPaneUI;
import lol.koblizek.bytelens.ui.Form;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

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

    static class CustomFlatSplitPaneUI extends FlatSplitPaneUI {
        static class CustomDivider extends BasicSplitPaneDivider {

            /**
             * Creates an instance of {@code BasicSplitPaneDivider}. Registers this
             * instance for mouse events and mouse dragged events.
             *
             * @param ui an instance of {@code BasicSplitPaneUI}
             */
            public CustomDivider(BasicSplitPaneUI ui) {
                super(ui);
            }

            @Override
            public void paint(Graphics g) {
                // super.paint(g);
            }
        }

        @Override
        public BasicSplitPaneDivider createDefaultDivider() {
            return new CustomDivider(this);
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
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createLeftPanel());
        splitPane.setRightComponent(sp);
        splitPane.setUI(new CustomFlatSplitPaneUI());
        splitPane.setDividerSize(2);
        add(createLeftBar(), BorderLayout.LINE_START);
        add(splitPane);
        handleToolbarButtons(this.getContentPane());
    }

    private JPanel createVirtualTitleBar(String title) {
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setBackground(Color.decode("0x323844"));
        titleBar.setPreferredSize(new Dimension(0, 38));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.decode("0xabb2bf"));
        titleLabel.setFont(titleBar.getFont().deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JButton closeButton = new JButton(icon("hide_dark", 16));
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        closeButton.setBackground(Color.decode("0x323844"));
        titleBar.add(titleLabel, BorderLayout.CENTER);
        titleBar.add(closeButton, BorderLayout.LINE_END);
        return titleBar;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(createVirtualTitleBar("Project"), BorderLayout.PAGE_START);
        leftPanel.setPreferredSize(new Dimension(240, 0));
        leftPanel.setName("appwindowform.leftPanel");
        leftPanel.setBackground(Color.decode("0x21252b"));
        return leftPanel;
    }

    private JToolBar createLeftBar() {
        JToolBar leftBar = new JToolBar();
        leftBar.setOrientation(JToolBar.VERTICAL);
        leftBar.setFloatable(false);
        var ico = new JButton(icon("projectDirectory_dark"));
        ico.setPreferredSize(new Dimension(32, 32));
        ico.setSelected(true);
        leftBar.add(ico);
        leftBar.add(Box.createRigidArea(new Dimension(0, 3)));
        leftBar.add(new JButton(icon("structure_dark")));
        leftBar.setBackground(Color.decode("0x21252b"));
        leftBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("0x282c34")));
        return leftBar;
    }

    private void handleToolbarButtons(Container wrapper) {
        JToolBar tb = (JToolBar) wrapper.getComponent(0);
        JSplitPane sp = (JSplitPane) wrapper.getComponent(1);
        JPanel left = (JPanel) sp.getLeftComponent();
        for (Component c : tb.getComponents()) {
            if (c instanceof JButton btn) {
                btn.addActionListener(e -> {
                    btn.setSelected(true);
                    Arrays.stream(tb.getComponents()).filter(x -> !x.equals(btn) && x instanceof JButton)
                            .forEach(x -> ((JButton) x).setSelected(false));
                    System.out.println("new selected");
                    // TODO: Here redraw based on selection
                    if (btn.getName() != null) {
                        ((JPanel)left.getComponent(1)).removeAll();
                        left.add(redrawBasedOnComponent(btn.getName(), left), 1);
                        left.revalidate();
                    } else {
                        logger().warn("Key identificator not found for selected button");
                    }
                });
            }
        }
    }

    private JPanel redrawBasedOnComponent(String key, JPanel panel) {
        return panel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(JETBRAINS_MONO.deriveFont(14f));
        menuBar.setPreferredSize(new Dimension(0, 38));
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
        syntaxScheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground = Color.decode("0x5a6061");
        syntaxScheme.getStyle(Token.COMMENT_KEYWORD).foreground = Color.decode("0xc477db");
        syntaxScheme.getStyle(Token.ANNOTATION).foreground = Color.decode("0xe3be7a");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = Color.decode("0xcb824b");
        syntaxScheme.getStyle(Token.SEPARATOR).foreground = Color.decode("0x3f9456");
        syntaxScheme.getStyle(Token.VARIABLE).foreground = Color.decode("0x3f9456");
        textArea.setSyntaxScheme(syntaxScheme);
    }
}
