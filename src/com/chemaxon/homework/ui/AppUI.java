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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import com.chemaxon.homework.command.ActionBuilder;
import com.chemaxon.homework.command.ExitCommand;
import com.chemaxon.homework.model.Model;

public class AppUI {

    public void createAndShowUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Canvas canvas = createCanvas();
        frame.add(canvas);
        frame.setJMenuBar(buildMenu(canvas));
        frame.setMinimumSize(new Dimension(400,400));
        frame.pack();
        frame.setVisible(true);
    }

    private JMenuBar buildMenu(Canvas canvas) {
        JMenuBar menubar = new JMenuBar();
        menubar.add(buildMainMenu(canvas));
        return menubar;
    }

    private JMenu buildMainMenu(Canvas canvas) {
        ExitCommand exitCommand = new ExitCommand();
        exitCommand.register(canvas);
        Action a = new ActionBuilder().forCommand(exitCommand).withContext(canvas).build();
        
        JMenu mainMenu = new JMenu("File");
        mainMenu.add(new JMenuItem(a));
        return mainMenu;
    }

    private Canvas createCanvas() {
        Canvas c = new Canvas(new Model());
        c.setBackground(Color.WHITE);
        return c;
    }

}
