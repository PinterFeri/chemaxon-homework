/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.geom.Point3D;
import com.chemaxon.homework.model.Model;
import com.chemaxon.homework.tool.Tool;

public class Canvas extends JComponent implements AppContext {

    private Model model;
    private Tool currentTool;
    
    public Canvas(Model model) {
        this.model = model;
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
//        super.paint(graphics);
        super.paintComponent(graphics);
        
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.translate(getWidth()/2, getHeight()/2);
        
        Point3D[] points = model.getPoints();
        int i=0;
        while (i<points.length-1){
            g.draw(new Line2D.Double(points[i].toPoint2D(), points[++i].toPoint2D()));
        }
        g.draw(new Line2D.Double(points[i].toPoint2D(), points[0].toPoint2D()));
        
        g.dispose();
    }
    
    @Override
    public void activateTool(Tool t){
        if (currentTool != null) {
            currentTool.removeNotify();
        }
        currentTool = t;
        if (currentTool != null) {
            currentTool.addNotify();
        }
    }

    @Override
    public Model getModel() {
        return model;
    }
    
}
