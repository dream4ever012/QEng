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

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;


/**
 * xlUsage command.
 * 
 * @author Jim Caprioli
 */
public class CmdUsage implements IStateCommand {
    private XlUi xldba;

    /**
     * Creates a new instance of xlTime.
     * @param opts to be printed in usage format
     */
    public CmdUsage(final XlUi dba) {
        xldba = dba;
    }

    /**
     * Prints options in usage format.
     *
     * @return state unchanged
     */
    public final int execute() {
        // Use the inbuilt formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.defaultOptPrefix = "";
        formatter.defaultLongOptPrefix = "";
        formatter.printHelp("command [argument(s)]", xldba.options);
        System.out.println("");

        return 0;
    }
}

