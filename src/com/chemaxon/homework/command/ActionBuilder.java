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

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.command.Command;

public class ActionBuilder {

    private Command command;
    private AppContext context;
    
    public ActionBuilder forCommand(Command c){
        command = c;
        return this;
    }
    
    public ActionBuilder withContext(AppContext c){
        context = c;
        return this;
    }
    
    public Action build(){
        if (command==null || context==null){
            throw new IllegalStateException("ActionBuilder is not configured correctly!");
        }
        
        Action a = new AbstractAction(command.getHumanReadableLabel()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.execute(context);
            }
        };
        
        return a;
    }
}
