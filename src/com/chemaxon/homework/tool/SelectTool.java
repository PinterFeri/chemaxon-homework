package com.chemaxon.homework.tool;

import static com.chemaxon.homework.ui.CanvasUtil.getTransformedCoordinate;
import static java.awt.geom.Point2D.distance;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.geom.Point3D;
import com.chemaxon.homework.ui.CanvasUtil;

public class SelectTool implements Tool {
    private AppContext context;
    private JComponent component;
    private MouseAdapter mouseAdapter;

    @Override
    public void register(final JComponent component) {
        this.component = component;
    }

    @Override
    public void unregister(final JComponent component) {
        this.component = null;

    }

    @Override
    public void setContext(final AppContext context) {
        this.context = context;

    }

    @Override
    public void addNotify() {
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final Point relativeCoordinate = getTransformedCoordinate(component, e.getX(), e.getY());

                final Point3D[] points = context.getModel().getPoints();
                for (int i = 0; i < points.length; i++) {
                    if (distance(
                            points[i].x,
                            points[i].y,
                            relativeCoordinate.getX(),
                            relativeCoordinate.getY()) <= CanvasUtil.VERTEX_SELECTION_SENSITIVITY_PIXELS) {
                        points[i].setSelected(!points[i].isSelected());
                    }
                }
                component.repaint();
            }
        };
        component.addMouseListener(mouseAdapter);

    }

    @Override
    public void removeNotify() {
        component.removeMouseListener(mouseAdapter);

    }

}
