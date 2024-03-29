DROP DATABASE IF EXISTS GameCenter;
CREATE DATABASE GameCenter;
USE GameCenter;

CREATE TABLE Prodotto
(
    CodSeriale varchar(20) NOT NULL,
    Nome varchar(50) NOT NULL,
    Prezzo numeric(10,2) NOT NULL,
    DataUscita date NOT NULL,
    DescrizioneRidotta text NOT NULL,
    DescrizioneCompleta text NOT NULL,
    Immagine varchar(100),
    FlagTipologia bit NOT NULL, --  0 = "videogioco" 1 = "hardware/accessorio"
    FlagVisibita bit NOT NULL, --  0 = "non visibile" 1 = "visibile"
    PRIMARY KEY(CodSeriale)
);

CREATE TABLE Genere
(
    NomeGenere varchar(50) NOT NULL,
    PRIMARY KEY(NomeGenere)
);

CREATE TABLE PEGI
(
    CodPEGI int NOT NULL,
    PRIMARY KEY(CodPEGI)
);

CREATE TABLE Caratteristiche
(
    CodSeriale varchar(20) NOT NULL,
    NomeGenere varchar(50) NOT NULL,
    CodPEGI int NOT NULL,
    FOREIGN KEY(CodSeriale) REFERENCES Prodotto(CodSeriale) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(NomeGenere) REFERENCES Genere(NomeGenere) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(CodPEGI) REFERENCES PEGI(CodPEGI) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Piattaforma
(
    NomePiattaforma varchar(50) NOT NULL,
    PRIMARY KEY(NomePiattaforma)
);

CREATE TABLE Formato
(
    TipoFormato varchar(50) NOT NULL,
    PRIMARY KEY(TipoFormato)
);


CREATE TABLE Disponibilita
(
     QuantitaDisponibile int NOT NULL,
     CodSeriale varchar(20) NOT NULL,
     NomePiattaforma varchar(50) NOT NULL,
     TipoFormato varchar(50) NOT NULL,
     FOREIGN KEY(CodSeriale) REFERENCES Prodotto(CodSeriale) ON UPDATE cascade ON DELETE cascade,
     FOREIGN KEY(NomePiattaforma) REFERENCES Piattaforma(NomePiattaforma) ON UPDATE cascade ON DELETE cascade,
     FOREIGN KEY(TipoFormato) REFERENCES Formato(TipoFormato) ON UPDATE cascade ON DELETE cascade   
);


CREATE TABLE Utente
(
    Email varchar(50) NOT NULL,
    PasswordUtente varchar(100) NOT NULL,
    PuntiFedelta int NOT NULL,
    Tipo bit NOT NULL, --  0 = "admin" 1 = "cliente"
    PRIMARY KEY (Email)
);

CREATE TABLE DatiSensibileUtente
(
    CodiceFiscale char(16) NOT NULL,
    Nome varchar(50) NOT NULL,
    Cognome varchar(50) NOT NULL,
	Immagine varchar(100),
    CAP int NOT NULL,
    Via varchar(20) NOT NULL,
    Civico int NOT NULL,
    Citta varchar(50) NOT NULL,
    Provincia varchar(50) NOT NULL,
    NumeroTelefono varchar(20) NOT NULL,
    Email varchar(50) NOT NULL,
    PRIMARY KEY(CodiceFiscale),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE MetodoPagamento
(
    NumeroCarta char(16) NOT NULL,
    TitolareCarta varchar(50) NOT NULL,
    Scadenza date NOT NULL,
    Email varchar(50) NOT NULL,
    PRIMARY KEY(NumeroCarta),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE IndirizziSpedizione
(
	CodIndirizzo int NOT NULL AUTO_INCREMENT,
    Nome varchar(50) NOT NULL,
    Cognome varchar(50) NOT NULL,
    CAP int NOT NULL,
    Via varchar(20) NOT NULL,
    Civico int NOT NULL,
    Citta varchar(50) NOT NULL,
    Provincia varchar(50) NOT NULL,
    NumeroTelefono varchar(20) NOT NULL,
    Email varchar(50) NOT NULL,
    PRIMARY KEY(CodIndirizzo),
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE StatoOrdine
(
    Stato varchar(50) NOT NULL,
    PRIMARY KEY(Stato)
);

CREATE TABLE Ordine
(
    CodOrdine int NOT NULL AUTO_INCREMENT,
    Sconto float,
    DataAcquisto date NOT NULL,
    Fattura varchar(100),
    PrezzoTotale float,
    StatoOrdine varchar(30) NOT NULL,
    Email varchar(50) NOT NULL,
    PRIMARY KEY(CodOrdine),
    FOREIGN KEY(StatoOrdine) REFERENCES StatoOrdine(Stato) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Include
(
    Quantita int,
    CodSeriale varchar(20) NOT NULL,
    CodOrdine int,
    PrezzoMomento float,
    Piattaforma varchar(50) NOT NULL,
    FOREIGN KEY(CodSeriale) REFERENCES Prodotto(CodSeriale) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(CodOrdine) REFERENCES Ordine(CodOrdine) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Comprende
(
	CodOrdine int NOT NULL,
    CodIndirizzo int NOT NULL,
    NumeroCarta char(16) NOT NULL,
    FOREIGN KEY(CodOrdine) REFERENCES Ordine(CodOrdine) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(CodIndirizzo) REFERENCES IndirizziSpedizione(CodIndirizzo) ON UPDATE cascade ON DELETE cascade,
	FOREIGN KEY(NumeroCarta) REFERENCES MetodoPagamento(NumeroCarta) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Recensione
(
    CodRecensione int NOT NULL AUTO_INCREMENT,
    Descrizione text NOT NULL,
    Valutazione int NOT NULL,
    CodSeriale varchar(20) NOT NULL,
    Email varchar(50) NOT NULL,
    PRIMARY KEY(CodRecensione),
    FOREIGN KEY(CodSeriale) REFERENCES Prodotto(CodSeriale) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(Email) REFERENCES Utente(Email) ON UPDATE cascade ON DELETE cascade
);

SET @GTAcode := 'A564DGR4BU54';
SET @FIFA23code := 'CD9D8G352NC5JA';
SET @TLOU2code := 'CUR3N43VS23YA';
SET @TLOURcode := 'CGRD2SDW2FYR';
SET @CYBERPUNK77code := '45HY33DD2SE24';
SET @MAFIA3code := 'BYD32A5KYNC4E3';
SET @PS5code := 'ACX5Z6DGXMUJR454';
SET @XBOXXcode := 'FBDTRNCF54';
SET @DUALSENSEcode := 'DVCH5342SDR';
SET @RTX4090code := 'AMREDHZOCDS';
SET @PS4code := 'GHYFVRSTEV';
SET @NITROKG2code := 'ANTPRCHRTL748D';
SET @RX6700XTcode := 'LRTE54HFJFI33DD';
SET @G305code := 'RTD342SERV78';
SET @DEADSPACEcode := 'GRT43SDC46';
SET @MORTALKOMBAT11code := 'MLI897HY5FF';
SET @HALO3code := 'BGT78UY5DDS';
SET @THESIMS4code := 'LMR56FDS2321AA';
SET @FORTNITEcode := 'NCBDGG55DSSWE';




INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@FORTNITEcode,'Fortnite: Salva il mondo', '9.99', '2017-07-25','Il magico mondo di Fortnite si amplica conuna campagna online, dove sfiderai ondate di nemici. Sarai in grado di salvati? Ricorda la tempesta è dietro l angolo ','Fortnite: Salva il Mondo è una modalità di gioco cooperativa e a pagamento del popolare videogioco Fortnite, sviluppato da Epic Games. In questo contesto, i giocatori si uniscono in squadre per affrontare ondate di nemici controllati dall intelligenza artificiale. Il principale obiettivo del gioco è sopravvivere, costruire difese e proteggere gli oggetti essenziali dall attacco di creature mostruose note come "Tempesta". I giocatori possono scegliere tra vari personaggi con abilità uniche e potenziabili, che consentono loro di svolgere ruoli specifici nel team. Utilizzando risorse raccolte nell ambiente di gioco, come legno, pietra e metallo, possono costruire strutture difensive per respingere le tempeste e gli avversari. Il gioco presenta una serie di missioni e sfide, ognuna con obiettivi specifici, che portano i giocatori attraverso una storia avvincente in un mondo post-apocalittico. Completando missioni e sfide, i giocatori guadagnano ricompense come esperienza, materiali di costruzione, armi migliorate e nuovi eroi. Fortnite: Salva il Mondo offre un esperienza di gioco diversa rispetto alla modalità più popolare e gratuita di Fortnite: Battle Royale, incentrata sulla competizione tra giocatori. Questa modalità cooperativa permette agli appassionati di cooperare con amici o giocatori di tutto il mondo per affrontare insieme le sfide di un ambiente ostile e misterioso.', 'Fortnite.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@THESIMS4code,'The Sims 4', '9.99', '2014-09-02','Quarto capitolo della amata saga dei "sims", affronta nuove sfide e tantissimi nuovi scenari in questo magnifico gioco di simulazione di vita','The Sims 4 è un videogioco di simulazione di vita sviluppato da Maxis e pubblicato da Electronic Arts. È il quarto capitolo della celebre serie "The Sims", in cui i giocatori possono creare, personalizzare e controllare la vita dei propri personaggi virtuali, noti come "Sims". Nel gioco, i giocatori hanno il potere di costruire e arredare case, creare Sims unici selezionando caratteristiche, personalità, abiti e obiettivi di vita. I Sims possono sviluppare relazioni, socializzare, cercare lavoro e perseguire varie carriere o hobby. Il gioco offre una vasta gamma di attività e interazioni, consentendo ai giocatori di seguire le vite dei Sims attraverso diversi momenti della giornata, come il lavoro, il tempo libero e le relazioni sociali. La gestione delle necessità fisiche e emotive dei Sims, come fame, sonno, divertimento e salute mentale, è fondamentale per mantenerli felici e soddisfatti. The Sims 4 è noto per il suo approccio creativo e la libertà di esplorare un mondo virtuale in cui ogni azione dei giocatori può avere conseguenze sulle vite dei Sims. Il gioco è stato continuamente supportato con espansioni, pacchetti di contenuti e aggiornamenti, offrendo sempre nuove sfide e possibilità per creare storie uniche all interno di questa simulazione di vita coinvolgente.', 'theSims4.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@HALO3code,'HALO 3', '14.90', '2007-09-25','Master Chief è un supersoldato impegnato in una guerra interstellare, saprai indossare i suoi panni e vincere la guerra?','La storia di Halo 3 si concentra su una guerra interstellare in corso nel XXVI secolo tra l umanità, un alleanza di razze aliene nota come Covenant e dei parassiti alieni conosciuti con il nome di Flood. Il giocatore veste i panni di Master Chief, un supersoldato dotato di una solida armatura con cui combatte i Covenant e i Flood. Il gioco presenta veicoli, armi ed elementi di gioco non presenti nei titoli precedenti della serie, oltre a concedere la possibilità di registrare filmati di gioco, condividere dei file multimediali e usufruire dell editor di mappe detto Fucina, uno strumento che consente al giocatore di apportare modifiche alle mappe del multigiocatore.', 'HALO3.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@MORTALKOMBAT11code,'Mortal Kombat 11', '9.99', '2019-04-19','l undicesimo capitolo della saga Mortal Kombat, unico nel suo genere un picchiaduro forte e violento.','Mortal Kombat 11 è un picchiaduro sviluppato da NetherRealm Studios e pubblicato da Warner Bros. Interactive Entertainment . È l undicesimo capitolo principale della serie Mortal Kombat e un sequel di Mortal Kombat X (2015). Il gioco è stato annunciato ai The Game Awards 2018 ed è stato rilasciato in Nord America ed Europa il 23 aprile 2019. Al momento del rilascio, le versioni per console di Mortal Kombat 11 hanno ricevuto recensioni molto favorevoli, che hanno elogiato il gameplay, la storia, la grafica e il netcode migliorato, ma hanno ricevuto critiche per la presenza di microtransazioni e l eccessiva dipendenza dal grinding.', 'MortalKombat.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@DEADSPACEcode,'Dead Space', '49.98', '2008-10-14','la nuova avventura di Isaac Clarke in un nuovo sparatutto horros, sei pronto alla sfida?','Il ritorno di un classico sparattutto horror fantascientifico, completamente ricostruito da zero, con un realismo visivo senza precedenti e un audio atmosferico 3D. Quella che doveva essere una missione di riparazione ordinaria per l ingegnere Isaac Clarke e per l equipaggio della USG Kellion si trasforma rapidamente in una battaglia per la sopravvivenza mentre la verità dietro agli orrori a bordo della nave inizia a svelarsi.', 'DeadSpace.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@G305code,'Logitech G305 LIGHTSPEED', '42.99', '2018-06-01','Mouse economico ma di grande affidabilita. Possiede una gramde autonomia e risuta essere estremamente sensibile.','Sensore Gaming HERO: il sensore ottico per mouse gaming offre fino a 10 volte più efficienza energetica rispetto ad altri mouse gaming grazie all IPS 400 e alla sensibilità fino a 12.000 DPI. La tecnologia LIGHTSPEED Wireless ultraveloce offre un esperienza di gioco senza lag, grazie a una velocità di aggiornamento superveloce di 1 ms', 'G305.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@NITROKG2code,'Acer Nitro KG272S Monitor 27"', '189.90', '2021-04-14','Il bellissimo monitor dell Asus con protezione degli occhi e ottimo refresh rate','La tecnologia AMD FreeSync Premium elimina le interruzioni e le interferenze durante il gameplay, sincronizzando il refresh rate del monitor da gaming con il frame rate del computer per una grafica fluida e reattiva. Proteggi gli occhi dall affaticamento grazie ad Acer BlueLightShield e Flickerless. Affronta lunghe sessioni di gioco sul tuo computer da gaming senza il fastidio dei riflessi del monitor con le tecnologie Acer ComfyView e Low Dimming.', 'AcerNitro.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@RX6700XTcode,'ASUS DUAL AMD Radeon RX 6700 XT', '394.99', '2021-03-18','La bellissimi scheda video casa ASUS unica nel suo genere, la miglione nei test prestazionali ','Le ventole con design Axial-tech abbinate ai doppi cuscinetti a sfera offrono maggior durata e meno turbolenze, aumentando la dispersione del calore e conseguentemente anche le prestazioni. L’avanzato controller ferma le ventole quando la temperatura interna della CPU scende sotto i 55 gradi. La piastra posteriore in metallo e la staffa di montaggio in acciaio inossidabile migliorano durata e resistenza alla torsione.', 'AsusRadeon.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@GTAcode,'Grand Theft Auto 5', '14.99', '2013-09-17','Si tratta del quinto capitolo della saga Grand Theft Auto, racconta la storia di tre diversi personaggi molto diversi tra loro la cui vita si intreccia ai fini di diventare ricchi sfondati. Il gioco è ambientato all interno dello Stato immaginario di San Andreas e offre al giocatore la possibilità di muoversi liberamente nella città immaginaria di Los Santos. Il gioco possiede anche una modalità online.','Los Santos: un enorme e soleggiata metropoli piena di sedicenti guru, attricette e celebrità sul viale del tramonto. Un tempo era l invidia del mondo occidentale, ma ora è costretta ad arrangiarsi per restare a galla in un epoca di incertezza economica e TV via cavo da quattro soldi. In mezzo a tutto questo, tre criminali molto diversi tra loro si danno da fare per sopravvivere e realizzarsi, ognuno a modo suo: l ambizioso Franklin è a caccia di soldi e di opportunità; Michael, un ex criminale professionista, sta facendo i conti con una "pensione" meno rosea del previsto; e Trevor, un maniaco violento, pensa solo a farsi e al prossimo, grande colpo. I tre non hanno altra scelta: decidono di rischiare il tutto per tutto in una serie di colpi audaci e pericolosi che potrebbero sistemarli per la vita. Con il più grande, il più dinamico e il più vario mondo di gioco mai creato, Grand Theft Auto V mescola sapientemente storia e gioco in modi del tutto nuovi, con i giocatori intenti a passare da una vita all altra dei tre protagonisti e a vivere lintreccio delle loro avventure. Ritornano tutti gli elementi distintivi tipici della serie, compresa l incredibile attenzione ai dettagli e il pungente umorismo di Grand Theft Auto, oltre che un nuovo e ambizioso approccio al mondo di gioco in multiplayer.', 'GTA5.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@FIFA23code,'FIFA 23', '29.99', '2022-09-22', 'Il nuovo capitolo della saga FIFA, il bellissimo simulatore videoludico di calcio. FIFA permette di sfidarti ogni giorni in gare sempre più difficili, puoi sfidare la CPU o un tuo amico, sia offline che online', 'EA SPORTS FIFA 23 porta l azione e il realismo del gioco a un nuovo livello, grazie ai progressi della tecnologia HyperMotion2 che può contare ora sul doppio delle movenze di atleti reali per offrire animazioni ancora più realistiche in ogni partita. Gioca i importanti tornei calcistici, con la FIFA World Cup maschile e femminile in arrivo su FIFA 23 durante la stagione, scendi in campo per la prima volta con i club femminili, con animazioni dedicate ottenute tramite la tecnologia HyperMotion2, e sfrutta le funzionalità cross-play per rendere semplice giocare con gli amici. Goditi un ottimo modo di giocare e creare la rosa dei tuoi sogni in FIFA Ultimate Team con i Momenti FUT e un sistema di intesa rivisto, realizza i tuoi sogni calcistici nella modalità Carriera mentre definisci la tua personalità come giocatore e vesti i panni di alcuni degli allenatori famosi al mondo per replicarne i successi in panchina. Esprimi tutta la tua personalità in campo con VOLTA FOOTBALL e Pro Club, con nuovi livelli di personalizzazione e un gameplay migliorato nelle strade e negli stadi. Comunque tu scelga di giocare, divertiti con oltre 19.000 giocatori, 700 squadre, oltre 100 stadi e 30 tornei, comprese la UEFA Champions League, la Premier League e le nuove Barclays FA Womens Super League e D1 Arkema francese, con un livello di realismo senza precedenti in FIFA 23','FIFA23.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@TLOU2code,'The Last Of Us Part 2', '19.99', '2020-06-19', 'Secondo capitolo della saga di The Last Of Us, esso riprende le vicende di Joel ed Alli nel mondo apocalittico causato dall epidemia generata dal fungo Cordyceps.', 'The Last of Us Part II è ambientato 5 anni dopo gli eventi del primo gioco, 25 anni dopo lo scoppio della pandemia di Cordyceps. Torneranno sia Ellie, che ora ha 19 anni, sia l ormai ultracinquantenne Joel, ma sarà la ragazza la protagonista principale e giocabile. La storia sarà divisa in quattro capitoli stagionali, a partire dalla Contea di Jackson, nel Wyoming, durante i mesi invernali. I due, stabilendosi nell insediamento di Tommy, vivono in relativa pace all interno della fiorente comunità. Mentre era lì, Ellie è persino riuscita a stringere amicizie con Dina e Jesse. Tuttavia, bisogna affrontare continue minacce da parte di infetti e altri sopravvissuti ostili. Quando un tragico evento sconvolge la tranquillità della sua vita, Ellie intraprende un viaggio alla ricerca della vendetta per sconfiggere un culto misterioso e pericoloso: i Serafiti. Mentre caccia i responsabili uno ad uno, si confronta con le traumatizzanti conseguenze fisiche ed emotive delle sue azioni. Ellie è piena di odio, che sarà un tema fondante della storia. Il gioco sarà principalmente ambientato a Seattle, nello Stato di Washington','TLOU2.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@TLOURcode,'The Last Of Us Remastered', '9.99', '2013-06-14', 'Remastered del primo capitolo della saga di The Last Of Us, esso racconta le vicende di Joel ed Alli nel mondo apocalittico causato dall epidemia generata dal fungo Cordyceps.', 'Nel 2013 un epidemia generata dal fungo Cordyceps mutato si scatena negli Stati Uniti d America, trasformando gli esseri umani in creature aggressive. Nei sobborghi di Austin Joel Miller sfugge al caos insieme a suo fratello Tommy e alla figlia tredicenne Sarah. Tuttavia, durante la fuga, Sarah viene colpita da un soldato e muore tra le braccia di Joel, che rimarrà inevitabilmente e profondamente segnato dall’accaduto. Vent anni dopo, nel 2033, la civiltà è stata decimata dall infezione. I sopravvissuti risiedono in zone di quarantena sotto stretta sorveglianza, in insediamenti indipendenti o in gruppi nomadi. Joel lavora come contrabbandiere insieme a Tess, la sua compagna in affari, nella zona di quarantena di Boston, nel Massachusetts. Marlene, il lidere di una milizia ribelle detta "Le Luci" affida loro un carico molto speciale e cioè di trasportare Ellie la prima ragazza immune all infezione in uno ospedale, sarà lei la persona che salverà l umanità?','TLOU1.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@CYBERPUNK77code,'Cyberpunk 2077', '34.99', '2020-12-10', 'Ambientato nel 2077 esso racconta la storia di V un cittadino di Night City che è una magalopoli comandata dalle corporazioni. V dovrà cercare di sopravvivere in questa città facendo lavori non sempre legali, ma improvvisamente uno strano avvenimento cambierà la sua vita.', 'Cyberpunk 2077 è un avventura a mondo aperto ambientata a Night City, una megalopoli ossessionata dal potere, dalla moda e dalle modifiche cibernetiche. Vestirai i panni di V, un mercenario fuorilegge alla ricerca di un impianto unico in grado di conferire l immortalità. Potrai personalizzare il cyberware, le abilità e lo stile di gioco del tuo personaggio ed esplorare un immensa città dove ogni scelta che farai plasmerà la storia e il mondo intorno a te Diventa un cyberpunk, un mercenario urbano dotato di potenziamenti cibernetici, e crea la tua leggenda sulle strade di Night City. Entra nell immenso mondo aperto di Night City, un luogo che definisce nuovi standard in termini di grafica, complessità e profondità. Accetta il lavoro più rischioso della tua vita e dai la caccia al prototipo di un impianto in grado di conferire l immortalità.','cyberpunk2077.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@MAFIA3code,'Mafia 3', '14.99', '2016-10-07', 'Terzo capitolo della saga MAFIA. Dopo anni di servizio in Vietnam, Lincoln Clay vede sterminare la sua famiglia adottiva, la mafia nera, dai gangster italiani. Lincoln costruisce una nuova famiglia sulle ceneri della precedente e va in cerca di vendetta', 'Siamo nel 1968 e dopo anni passati in Vietnam, Lincoln Clay ha scoperto una verità: famiglia non è con chi sei nato, è per chi muori. Tornato a casa a New Bordeaux, Lincoln vuole lasciarsi alle spalle il suo passato criminale. Ma quando la sua famiglia adottiva, la mafia nera, viene tradita e sterminata dai gangster italiani, Lincoln intraprende un cammino di redenzione e vendetta per eliminare in stile militare i malavitosi responsabili. Tra violente sparatorie, combattimenti a mani nude e frenetici inseguimenti in auto, servirà tutta la sua conoscenza della vita da strada. Sporcandosi le mani in compagnia della gente giusta, però, è possibile dominare la malavita, dopo aver affrontato qualche scelta difficile. La vendetta a modo tuo: Scegli il tuo stile di gioco preferito, dalla forza bruta ad armi spianate alle tattiche da cacciatore, per abbattere la mafia italiana','Mafia3.jpg',0,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@PS5code,'Playstation 5', '550.00', '2020-11-12','La quinta generazione della console di casa Sony, essa offre la miglior esperienza immaginabile grazie all ausilio del nuovissimo SSD ultra performande e una potenza computazionale senza precedenti ', 'La PlayStation 5 utilizza un processore Zen 2 di AMD con 8 core a una frequenza variabile limitata a 3,5 GHz. La GPU è un sistema su chip (SoC) personalizzato, basato su RDNA 2 di AMD, con 36 unità di calcolo funzionanti a frequenza variabile, limitato a 2,23 GHz e capace di 10,28 TFLOPS. Sia la CPU che la GPU sono monitorate da uno speciale sistema di boost con tecnologia AMD SmartShift in grado di regolare la frequenza di questi sistemi in base alle attività correnti di entrambi i chip, per indirizzare la potenza assorbita costante ideale e un modello di prestazioni SoC. La GPU include il supporto per l accelerazione hardware del rendering ray tracing, che consente la grafica ray tracing in tempo reale. L hardware include una nuova tecnologia audio chiamata Tempest Engine basata sulla tecnologia AMD GPU, che consente non solo di tenere conto di centinaia di sorgenti sonore all interno di un gioco nella produzione di uscita audio rispetto alle 50 di PlayStation 4, ma anche di come tale audio viene presentato in base al dispositivo e alle preferenze dell utente finale. L unità includerà 16 GB di SDRAM GDDR6 con una larghezza di banda di 448 GB/s. Una soluzione di archiviazione SSD personalizzata è stata progettata per PlayStation 5 in modo da fornire dati input/output più veloci per tempi di caricamento rapidi e larghezza di banda maggiore per rendere i giochi più coinvolgenti, oltre a supportare il flusso di contenuti richiesto dal disco per una risoluzione 8K (UHD). Il sistema base offrirà un SSD da 825 GB collegato tramite un interfaccia a 12 canali al sistema principale, raggiungendo una velocità di trasferimento di 5,5 GB/s non compressa e tra 8 e 9 GB/s con la compressione usando il protocollo Oodle Kraken di RAD Game Tools. La dimensione atipica dell unità è risultata ottimale per il percorso a 12 canali per il sistema piuttosto che per le unità più tipiche da 500 GB o 1 TB. L archiviazione diretta per i giochi sarà espandibile tramite una porta NVM Express (NVMe) che supporta i formati M.1 o M.2, mentre l archiviazione aggiuntiva può essere resa disponibile tramite unità compatibili con USB. Il sistema include un unità ottica Ultra HD Blu-ray compatibile con risoluzioni 4K e 8K. Sebbene l installazione del gioco da un disco sia obbligatoria per sfruttare l SSD, l utente avrà un controllo accurato di quanto desidera installare, come installare solo i componenti multiplayer di un gioco','PS5.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@XBOXXcode,'XBOX Series X', '499.00', '2020-11-10', 'L ultima generazione della console della Microsoft, la più potente del mercato con una potenza che supera i 12 teraflops.', 'Arriva la nuova Xbox Series X, la console Xbox più veloce e potente di sempre. Gioca a migliaia di titoli di quattro generazioni di console: tutti i giochi hanno un aspetto e una riproduzione ottimale su Xbox Series X. Il cuore di Series X è Xbox Velocity Architecture, che abbina una SSD personalizzata con software integrato per un gameplay più veloce e semplificato, con tempi di caricamento notevolmente ridotti. Con Quick Resume passi senza problemi e in un lampo da un gioco all’altro. Esplora nuovi e ricchi mondi e goditi l azione come mai prima d ora con l impareggiabile potenza di elaborazione grafica di 12 teraflop. Divertiti con giochi 4K fino a 120 fotogrammi al secondo, suono spaziale 3D avanzato e altro ancora. Inizia con una libreria istantanea di più di 100 giochi di alta qualità, inclusi tutti i nuovi titoli Xbox Game Studios nel giorno del lancio, con Xbox Game Pass Ultimate.','XboxSeriesX.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@DUALSENSEcode,'DualSense', '55.00', '2020-11-12','Il controller per la nuovissima PlayStation 5 estremamente ergonomico, un design ultra moderno ma soprattuto le funzionalità della vibrazione con feedback aptico e dei grilletti adattivi dinamici che permettono al videogiocatore una maggior immersione nel proprio gioco preferito', 'Il nuovo controller per PlayStation 5 è realizzato per regalarti un’esperienza di gioco unica! Innovativo e tecnologicamente avanzato, il controller wireless DualSense è dotato di tutte le funzionalità di cui hai bisogno per sentirti il vero protagonista del gioco, come: Feedback aptico, Grilletti adattivi, Microfono integrato e ingresso cuffie, Tasto Crea, Funzionalità familiari del Dualshock®4, Sensore di movimento, Design iconico e confortevole, Batteria incorporata e ricarica tramite USB Type-C®4. A stupirti, poi, sarà anche il design: iconico, confortevole e compatto, per renderti il vero protagonista del gioco! Grazie alla tecnologia innovativa del controller per PlayStation 5 DualSense™, ti sembrerà di entrare nella finzione e di vivere l’avventura in prima persona. Con il feedback fisico, il coinvolgimento è assicurato: il joystick per PS5 reagisce alle tue azioni in gioco grazie ai doppi attuatori che sostituiscono i tradizionali motori di vibrazione. In questo modo, ogni sensazione viene simulata direttamente nelle tue mani: ti basterà impugnare il controller wireless DualSense™ per sentire le vere vibrazioni del rinculo delle diverse armi o dell’ambiente circostante. Inoltre, attraverso i grilletti adattivi avrai modo di sperimentare i livelli di forza e tensione ogni qual volta interagirai con l equipaggiamento e le scene del gioco. Che si tratti del lancio di una freccia d’arco o di arrestare improvvisamente la tua auto da corsa sul fango, con il controller PlayStation 5 ti sentirai fisicamente collegato alle azioni sullo schermo. Durante l’avventura, non dimenticare di condividere i successi con i tuoi amici! Il nuovo joypad PlayStation 5 ha il microfono integrato per chiacchierare direttamente con i compagni di gioco. Attivare o disattivare l acquisizione vocale, poi, è semplice e immediato: basta un click sul tasto Mute dedicato per silenziarti o urlare a gran voce!','Dualsense.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@RTX4090code,'Nvidia GeForce RTX 4090', '2155.00', '2022-11-16', 'NVIDIA GeForce RTX 4090 è la GPU GeForce definitiva. Si tratta di un enorme passo avanti in termini di prestazioni, efficienza e grafica basata su IA. Scopri il gaming ad altissime prestazioni, mondi virtuali incredibilmente dettagliati, produttività senza precedenti e nuovi modi di creare. Basata sull architettura NVIDIA Ada Lovelace e include 24 GB di memoria G6X per offrire l esperienza definitiva per giocatori e creativi.', 'Nvidia GeForce RTX 4090 è una delle schede video NVIDIA top di gamma ad essere basate sull architettura Ada Lovelace, introdotta sul mercato nel 2022. Costruita con tecnologia produttiva a 4nm, è in grado di assicurare frame al secondo medi molto elevati a tutte le risoluzioni video anche selezionando impostazioni qualitative molto spinte. Questa scheda implementa la terza generazione di architettura NVIDIA con supporto al Ray Tracing, tecnica grazie alla quale si ottiene un netto incremento della qualità l immagine grazie ad un calcolo più preciso delle sorgenti di illuminazione e di come queste si riflettono all interno delle scene dei videogiochi. Le prestazioni ottenibili con i giochi abilitando il Ray Tracing sono quindi ben più elevate di quanto concesso in precedenza con le schede GeForce RTX della famiglia 3000 basate su architettura di seconda generazione. Nvidia GeForce RTX 4090 è dotata di 24GB di memoria GDDR6X, su bus a 384bit con una frequenza di clock di 1325 MHz per una bandwidth complessiva di 1008 GB/s. La GPU AD 102 opera con una frequenza di base clock di 2230MHz, potendosi spingere sino a 2520MHz come frequenza di clock massima. La scheda ha un design a tre slot d ingombro sulla scheda madre e viene alimentata dal sistema con un collegamento a 1x16 pin. Il TDP è pari a 450W.','NvidiaGeForce4090.jpg',1,1);
INSERT INTO Prodotto(CodSeriale, Nome, Prezzo, DataUscita, DescrizioneRidotta, DescrizioneCompleta, Immagine, FlagTipologia, FlagVisibita)
VALUES (@PS4code,'PlayStation 4', '199.00', '2013-11-15', 'La quarta generazione della console di casa Sony, uscita nel lontano 2013 ma ancora oggi è estremamente utilizzata e ben supportata dalle varie software house che sviluppano videogiochi', 'La PlayStation 4 utilizza un unità di elaborazione accelerata (APU) sviluppata da AMD in collaborazione con Sony. Combina un unità di elaborazione centrale (CPU) e un unità di elaborazione grafica (GPU), nonché altri componenti come un controller di memoria e un decodificatore video . La CPU è composta da due moduli Jaguar quad-core da 28 nm per un totale di 8 core x86-64 , 7 dei quali sono disponibili per gli sviluppatori di giochi. La GPU è composta da 18 unità di calcolo per produrre una prestazione di picco teorica di 1,84 TFLOP . La memoria GDDR5 del sistema è in grado di funzionare a una frequenza di clock massima di 2,75 GHz (5500 MT/s) e ha una larghezza di banda di memoria massima di 176 GB/s.La console contiene 8 GB di memoria GDDR5 , 16 volte la quantità di RAM trovata nella PS3 e dovrebbe dare alla console una notevole longevità. Include anche chip personalizzati secondari che gestiscono le attività associate al download, al caricamento e al gameplay sociale. Queste attività possono essere gestite senza interruzioni in background durante il gioco o mentre il sistema è in modalità di sospensione. La console contiene anche un modulo audio, che può supportare la chat in-game e "un numero molto elevato" di flussi audio da utilizzare nel gioco.Tutti i modelli PlayStation 4 supportano i profili colore HDR ( High Dynamic Range ). La sua unità ottica di sola lettura è in grado di leggere dischi Blu-ray a velocità fino a tre volte superiori a quelle del suo predecessore La console è dotata di un modulo di decompressione hardware zlib al volo . Il modello PS4 originale supporta standard video fino a 1080p e 1080i , mentre il modello Pro supporta la risoluzione 4K . La console include un disco rigido da 500 gigabyte per ulteriore spazio di archiviazione, che può essere aggiornato dall utente. Software di sistema 4.50, rilasciato il 9 marzo 2017,consentito l uso di dischi rigidi USB esterni fino a 8 TB per ulteriore spazio di archiviazione. La PlayStation 4 è dotata di connettività Wi-Fi ed Ethernet , Bluetooth e due porte USB 3.0 .È inclusa anche una porta ausiliaria per il collegamento alla PlayStation Camera , una fotocamera digitale con rilevamento del movimento introdotta per la prima volta su PS3. Un auricolare mono, che può essere collegato al DualShock 4, è fornito in bundle con il sistema. Le opzioni di uscita audio/video includono TV HDMI e audio ottico S/PDIF . La console non dispone di un uscita audio/video analogica .La PS4 presenta una funzione " Modalità di riposo ". Ciò pone la console in uno stato di basso consumo consentendo agli utenti di riprendere immediatamente il gioco o l app una volta riattivata la console. La console è anche in grado di scaricare contenuti come giochi e aggiornamenti del sistema operativo mentre si trova in questo stato.','PS4.jpg',1,1);


INSERT INTO Genere(NomeGenere)
VALUES ('RPG'); 
INSERT INTO Genere(NomeGenere)
VALUES ('Sportivo');
INSERT INTO Genere(NomeGenere)
VALUES ('Sparattutto');
INSERT INTO Genere(NomeGenere)
VALUES ('Simulazione');
INSERT INTO Genere(NomeGenere)
VALUES ('Picchiaduro');
INSERT INTO Genere(NomeGenere)
VALUES ('FPS');
INSERT INTO Genere(NomeGenere)
VALUES ('Survivol');
INSERT INTO Genere(NomeGenere)
VALUES ('Battle Royale');

INSERT INTO PEGI(CodPEGI)
VALUES('3');
INSERT INTO PEGI(CodPEGI)
VALUES('7');
INSERT INTO PEGI(CodPEGI)
VALUES('12');
INSERT INTO PEGI(CodPEGI)
VALUES('16');
INSERT INTO PEGI(CodPEGI)
VALUES('18');

INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @DEADSPACEcode, 'Sparattutto');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @MORTALKOMBAT11code, 'Picchiaduro');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('16', @HALO3code, 'FPS');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('12', @THESIMS4code, 'Simulazione');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('12', @FORTNITEcode, 'Battle Royale');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @GTAcode, 'RPG');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('3', @FIFA23code, 'Sportivo');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @TLOU2code, 'Survivol');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @TLOURcode, 'Survivol');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @CYBERPUNK77code, 'RPG');
INSERT INTO Caratteristiche(CodPEGI, CodSeriale, NomeGenere)
VALUES ('18', @MAFIA3code, 'RPG');


INSERT INTO Piattaforma(NomePiattaforma)
VALUES ('PlayStation 5');
INSERT INTO Piattaforma(NomePiattaforma)
VALUES ('PlayStation 4');
INSERT INTO Piattaforma(NomePiattaforma)
VALUES ('XBOX Series X');
INSERT INTO Piattaforma(NomePiattaforma)
VALUES ('XBOX Series S');
INSERT INTO Piattaforma(NomePiattaforma)
VALUES ('PC');


INSERT INTO Formato(TipoFormato)
VALUES ('Digitale');
INSERT INTO Formato(TipoFormato)
VALUES ('Fisico');

INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('12', @FORTNITEcode,'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4', @FORTNITEcode,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('8', @FORTNITEcode,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('32', @FORTNITEcode,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('111', @FORTNITEcode,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('41', @FORTNITEcode,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('20', @FORTNITEcode,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('33', @FORTNITEcode,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('71', @FORTNITEcode,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('44', @THESIMS4code,'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('37', @THESIMS4code,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('112', @THESIMS4code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('234', @THESIMS4code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('21', @THESIMS4code,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('221', @THESIMS4code,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('112', @THESIMS4code,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('321', @THESIMS4code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('21', @THESIMS4code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('6', @HALO3code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('23', @MORTALKOMBAT11code,'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('7', @MORTALKOMBAT11code,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @MORTALKOMBAT11code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('0', @MORTALKOMBAT11code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('12', @MORTALKOMBAT11code,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4', @MORTALKOMBAT11code,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @MORTALKOMBAT11code,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('11', @MORTALKOMBAT11code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('7', @MORTALKOMBAT11code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4', @DEADSPACEcode,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('22', @NITROKG2code,'PC','Fisico');  
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('12', @RX6700XTcode,'PC','Fisico');  
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('28', @G305code,'PC','Fisico');     
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('30', @GTAcode, 'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4', @GTAcode,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('0', @GTAcode,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('20', @GTAcode,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('9', @GTAcode,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4', @GTAcode,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @GTAcode,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('1', @GTAcode,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('2', @GTAcode,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('20', @TLOU2code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('150', @TLOU2code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @TLOURcode,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('50', @TLOURcode,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('36', @FIFA23code,'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('44', @FIFA23code,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @FIFA23code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('20', @FIFA23code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('90', @FIFA23code,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('40', @FIFA23code,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10', @FIFA23code,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('34', @FIFA23code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('0',@FIFA23code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('2',@CYBERPUNK77code,'PlayStation 5','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('40',@CYBERPUNK77code,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10',@CYBERPUNK77code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('20',@CYBERPUNK77code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('79',@CYBERPUNK77code,'XBOX Series X','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('43',@CYBERPUNK77code,'XBOX Series X','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('100',@CYBERPUNK77code,'XBOX Series S','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('0',@CYBERPUNK77code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('200',@CYBERPUNK77code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('4',@MAFIA3code,'PlayStation 4','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('80',@MAFIA3code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('10',@MAFIA3code,'PC','Digitale');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('7',@MAFIA3code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('7',@PS5code,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('54',@DUALSENSEcode,'PlayStation 5','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('18',@RTX4090code,'PC','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('23',@PS4code,'PlayStation 4','Fisico');
INSERT INTO Disponibilita(QuantitaDisponibile, CodSeriale, NomePiattaforma, TipoFormato)
VALUES ('77',@XBOXXcode,'XBOX Series X','Fisico');


SET @CARLOemail := 'Carlo.Tucci@gmail.com';
SET @PASQUALEemail := 'Pasquale.Corvino@gmail.com';
SET @LUCAemail := 'Luca.Rossi@gmail.com';


INSERT INTO Utente(Email, PasswordUtente, PuntiFedelta, Tipo)
VALUES (@CARLOemail, 'Carlo2023!', '2918', 1);
INSERT INTO Utente(Email, PasswordUtente, PuntiFedelta, Tipo)
VALUES (@PASQUALEemail, 'Pasquale2023!', '797', 1);
INSERT INTO Utente(Email, PasswordUtente, PuntiFedelta, Tipo)
VALUES (@LUCAemail, 'Luca2023!', '0', 0);


INSERT INTO DatiSensibileUtente(CodiceFiscale, Nome, Cognome, Immagine, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('TCCCRL02M12H501L', 'Carlo', 'Tucci', 'Avatar.png', '00118', 'Giovanni Falcone', '3', 'Roma', 'Roma', '+393342518794', @CARLOemail);
INSERT INTO DatiSensibileUtente(CodiceFiscale, Nome, Cognome, Immagine, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('CRYU34FTOK903DGW', 'Pasquale', 'Corvini', 'Avatar.png', '80020', 'Campania', '3', 'Frattaminore', 'Napoli', '+393334455667', @PASQUALEemail);
INSERT INTO DatiSensibileUtente(CodiceFiscale, Nome, Cognome, Immagine, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('RSSLCU96M27L219U', 'Luca', 'Rossi', 'Avatar.png', '10071', 'Clanio', '24', 'Torino', 'Torino', '+393478951025',@LUCAemail);


INSERT INTO MetodoPagamento(NumeroCarta, TitolareCarta, Scadenza, Email)
VALUES ('5879485763284798', 'Carlo Tucci', '2024-06-12', @CARLOemail);
INSERT INTO MetodoPagamento(NumeroCarta, TitolareCarta, Scadenza, Email)
VALUES ('5896475896542158', 'Carlo Tucci', '2025-03-22', @CARLOemail);
INSERT INTO MetodoPagamento(NumeroCarta, TitolareCarta, Scadenza, Email)
VALUES ('5684956215478955', 'Pasquale Corvino', '2027-02-11', @PASQUALEemail);


INSERT INTO IndirizziSpedizione(Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('Carlo', 'Tucci', '00118', 'Giovanni Falcone', '3', 'Roma', 'Roma', '+393342518794', @CARLOemail);
INSERT INTO IndirizziSpedizione(Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('Pasquale', 'Corvini', '80020', 'Campania', '3', 'Frattaminore', 'Napoli', '+393334455667', @PASQUALEemail);
INSERT INTO IndirizziSpedizione(Nome, Cognome, CAP, Via, Civico, Citta, Provincia, NumeroTelefono, Email)
VALUES ('Carlo', 'Tucci', '80020', 'Roma', '31', 'Orta Di Atella', 'Caserta', '+393332211000', @CARLOemail);


INSERT INTO StatoOrdine(Stato)
VALUES ('In Lavorazione');
INSERT INTO StatoOrdine(Stato)
VALUES ('Spedito');
INSERT INTO StatoOrdine(Stato)
VALUES ('In Consegna');
INSERT INTO StatoOrdine(Stato)
VALUES ('Consegnato');


INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-01-03', 'Fattura1.pdf', '660','In Lavorazione',@CARLOemail);
INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-02-05', 'Fattura2.pdf','114.95','In Lavorazione',@PASQUALEemail);
INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-04-06', 'Fattura3.pdf', '2174.99','In Lavorazione',@CARLOemail);
INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-05-16', 'Fattura4.pdf', '84.97','In Lavorazione',@CARLOemail);
INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-04-05', 'Fattura5.pdf', '279.85','In Lavorazione',@PASQUALEemail);
INSERT INTO Ordine(Sconto, DataAcquisto, Fattura, PrezzoTotale, StatoOrdine, Email)
VALUES ('-0','2023-07-05', 'Fattura6.pdf', '404.98','In Lavorazione',@PASQUALEemail);

INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('2',@DUALSENSEcode,'01', '55.00','Ps5 Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@PS5code,'01', '550.00','Ps5 Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('3',@MAFIA3code,'02', '14.99','Ps4 Digitale');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('2',@CYBERPUNK77code,'02', '34.99', 'XboxS Digitale');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@RTX4090code,'03', '2155.00', 'Pc Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@TLOU2code,'03', '19.99','Ps4 Digitale');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@MAFIA3code,'04', '14.99', 'XboxX Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('2',@CYBERPUNK77code,'04', '34.99','Ps4 Digitale');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('3',@THESIMS4code,'05', '9.99','Ps4 Digitale');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('2',@FIFA23code,'05', '29.99','XboxX Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@NITROKG2code,'05', '189.90','Pc Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@RX6700XTcode,'06', '394.99','Pc Fisico');
INSERT INTO Include(Quantita, CodSeriale, CodOrdine, PrezzoMomento, Piattaforma)
VALUES ('1',@THESIMS4code,'06', '9.99','Pc Fisico');


INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('01', '01', '5879485763284798');
INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('02', '02', '5684956215478955');
INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('03', '01', '5896475896542158');
INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('04', '01', '5896475896542158');
INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('05', '02', '5684956215478955');
INSERT INTO Comprende(CodOrdine, CodIndirizzo, NumeroCarta)
VALUES('06', '02', '5684956215478955');

INSERT INTO Recensione(Descrizione, Valutazione, CodSeriale, Email)
VALUES ('La console è perfetta ed è molto divertente da usare', '5', @PS5code, @CARLOemail);
INSERT INTO Recensione(Descrizione, Valutazione, CodSeriale, Email)
VALUES ('La console è bella ma risulta essere troppo ingrombrante', '3', @PS5code, @PASQUALEemail);
INSERT INTO Recensione(Descrizione, Valutazione, CodSeriale, Email)
VALUES ('La console è bellissima ma leggermente rumorosa', '4', @PS5code, @CARLOemail);
INSERT INTO Recensione(Descrizione, Valutazione, CodSeriale, Email)
VALUES ('Bel gioco', '4', @CYBERPUNK77code, @PASQUALEemail);