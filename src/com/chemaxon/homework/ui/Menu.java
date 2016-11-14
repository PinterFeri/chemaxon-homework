package com.chemaxon.homework.ui;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.chemaxon.homework.command.ActionBuilder;
import com.chemaxon.homework.command.Command;
import com.chemaxon.homework.command.ExitCommand;
import com.chemaxon.homework.command.MoveCommand;
import com.chemaxon.homework.command.SelectCommand;

public class Menu extends JMenuBar {

	public Menu(Canvas canvas) {
        this.add(buildMainMenu(canvas));
        this.add(buildEditMenu(canvas));
	}

    private JMenu buildMainMenu(Canvas canvas) {
        JMenu mainMenu = new JMenu("File");
        addMenuitem(canvas, mainMenu, new ExitCommand());        
        
        return mainMenu;
    }
    
    private JMenu buildEditMenu(Canvas canvas) {
        JMenu mainMenu = new JMenu("Edit");
        addMenuitem(canvas, mainMenu, new SelectCommand());
        addMenuitem(canvas, mainMenu, new MoveCommand());
        
        return mainMenu;
    }
    
    private void addMenuitem(final Canvas canvas, final JMenu mainMenu, final Command command) {
    	command.register(canvas);
        Action a = new ActionBuilder().forCommand(command).withContext(canvas).build();
        mainMenu.add(new JMenuItem(a));
    }
	
}
