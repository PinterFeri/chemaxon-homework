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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import com.chemaxon.homework.command.ActionBuilder;
import com.chemaxon.homework.command.Command;
import com.chemaxon.homework.command.ExitCommand;
import com.chemaxon.homework.command.MoveCommand;
import com.chemaxon.homework.command.SelectCommand;
import com.chemaxon.homework.model.Model;

public class AppUI {
	static class mouse extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
		}
	}
	
    public void createAndShowUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Canvas canvas = createCanvas();
        frame.add(canvas);
        frame.setJMenuBar(new Menu(canvas));
        frame.setMinimumSize(new Dimension(400,400));
        frame.pack();
        frame.setVisible(true);
    }

    private Canvas createCanvas() {
        Canvas c = new Canvas(new Model());
        c.setBackground(Color.WHITE);
        return c;
    }

}
