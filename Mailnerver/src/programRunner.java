import java.util.*;

public class programRunner {
   boolean exit = false;
   database database = new database();

   public void startProgram(){
      System.out.println("Eingaben:\n\n0: Exit\n1: Neuen Empfänger\n2: Empfänger löschen\n3: Zeige nächsten Versand von einem Empfänger");
      programRunner programRunner = new programRunner();
      programRunner.runner();
   }

   public void runner(){
      boolean exit = false;
      Scanner s = new Scanner(System.in);

      mailnerver myMailnerver = new mailnerver(database);
      Thread t = new Thread(myMailnerver);
      t.start();

      int eingabe;
      do{
         try{
            eingabe = s.nextInt();

            if(eingabe == 0){
               exit = true;
            }
            else if(eingabe == 1){
               database.insertToTable();
            }
            else if(eingabe == 2){
               database.deleteData();
            }
            else if(eingabe == 3){
               database.getData();
            }
            else{
               System.out.println("Ungültige Eingabe");
               s.next();
            }
         }catch(Exception e){
            System.out.println("Ungültige Eingabe");
            s.next();
         }
      }while(!exit);

      s.close();
      myMailnerver.quit();
      System.exit(0);
   }
}