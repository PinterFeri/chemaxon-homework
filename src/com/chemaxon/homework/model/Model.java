/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework.model;

import com.chemaxon.homework.geom.Point3D;
import com.chemaxon.homework.geom.Transformation;

public class Model {

    private Point3D[] points;
    
    public Model() {
        points = new Point3D[]{
                new Point3D(20, 40 * (Math.sqrt(3)/2), 4),
                new Point3D(-20, 40 * (Math.sqrt(3)/2), 4),
                new Point3D(-40, 0, 0),
                new Point3D(-20, -40 * (Math.sqrt(3)/2), -4),
                new Point3D(20, -40 * (Math.sqrt(3)/2), -4),
                new Point3D(40, 0, 0),
        };
    }
    
    public void transform(Transformation t){
        for (Point3D p : points){
            t.transform(p);
        }
    }

    public Point3D[] getPoints(){
        return points;
    }
}
