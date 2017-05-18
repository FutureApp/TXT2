Programmaufruf(Parameter): 
java -jar <name of jar>.jar <URL to wiki-article>

___
Programmaufruf(Beispiel):
java -jar CHTask3.jar https://de.wikipedia.org/wiki/Lindentunnel

___
Anforderung:
Java 1.8.*    -> Analyse
yEd  3.16.2.1 -> Visualisierung
___
Struktur des Ergebnissordners / files:
 ./CompHuman/result/<articeName>_SAnalysis.graphml 		(Enthält die S.Analyse Revisionsseite; Präsentation der Informationen in GraphMl-Syntax;
														 Geeignet zum Einspeisen  in yEd - Applikation)
												   												   
 ./CompHuman/result/<articeName>_SAnalysis.xml			(Enthält die S.Analyse Revisionsseite; Präsentation der Informationen in XML-Syntax;	
														 Geeignet zum Einspeisen  in yEd - Applikation (Umbenennen   in <..>.graphxml)
													 
 ./CompHuman/result/<articeName>_ByteAnalysis.graphml	(Enthält die Byte.Analyse Revisionsseite; Präsentation der Informationen in GraphMl-Syntax;
                                                         Geeignet zum Einspeisen  in yEd - Applikation)
 
 ./CompHuman/result/<articeName>_ByteAnalysis.xml		(Enthält die Byte.Analyse Revisionsseite; Präsentation der Informationen in XML-Syntax;	
                                                         Geeignet zum Einspeisen  in yEd - Applikation (Umbenennen   in <..>.graphxml)
														 
														 

___
Visualisierung der Ergebnisse (yEd: https://www.yworks.com/downloads)			
Nach erfolgreicher Generierung der Dateien, können diese mittels yEd visualisiert werden. Bei der Konfiguration kann der User nach belieben verfahren. 
Da die vordefinierten Grundeinstellung minimal sind(tatsächliche Node-Formen etc.)User ist selbstbestimmt bei Gestaltung der Visualisierung (generierte Daten). 
Zur Unterstützung wird jedoch unter ./res/CHTask3_yEd_Config.cnfx eine 
Mapper Konfigdatei von bzw. für yEd- Visualisierungen bereitgestellt.
Vorgehen: 
	1)	yEd Downloaden (siehe Link)
	2)	Programm ausführen.
	3)	Konfig-Datei für den Mapper importieren.
	4)	xxx.graphml Datei importieren.
	5)	Unter den Reiter Bearbeiten auf Eigenschaften-Abbildung klicken
	6)	Alles auswählen und auf anwenden.
	7)	Anschließend auf einem beliebigen Layout unter Reiter Layout, klicken. (Empfohlenes Layout: <Kreisförmig>)
											 
		
___
S.Analyse Vs. Byte.Analyse		



	
S.Analyse steht simpleRanking Analyse ->

	a) Content-Anreicherung(positiv)		– Aktionswert +1  || 	User ist im Konsens mit dem Autor.
	b) Content-Vernichtung(negativ/Delete)  – Aktionswert -1  || 	User ist nicht im Konsens mit dem vorherigen Content bzw. dem Autor.
	c) Content-Manipulation(neutral) 		– Aktionswert 0.1 || 	Anzahl an Artikelbytes unverändert.  
																    User hat lediglich Rechtschreibung und/oder Grammatik verbessert. 
																    Insgesamt positive Stimmung auf Content bzw. Autor. Beachte Sie den Hinweis am Ende der Sektion.

Zusammenfassung: 
Mapping der Bytezahlen gemäß dem Vorzeichen der Bytezahl-veränderung auf {-1,0.1,1}

ByteAnalyse -
Mapping analog zu S.Analyse. Unterschied besteht durch das Mapping der Aktionswerten. 
Aktionswerten stimmen mit der tatsächlichen Bytezahl-veränderung überein. 
Bei 0 Byte Veränderung, wird der Aktionswert 0.1 vergeben.



Fazit: 
S.Analyse bietet eine gute abstrakte Übersicht der Beziehungen. 
Byte-Analyse hingegen bietet genauere Informationsauskunft, allerdings auf Kosten der Übersichtlichkeit.

Hinweise: 
+ 	yEd bildet beim Importieren die 0.1 auf 1.0 automatisch ab. -> Kein Problem: 
	(1) Grundstimmung trotzdem positiv. 
	(2) original Daten bleiben durch <…>.xml erhalten. 
+ 	Byte.-/ S.-Analyse enthalten Beide, gemäß des Aktionswerte-Mappings, die jeweiligen Kanten-Gewichte.
+ 	S.Analyse: Werte werden um den Faktor 10 erhöht. (Visualisierungsgrund)
+ 	Byte.Analyse: Werte werden um den Faktor 10 erniedrigt. (Visualisierungsgrund)
++ 	Beide Verfahren bedienen sich der chronologischen, sequenziellen Analyse des Edit- Netzwerkes.



___		
Ausgaben von Beispielaufrufe:
Beispiel-Ausgaben des Programms sind im Ordner: ./bsp zu finden.

Hinweis: Daten mit einer Kennung yEd zeigen an, dass diese durch Bearbeiten im yEd Programm entstanden sind.

___
Legende des Graphen:

Knoten-Farbe                       - entsprechend den Vorgaben
Knoten-Form 					   - entsprechend den Vorgaben

Zusätlich:
(LIGHTPINK)      Knoten(Umrandung) - Zeigt an, dass dieser User der initial-Autor ist.
(LIGHTSKYBLUE)   Knoten(Umrandung) - Zeigt an, dass es sich um den chronologisch Letzten User bzw. Contentprovider handelt.
(DARKRED) 		 Knoten(Umrandung) - Zeigt an, dass User sowohl init. User als auch chronlogisch letzter User ist.


___
Getestet mit:
https://de.wikipedia.org/wiki/Lindentunnel 		-- ./bsp/lindentunnel/.
https://de.wikipedia.org/wiki/Kleiner_Rohrplan  -- ./bsp/kleinerRohrplan/.

yEd-Version - V 3.16.2.1


___
Allgemeiner Hinweis:
1) Programm ist nur für die deutsche Wikipedia konzipiert. Keine Angabe zum Progammoutput für anderssprachige Wikipedia-Artikel.

___
Code
Der Entrypoint des Programms ist gegeben durch: comphuman.task3.editNetwork/CH_TaskRunner3.java gegeben.

___
Source-Code:
Git Release (CHTask3): https://github.com/FutureApp/TextTech1/releases/tag/CHTask3
