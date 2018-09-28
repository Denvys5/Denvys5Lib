/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.denvys5.utils.java;

import sun.security.util.Debug;

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.util.HashMap;

/**
 * This class extends ClassLoader with additional support for defining classes
 * with an associated code source and permissions which are retrieved by the
 * system policy by default.
 *
 * @author Li Gong
 * @author Roland Schemers
 */
public class eSecureClassLoader extends ClassLoader {
    /*
     * If initialization succeed this is set to true and security checks will
     * succeed. Otherwise the object is not initialized and the object is
     * useless.
     */

    private final boolean initialized;

    // HashMap that maps CodeSource to ProtectionDomain
    // @GuardedBy("pdcache")
    private final HashMap<CodeSource, ProtectionDomain> pdcache = new HashMap<>(11);

    private static final Debug debug = Debug.getInstance("scl");

    static {
        ClassLoader.registerAsParallelCapable();
    }

    /**
     * Creates a new SecureClassLoader using the specified parent class loader
     * for delegation.
     *
     * <p>
     * If there is a security manager, this method first calls the security
     * manager's <code>checkCreateClassLoader</code> method to ensure creation
     * of a class loader is allowed.
     * <p>
     *
     * @param parent the parent ClassLoader
     * @throws SecurityException if a security manager exists and its
     *                           <code>checkCreateClassLoader</code> method doesn't allow creation of a
     *                           class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    protected eSecureClassLoader(ClassLoader parent) {
        super(parent);
        // this is to make the stack depth consistent with 1.1
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkCreateClassLoader();
        }
        initialized = true;
    }

    /**
     * Creates a new SecureClassLoader using the default parent class loader for
     * delegation.
     *
     * <p>
     * If there is a security manager, this method first calls the security
     * manager's <code>checkCreateClassLoader</code> method to ensure creation
     * of a class loader is allowed.
     *
     * @throws SecurityException if a security manager exists and its
     *                           <code>checkCreateClassLoader</code> method doesn't allow creation of a
     *                           class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    protected eSecureClassLoader() {
        super();
        // this is to make the stack depth consistent with 1.1
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkCreateClassLoader();
        }
        initialized = true;
    }

    /**
     * Converts an array of bytes into an instance of class Class, with an
     * optional CodeSource. Before the class can be used it must be resolved.
     * <p>
     * If a non-null CodeSource is supplied a ProtectionDomain is constructed
     * and associated with the class being defined.
     * <p>
     *
     * @param name the expected name of the class, or <code>null</code> if not
     *             known, using '.' and not '/' as the separator and without a trailing
     *             ".class" suffix.
     * @param b    the bytes that make up the class data. The bytes in positions
     *             <code>off</code> through <code>off+len-1</code> should have the format of
     *             a valid class file as defined by
     *             <cite>The Java&trade; Virtual Machine Specification</cite>.
     * @param off  the start offset in <code>b</code> of the class data
     * @param len  the length of the class data
     * @param cs   the associated CodeSource, or <code>null</code> if none
     * @return the <code>Class</code> object created from the data, and optional
     * CodeSource.
     * @throws ClassFormatError          if the data did not contain a valid class
     * @throws IndexOutOfBoundsException if either <code>off</code> or
     *                                   <code>len</code> is negative, or if <code>off+len</code> is greater than
     *                                   <code>b.length</code>.
     * @throws SecurityException         if an attempt is made to add this class to a
     *                                   package that contains classes that were signed by a different set of
     *                                   certificates than this class, or if the class name begins with "java.".
     */
    protected final Class<?> defineClass(String name,
                                         byte[] b, int off, int len,
                                         CodeSource cs) {
        return defineClass(name, b, off, len, getProtectionDomain(cs));
    }

    /**
     * Converts a {@link java.nio.ByteBuffer <tt>ByteBuffer</tt>} into an
     * instance of class <tt>Class</tt>, with an optional CodeSource. Before the
     * class can be used it must be resolved.
     * <p>
     * If a non-null CodeSource is supplied a ProtectionDomain is constructed
     * and associated with the class being defined.
     * <p>
     *
     * @param name the expected name of the class, or <code>null</code> if not
     *             known, using '.' and not '/' as the separator and without a trailing
     *             ".class" suffix.
     * @param b    the bytes that make up the class data. The bytes from positions
     *             <tt>b.position()</tt> through <tt>b.position() + b.limit() -1</tt>
     *             should have the format of a valid class file as defined by
     *             <cite>The Java&trade; Virtual Machine Specification</cite>.
     * @param cs   the associated CodeSource, or <code>null</code> if none
     * @return the <code>Class</code> object created from the data, and optional
     * CodeSource.
     * @throws ClassFormatError  if the data did not contain a valid class
     * @throws SecurityException if an attempt is made to add this class to a
     *                           package that contains classes that were signed by a different set of
     *                           certificates than this class, or if the class name begins with "java.".
     * @since 1.5
     */
    protected final Class<?> defineClass(String name, java.nio.ByteBuffer b,
                                         CodeSource cs) {
        return defineClass(name, b, getProtectionDomain(cs));
    }

    /**
     * Returns the permissions for the given CodeSource object.
     * <p>
     * This method is invoked by the defineClass method which takes a CodeSource
     * as an argument when it is constructing the ProtectionDomain for the class
     * being defined.
     * <p>
     *
     * @param codesource the codesource.
     * @return the permissions granted to the codesource.
     */
    protected PermissionCollection getPermissions(CodeSource codesource) {
        check();
        return new Permissions(); // ProtectionDomain defers the binding
    }

    /*
     * Returned cached ProtectionDomain for the specified CodeSource.
     */
    private ProtectionDomain getProtectionDomain(CodeSource cs) {
        if (cs == null)
            return null;

        ProtectionDomain pd = null;
        synchronized (pdcache) {
            pd = pdcache.get(cs);
            if (pd == null) {
                PermissionCollection perms = getPermissions(cs);
                pd = new ProtectionDomain(cs, perms, this, null);
                pdcache.put(cs, pd);
                if (debug != null) {
                    debug.println(" getPermissions " + pd);
                    debug.println("");
                }
            }
        }
        return pd;
    }

    /*
     * Check to make sure the class loader has been initialized.
     */
    private void check() {
        if (!initialized) {
            throw new SecurityException("ClassLoader object not initialized");
        }
    }

}
