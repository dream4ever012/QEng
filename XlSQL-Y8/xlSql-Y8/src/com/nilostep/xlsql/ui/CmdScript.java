/*(Header: NiLOSTEP / xlSQL)

 Copyright (C) 2004 NiLOSTEP
   NiLOSTEP Information Sciences
   http://nilostep.com
   nilo.de.roock@nilostep.com

 This program is free software; you can redistribute it and/or modify it 
 under the terms of the GNU General Public License as published by the Free 
 Software Foundation; either version 2 of the License, or (at your option) 
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details. You should have received a copy of the GNU General Public 
 License along with this program; if not, write to the Free Software 
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.nilostep.xlsql.ui;

import com.nilostep.xlsql.util.xlFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Run an SQL script.
 * 
 * @author Jim Caprioli
 */
public class CmdScript implements IStateCommand {
    private XlUi xldba;

    /**
     * Creates a new instance of this class.
     * 
     * @param dba object
     */
    public CmdScript(final XlUi dba) {
        xldba = dba;
    }
    
    public CmdScript() {;} 

    /**
     * Run SQL script.
     * 
     * @return state after execution
     * 
     * @throws IOException
     */
    public final int execute() {
        try {
            String file = xldba.commandline.getOptionValue("script");
            String[] ext = {"sql"};
            File f = xlFile.settle(file, ext);

            if (!f.canRead()) {
                throw new IOException("Scriptname..?");
            }
            
            doScript(f, xldba.con);
            
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage() + ":"
                               + e.getSQLState());
        } finally {
            System.out.println("");
        }

        return 0;
    }
    
    // REFACTOR: -->OUT? to util perhaps? ( remove default Constructor )
    public void doScript(File f, Connection c) throws IOException, SQLException {
            String sql = "";
            String scriptLine;
            FileInputStream fin = new FileInputStream(f);
            BufferedReader myInput = new BufferedReader(
                                             new InputStreamReader(fin));
            int count = 0;
            Statement stm = c.createStatement();

            while ((scriptLine = myInput.readLine()) != null) {
                if (!(scriptLine.startsWith("--"))
                        && !(scriptLine.startsWith("#"))
                        && !(scriptLine.length() == 0)) {
                    sql = sql + scriptLine;

                    if (sql.endsWith(";")) {
                        sql = sql.replace(';', ' ').trim();
                        count++;
                        stm.execute(sql);
                        sql = "";
                    }
                }
            }

            System.out.println(count
                               + " statements processed: close to commit to xlSQL");
    }
}

