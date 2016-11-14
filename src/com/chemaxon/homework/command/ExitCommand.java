/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework.command;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.chemaxon.homework.AppContext;

public class ExitCommand implements Command {

    private static final String INPUT_MAP_NAME = "close";
    private static final KeyStroke ACTIVATION_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK);
    
    private JComponent component;
    
    @Override
    public void register(JComponent component) {
        this.component = component;
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ACTIVATION_KEY_STROKE, INPUT_MAP_NAME);
        component.getActionMap().put(INPUT_MAP_NAME, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitCommand.this.execute(null);
            }
        });
    }

    @Override
    public void unregister(JComponent component) {
        if (component != this.component){
            throw new IllegalArgumentException("ExitCommand is not registered to the given component: "+component);
        }
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(ACTIVATION_KEY_STROKE);
        component.getActionMap().remove(INPUT_MAP_NAME);
    }

    @Override
    public void execute(AppContext context) {
        SwingUtilities.getWindowAncestor(component).dispose();
    }

    @Override
    public String getHumanReadableLabel() {
        return "Exit";
    }

}
