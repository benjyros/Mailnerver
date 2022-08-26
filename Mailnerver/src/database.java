import java.sql.*;

import javax.swing.JOptionPane;

public class database {
   boolean checkData;
   boolean nextQuestion;
   Connection c = null;
   GUI gui = new GUI();

   private void connect(){
      try {
         if(c == null){
            String currentPath = new java.io.File(".").getCanonicalPath();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + currentPath + "\\db\\Mailnerver.db");
         }
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
   }

   public void createTable(){
      connect();
      try{
         Statement stmt = c.createStatement();

         String sql = "CREATE TABLE IF NOT EXISTS empfaenger (\n"
               + "   Personen_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
               + "   emailadresse VARCHAR(50) NOT NULL,\n"
               + "   naechsteAbsendezeit DATETIME NOT NULL,\n"
               + "   letzteAbsendezeit DATETIME NOT NULL);";
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }

   public void insertToTable(){
      connect();
      String email;
      boolean formatparse = true;
      do{
         email = gui.askEmail();
         if(email != null){
            checkData(email);
         }
         else{
            nextQuestion = false;
            break;
         }
      }while(!checkData);

      if(nextQuestion && checkData){
         java.util.Date absendezeit = gui.askDateTime();

         Date absendeDatum = new Date(absendezeit.getTime());
         Date letzteAbsendezeit = new Date(System.currentTimeMillis());
         if(absendezeit != null && formatparse){
            try{
               String sql = "INSERT INTO empfaenger (Personen_ID,emailadresse,naechsteAbsendezeit,letzteAbsendezeit) VALUES (?,?,?,?);";
               PreparedStatement pstmt = c.prepareStatement(sql);
               pstmt.setInt(1, getCount() + 1);
               pstmt.setString(2, email);
               pstmt.setDate(3, absendeDatum);
               pstmt.setDate(4, letzteAbsendezeit);
               pstmt.executeUpdate();

               gui.successful("insert", null, "");
            }catch(Exception e){
               gui.error("insert");
            }
         }
         else{
            gui.error("insert");
         }
      }
   }

   /*
   private void modifyTable(){
      connect();
      try{
         Statement stmt = c.createStatement();
         String sql = "ALTER TABLE empfaenger ADD COLUMN letzteAbsendezeit DATETIME NOT NULL;";
         stmt.executeUpdate(sql);

         stmt.close();
         c.close();
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table has been modified successfully");
   }
   */

   /*
   public void dropTable(){
      connect();
      try{
         Statement stmt = c.createStatement();
         String sql = "DROP TABLE empfaenger;";
         stmt.executeUpdate(sql);

         stmt.close();
         c.close();
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table has been deleted successfully");
   }
   */
   

   public void deleteData(){
      connect();
      String email = gui.askEmail();
      String sql;
      PreparedStatement pstmt;

      boolean deletedData = false;
      if(email != null){
         try{
            sql = "SELECT emailadresse FROM empfaenger WHERE emailadresse = ?;";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
   
            while(rs.next()){
               try{
                  sql = "DELETE FROM empfaenger WHERE emailadresse = ?;";
                  pstmt = c.prepareStatement(sql);
                  pstmt.setString(1, email);
                  pstmt.executeUpdate();
                  deletedData = true;
               }catch(Exception e){
                  gui.error("delete");
               }
            }
            if(deletedData){
               gui.successful("delete", null, "");
            }
            else{
               JOptionPane.showMessageDialog(null,
                  "Dieser Empfänger existiert nicht",
                  "Error - Delete Data",
                  JOptionPane.WARNING_MESSAGE);
            }
         }catch(Exception e){
            gui.error("delete");
         }
      }
   }

   public void getData(){
      connect();
      java.sql.Date naechsteAbsendezeit = null;
      String email;
      boolean gotData = false;
      do{
         email = gui.askEmail();
         checkData(email);
      }while(checkData = false);

      try{
         String sql = "SELECT naechsteAbsendezeit FROM empfaenger WHERE emailadresse = ?;";
         PreparedStatement pstmt = c.prepareStatement(sql);
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){
            naechsteAbsendezeit = rs.getDate("naechsteAbsendezeit");
            gotData = true;
         }

         if(gotData){
            gui.successful("read", naechsteAbsendezeit, email);
         }
         else{
            JOptionPane.showMessageDialog(null,
                  "Dieser Empfänger existiert nicht.\nBitte geben Sie eine andere Emailadresse ein.",
                  "Error - Read Data",
                  JOptionPane.WARNING_MESSAGE);
         }
      }catch(Exception e){
         gui.error("read");
      }
   }

   public Date getDataAbsendezeit(int personenID){
      connect();
      Date naechsteAbsendezeit = null;
      try{
         String sql = "SELECT naechsteAbsendezeit FROM empfaenger WHERE Personen_ID = ?;";
         PreparedStatement pstmt = c.prepareStatement(sql);
         pstmt.setInt(1, personenID);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){
            naechsteAbsendezeit = rs.getDate("naechsteAbsendezeit");
         }
      }catch(Exception e){
         naechsteAbsendezeit = null;
         System.out.println("error");
      }
      return naechsteAbsendezeit;
   }

   private void checkData(String email){
      nextQuestion = true;
      checkData = false;
      try{
         String sql = "SELECT emailadresse FROM empfaenger WHERE emailadresse = ?;";
         PreparedStatement pstmt = c.prepareStatement(sql);
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){
            nextQuestion = false;
         }
         if(nextQuestion){
            checkData = true;
         }
         else{
            JOptionPane.showMessageDialog(null,
                  "Dieser Empfänger existiert bereits.\nBitte geben Sie eine neue Emailadresse ein.",
                  "Error - Insert Into Table",
                  JOptionPane.WARNING_MESSAGE);
         }
      }catch(Exception e){
         System.out.println(e.getMessage());
      }
   }

   public String getDataEmail(int personenID){
      connect();
      String email= "";
      try{
         String sql = "SELECT emailadresse FROM empfaenger WHERE Personen_ID = ?;";
         PreparedStatement pstmt = c.prepareStatement(sql);
         pstmt.setInt(1, personenID);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){
            email = rs.getString("emailadresse");
         }
      }catch(Exception e){
         gui.error("read");
      }
      return email;
   }

   

   public Date getLetzteAbsendezeit(String email){
      connect();
      Date letzteAbsendezeit = null;
      try{
         String sql = "SELECT letzteAbsendezeit FROM empfaenger WHERE emailadresse = ?;";
         PreparedStatement pstmt = c.prepareStatement(sql);
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){
            letzteAbsendezeit = rs.getDate("letzteAbsendezeit");
         }
      }catch(Exception e){
         gui.error("read");
      }
      return letzteAbsendezeit;
   }

   public int getCount(){
      connect();
      int personenCount = 0;
      try{
         Statement stmt = c.createStatement();
         String sql = "SELECT COUNT(*) AS count  FROM empfaenger;";
         ResultSet rs = stmt.executeQuery(sql);
         
         if(rs.next()){
            personenCount = rs.getInt("count");
         }
         else{
            personenCount = 0;
         }
      }catch(Exception e){
         gui.error("read");
      }
      return personenCount;
   }

   public void setnaechsteAbsendezeit(String email, Date naechsteAbsendezeit, Date letzteAbsendezeit){
      connect();
      long instance = naechsteAbsendezeit.getTime() + ((naechsteAbsendezeit.getTime() - letzteAbsendezeit.getTime()) / 2);
      letzteAbsendezeit = naechsteAbsendezeit;
      naechsteAbsendezeit = new Date(instance);
      try{
         String sql = "UPDATE empfaenger SET naechsteAbsendezeit = ?, " +
                        "letzteAbsendezeit = ?" +
                        "WHERE emailadresse = ?;";
         PreparedStatement stmt = c.prepareStatement(sql);
         
         stmt.setDate(1, naechsteAbsendezeit);
         stmt.setDate(2, letzteAbsendezeit);
         stmt.setString(3, email);
         stmt.executeUpdate();
      }catch(Exception e){
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
      }
   }
}