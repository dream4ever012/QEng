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

import com.nilostep.xlsql.database.AExporter;
import com.nilostep.xlsql.database.xlException;
import com.nilostep.xlsql.database.xlInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


/**
 * Main xlSQL commandline interface.
 * 
 * @version $Revision: 1.4 $
 * @author $author$
 */
public class XlUi {
    private static final String PROMPT = "xlsql>";
    static final int IDLE = 1;
    static final int CONNECTED = 2;
    static final int READ = 3;
    static final int OPEN = 4;

    //
    private int state = IDLE;
    private String command;

    // NiLOSTEP... make private:
    Options options;
    AExporter exporter;
    xlInstance instance;
    Connection con;
    CommandLine commandline;
    //
    

    /**
     * Creates a new XlUi object.
     */
    public XlUi() {
        setState(IDLE);
    }

    /**
     * Parse and execute.
     * @throws xlException when parse excepton
     */
    public final void doIt() throws xlException {
        try {
            CommandLineParser parser = new BasicParser();
            String[] arguments = getCommand().split(" ");
            commandline = parser.parse(options, arguments);

            XlUiParser cmdlineparser = new XlUiParser();
            IStateCommand cmd = cmdlineparser.parseC(this);

            if (cmd != null) {
                int newstate = cmd.execute();
                state = (newstate > 0) ? newstate : state;
                setState(state);
            } else {
                throw new xlException("?.."); // cannot happen
            }
        } catch (ParseException pe) {
            throw new xlException(pe.getMessage());
        }
    }

    /**
     * Getter for property command.
     * 
     * @return Value of property command.
     */
    public final java.lang.String getCommand() {
        return command;
    }

    /**
     * Getter for property state.
     * @return Value of property state.
     */
    public final int getState() {
        return state;
    }

    /**
     * Commandline loop.
     * @throws xlException
     */
    private void go() {
        BufferedReader in = new 
                            BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n*** caution: development release ***");
        System.out.println("xlSQL/Y7 Excel JDBC Driver");
        System.out.println("Copyright 2004 by NiLOSTEP Information Sciences");
        System.out.println("http://xlsql.dev.java.net\n");

        while (true) {
            switch (state) {
                case IDLE:
                    System.out.print(PROMPT + " -idle#");

                    break;

                case CONNECTED:
                    System.out.print(PROMPT + " -connected#");

                    break;

                case READ:
                    System.out.print(PROMPT + " -open read only#");

                    break;

                case OPEN:
                    System.out.print(PROMPT + " -open#");

                    break;

                default:
                    break;
            }

            try {
                System.out.flush();

                // add - in front of first option as required by commons
                String line = "-" + in.readLine();

                if ((line == null) || line.equals("-quit")
                        || line.equals("-exit") || line.equals("-bye")
                        || line.equals("-q")) {
                    break;
                }

                if (line.length() == 0) { // Ignore blank lines
                    throw new xlException("");
                }

                setCommand(line);


                // do parsing and execution
                doIt();
            } catch (xlException pe) {
                System.err.println("..? Enter h for help \n");

                continue;
            } catch (IOException io) {
                break;
            }
        }
    }

    /**
     * Main method.
     * @param args (Not used.)
     */
    public static void main(final String[] args) {
        try {
            XlUi xldba = new XlUi();
            xldba.go();
        } catch (Exception e) {
            System.out.println("?.. Abnormal program termination\n");
            e.printStackTrace();
        }
    }

    /**
     * Setter for property command.
     * 
     * @param newcommand New value of property command.
     */
    public final void setCommand(final String newcommand) {
        command = newcommand;
    }

    /**
     * Setter for property state.
     * @param newstate New value of property state.
     */
    public final void setState(final int newstate) {
        state = newstate;

        Option option;

        if (state == IDLE) {
            options = new Options();
            options.addOption("c", true, "[ config ]");
            options.addOption("h", false, "help");
            options.addOption("quit", false, "end xldba session");
            options.addOption("t", false, "display time");
        } else if (state == CONNECTED) {
            options = new Options();
            options.addOption("d", false, "disconnect");
            option = new Option("engine", true, 
                                "[ SET | ADD | REMOVE ] [ name ]");
            option.setArgs(2);
            options.addOption(option);
            options.addOption("h", false, "help");
            options.addOption("o", false, "open database");
            options.addOption("open", true, "[ path ]");
            options.addOption("ping", false, "ping engine");
            options.addOption("quit", false, "end xldba session");
            options.addOption("read", true, "[ path ]");
            options.addOption("r", false, "open database for read");
            option = new Option("set", true, "[ parameter ] [ value ]");
            option.setArgs(2);
            options.addOption(option);
            options.addOption("show", true, "[ ALL | parameter ]");
            options.addOption("t", false, "display time");
        } else if (state == READ) {
            options = new Options();
            options.addOption("cat", false, "catalog");
            options.addOption("close", false, "close");
            option = new Option("export", true, 
                 "[ ALL | (sch.)tab ] AS [ hsqldb | mysql | xml ] TO [ path | engine | out ]");
            option.setArgs(5);
            options.addOption(option);
            options.addOption("h", false, "help");
            options.addOption("quit", false, "end xldba session");
            options.addOption("t", false, "display time");
        } else if (state == OPEN) {
            options = new Options();
            options.addOption("close", false, "close");
            option = new Option("dir2xl", true, 
                 "[ all | files | subdirs ] in [ path ] as [ list | tree ] to "
                 + " [ (schema.)table ]");
            option.setArgs(7);
            options.addOption(option);
            options.addOption("h", false, "help");
            options.addOption("quit", false, "end xldba session");
            options.addOption("sql", false, "interactive SQL session");
            options.addOption("script", true, "[ file ] run SQL script");
            options.addOption("t", false, "display time");
        }
    }
}

