/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;

/**
 * A Tool that can draw a line. 
 * 
 * @author Boyd Bouck
 * @version 1 Mar 2024
 */
public final class LineTool extends AbstractDrawingTool {
    
    /**
     * Constructs a new LineTool with placeholder fields. 
     */
    public LineTool() {
        super();
    }
    
    
    /**
     * Constructs a new LineTool with a given DrawingTool, Stroke, and Color. 
     * 
     * @param theTool is the type of DrawingTool. 
     * @param theThickness is the Stroke of the shape drawn by this tool. 
     * @param theColor is the Color of the shape drawn by this tool. 
     */
    public LineTool(final DrawingTool theTool, final Stroke theThickness, 
            final Color theColor) {
        super(theTool, theThickness, theColor);
    }
    
    /**
     * Returns the Shape drawn by this LineTool. Includes the type of shape, 
     * start point, and end point, but not Stroke or Color. 
     */
    @Override
    public Shape getShape() {
        return new Line2D.Double(getStartPoint(), getEndPoint());
    }

}
