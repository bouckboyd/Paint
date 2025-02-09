/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

/**
 * A Tool that can draw a rectangle with rounded corners. 
 * 
 * @author Boyd Bouck
 * @version 8 Mar 2024
 */
public final class RoundRectangleTool extends AbstractDrawingTool {
    
    /**
     * The arc width and arc height used to round off the corners in a RoundRectangleTool. 
     */
    private static final double ARC = 50.0;
    
    /**
     * Constructs a new RoundRectangleTool with placeholder fields. 
     */
    public RoundRectangleTool() {
        super();
    }
    
    /**
     * Constructs a new RoundRectangleTool with a given DrawingTool, Stroke, and Color. 
     * 
     * @param theTool is the type of DrawingTool. 
     * @param theThickness is the Stroke of the shape drawn by this tool. 
     * @param theColor is the Color of the shape drawn by this tool. 
     */
    public RoundRectangleTool(final DrawingTool theTool, final Stroke theThickness, 
            final Color theColor) {
        super(theTool, theThickness, theColor);
    }

    /**
     * Returns the Shape drawn by this RoundRectangleTool. Includes the type of shape, 
     * start point, and end point, but not Stroke or Color. 
     */
    @Override
    public Shape getShape() {
        
        final RoundRectangle2D.Double rect; 
        
        final double startX = getStartPoint().getX();
        final double startY = getStartPoint().getY();
        final double endX = getEndPoint().getX();
        final double endY = getEndPoint().getY();
        
        
        if (startX <= endX) {
            if (startY <= endY) {
                rect = new RoundRectangle2D.Double(startX, startY, endX - startX, 
                        endY - startY, ARC, ARC);
            } else {
                rect = new RoundRectangle2D.Double(startX, endY, endX - startX, 
                        startY - endY, ARC, ARC);
            }
        } else {
            if (startY <= endY) {
                rect = new RoundRectangle2D.Double(endX, startY, startX - endX, 
                        endY - startY, ARC, ARC);
            } else {
                rect = new RoundRectangle2D.Double(endX, endY, startX - endX, 
                        startY - endY, ARC, ARC);
            } 
        }
        
        return rect;
    }
    
}
