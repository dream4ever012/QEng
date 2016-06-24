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
import java.sql.*;


/**
 * Simple selftest for xlSQL Excel JDBC Driver
 * 
 * @version $Revision: 1.2 $
 * @author $author$
 */
public class TestXlsql {
    private TestXlsql() {
        ;
    }

    /**
     * Main
     * 
     * @param args N/a
     */
    public static void main(String[] args) {

        try {
            
            String driver = "com.nilostep.xlsql.jdbc.xlDriver";
            Driver d = (Driver) Class.forName(driver).newInstance();
            System.out.println("Driver was successfully loaded.");
            String protocol = "jdbc:nilostep:excel";
            String database = System.getProperty("user.dir");
            String url = protocol + ":" + database;
            Connection con = DriverManager.getConnection(url);
            Statement stm = con.createStatement();
            
            String sql = "DROP TABLE \"demo.xlsqly8\" IF EXISTS;"
                         + "CREATE TABLE \"demo.xlsqly8\" (v varchar);";
            stm.execute(sql);

            // because it is release Y8 we'll do 8000
            for (int i = 0; i < 8000; i++) {
                sql = "INSERT INTO \"demo.xlsqly8\" VALUES ('xlSQL Y8 - NiLOSTEP');";
                stm.execute(sql);
            }
            
            
            sql = "select count(*) from \"demo.xlsqly8\"";

            ResultSet rs = stm.executeQuery(sql);

            //
            while (rs.next()) {
                System.out.println("Sheet xlsqly8 has " + rs.getString(1)
                                   + " rows.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Are you sure this is WinXP and Java 1.4.2 ..? ");
            e.printStackTrace();
        }
    }
}
