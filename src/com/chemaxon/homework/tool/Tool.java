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

import com.chemaxon.homework.AppContext;
import com.chemaxon.homework.Registerable;

public interface Tool extends Registerable{

    public void setContext(AppContext context);
    
    public void addNotify();
    
    public void removeNotify();
}
