import javax.swing.JOptionPane;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GUI {
    boolean ersteDurchfuehrung;
    boolean validMail;
    boolean validDate;

    public String askEmail(){
        ersteDurchfuehrung = true;
        validMail = true;
        
        String frage = "Geben Sie die Empfängeradresse ein:";
        String eingabe;

        do{
            String instance = frage;
            if(ersteDurchfuehrung == false){
                instance = "Ungültige Eingabe\n" + frage;
            }

            eingabe = (String) JOptionPane.showInputDialog(null,
                instance,
                "Email",
                JOptionPane.DEFAULT_OPTION);
            
            if(eingabe != null){
                if(!isValid(eingabe)){
                    validMail = false; 
                }
                else{
                    validMail = true;
                }
            }
            else{
                return null;
            }

            ersteDurchfuehrung = false;
        }while(eingabe.isEmpty() || !validMail);

        return eingabe;
    }

    private boolean isValid(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public Date askDateTime(){
        ersteDurchfuehrung = true;
        validDate = true;

        Date absendeDatum = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date jetzigesDatum = new Date();
        
        String frage = "Geben Sie Ihr gewünschte Absendezeit ein:\nFormat: 'yyyy-MM-dd kk:mm:ss'";
        String eingabe;
        
        do{
            String instance = frage;
            if(ersteDurchfuehrung == false){
                instance = "Ungültige Eingabe\n" + frage;
            }

            eingabe = (String) JOptionPane.showInputDialog(null,
                instance,
                "Absendezeit",
                JOptionPane.DEFAULT_OPTION);

            ersteDurchfuehrung = false;
            validDate = true;

            if(eingabe != null){
                try{
                    absendeDatum = format.parse(eingabe);
                }catch(ParseException e){
                    validDate = false;
                }
            }
            else{
                return null;
            }

            
        }while(eingabe.isEmpty() || validDate == false || jetzigesDatum.after(absendeDatum));

        return absendeDatum;
    }

    public void error(String typ){
        if(typ == "delete"){
            JOptionPane.showMessageDialog(null,
                "Beim Löschen des Empfängers ist ein Fehler aufgetreten.",
                "Error - Delete Data",
                JOptionPane.WARNING_MESSAGE);
        }
        if(typ == "insert"){
            JOptionPane.showMessageDialog(null,
                "Beim Einfügen eines Empfängers ist ein Fehler aufgetreten.",
                "Error - Insert Into Table",
                JOptionPane.WARNING_MESSAGE);
        }
        if(typ == "read"){
            JOptionPane.showMessageDialog(null,
                "Beim Abfragen der Daten ist ein Fehler aufgetreten.",
                "Error - Show Data",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void successful(String typ, java.sql.Date naechsteAbsendezeit, String empfaenger){
        if(typ == "delete"){
            JOptionPane.showMessageDialog(null,
                "Ihr Empfänger wurde erfolgreich gelöscht.",
                "Success - Delete Data",
                JOptionPane.WARNING_MESSAGE);
        }
        if(typ == "insert"){
            JOptionPane.showMessageDialog(null,
                "Ihr Empfänger wurde erfolgreich aufgenommn.",
                "Success - Insert Into Table",
                JOptionPane.WARNING_MESSAGE);
        }
        if(typ == "read"){
            Date utilDate = new Date(naechsteAbsendezeit.getTime());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            JOptionPane.showMessageDialog(null,
                "Die nächste Absendezeit von " + empfaenger + ": " + df.format(utilDate),
                "Success - Show Data",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}