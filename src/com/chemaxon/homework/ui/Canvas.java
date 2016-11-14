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

import static com.chemaxon.homework.ui.CanvasUtil.getComponentOrigo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.geom.Point3D;
import com.chemaxon.homework.model.Model;
import com.chemaxon.homework.tool.Tool;

public class Canvas extends JComponent implements AppContext {

    private final Model model;
    private Tool currentTool;

    public Canvas(final Model model) {
        this.model = model;
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        // super.paint(graphics);
        super.paintComponent(graphics);

        final Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        final Point origo = getComponentOrigo(this);
        g.translate(origo.getX(), origo.getY());

        drawSelectedItems(g, model.getPoints());
        drawNormalItems(g, model.getPoints());

        g.dispose();
    }

    @Override
    public void activateTool(final Tool t) {
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

    private void drawNormalItems(final Graphics2D graphics2d, final Point3D[] points) {
        setNormalDrawingStyle(graphics2d);

        for (int i = 0; i < points.length; i++) {
            graphics2d.draw(
                    new Line2D.Double(points[i].toPoint2D(), points[(i == points.length - 1) ? 0 : i + 1].toPoint2D()));
        }
    }

    private void drawSelectedItems(final Graphics2D graphics2d, final Point3D[] points) {
        setSelectionDrawingStyle(graphics2d);

        for (int i = 0; i < points.length; i++) {
            drawSelectedPoint(graphics2d, points[i]);
            drawSelectedEdge(graphics2d, points[i], points[(i == points.length - 1) ? 0 : i + 1]);
        }
    }

    private void drawSelectedEdge(final Graphics2D graphics2d, final Point3D pointFrom, final Point3D pointTo) {
        if (pointFrom.isSelected() && pointTo.isSelected()) {
            graphics2d.draw(new Line2D.Double(pointFrom.toPoint2D(), pointTo.toPoint2D()));
        }
    }

    private void drawSelectedPoint(final Graphics2D graphics2d, final Point3D point) {
        if (point.isSelected() || point.isDragged()) {
            graphics2d.fillOval(
                    (new Double(point.x)).intValue() - CanvasUtil.VERTEX_SELECTION_RADIUS,
                    (new Double(point.y)).intValue() - CanvasUtil.VERTEX_SELECTION_RADIUS,
                    CanvasUtil.VERTEX_SELECTION_RADIUS * 2,
                    CanvasUtil.VERTEX_SELECTION_RADIUS * 2);
        }
    }

    private void setSelectionDrawingStyle(final Graphics2D graphics2d) {
        graphics2d.setStroke(new BasicStroke(CanvasUtil.SELECTED_LINE_WIDTH));
        graphics2d.setColor(Color.YELLOW);
    }

    private void setNormalDrawingStyle(final Graphics2D graphics2d) {
        graphics2d.setStroke(new BasicStroke(CanvasUtil.BASIC_LINE_WIDTH));
        graphics2d.setColor(Color.BLACK);
    }

}
