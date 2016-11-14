/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework.tool;

import javax.swing.JComponent;

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.tool.Tool;

public class ToolBuilder {

    private JComponent component;
    private AppContext context;
    
    public ToolBuilder withContext(AppContext context){
        this.context = context;
        return this;
    }
    
    public ToolBuilder forComponent(JComponent component){
        this.component = component;
        return this;
    }
    
    public Tool build(Class<? extends Tool> clazz){
        if (component==null || context==null){
            throw new IllegalStateException("ToolBuilder is not configured correctly!");
        }

        try {
            Tool t = clazz.newInstance();
            t.setContext(context);
            t.register(component);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
