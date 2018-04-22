# Aktivity
(! popis předchozích verzí je přesunutý do "Readme.MD")

Rezervace zdrojů a cestovní příkazy
===================================
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
