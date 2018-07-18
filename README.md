# Aktivity
(! popis předchozích verzí je přesunutý do "Readme.MD")

Rezervace zdrojů a cestovní příkazy
===================================
LXIII. V tabulce cesty dodělány ve formuláři "cesty.xml" filtry na Platí Od-Do, účastník a vlastník - dodělat vazbu na výběr podkladových dat k zobrazení a přidat sloupec "Vlastník, účastník"
LXII. Byl změněn dotaz na pole cest - zohledněn výskyt účastníka v cestě jiného zřizovatele, třeba dodělat ještě filtr PlatiOd - PlatiDo
LXI. Ve formuláři "vlastní cesty" byl přidán symbol, jestli byl účastník nebo zdroj schválen nabo neschválen
   - ve schvalování je potřeba udělat refresh schvalované tabulky (zdroj nebo účastník) po schválení nebo zamítnutí - nepřekreslí se szmbol
   - ve formuláři dispečera je potřeba udělat refresh pravé strany s výpisem buď zdroje nebo účastníka - hotovo - je nutné překreslit návratovou tabulku všech dispečerů
LX. Do schvalovacích tabulek byly vloženy nadpisy 
LIX. Schvalování zdrojů - vytvořena funkce na ne|lze editovat záměnu vozidla a ošetřen cyklus schválení a zamítnutí (zda-li osoba může)
LVIII. Schvalování zdrojů - změna ikon
LVII. Schvalování zdrojů rozpracováno - je nutné:
        - ve schvalování osob i zdrojů - omezit schvalování pokud to provedla nadřízená osoba
        - při prvním vstupu do XHTML načíst data (bez ohledu na skutečnost, že data již dříve načtená byla)
LVI. Úprava menu, přidáno GDPR, příprava třídy SchvalovaniRez.java --- GDPR nutno zcela nově naprogramovat.
LV. Započaty práce na Schvalování rezervací dispečerem  
LIV. Do knihovny doplněn font Times New Roman - Crimson pro generování češtiny (.JAR files)
LIII. V Cesta.xhtml a CestyForm.xhtml přidáno přesměrování na stránku za název stránky "?faces-redirect=true"  
LII. - Jasper ukončuje session a odkládá ji a tím pádem zničí aplikaci. Je nutné tvorbu PDF dát do samostatného programu a formou parametrů a výsledného souboru to vyřešit. 
       - Jasper je vytvořen jako Singleton
LI.- Konečně funguje tvorba PDF, ale chybí tam české znaky - doladit
     - otevírat PDF nebo HTML v novém okně   
L.- Chyba na straně GlassFishe - nutné nastavit parametr: "org.jboss.weld.serialization.beanIdentifierIndexOptimization" = false
      https://stackoverflow.com/questions/36104833/org-jboss-weld-exceptions-illegalstateexception-weld-000227-after-every-change/37455290#37455290
    - Je nutné také nstavit proměnnou : "private static final long serialVersionUID = 1L;"   u všech Serializable objektů
    - blbne převod cp1250 na UTF-8 (Jasper tvrdí, že to není UTF-8) - nutné nahrát český font do aplikace (viz. Times Ner Roman - Crimson)
    - naplněna knihovna (LIB) "Jasper651" o nutné .JAR soubory podle vzoru:
      https://mvnrepository.com/artifact/net.sf.jasperreports/jasperreports/6.5.1
XLIX. - je nutné stáhnout hromady knihoven z Apache, Spring, ...
XLVIII. Vytvořen funkční vzor Cesta.jrxml v Tibco Jasper - subData už fungují - musí být vnořené objekty
        - ošetření řetězce z Win1250 > UTF-8
        - je nutné stáhnout hromady knihoven z Apache, Spring, ...
XLVII. Vytvořena testovací šablona .jrxml v Tibco Jasper - nefungují subTabulky (subData) - vyřešit
        - ošetřit řetězce Win1250 > UTF-8 - naprogramovat převody
        - vyhozena knihovna JSON - využiji JSON z JavaEE
XLVI. Vytvořen JSON objekt pomoci JSON-P z JavyEE (GlassFish) pro třídu "entity.Cesta" 
      - pokračovat s tvorbou "Příkazu k jízdě" v Jasper
XLV. Příprava na tisk průvodky JSON se bude muset "vydřít" na základě  jednotlivých členů třídy
XLIV. Vyčištěna databáze od starých záznamů, 
    - ucastnik nemá správně platiod - platido hodnoty zdedene z cesty - opravit
XLIII. Doladěny výběry dat do Helperů, zbývá dodělat níže uvedené úkoly
XLII. Tabulky formulářů dipečera byly doplněny o "delete" funkce a optimalizováno jejich ukládání, je potřeba dořešit možnost záznam smazat 
(byl-li použit někde ve schvalování - smazat nelze)
XLI. V helperech dodělat PlatiOd-PlatiDo - filtry na platné záznamy a u zdrojů vybrat pouze vhodné typy
     ve formuláři dispečerů dodělat mazání zástupců. osob a zdrojů
XL. Formulář dispečerů je předěláván na helper - doladit a nakonfigurovat zdroje
XXXIX. Dodělat ikony schvalování - omezit oprávnění na spuštění schvalování  (začínám v tom mit zmatek)
XXXVIII. Započaty práce na ikonách schvalování - dodělat stavy rovnou do věty schvalovani       
XXXVII. Editace SQL dotazu výběru účastníků - UcastnikFacade - dodělat - výběr rolí zástupce a vedoucí (do xhtml)       
XXXVI. Editace SQL dotazu výběru účastníků - UcastnikFacade - dodělat - vyladit
XXXV. Oprava editace dispečerů - drobné úpravy
XXXIV. Oprava editace dispečerů, nechodí výběr dat ve schvalování
XXXIII. Oprava editace dispečerů - datový model 
XXXII. Příprava schvalování - návrh
XXXI. Platí předchozí - dodělat p:orderList s rezervacemi - nezobrazují se správně
XXX. Začít řešit schvalování, u helperu vyřešit zobrazení detailu rezervace u zdrojů v samostatném dialogu
XXIX. Dodělán NativeQuery na výběr Zdrojů pro HelperZdroj a HelperOsoba
      doladit celý formulář 
XXVIII. Dodělán NativeQuery na výběr Zdrojů pro HelperZdroj - ladit zobrazení, zohlednit účastníky 
XXVII. Nachystané NativeQuery na výběr Zdrojů pro HelperZdroj - ladit
XXVI. dodělat XXV. - započaty práce na výběru zdrojů pro rezervaci 
XXV. cestaForm je nachystaná - opravit cestaForm.xhtml - předělat rozvržení
XXIV. Opraveno chování Helperů - chyběl jim node <h:head> - dodělat cestForm - předělat na grid
      Helperům nastavit data před zobrazením @Inject do cestaForm a následně nakonfiguraovat data.
XXIII.Předěláno programové chování helperů - špatně se zobrazují
XXII. NativeQuery - VYPNUL jsem cache Entity.Cesta a Entity.Rezervace a situace se změnila - celková Cache je zapnuta
      -- zakomentováno v persistence.xml
XXI. NativeQuery v cestách se chová nestandardně - VYPNUL jsem cache a situace se změnila
     Popis chování: https://wiki.eclipse.org/EclipseLink/Examples/JPA/Caching
XX. Předělán CestaForm - je třeba změnit načítání dat do cesty (cashe fungují nestandardně)
XIX. Ošetřeno pole "Email.smer"
XVII. Cesty - příprava na NativeQuery ukládání dat - dodělat
XVI. Založena komunikace e-mailem - dodělat i předchozí vady 
XV. Info o zdroji, dodělat řazení účastníků - blbne to
XIV.  Dialog detailu rezervace - dodělat
XIII. Započaty práce na dialogu detailu rezervace
XII. Přidán kaskádový styl pro zobrazení rezervace - dodělat
XI.On-Line sloupce jsou hotove - dodelat zobrazovani cest a samotnou rezervaci
X. Oprava rozvoje kalendáře - dodělat předchozí
IX. Upraven rozvoj kalendáře i jeho sbalení - doladit mezni data,
dodelat vyber zdroje v nvaznosti na PlatiOd, PlatiDo
VIII. Upraven rozvoj kalendáře i jeho sbalení - začít pracovat na vyplňování daty
VII. Úpravy generování sloupců kalendáře - dodělat zaokrouhlování a přegenerování rozvoje
VI. Rezervace - rendering tlačítek rozvoje, Kalenar metoda plnění
V. Rezervace ošetření PlatiOd - PlatiDo - nutné dělat pře FaceContext