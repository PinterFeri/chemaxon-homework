package com.chemaxon.homework.tool;

import static com.chemaxon.homework.ui.CanvasUtil.getTransformedCoordinate;
import static java.awt.geom.Point2D.distance;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.geom.Point3D;
import com.chemaxon.homework.ui.CanvasUtil;

public class MoveToolMouseAdapter extends MouseAdapter {
    private final AppContext context;
    private final JComponent component;
    private Point previousMouseCoordinate;

    public MoveToolMouseAdapter(final AppContext context, final JComponent component) {
        this.context = context;
        this.component = component;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            final Point mouseCoordinate = getTransformedCoordinate(component, e.getX(), e.getY());
            previousMouseCoordinate = new Point(mouseCoordinate.x, mouseCoordinate.y);

            final Point3D[] points = context.getModel().getPoints();
            if (!setDraggedIfClickedOnVertex(points, mouseCoordinate)) {
                setDraggedIfClickedOnEdge(points, mouseCoordinate);
            }
            component.repaint();
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            releaseAllDraggedPoints(context.getModel().getPoints());
            component.repaint();
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            final Point mouseCoordinate = getTransformedCoordinate(component, e.getX(), e.getY());
            final Point3D[] points = context.getModel().getPoints();

            final Point relativeCoordinate = new Point(
                    previousMouseCoordinate.x - mouseCoordinate.x,
                    previousMouseCoordinate.y - mouseCoordinate.y);
            previousMouseCoordinate = new Point(mouseCoordinate.x, mouseCoordinate.y);

            updatePointsCoordinates(points, relativeCoordinate);

            component.repaint();
        }
    }

    private void releaseAllDraggedPoints(final Point3D[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i].isDragged()) {
                points[i].setDragged(false);
            }
        }
    }

    private void updatePointsCoordinates(final Point3D[] points, final Point relativeCoordinate) {
        for (int i = 0; i < points.length; i++) {
            if (points[i].isDragged()) {
                points[i].x -= relativeCoordinate.getX();
                points[i].y -= relativeCoordinate.getY();
            }
        }
    }

    private boolean setDraggedIfClickedOnVertex(final Point3D[] points, final Point relativeCoordinate) {
        boolean result = false;
        int i = 0;
        while (!result && i < points.length) {
            if (distance(
                    points[i].x,
                    points[i].y,
                    relativeCoordinate.getX(),
                    relativeCoordinate.getY()) <= CanvasUtil.EDGE_MOVING_SENSITIVITY_PIXELS) {
                points[i].setDragged(true);
                result = true;
            }
            i++;
        }

        return result;
    }

    private boolean setDraggedIfClickedOnEdge(final Point3D[] points, final Point2D relativeCoordinate) {
        boolean result = false;
        int nextPointIndex = 0;
        int i = 0;
        while (!result && i < points.length) {
            nextPointIndex = (i == points.length - 1) ? 0 : i + 1;
            if (Line2D.ptSegDist(
                    points[i].x,
                    points[i].y,
                    points[nextPointIndex].x,
                    points[nextPointIndex].y,
                    relativeCoordinate.getX(),
                    relativeCoordinate.getY()) <= CanvasUtil.EDGE_MOVING_SENSITIVITY_PIXELS) {
                points[i].setDragged(true);
                points[nextPointIndex].setDragged(true);
                result = true;
            }
            i++;
        }

        return result;
    }

}
