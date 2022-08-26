# Lern-Bericht
Mailnerver - Shayanthan Ravindran, Benjamin Peterhans

## Einleitung

Für dieses Lernatelierprojekt konnten wir wiederum ein Projekt aus der Aufgabenliste aussuchen. Wieder habe ich das Projekt mit dem vorherigen Partner (Shayanthan Ravindran) vom Projekt ILA_1301 bearbeitet. Wir haben uns in der Entscheidungsphase für einen "Mailnerver" entschieden, welches wir gerne realisieren wollten. Weitere Details zu diesem Mailnerver kommen in der Produktbeschreibung vor.

## Ziele

Ich kann...

Z1:
...in Java mit SQLITE arbeiten.
Z2:
...die Datenbanksprache SQL in Java anwenden.
Z3:
...mithilfe des Internets eine E-Mail mit Gmail absenden.
Z4:
...das GUI in diesem Projekt miteinimplementieren.

## Beschreibung

In diesem Programm geht es darum, einen bestimmten Empfänger "vollzuspammen". Es wurden dafür mehrere Optionen beziehungsweise drei Optionen rein implementiert, mit denen der Benutzer etwas eingeben kann. Der Benutzer kann auch ganz normal das Programm mit einer Eingabe beenden (mittels "Threading" ist dies möglich).
Die erste Funktion wäre das Einfügen von einem neuen Empfänger. Der Benutzer wird dort nach der Empfängeradresse und dem Absendedatum gefragt.
Die zweite Funktion ist das Löschen eines Empfängers. Auch hier wird der Benutzer aufgefordert, die E-Mail des Empfängers einzugeben.
Zuletzt kann man noch anzeigen lassen, wann die nächste E-Mail an einen Empfänger gesendet wird (Eingabe des Benutzers mit der Empfängeradresse).

Der Mailnerver wurde so programmiert, dass die Wartezeit bis zur nächsten Absendezeit mit der Verschickung halbiert. Das heisst, wenn man dem Empfänger in zwei Tagen eine E-Mail schicken will, wird dies auch gemacht und zusätzlich wird diese zwei Tagen halbiert, also auf einen Tag. Das bedeutet, dass die zweite E-Mail, die verschickt wird, einen Tag später nach der ersten Verschickung erfolgt.

## Demo

[![IMAGE ALT TEXT](http://img.youtube.com/vi/UHTzR0_b-g4/0.jpg)](http://www.youtube.com/watch?v=UHTzR0_b-g4 "Mailnerver")

## Codeausschnitt

<script src="https://gist.github.com/PBenjy/240716155aa371d9af713f74ec2006c5.js"></script>

## Verifikation

Z1: Wird mit dem Datenbank "Mailnerver.db" im Projektordner und dem Code in "database.java" von Zeile 11 bis 22 validiert.

Z2: Wird mit dem Beispielausschnitt aus dem Code in "database.java" von Zeile 24 bis 42 validiert.

Z3: Wird mit dem Code in "mailnerver.java" von Zeile 76 bis 79 validiert (Ausschnitt wo die Email versendet wird).

Z4: Wird mit einem Ausschnitt aus dem Code in "GUI.java" von Zeile 116 bis 137 validiert.

# Reflektion zum Arbeitsprozess

##### Einarbeitung:

Mit den Erfahrungen aus dem letzten Projekt ILA-1301 konnten wir uns recht gut in dieses Projekt einarbeiten. Wir beide denken, dass die Zusammenarbeit im Vergleich zum letzten Mal besser ist. Mit der Planung sind wir eigentlich recht zufrieden, ausser dass wir halt die «Nice-to-Have-Anforderungen» nicht in das Programm implementieren konnten.

Die Entscheidung, mit Visual Studio Code zu arbeiten, kann man zum Teil als Fehler einsehen. Anfangs mussten wir zuerst Auskunft holen, mit welchen «Extension-Packs» arbeiten muss. Mit NetBeans wäre dies höchstwahrscheinlich einfacher gewesen. Durch die «Extension-Packs» konnten wir dann auch gut die .jar-Dateien ins Projekt einfügen, ohne dass wir Probleme bekamen, immer wieder den sogenannten «Classpath» eingeben zu müssen (manchmal funktionierte es, manchmal leider nicht).

#### Meine Arbeit:

Bei meiner Realisierung bin ich auf Probleme gestossen, die mir auch ein wenig Mühe bereiteten. Da ich nicht viel wusste, wie man eine E-Mail versenden kann, habe ich nach Code-Examples aus dem Internet gesucht. Ehrlich gesagt wäre ich nie imstande, das Versenden von E-Mails eigenhändig zu programmieren. Mit den Code-Examples konnte ich einigermassen verstehen, wie eine E-Mail in Java aufgebaut und generiert wird.
Probleme bei der Datenbank hatte ich auch, da manchmal die Daten, die ich in die Datenbank einfügen wollte, nicht richtig "funktionierten", oder leichter gesagt: Ich bekam immer eine Exception-Meldung. Mit etwas mehr Nachdenken und genauerem Hinsehen konnte ich das Problem ausfindig machen: In die String-Message von SQL konnte ich die initialisierten Datentypen nicht einfügen. Ich musste dafür das "PreparedStatement" anwenden. Mit dem konnte ich so die Daten in dem SQL-String einfügen.

 
