/*
 * TCSS 305 - Assignment 5
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import model.DrawingTool;
import model.EllipseTool;
import model.LineTool;
import model.RectangleTool;
import model.RoundRectangleTool;

/**
 * Draws and displays the shapes that the user paints. 
 * 
 * @author Boyd Bouck
 * @version 8 Mar 2024
 */
public final class DrawingPanel extends JPanel {

    /**
     * A version ID for serialization of this class. 
     */
    private static final long serialVersionUID = 7195518585499696974L;

    /**
     * A Stroke with a width of zero used for comparison. 
     */
    private static final Stroke ZERO_STROKE = new BasicStroke(0);
    
    /**
     * Default spacing for the grid lines. 
     */
    private static final int INITIAL_GRID_SPACING = 30;
    
    /**
     * A Toolkit for use by the DrawingPanel. 
     */
    private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    
    /**
     * Color for the grid lines. The color is (not) Husky Gold. 
     */
    private static final Color GRID_COLOR = new Color(220, 220, 220); 
    
    /**
     * Thickness for the grid lines. 
     */
    private static final Stroke GRID_STROKE = new BasicStroke(1);
    
    /**
     * Name for property change to enable the clear and undo buttons. 
     */
    private static final String ENABLE_CLEAR = "e";
    
    /**
     * Name for property change to disable the clear and undo buttons. 
     */
    private static final String DISABLE_CLEAR = "d";
    
    /**
     * Tool currently being used to draw on the panel. 
     */
    private DrawingTool myCurrentTool;
    
    /**
     * Thickness for the shape currently being drawn. 
     */
    private Stroke myThickness;
    
    /**
     * Color for the shape currently being drawn. 
     */
    private Color myColor; 
    
    /**
     * Stores previously drawn shapes. 
     */
    private final List<DrawnShape> myDrawnShapes;
    
    /**
     * Signifies if the panel was just cleared. 
     */
    private boolean myCleared;
    
    /**
     * Signifies if a call to paintComponent is triggered explicitly in the code, or if it is
     * triggered implicitly (because of resizing). 
     */
    private boolean myCallForResize;
    
    /**
     * A PropertyChangeSupport object to fire events regarding the clear menu button. 
     */
    private PropertyChangeSupport myPCS;
    
    /**
     * The spacing between each grid line in pixels. 
     */
    private int myGridSpacing;
    
    /**
     * Signifies whether or not the grid is visible. 
     */
    private boolean myGridVisibility;
    
    
    /**
     * Constructs a new DrawingPanel. 
     */
    public DrawingPanel() {
        super();
        myDrawnShapes = new ArrayList<>();
        myThickness = new BasicStroke(2); // default thickness
        myColor = PaintGUI.DEFAULT_LINE_COLOR; // default color
        myCleared = false;
        myPCS = new PropertyChangeSupport(this);
        myGridSpacing = INITIAL_GRID_SPACING; 
        myGridVisibility = false;
        setup();
    }
    
    /**
     * Sets up the drawing panel. 
     */
    public void setup() {
        
        setBackground(Color.WHITE);
        
        final MouseListener listener = new MouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
    }
    
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }
    
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener); 
    }
    
    public void setCurrentTool(final DrawingTool theTool) {
        myCurrentTool = theTool;
    }
    
    public void setThickness(final Stroke theThickness) {
        myThickness = theThickness; 
    }
    
    public void setColor(final Color theColor) {
        myColor = theColor;
    }
    
    public void setGridSpacing(final int theSpacing) {
        myGridSpacing = theSpacing;
        myCleared = true;
        myCallForResize = false;
        repaint();
    }
    
    public void setGridVisibility(final boolean theVisibility) {
        myGridVisibility = theVisibility; 
        repaint();
    }
    
    /**
     * @return the DrawingTool used to draw the current shape. 
     */
    public DrawingTool getCurrentTool() {
        return myCurrentTool;
    }
    
    /**
     * Clears all elements from the DrawingPanel. This method is called when
     * the 'Clear' button is clicked. 
     */
    public void clearList() {
        myDrawnShapes.clear();
        myPCS.firePropertyChange(DISABLE_CLEAR, true, false);
        myCleared = true;
        myCallForResize = false;
        repaint();
    }
    
    /**
     * Removes the most recently drawn shape from the DrawingPanel. This method is called when
     * the 'Undo' button is clicked. 
     */
    public void undoLastShape() {
        myDrawnShapes.remove(myDrawnShapes.size() - 1);
        if (myDrawnShapes.isEmpty()) {
            // disable clear and undo buttons
            myPCS.firePropertyChange(DISABLE_CLEAR, true, false);
        }
        repaint();
    }
    
    /**
     * Paints the DrawnShapes to the DrawingPanel. 
     * 
     * @param theGraphics
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        // Blurs pixels for sharper image
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draws the grid lines (if enabled)
        if (myGridVisibility) {
            g2d.setColor(GRID_COLOR);
            g2d.setStroke(GRID_STROKE);
            final int height = TOOLKIT.getScreenSize().height; 
            final int width = TOOLKIT.getScreenSize().width;
            for (int i = myGridSpacing; i < height; i += myGridSpacing) {
                g2d.drawLine(0, i, width, i);
            }
            for (int i = myGridSpacing; i < width; i += myGridSpacing) {
                g2d.drawLine(i, 0, i, height);
            }
        }
        
        // Draws each shape in the myDrawnShapes list. 
        for (final DrawnShape shape : myDrawnShapes) {
            g2d.setColor(shape.getColor()); 
            g2d.setStroke(shape.getThickness()); 
            g2d.draw(shape.getTool().getDrawnShape().getShape()); 
        }
        
        // Draws the current shape
        if (!myThickness.equals(ZERO_STROKE) && !myCleared && !myCallForResize) {
            g2d.setColor(myColor);
            g2d.setStroke(myThickness);
            g2d.draw(myCurrentTool.getShape());
        } 
        
        myCleared = false;
        myCallForResize = true;
        
    }
    
    /**
     * Listens for MouseEvents for use by the DrawingPanel. 
     * 
     * @author Boyd Bouck
     * @version 8 Mar 2024
     */
    private final class MouseListener extends MouseInputAdapter {
        
        /** 
         * Keeps track of whether or not the mouse is dragged between press and release,
         * so that shapes will only be added to myDrawnShapes if mouse was dragged. 
         */
        private boolean myDragged;
        
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myCurrentTool.setStartPoint(theEvent.getPoint());
        }
        
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCurrentTool.setEndPoint(theEvent.getPoint());
            myDragged = true;
            myCallForResize = false;
            repaint(); 
        }
        
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            
            if (!myThickness.equals(ZERO_STROKE) && myDragged) {
                
                if (myDrawnShapes.isEmpty()) {
                    myPCS.firePropertyChange(ENABLE_CLEAR, false, true);
                }
            
                if (myCurrentTool instanceof LineTool) {
                    myDrawnShapes.add(new LineTool(myCurrentTool, myThickness, myColor).
                            getDrawnShape());
                } else if (myCurrentTool instanceof RectangleTool) {
                    myDrawnShapes.add(new RectangleTool(myCurrentTool, myThickness, myColor).
                            getDrawnShape());
                } else if (myCurrentTool instanceof RoundRectangleTool) {
                    myDrawnShapes.add(new RoundRectangleTool(myCurrentTool, myThickness, 
                            myColor).getDrawnShape());
                } else {
                    myDrawnShapes.add(new EllipseTool(myCurrentTool, myThickness, myColor).
                            getDrawnShape());
                }
            
            }
            
            myDragged = false;
        }
        
    }

}
