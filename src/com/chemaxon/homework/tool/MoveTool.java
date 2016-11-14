package com.chemaxon.homework.tool;

import java.awt.event.MouseAdapter;

import javax.swing.JComponent;

import com.chemaxon.homework.AppContext;

public class MoveTool implements Tool {
    private AppContext context;
    private JComponent component;
    private MouseAdapter mouseAdapter;
    private boolean edgeMoving;

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
        mouseAdapter = new MoveToolMouseAdapter(context, component);
        component.addMouseListener(mouseAdapter);
        component.addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void removeNotify() {
        component.removeMouseListener(mouseAdapter);
        component.removeMouseMotionListener(mouseAdapter);
    }

}
