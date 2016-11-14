/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework;

import com.chemaxon.homework.model.Model;
import com.chemaxon.homework.tool.Tool;

public interface AppContext {

    public Model getModel();
    
    public void activateTool(Tool t);
}
