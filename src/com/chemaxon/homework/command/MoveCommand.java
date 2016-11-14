package com.chemaxon.homework.command;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.tool.MoveTool;
import com.chemaxon.homework.tool.SelectTool;
import com.chemaxon.homework.tool.Tool;
import com.chemaxon.homework.tool.ToolBuilder;
import com.chemaxon.homework.ui.Canvas;

public class MoveCommand implements Command {

    private static final String INPUT_MAP_NAME = "move";
    private static final KeyStroke ACTIVATION_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.ALT_DOWN_MASK);
    
    private JComponent component;
    
    @Override
    public void register(JComponent component) {
        this.component = component;
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ACTIVATION_KEY_STROKE, INPUT_MAP_NAME);
        component.getActionMap().put(INPUT_MAP_NAME, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	MoveCommand.this.execute(null);
            }
        });
    }

    @Override
    public void unregister(JComponent component) {
        if (component != this.component){
            throw new IllegalArgumentException("MoveCommand is not registered to the given component: "+component);
        }
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(ACTIVATION_KEY_STROKE);
        component.getActionMap().remove(INPUT_MAP_NAME);
    }

    @Override
    public void execute(AppContext context) {
    	if (context != null) {
    		Tool tool = new ToolBuilder().withContext(context).forComponent(component).build(MoveTool.class);
    		context.activateTool(tool);
    	}
    }

    @Override
    public String getHumanReadableLabel() {
        return "Move";
    }

}
