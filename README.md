# FRePL



Komanda: 

Karolis Žukauskas IFF-0/9

Simas Markevičius IFF-0/9

Vilhelmas Stankevičius IFF-0/9




Pasirinkti darbo įrankiai

Kompiliatoriaus kalba: C++

Kompiliatoriaus kompiliatorius: GCC



Bendras kalbos aprašymas:

Pavadinimas: FRePL (File Read Programming Language)
Bendra kalbos idėja: Galima kodo nuskaityti kintamuosius tiesiai iš failo
Palaikomos struktūros: sąlyginiai sakiniai, ciklai, kintamieji, funkcijos
Palaikomi duomenų tipai: char, number, bool, array, struct, array
Tipų tikrinimas: yra, sukuriant kintamąjį nustatomas jo tipas, kurio vėliau negalima keisti




Palaikomų kalbos konstrukcijų pavyzdžiai:

Komentarai:
	Vienos eilutės: // Čia yra vienos eilutės komentaras
	Keleto eilučių:  /*  Čia yra keleto
      eilučių komentaras */

sąlyginiai sakiniai, ciklai, kintamieji, funkcijos
Sąlyginiai sakiniai: 
If sąlygos sakiniai aprašomi pradedant žodžiu “if” ir padedant skliaustelius po žodžio, kur tarp skliaustelių įrašoma sąlyga, jei atitinka sąlygą - grąžiname “true” ir vykdome kodą esantį sakinio kodo bloke. Neatitikus sąlygos grąžiname “false” ir praleidžiame kodo bloką po tuo sakiniu. 
Pavyzdys:
if(a > 5)
{
// Kūnas
}
Ciklai:
For:
Ciklai aprašomi pradedant žodžiu “for” ir padedant skliaustelius po žodžio, kur tarp skliaustelių įrašoma sąlyga, kuriai sugrąžinus “true” bus vis dar vykdomas ciklas. Sąlygai sugrąžinus “false” ciklas nebevykdomas. Viduje ciklo (galvoje) galima bus įrašyti kintamąjį bei jį deklaruoti ir įrašyti jo kitimą per kiekvieną iteraciją. Kūnas kaip ir “if” sąlyginiame sakinyje pažymimas tarp ‘{‘ ‘}’ ženklų.
Pavyzdys:
for(int i = 0; i < 10; i++) 	// Galva
{
// Kūnas
}
While:
Labai panašūs į “for” ciklus, kur šiuo atveju yra tik ciklo sąlyga įrašoma į ciklą. Šis yra paprasčiau skaitomas, tačiau sąlygos iteracija turi būtinai vykti pačiame cikle, kad nesigautų begalinis ciklas. While ciklus aprašysime pradedant žodžiu “while”, tada skliausteliai iš abiejų pusių ‘(‘ ‘)’ tarp kurių yra įrašoma sąlyga, kuri turi būti patenkinta, kad ciklas vyktų.
while(a < 10)
{
// Kūnas, čia norime standartiškai pakeisti a vertę
}
Kintamieji:
Kintamieji gali turėti skirtingus tipus, kad saugotų skirtingą informaciją. Kintamieji deklaruojami aprašant tipą, tada pavadinimas ir iš karto galima jį prilyginti atitinkamo tipo naujai vertei arba seniau deklaruotam kintamam su įrašyta verte. 
Pavyzdys:
int Count = 5;
Funkcijos:
Funkcijos turi pavadinimą, grąžinimo tipą ir argumentus. Funkcija gali priimti ir grąžinti kelias reikšmes. Jei funkcija yra grąžinimo tipo, tai ji privalo grąžinti vertę. Pati funkcija yra iškviečiama parašant jos pavadinimą ir įrašant argumentus, o aprašoma tokiu pavyzdžiu:
grąžinamasTipas funkcijosPavadinimas(argumentų sąrašas)
{
// funkcijos kūnas;
}
Struct:
Struct yra duomenų struktūra, kuri gali laikyti savyje skirtingų tipų kintamuosius (bet ne funkcijas). Pradedame rašyti raktiniu žodžiu “struct”, tada iš didžiosios rašoma pavadinimą ir ženklus ‘{‘ ‘}’ atidaryti bei uždaryti struct. Pavyzdžiui su skirtingais duomenimis, kad būtų aiškiau:
struct Student
{
	char name[20];
	char surname[20];
char group[10];
	Int age;
}

Array:
Array (liet. masyvas) yra kolekcija to pačio tipo, kur atmintis iš karto yra rezervuojama deklaravus, atmintis yra statinė ir negali būti pakeistą masyvo dydis, tik viduje esančios vertės gali būti prilyginamos naujoms vertėms to pačio tipo. Aprašome taip: kintamojoTipas pavadinimas[kiekis];
Pavyzdžiui:
int Grades[15];  


Unikali savybė:

Leidžia nuskaityti kintamuosius iš failų štai taip:

Savybės naudojimo pavyzdys:

int Example(int variable1, int variable2) {
	int product fetch “product.csv” field 4 delimedby ‘,’;
 	product =  product*variable1*variable2;
	return product;	
}

int result = Example(3,2);
int res = fetch “data.txt” field 4 delimedby ‘\n’ 

