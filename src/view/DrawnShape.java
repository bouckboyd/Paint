/*
 * TCSS 305 - Assignment 5
 */

package view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import model.DrawingTool;

/**
 * Represents a single shape that has been drawn on the DrawingPanel. DrawnShapes consist of
 * the tool used to draw them, a thickness, and a color. 
 * 
 * @author Boyd Bouck
 * @version 1 Mar 2024
 */
public final class DrawnShape {

    /**
     * The DrawingTool that draws this DrawnShape. 
     */
    private final DrawingTool myTool; 
    
    /**
     * The Stroke thickness of this DrawnShape. 
     */
    private Stroke myThickness;
    
    /**
     * The Color of this DrawnShape. 
     */
    private final Color myColor; 
    
    public DrawnShape(final DrawingTool theTool, final Stroke theThickness, 
            final Color theColor) {
        myTool = theTool;
        myThickness = theThickness;
        myColor = theColor;
    }
    
    /**
     * @return the Tool of this DrawnShape. 
     */
    public DrawingTool getTool() {
        return myTool;
    }
    
    /**
     * @return the Stroke thickness of this DrawnShape. 
     */
    public Stroke getThickness() {
        return myThickness;
    }
    
    /**
     * @return the Color of this DrawnShape. 
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * Returns the Shape represented by this DrawnShape. Returns a Line2D.Double, 
     * Rectangle2D.Double, or Ellipse2D.Double with the appropriate start and end points. 
     * 
     * @return the Shape represented by this DrawnShape. 
     */
    public Shape getShape() {
        return myTool.getShape();
    }
    
    /**
     * Sets the thickness of this DrawnShape. 
     * 
     * @param theThickness is the new Stroke that myThickness will be set to. 
     */
    public void setThickness(final Stroke theThickness) {
        myThickness = theThickness;
    }
    
}
