import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class mailnerver implements Runnable{
   Session newSession = null;
	MimeMessage mimeMessage = null;

   database database;
   
   boolean running = true;
   public mailnerver(database database){
      this.database = database;
   }

   public void quit(){
      running = false;
   }

   public void run(){
      setProperties();

      Date absendeDatum = null;
      java.sql.Date sqlDate = null;
      Date jetzigesDatum = new Date();

      String email = "";
      boolean formatparse = true;

      int personenID;
      int instance = 0;

      while(running){
         personenID = 1;
         do{
            sqlDate = database.getDataAbsendezeit(personenID);
            
            if(sqlDate == null){
               absendeDatum = new Date(java.sql.Date.valueOf("9999-12-31").getTime());
               formatparse = false;
            }
            else{
               Date utilDate = new Date(sqlDate.getTime());
               email = database.getDataEmail(personenID);
               absendeDatum = utilDate;
               instance = personenID;
               formatparse = true;
            }
            personenID++;
            
            if(personenID > database.getCount()){
               personenID = 1;
            }
         }while(!jetzigesDatum.after(absendeDatum) || formatparse == false);
         
         try {
            setMail(instance);
         } catch (MessagingException | IOException e) {
            e.printStackTrace();
         }
         try {
            sendMail(email, sqlDate);
         } catch (MessagingException e) {
            e.printStackTrace();
         }
         System.out.println("Email send successfully!");
      }
   }

   private void sendMail(String email, java.sql.Date naechsteAbsendezeit) throws MessagingException{
      String fromUser = "yourmail@mail.com";
		String fromUserPassword = "somePasswordUWontGetLol";
		String emailHost = "smtp.gmail.com";

      Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();

      java.sql.Date letzteAbsendezeit = database.getLetzteAbsendezeit(email);
      database.setnaechsteAbsendezeit(email, naechsteAbsendezeit, letzteAbsendezeit);
   }

   private MimeMessage setMail(int personenID) throws AddressException, MessagingException, IOException{

		String to = database.getDataEmail(personenID);
		String emailSubject = "Test Mail";
		String emailBody = "Test Body of my email";

		mimeMessage = new MimeMessage(newSession);
      mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      mimeMessage.setSubject(emailSubject);
	   
      MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(emailBody,"text/html");
		
      MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(bodyPart);
		
      mimeMessage.setContent(multiPart);
		return mimeMessage;
	}

   private void setProperties(){
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		newSession = Session.getDefaultInstance(properties,null);
	}
}
