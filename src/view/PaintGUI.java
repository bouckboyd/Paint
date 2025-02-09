/*
 * TCSS 305 - Assignment 5
 */

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.EllipseTool;
import model.LineTool;
import model.RectangleTool;
import model.RoundRectangleTool;

/**
 * Presents the GUI for the Paint application.
 * 
 * @author Boyd Bouck 
 * @version 8 Mar 2024
 */
public final class PaintGUI {
    
    // constants 
    
    /**
    * The initial color for the DrawingPanel. 
    */
    static final Color DEFAULT_LINE_COLOR = new Color(0, 0, 255);
    
    /**
     * Width of the JFrame when the GUI starts. 
     */
    private static final int INITIAL_FRAME_WIDTH = 
            Toolkit.getDefaultToolkit().getScreenSize().width / 3;
    
    /**
     * Height of the JFrame when the GUI starts. 
     */
    private static final int INITIAL_FRAME_HEIGHT = 
            Toolkit.getDefaultToolkit().getScreenSize().height / 3;
    
    /**
     * Max thickness in pixels the thickness slider can be set to. 
     */
    private static final int MAX_THICKNESS = 20;
    
    /**
     * Initial thickness in pixels the thickness slider will be set to. 
     */
    private static final int INITIAL_THICKNESS = 2;
    
    /**
     * Spacing of major ticks for the thickness slider. 
     */
    private static final int MAJOR_TICK_SPACING = 5;
    
    /**
     * Spacing of minor ticks for the thickness slider. 
     */
    private static final int MINOR_TICK_SPACING = 1;
    
    /**
     * Minimum grid spacing shown on the grid slider. 
     */
    private static final int MIN_GRID_SPACING = 10;
    
    /**
     * Maximum grid spacing shown on the grid slider. 
     */
    private static final int MAX_GRID_SPACING = 50;
    
    /**
     * Grid spacing that the grid slider is initially set to. 
     */
    private static final int INITIAL_GRID_SPACING = 30;
    
    /**
     * The icon for the About window. 
     */
    private static final Icon ABOUT_ICON = new ImageIcon("files/brush_icon-64.png");
    
    /**
     * Title for the JFrame and About JOptionPane. 
     */
    private static final String WINDOW_TITLE = "TCSS 305 Paint";
    
    /**
     * The label that goes on Line buttons. 
     */
    private static final String LINE_TITLE = "Line";
    
    /**
     * The label that goes on Rectangle buttons. 
     */
    private static final String RECTANGLE_TITLE = "Rectangle";
    
    /**
     * The label that goes on RoundRectangle buttons. 
     */
    private static final String ROUND_RECTANGLE_TITLE = "RoundRectangle";
    
    /**
     * The label that goes on Ellpise buttons. 
     */
    private static final String ELLIPSE_TITLE = "Ellispe"; 
    
    /**
     * Stores String representing the three types of shapes. 
     */
    private static final List<String> SHAPE_LIST = new ArrayList<>(); 

    
    // instance fields
    
    /**
     * Main window for the GUI. 
     */
    private final JFrame myFrame;
    
    /**
     * An instance of DrawingPanel for the GUI. 
     */
    private final DrawingPanel myDrawingPanel;
    
    /**
     * LineTool currently in use. 
     */
    private final LineTool myLineTool;
    
    /**
     * RectangleTool currently in use. 
     */
    private final RectangleTool myRectangleTool;
    
    /**
     * RoundRectangleTool currently in use. 
     */
    private final RoundRectangleTool myRoundRectangleTool; 
    
    /**
     * EllipseTool currently in use. 
     */
    private final EllipseTool myEllipseTool;
    
    /**
     * Action for drawing lines. 
     */
    private final LineAction myLineAction; 
    
    /**
     * Action for drawing rectangles. 
     */
    private final RectangleAction myRectangleAction;
    
    /**
     * Action for drawing rounded rectangles. 
     */
    private final RoundRectangleAction myRoundRectangleAction;
    
    /**
     * Action for drawing ellipses. 
     */
    private final EllipseAction myEllipseAction;
    

    /**
     * Initializes the instance fields for and starts the GUI. 
     */
    public PaintGUI() { 
        
        myFrame = new JFrame(WINDOW_TITLE);
        myDrawingPanel = new DrawingPanel();
        
        myLineTool = new LineTool();
        myRectangleTool = new RectangleTool();
        myRoundRectangleTool = new RoundRectangleTool();
        myEllipseTool = new EllipseTool();
        
        myLineAction = new LineAction();
        myRectangleAction = new RectangleAction();
        myRoundRectangleAction = new RoundRectangleAction();
        myEllipseAction = new EllipseAction();

        // setup and display the GUI
        start();
        
    }
    
    /**
     * Initializes the List<String> of types of shapes. 
     */
    public void fillList() {
        SHAPE_LIST.add(LINE_TITLE);
        SHAPE_LIST.add(RECTANGLE_TITLE);
        SHAPE_LIST.add(ROUND_RECTANGLE_TITLE);
        SHAPE_LIST.add(ELLIPSE_TITLE);
    }

    /**
     * Performs all tasks necessary to display the UI.
     */
    protected void start() {
        
        fillList();
        
        myDrawingPanel.setCurrentTool(myLineTool);
        
        myFrame.setJMenuBar(createMenuBar());
        myFrame.add(myDrawingPanel);
        myFrame.add(createToolBar(), BorderLayout.SOUTH);
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final IllegalAccessException e) { 
        } catch (final UnsupportedLookAndFeelException e) {
        } catch (final ClassNotFoundException e) {
        } catch (final InstantiationException e) {
        }
        
        final ImageIcon icon = new ImageIcon("files/brush_icon.png");
        myFrame.setIconImage(icon.getImage());

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(INITIAL_FRAME_WIDTH, INITIAL_FRAME_HEIGHT);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        
    }
    
    /**
     * Creates the menu bar and adds its components. 
     * 
     * @return the menu bar for the GUI. 
     */
    private JMenuBar createMenuBar() {
        
        final JMenuBar menuBar = new JMenuBar();
        
        // setup the options menu
        final JMenu options = new JMenu("Options");
        final JMenu thickness = new JMenu("Thickness");
        final JMenu gridSpacing = new JMenu("Grid spacing");
        thickness.add(createThicknessSlider());
        options.add(thickness);
        gridSpacing.add(createGridSpacingSlider());
        options.add(gridSpacing);
        options.addSeparator();
        options.add(createColorMenuItem());
        options.addSeparator();
        options.add(createClearButton());
        menuBar.add(options);
        
        // setup the tools menu
        final JMenu tools = new JMenu("Tools");
        final ButtonGroup menuGroup = new ButtonGroup();
        for (final String shape : SHAPE_LIST) {
            
            final JRadioButtonMenuItem button;
            
            // add Actions to each button
            if (shape.equals(LINE_TITLE)) {
                button = new JRadioButtonMenuItem(myLineAction);
                button.setSelected(true);
            } else if (shape.equals(RECTANGLE_TITLE)) {
                button = new JRadioButtonMenuItem(myRectangleAction);
            } else if (shape.equals(ROUND_RECTANGLE_TITLE)) {
                button = new JRadioButtonMenuItem(myRoundRectangleAction);
            } else {
                button = new JRadioButtonMenuItem(myEllipseAction);
            }
            
            menuGroup.add(button);
            tools.add(button);
            
        }
        menuBar.add(tools);
        
        // setup the help menu
        final JMenu help = new JMenu("Help");
        help.add(createAboutButton());
        menuBar.add(help);
        
        return menuBar;
    }
    
    /**
     * Initializes the toolbar and adds all necessary components. 
     * 
     * @return the toolbar for the GUI. 
     */
    private JToolBar createToolBar() {
        
        final JToolBar toolbar = new JToolBar();
        final ButtonGroup toolbarGroup = new ButtonGroup();
        for (final String shape : SHAPE_LIST) {
            final JToggleButton button; 
            
            // add Actions to each button
            if (shape.equals(LINE_TITLE)) {
                button = new JToggleButton(myLineAction);
                button.setSelected(true);
            } else if (shape.equals(RECTANGLE_TITLE)) {
                button = new JToggleButton(myRectangleAction);
            } else if (shape.equals(ROUND_RECTANGLE_TITLE)) {
                button = new JToggleButton(myRoundRectangleAction);
            } else {
                button = new JToggleButton(myEllipseAction);
            }
            
            toolbarGroup.add(button);
            toolbar.add(button);
            
        }
        
        // Create the Undo button. 
        final JButton undo = new JButton();
        // i need to make the undo button smaller somehow. 
        undo.setIcon(new ImageIcon("files/icons8-undo-30.png")); 
        undo.setEnabled(false);
        undo.addActionListener(new UndoActionListener(undo));
        toolbar.add(undo);
        
        // Create the Grid check box. 
        final JCheckBox grid = new JCheckBox("Grid");
        grid.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawingPanel.setGridVisibility(grid.isSelected());
            }
            
        });
        toolbar.add(grid);
        
        return toolbar; 
    }
    
    /**
     * Creates the slider for shape thickness. 
     * 
     * @return a slider to control shape thickness. 
     */
    private JSlider createThicknessSlider() {
        
        final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 
                MAX_THICKNESS, INITIAL_THICKNESS);
        slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        slider.setMinorTickSpacing(MINOR_TICK_SPACING);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myDrawingPanel.setThickness(new BasicStroke(slider.getValue()));
            }
            
        });
        
        return slider;
    }
    
    /**
     * Creates the slider for grid spacing. 
     * 
     * @return a slider to control the grid spacing. 
     */
    private JSlider createGridSpacingSlider() {
        
        final JSlider slider = new JSlider(SwingConstants.HORIZONTAL,
                MIN_GRID_SPACING, MAX_GRID_SPACING, INITIAL_GRID_SPACING);
        slider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myDrawingPanel.setGridSpacing(slider.getValue());
            }
            
        });
        
        return slider;
    }
    
    /**
     * Initializes and sets up the color menu item. 
     * 
     * @return a JMenuItem for color selection. 
     */
    public JMenuItem createColorMenuItem() {
        
        final JMenuItem cmi = new JMenuItem("Color...");
        final JColorChooser colorChooser = new JColorChooser(DEFAULT_LINE_COLOR);
        cmi.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                colorChooser.setColor(JColorChooser.showDialog(
                        null, "Select A Color", colorChooser.getColor()));
                myDrawingPanel.setColor(colorChooser.getColor());
            }
            
        });
        
        return cmi;
    }
    
    /**
     * Initializes and sets up the clear button in the menu. 
     * 
     * @return the functional clear button. 
     */
    public JMenuItem createClearButton() {
        
        final JMenuItem clearBtn = new JMenuItem("Clear");
        clearBtn.setEnabled(false);
        clearBtn.addActionListener(new ClearActionListener(clearBtn));
        
        return clearBtn;
    }
    
    /**
     * Initializes and sets up the about button in the menu. 
     * 
     * @return the functional about button. 
     */
    public JMenuItem createAboutButton() {
        
        final JMenuItem aboutBtn = new JMenuItem("About...");
        aboutBtn.addActionListener(new ActionListener() { 
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myFrame, "Boyd Bouck\nWinter 2024", 
                        WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE, ABOUT_ICON);
            }
            
        });
        
        return aboutBtn;
    }
    
    /**
     * Action for drawing a line. 
     * 
     * @author Boyd Bouck
     * @version 1 Mar 2024
     */
    private final class LineAction extends AbstractAction {
        
        /**
         * Version ID for serialization of this class. 
         */
        private static final long serialVersionUID = -1758026835026722768L;

        /**
         * Constructs a new LineAction. 
         */
        LineAction() {
            super(LINE_TITLE, new ImageIcon("files/line_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setCurrentTool(myLineTool);
        }
        
    }
    
    /**
     * Action for drawing a rectangle. 
     * 
     * @author Boyd Bouck
     * @version 1 Mar 2024
     */
    private final class RectangleAction extends AbstractAction {
        
        /**
         * Version ID for serialization of this class. 
         */
        private static final long serialVersionUID = 2230249129153365757L;

        /**
         * Constructs a new RectangleAction. 
         */
        RectangleAction() {
            super(RECTANGLE_TITLE, new ImageIcon("files/rectangle_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setCurrentTool(myRectangleTool);
        }
        
    }
    
    /**
     * Action for drawing a rounded rectangle. 
     * 
     * @author Boyd Bouck
     * @version 8 Mar 2024
     */
    private final class RoundRectangleAction extends AbstractAction {

        /**
         * Version ID for serialization of this class. 
         */
        private static final long serialVersionUID = -709150524800905783L;

        /**
         * Constructs a new RoundRectangleAction. 
         */
        RoundRectangleAction() {
            super(ROUND_RECTANGLE_TITLE, new ImageIcon("files/roundrectangle_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setCurrentTool(myRoundRectangleTool);
        }
        
    }
    
    /**
     * Action for drawing an ellipse. 
     * 
     * @author Boyd Bouck
     * @version 1 Mar 2024
     */
    private final class EllipseAction extends AbstractAction {
        
        /**
         * Version ID for serialization of this class. 
         */
        private static final long serialVersionUID = -8689990838735889801L;

        /**
         * Constructs a new EllipseAction. 
         */
        EllipseAction() {
            super(ELLIPSE_TITLE, new ImageIcon("files/ellipse_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setCurrentTool(myEllipseTool);
        }
        
    }
    
    /**
     * Action listener for the "Clear" button. 
     * 
     * @author Boyd Bouck
     * @version 8 Mar 2024
     */
    private final class ClearActionListener implements ActionListener, PropertyChangeListener {
        
        /**
         * Reference to the Clear menu item for use by its ActionListener. 
         */
        private final JMenuItem myButton;
        
        /**
         * Constructs a new ClearActionListener. 
         * 
         * @param theButton is the menu button tied to this listener.
         */
        ClearActionListener(final JMenuItem theButton) {
            myButton = theButton;
            myDrawingPanel.addPropertyChangeListener(this);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.clearList();
            myButton.setEnabled(false);
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            myButton.setEnabled((boolean) theEvent.getNewValue());
        }
        
    }
    
    /**
     * Action listener for the "Undo" button. 
     * 
     * @author Boyd Bouck
     * @version 8 Mar 2024
     */
    private final class UndoActionListener implements ActionListener, PropertyChangeListener {
        
        /**
         * Reference to the Undo button for use by its ActionListener.
         */
        private final JButton myButton;
        
        /**
         * Constructs a new UndoActionListener. 
         * 
         * @param theButton is the button tied to this listener. 
         */
        UndoActionListener(final JButton theButton) {
            myButton = theButton;
            myDrawingPanel.addPropertyChangeListener(this);
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.undoLastShape();
        }
        
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            myButton.setEnabled((boolean) theEvent.getNewValue());
        }
        
    }

}
