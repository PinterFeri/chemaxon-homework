/*
 * Copyright (c) 1998-2015 ChemAxon Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * ChemAxon. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with ChemAxon.
 *
 */
package com.chemaxon.homework.geom;

import java.awt.geom.Point2D;

/**
 * Point in three dimensional space.
 *
 * @author  Peter Csizmadia
 */
public class Point3D {

    /**
     * The x coordinate.
     */
    public transient double x;

    /**
     * The y coordinate.
     */
    public transient double y;

    /**
     * The z coordinate.
     */
    public transient double z;

    /**
     * Construct a zero point.
     */
    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Copy constructor.
     * @param p   the point to copy
     */
    public Point3D(final Point3D p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    /**
     * Construct a point from the specified coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Set coordinates.
     * @param p   point object containing the new coordinates
     * @since Marvin 3.5, 11/04/2004
     */
    public void set(final Point3D p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    /**
     * Two points equal if their coordinates equal.
     * @param o   the other point
     * @return true if the coordinates equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if(o instanceof Point3D) {
            Point3D p = (Point3D) o;
            return x == p.x && y == p.y && z == p.z;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (x + y +z);
    }

    /**
     * Calculates the distance between two points.
     * @param p    the other point
     * @return the distance
     */
    public final double distance(final Point3D p) {
        double dx = x - p.x;
        double dy = y - p.y;
        double dz = z - p.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    /**
     * Calculates the square of the length of the vector defining the point.
     * @since Marvin 6.2
     * @return the square of the length
     */
    public double lengthSquare() {
        return x * x + y * y + z * z;
    }

    /**
     * Calculates the distance between two points in the XY plane.
     * @param p    the other point
     * @return the distance
     * @since Marvin 4.1, 04/28/2006
     */
    public final double distance2D(final Point3D p) {
        double dx = x - p.x;
        double dy = y - p.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Calculates the absolute angle in the XY plane of the vector pointing
     * from this point to the other point.
     * In other words, takes the difference between this vector
     * and the vector pointing from the origin to the other point and
     * returns the angle of the resultant vector's projection onto the XY plane.
     * @param x2    the other point's x coordinate
     * @param y2    the other point's y coordinate
     * @return the angle (from -pi to pi)
     * @since   Marvin 3.0
     */
    public final double angle2D(final double x2, final double y2) {
        double dx = x2 - x;
        double dy = y2 - y;
        return dx < -1e-7 || dx > 1e-7 || dy < -1e-7 || dy > 1e-7 ?
                Math.atan2(dy, dx) : 0;
    }

    /**
     * Gets a string representation of the point
     * @return the string representation
     */
    @Override
    public String toString() {
        return "DPoint3("+x+", "+y+", "+z+")";
    }

    /**
     * Subtract the given v2 vector from this one.
     * @since Marvin 6.2
     * @param v2
     */
    public void subtract(final Point3D v2) {
        x -= v2.x;
        y -= v2.y;
        z -= v2.z;
    }

    /**
     * Calculate the angle of two vectors (this and p) in the range 0.0 through pi.
     * @since Marvin 6.2
     * @param p
     * @return angle of two vectors (this and p) in the range 0.0 through pi
     */
    public double angle3D(final Point3D p){
        double length = Math.sqrt(x*x+y*y+z*z);
        length = (length < 1e-10) ? 1e-10 : length;
        double p_length = Math.sqrt(p.x*p.x+p.y*p.y+p.z*p.z);
        p_length = (p_length < 1e-10) ? 1e-10 : p_length;
        double dotproduct = x * p.x + y * p.y+ z * p.z;
        return Math.acos(dotproduct/length/p_length);
    }

    /**
     * Calculates the scalar product of the two vectors
     * @since Marvin 6.2
     * @param v1
     * @param v2
     * @return the scalar product
     */
    public static double scalar(final Point3D v1, final Point3D v2) {
        return v1.x * v2.x +  v1.y * v2.y +  v1.z * v2.z;
    }

    /**
     * Calculates the cross product of the two vectors
     * @since Marvin 6.2
     * @param v1
     * @param v2
     * @return the cross product
     */
    public static Point3D cross(final Point3D v1, final Point3D v2) {
        return new Point3D(v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

    /**
     * Calculates the sum of the two vectors
     * @since Marvin 6.2
     * @param v1
     * @param v2
     * @return the sum
     */
    public static Point3D add(final Point3D v1, final Point3D v2) {
        return new Point3D(v1.x+v2.x,
                v1.y+v2.y, v1.z+v2.z);
    }

    /**
     * Calculates the difference of the two vectors
     * @since Marvin 6.2
     * @param v1
     * @param v2
     * @return v1 - v2
     */
    public static Point3D subtract(final Point3D v1, final Point3D v2) {
        return new Point3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    /**
     * Checks whether three 3D points can be connected with a line.
     * @since Marvin 6.2
     * @param a
     * @param b
     * @param c
     * @return true if points are on the same line
     */
    public static boolean isCollinear(final Point3D a, final Point3D b, final Point3D c) {
        // Three points are collinear iff |(a-b)x(a-c)| = 0
        Point3D d1 = subtract(a, c);
        Point3D d2 = subtract(a, b);
        Point3D cross = cross(d1, d2);

        // Check whether cross product is 0:
        double epsilon = 0.0076; // sin(5 fok)^2
        return cross.lengthSquare() < d1.lengthSquare() * d2.lengthSquare() * epsilon;
    }

    /**
     * Add the given point to this one.
     * @param v1
     */
    public void add(final Point3D v1) {
        x += v1.x;
        y += v1.y;
        z += v1.z;
    }

    /**
     * Scales the vector with the given factor.
     * @param scaleFactor the scale factor
     */
    public void scale(double scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
        z *= scaleFactor;
    }
    
    public Point2D toPoint2D(){
        return new Point2D.Double(x, y);
    }
    
    /**
     * Rounds the represented value to the given decimal precision.
     * @param decimals how many deciamls should be left
     */
    public void roundTo(int decimals) {
        if(decimals <= 0) {
            throw new IllegalArgumentException("We can only round to poitive decimals");
        }
        double multiplyer = Math.pow(10, decimals);
        x=Math.round(x*multiplyer)/multiplyer;
        y=Math.round(y*multiplyer)/multiplyer;
        z=Math.round(z*multiplyer)/multiplyer;
    }
}

