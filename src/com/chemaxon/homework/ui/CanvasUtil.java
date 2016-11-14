package com.chemaxon.homework.ui;

import java.awt.Point;

import javax.swing.JComponent;

public class CanvasUtil {
    public static int VERTEX_SELECTION_RADIUS = 4;
    public static int SELECTED_LINE_WIDTH = 4;
    public static int BASIC_LINE_WIDTH = 1;
    public static int VERTEX_SELECTION_SENSITIVITY_PIXELS = 5;
    public static int EDGE_MOVING_SENSITIVITY_PIXELS = 3;

    public static Point getComponentOrigo(final JComponent component) {
        return new Point(component.getWidth() / 2, component.getHeight() / 2);
    }

    public static Point getTransformedCoordinate(final JComponent component, final int x, final int y) {
        final Point origo = getComponentOrigo(component);
        return new Point(x - origo.x, y - origo.y);
    }

}
