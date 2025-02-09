/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;
import view.DrawnShape;

/**
 * Contains default behavior for all DrawingTools. 
 * 
 * @author Boyd Bouck
 * @version 1 Mar 2024
 */
public abstract class AbstractDrawingTool implements DrawingTool {
    
    /**
     * A placeholder Point, off of the DrawingPanel. 
     */
    private static final Point NO_POINT = new Point(-50, -50);
    
    /**
     * Placeholder Stroke for construction, will be replaced. 
     */
    private static final Stroke NO_STROKE = new BasicStroke(1);
    
    /**
     * Placeholder Color for construction, will be replaced. 
     */
    private static final Color NO_COLOR = Color.BLACK;

    /**
     * The start point of the shape this tool draws. 
     */
    private Point myStartPoint;
    
    /**
     * The end point of the shape this tool draws. 
     */
    private Point myEndPoint;
    
    /**
     * The DrawnShape that is drawn by this tool. 
     */
    private DrawnShape myDrawnShape; 
    
    /**
     * Constructs a new AbstractDrawingTool with placeholder values for the fields. 
     */
    protected AbstractDrawingTool() {
        myStartPoint = NO_POINT;
        myEndPoint = NO_POINT;
        myDrawnShape = new DrawnShape(this, NO_STROKE, NO_COLOR); 
    }
    
    /**
     * Constructs a new AbstractDrawingTool given the type of tool, thickness, and color. 
     * 
     * @param theTool is this tools concrete DrawingTool class. 
     * @param theThickness is the thickness of the shape drawn by this DrawingTool. 
     * @param theColor is the Color of the shape drawn by this DrawingTool. 
     */
    protected AbstractDrawingTool(final DrawingTool theTool, final Stroke theThickness, 
            final Color theColor) {
        myStartPoint = theTool.getStartPoint();
        myEndPoint = theTool.getEndPoint();
        myDrawnShape = new DrawnShape(this, theThickness, theColor);
    }
    
    /**
     * Sets the start point for this tool to the given point. 
     * 
     * @param thePoint is the new start point of this tool. 
     */
    @Override
    public void setStartPoint(final Point thePoint) {      
        myStartPoint = (Point) thePoint.clone();
        myEndPoint = (Point) thePoint.clone();
    }

    /**
     * @return the start point of this tool. 
     */
    @Override
    public Point getStartPoint() {
        return myStartPoint;
    }
    
    /**
    * Sets the end point for this tool to the given point. 
    * 
    * @param thePoint is the new end point of this tool. 
    */
    @Override
    public void setEndPoint(final Point thePoint) {      
        myEndPoint = (Point) thePoint.clone();
    }

    /**
     * @return the end point of this tool. 
     */
    @Override
    public Point getEndPoint() {
        return myEndPoint;
    }
    
    /**
     * @return the DrawnShape drawn by this tool. 
     */
    @Override
    public DrawnShape getDrawnShape() {
        return myDrawnShape;
    }
    
}
