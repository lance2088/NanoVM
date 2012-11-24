package org.nanovm;

//
//  NanoVMTool, Converter and Upload Tool for the NanoVM
//  Copyright (C) 2005 by Till Harbaum <Till@Harbaum.org>
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
//  Parts of this tool are based on public domain code written by Kimberley
//  Burchett: http://www.kimbly.com/code/classfile/
//

//
// UVMWriter.java
//
import org.nanovm.converter.ClassLoader;
import java.io.*;

public class CodeWriter {

    static public void write(String fileName, NanoVMByteCode code) {
        System.out.println("Generating unified class file ...");

        // overwrite target config when -c option was given
        System.out.println("Writing C header file");

        // write header file to disk
        try {
            System.out.println("Writing to file " + fileName);
            File outputFile = new File(fileName);
            FileOutputStream out = new FileOutputStream(outputFile);

            out.write(("/* " + fileName + ", autogenerated from " +
                    ClassLoader.getClassInfo(0).getName() +
                    " class */\n").getBytes());

            out.write("{\n".getBytes());

            int size = code.getSize();

            for (int i = 0; i < size; i++) {
                String str = "0" + Integer.toHexString(0xff & code.getByte(i));
                out.write(("0x" + str.substring(str.length() - 2)).getBytes());

                if (i < size - 1) {
                    out.write(",".getBytes());
                }
                if (i % 16 == 15) {
                    out.write("\n".getBytes());
                } else {
                    out.write(" ".getBytes());
                }
            }

            out.write("\n};\n".getBytes());
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing header file: " + e.toString());
            System.exit(-1);
        }

    }
}