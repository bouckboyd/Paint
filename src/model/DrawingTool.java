/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.Point;
import java.awt.Shape;
import view.DrawnShape;

/**
 * Interface for an Object that can be used to draw a shape. 
 * 
 * @author Boyd Bouck
 * @version 1 Mar 2024
 */
public interface DrawingTool {

    /**
     * Returns the Shape drawn by this DrawingTool. Includes the type of shape, 
     * start point, and end point, but not Stroke or Color. 
     */
    Shape getShape(); 
    
    /**
     * @return the DrawnShape drawn by this tool. 
     */
    DrawnShape getDrawnShape();
    
    /**
     * Sets the start point for this tool to the given point. 
     * 
     * @param thePoint is the new start point of this tool. 
     */
    void setStartPoint(Point thePoint);
    
    /**
     * Sets the end point for this tool to the given point. 
     * 
     * @param thePoint is the new end point of this tool. 
     */
    void setEndPoint(Point thePoint);
    
    /**
     * @return the start point of this tool. 
     */
    Point getStartPoint();
    
    /**
     * @return the end point of this tool. 
     */
    Point getEndPoint();
    
}
