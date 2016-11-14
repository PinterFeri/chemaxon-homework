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

/**
 * 3D transformation matrix.
 *
 * @author  Peter Csizmadia
 */
public class Transformation {

    /** The m00 element of the matrix. */
    public transient double m00;

    /** The m01 element of the matrix. */
    public transient double m01;

    /** The m02 element of the matrix. */
    public transient double m02;

    /** The m03 element of the matrix. */
    public transient double m03;

    /** The m10 element of the matrix. */
    public transient double m10;

    /** The m11 element of the matrix. */
    public transient double m11;

    /** The m12 element of the matrix. */
    public transient double m12;

    /** The m13 element of the matrix. */
    public transient double m13;

    /** The m20 element of the matrix. */
    public transient double m20;

    /** The m21 element of the matrix. */
    public transient double m21;

    /** The m22 element of the matrix. */
    public transient double m22;

    /** The m23 element of the matrix. */
    public transient double m23;

    /** The m30 element of the matrix. */
    public transient double m30;

    /** The m31 element of the matrix. */
    public transient double m31;

    /** The m32 element of the matrix. */
    public transient double m32;

    /** The m33 element of the matrix. */
    public transient double m33;

    /**
     * Copy constructor.
     * @param t   the transformation to copy
     */
    public Transformation(Transformation t) {
        set(t);
    }

    /** Constructs an identity transformation. */
    public Transformation() {
        setIdentity();
    }

    /**
     * Copy.
     * @param t   target transformation
     */
    public void set(Transformation t) {
        m00 = t.m00; m01 = t.m01; m02 = t.m02; m03 = t.m03;
        m10 = t.m10; m11 = t.m11; m12 = t.m12; m13 = t.m13;
        m20 = t.m20; m21 = t.m21; m22 = t.m22; m23 = t.m23;
        m30 = t.m30; m31 = t.m31; m32 = t.m32; m33 = t.m33;
    }

    /**
     * Sets all components to zero.
     * @since Marvin 4.1, 01/23/2006
     */
    public final void setZero() {
        m00 = 0; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = 0; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = 0; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 0;
    }

    /**
     * Makes identity transformation.
     */
    public final void setIdentity() {
        m00 = 1; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = 1; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = 1; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;
    }

    /**
     * Gets the Euler angles from the rotation transformation.
     * @return array consisting of the 3 rotation angles about X, Y, Z
     * @since Marvin 4.1, 01/30/2006
     */
    public final double[] getEuler() {
        double theta = Math.asin(-m20);
        double cosTheta = Math.cos(theta);
        double phi = m10*m10 + m20*m20 > 1e-30? Math.atan2(m10, m00) : 0;
        double psi;
        if(m21*m21 + m22*m22 > 1e-30) {
            psi = cosTheta > 0? Math.atan2(m21, m22)
                              : Math.atan2(-m21, -m22);
        } else {
            psi = 0;
        }
        return new double[] {psi, theta, phi};
    }

    /**
     * Sets the rotational component using the Euler angles provided.
     * The other non-rotational elements are set as if this were an identity
     * matrix. The euler parameters are three rotation angles applied first
     * about the X, then Y, then Z axis.
     * @param psi    rotation angle about X axis
     * @param theta  rotation angle about Y axis
     * @param phi    rotation angle about Z axis
     * @since Marvin 4.1, 01/30/2006
     */
    public final void setEuler(double psi, double theta, double phi) {
        double sinPsi = Math.sin(psi);
        double sinTheta = Math.sin(theta);
        double sinPhi = Math.sin(phi);
        double cosPsi = Math.cos(psi);
        double cosTheta = Math.cos(theta);
        double cosPhi = Math.cos(phi);
        m00 = cosTheta * cosPhi;
        m01 = -(cosPsi * sinPhi) + (sinPsi * sinTheta * cosPhi);
        m02 = (sinPsi * sinPhi) + (cosPsi * sinTheta * cosPhi);
        m10 = cosTheta * sinPhi;
        m11 = (cosPsi * cosPhi) + (sinPsi * sinTheta * sinPhi);
        m12 = -(sinPsi * cosPhi) + (cosPsi * sinTheta * sinPhi);
        m20 = -sinTheta;
        m21 = sinPsi * cosTheta;
        m22 = cosPsi * cosTheta;
        m33 = 1;
        m03 = m13 = m23 = m30 = m31 = m32 = 0;
    }

    /**
     * Gets the scale factor.
     * The scale equals to (-1)^sgn(det(T))*|det(T)|^(1/3)
     * @return the scale factor
     */
    public final double getScale() {
        double c = determinant();
        return (c < 0)? -Math.pow(-c, 1/3.0) : Math.pow(c, 1/3.0);
    }

    /**
     * Sets the scale factor.
     * @param scale   the scale
     */
    public final void setScale(double scale) {
        double c = scale / getScale();
        m00 *= c;
        m01 *= c;
        m02 *= c;
        m10 *= c;
        m11 *= c;
        m12 *= c;
        m20 *= c;
        m21 *= c;
        m22 *= c;
    }

    /**
     * Computes the determinant of the matrix.
     * @return the determinant
     * @since Marvin 4.1, 01/12/2006
     */
    public final double determinant() {
        return m00*(m11*m22 - m12*m21)
             + m01*(m12*m20 - m10*m22)
             + m02*(m10*m21 - m11*m20);
    }

    /**
     * Computes the determinant of the 2D matrix.
     * @return the determinant of the 2D matrix
     * @since Marvin 4.1, 01/12/2006
     */
    public final double determinant2D() {
        return m00*m11 - m01*m10;
    }

    /**
     * Sets the rotation components.
     * @param nx   normal vector x
     * @param ny   normal vector y
     * @param nz   normal vector z
     * @param phi  angle
     */
    public final void setRotation(double nx, double ny, double nz, double phi) {
        double r = Math.sqrt(nx*nx + ny*ny + nz*nz);
        if(r == 0){
            nx = 0;
            ny = 0;
            nz = 0;
        } else {
            nx /= r;
            ny /= r;
            nz /= r;
        }
        double c = Math.cos(phi);
        double s = Math.sin(phi);
        double a = 1 - c;
        m00 = a*nx*nx + c;
        m01 = a*nx*ny - s*nz;
        m02 = a*nx*nz + s*ny;
        m10 = a*nx*ny + s*nz;
        m11 = a*ny*ny + c;
        m12 = a*ny*nz - s*nx;
        m20 = a*nx*nz - s*ny;
        m21 = a*ny*nz + s*nx;
        m22 = a*nz*nz + c;
    }

    /**
     * Sets the rotation center. This method can be called <i>after</i>
     * {@link #setRotation(double, double, double, double) setRotation}
     * (order is important!) if a point other than the origin is meant to be
     * the rotation center. It sets the translation vector in the fourth
     * column ({@link #m03}, {@link #m13} and {@link #m23})
     * in such a way that point <code>o</code> will be the center of rotation
     * specified by the upper left 3x3 components.
     * @param o     the center
     */
    public final void setRotationCenter(Point3D o) {
        m03 = o.x - m00*o.x - m01*o.y - m02*o.z;
        m13 = o.y - m10*o.x - m11*o.y - m12*o.z;
        m23 = o.z - m20*o.x - m21*o.y - m22*o.z;
    }

    /**
     * Sets the translation components
     * ({@link #m03}, {@link #m13} and {@link #m23}).
     * @param p     the translation
     */
    public final void setTranslation(Point3D p) {
        m03 = p.x;
        m13 = p.y;
        m23 = p.z;
    }

    /**
     * Sets the translation components
     * ({@link #m03}, {@link #m13} and {@link #m23}).
     * @param x0    x translation
     * @param y0    y translation
     * @param z0    z translation
     */
    public final void setTranslation(double x0, double y0, double z0) {
        m03 = x0;
        m13 = y0;
        m23 = z0;
    }

    /**
     * Transforms a point.
     * @param p   the point
     */
    public final void transform(Point3D p) {
        double x = p.x;
        double y = p.y;
        double z = p.z;
        p.x = m00*x + m01*y + m02*z + m03;
        p.y = m10*x + m11*y + m12*z + m13;
        p.z = m20*x + m21*y + m22*z + m23;
    }

    /**
     * Multiplies by another matrix from right.
     * @param b   the other matrix
     */
    public final void mul(Transformation b) {
        double x00 = m00*b.m00 + m01*b.m10 + m02*b.m20 + m03*b.m30;
        double x01 = m00*b.m01 + m01*b.m11 + m02*b.m21 + m03*b.m31;
        double x02 = m00*b.m02 + m01*b.m12 + m02*b.m22 + m03*b.m32;
        double x03 = m00*b.m03 + m01*b.m13 + m02*b.m23 + m03*b.m33;
        double x10 = m10*b.m00 + m11*b.m10 + m12*b.m20 + m13*b.m30;
        double x11 = m10*b.m01 + m11*b.m11 + m12*b.m21 + m13*b.m31;
        double x12 = m10*b.m02 + m11*b.m12 + m12*b.m22 + m13*b.m32;
        double x13 = m10*b.m03 + m11*b.m13 + m12*b.m23 + m13*b.m33;
        double x20 = m20*b.m00 + m21*b.m10 + m22*b.m20 + m23*b.m30;
        double x21 = m20*b.m01 + m21*b.m11 + m22*b.m21 + m23*b.m31;
        double x22 = m20*b.m02 + m21*b.m12 + m22*b.m22 + m23*b.m32;
        double x23 = m20*b.m03 + m21*b.m13 + m22*b.m23 + m23*b.m33;
        double x30 = m30*b.m00 + m31*b.m10 + m32*b.m20 + m33*b.m30;
        double x31 = m30*b.m01 + m31*b.m11 + m32*b.m21 + m33*b.m31;
        double x32 = m30*b.m02 + m31*b.m12 + m32*b.m22 + m33*b.m32;
        double x33 = m30*b.m03 + m31*b.m13 + m32*b.m23 + m33*b.m33;
        m00 = x00;
        m01 = x01;
        m02 = x02;
        m03 = x03;
        m10 = x10;
        m11 = x11;
        m12 = x12;
        m13 = x13;
        m20 = x20;
        m21 = x21;
        m22 = x22;
        m23 = x23;
        m30 = x30;
        m31 = x31;
        m32 = x32;
        m33 = x33;
    }
    
    /**
     * Multiplies by another matrix from left.
     * @param b   the other matrix
     */
    public final void leftMul(Transformation b) {
        Transformation clonedB = new Transformation(b);
        clonedB.mul(this);
        set(clonedB);
    }

    /**
     * Tests whether the transformation is 3D.
     * @return true if the transformation is 3D, false otherwise
     * @since Marvin 4.0.2, 11/04/2005
     */
    public boolean is3d() {
        return m20 != 0 || m21 != 0 || m23 != 0;
    }

    /**
     * Inverts the matrix.
     */
    public final void invert() {
        double b00 = 1, b01 = 0, b02 = 0, b03 = 0;
        double b10 = 0, b11 = 1, b12 = 0, b13 = 0;
        double b20 = 0, b21 = 0, b22 = 1, b23 = 0;
        double b30 = 0, b31 = 0, b32 = 0, b33 = 1;
        double x00 = m00;
        double x01 = m01;
        double x02 = m02;
        double x03 = m03;
        double q = m10/x00;
        b10 -= q*b00;
        double x11 = m11 - q*m01; b11 -= q*b01;
        double x12 = m12 - q*m02; b12 -= q*b02;
        double x13 = m13 - q*m03; b13 -= q*b03;
        q = m20/x00;
        b20 -= q*b00;
        double x21 = m21 - q*m01; b21 -= q*b01;
        double x22 = m22 - q*m02; b22 -= q*b02;
        double x23 = m23 - q*m03; b23 -= q*b03;
        q = m30/x00;
        b30 -= q*b00;
        double x31 = m31 - q*m01; b31 -= q*b01;
        double x32 = m32 - q*m02; b32 -= q*b02;
        double x33 = m33 - q*m03; b33 -= q*b03;
        q = x21/x11;
        b20 -= q*b10;
        x21 -= q*x11; b21 -= q*b11;
        x22 -= q*x12; b22 -= q*b12;
        x23 -= q*x13; b23 -= q*b13;
        q = x31/x11;
        b30 -= q*b10;
        x31 -= q*x11; b31 -= q*b11;
        x32 -= q*x12; b32 -= q*b12;
        x33 -= q*x13; b33 -= q*b13;
        q = x32/x22;
        b30 -= q*b20;
        x31 -= q*x21; b31 -= q*b21;
        x32 -= q*x22; b32 -= q*b22;
        x33 -= q*x23; b33 -= q*b23;

        m30 = b30/x33;
        m20 = (b20 - x23*m30)/x22;
        m10 = (b10 - x12*m20 - x13*m30)/x11;
        m00 = (b00 - x01*m10 - x02*m20 - x03*m30)/x00;
        m31 = b31/x33;
        m21 = (b21 - x23*m31)/x22;
        m11 = (b11 - x12*m21 - x13*m31)/x11;
        m01 = (b01 - x01*m11 - x02*m21 - x03*m31)/x00;
        m32 = b32/x33;
        m22 = (b22 - x23*m32)/x22;
        m12 = (b12 - x12*m22 - x13*m32)/x11;
        m02 = (b02 - x01*m12 - x02*m22 - x03*m32)/x00;
        m33 = b33/x33;
        m23 = (b23 - x23*m33)/x22;
        m13 = (b13 - x12*m23 - x13*m33)/x11;
        m03 = (b03 - x01*m13 - x02*m23 - x03*m33)/x00;
    }

    /**
     * Gets a string representation of the matrix.
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[["+m00+", "+m01+", "+m02+", "+m03+"],\n"+
               " ["+m10+", "+m11+", "+m12+", "+m13+"],\n"+
               " ["+m20+", "+m21+", "+m22+", "+m23+"],\n"+
               " ["+m30+", "+m31+", "+m32+", "+m33+"]]";
    }

    public boolean isIdentity() {
        boolean identity = true;
        identity &= Double.compare(m00, 1) == 0;
        identity &= Double.compare(m01, 0) == 0;
        identity &= Double.compare(m02, 0) == 0;
        identity &= Double.compare(m03, 0) == 0;
                    
        identity &= Double.compare(m10, 0) == 0;
        identity &= Double.compare(m11, 1) == 0;
        identity &= Double.compare(m12, 0) == 0;
        identity &= Double.compare(m13, 0) == 0;
                    
        identity &= Double.compare(m20, 0) == 0;
        identity &= Double.compare(m21, 0) == 0;
        identity &= Double.compare(m22, 1) == 0;
        identity &= Double.compare(m23, 0) == 0;
                    
        identity &= Double.compare(m30, 0) == 0;
        identity &= Double.compare(m31, 0) == 0;
        identity &= Double.compare(m32, 0) == 0;
        identity &= Double.compare(m33, 1) == 0;
        return identity;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Transformation ct = (Transformation)o;
        boolean equals = true;
        equals &= m00 == ct.m00;
        equals &= m01 == ct.m01;
        equals &= m02 == ct.m02;
        equals &= m03 == ct.m03;
                              
        equals &= m10 == ct.m10;
        equals &= m11 == ct.m11;
        equals &= m12 == ct.m12;
        equals &= m13 == ct.m13;
                              
        equals &= m20 == ct.m20;
        equals &= m21 == ct.m21;
        equals &= m22 == ct.m22;
        equals &= m23 == ct.m23;
                              
        equals &= m30 == ct.m30;
        equals &= m31 == ct.m31;
        equals &= m32 == ct.m32;
        equals &= m33 == ct.m33;
        return equals;
    }
    
    public boolean equals(Transformation ct, double epsylon) {
        boolean equals = true;
        equals &= Math.abs(m00 - ct.m00) < epsylon;
        equals &= Math.abs(m01 - ct.m01) < epsylon;
        equals &= Math.abs(m02 - ct.m02) < epsylon;
        equals &= Math.abs(m03 - ct.m03) < epsylon;
                  
        equals &= Math.abs(m10 - ct.m10) < epsylon;
        equals &= Math.abs(m11 - ct.m11) < epsylon;
        equals &= Math.abs(m12 - ct.m12) < epsylon;
        equals &= Math.abs(m13 - ct.m13) < epsylon;
                  
        equals &= Math.abs(m20 - ct.m20) < epsylon;
        equals &= Math.abs(m21 - ct.m21) < epsylon;
        equals &= Math.abs(m22 - ct.m22) < epsylon;
        equals &= Math.abs(m23 - ct.m23) < epsylon;
                  
        equals &= Math.abs(m30 - ct.m30) < epsylon;
        equals &= Math.abs(m31 - ct.m31) < epsylon;
        equals &= Math.abs(m32 - ct.m32) < epsylon;
        equals &= Math.abs(m33 - ct.m33) < epsylon;
        return equals;
    }
        
    @Override
    public int hashCode() {
        return 1;
    }
}

