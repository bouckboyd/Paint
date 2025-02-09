/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 * A Tool that can draw a rectangle. 
 * 
 * @author Boyd Bouck
 * @version 1 Mar 2024
 */
public final class RectangleTool extends AbstractDrawingTool {
    
    /**
     * Constructs a new RectangleTool with placeholder fields. 
     */
    public RectangleTool() {
        super();
    }
    
    /**
     * Constructs a new RectangleTool with a given DrawingTool, Stroke, and Color. 
     * 
     * @param theTool is the type of DrawingTool. 
     * @param theThickness is the Stroke of the shape drawn by this tool. 
     * @param theColor is the Color of the shape drawn by this tool. 
     */
    public RectangleTool(final DrawingTool theTool, final Stroke theThickness, 
            final Color theColor) {
        super(theTool, theThickness, theColor);
    }

    /**
     * Returns the Shape drawn by this RectangleTool. Includes the type of shape, 
     * start point, and end point, but not Stroke or Color. 
     */
    @Override
    public Shape getShape() {
        
        final Rectangle2D.Double rect; 
        
        final double startX = getStartPoint().getX();
        final double startY = getStartPoint().getY();
        final double endX = getEndPoint().getX();
        final double endY = getEndPoint().getY();
        
        
        if (startX <= endX) {
            if (startY <= endY) {
                rect = new Rectangle2D.Double(startX, startY, endX - startX, endY - startY);
            } else {
                rect = new Rectangle2D.Double(startX, endY, endX - startX, startY - endY);
            }
        } else {
            if (startY <= endY) {
                rect = new Rectangle2D.Double(endX, startY, startX - endX, endY - startY);
            } else {
                rect = new Rectangle2D.Double(endX, endY, startX - endX, startY - endY);
            } 
        }
        
        return rect;
    }
    
}
