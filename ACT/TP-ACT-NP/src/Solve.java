
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Solve {
	
	/* Definition ds couleur pour les affichages dans la console */
	public static final String CYAN = "\u001B[36m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String WHITE = "\u001B[37m";
	final static String NL = "\n";
	
	static int nbObjet = 0;
	static int [] poids = null;
	static int nSac = 0;
	static int cap = 0;
	
	
	/**
	 * Cette methode permet de parser la ligne de commande afin d'indexer les differentes options presentes dans la commande.
	 * @param args Correspond a la ligne de commande.
	 * @return Retourne un objet Map qui contient <Key = nom de l option, value = valeur de l'option>.
	 */
    private static Map<String, String> parseCommandeLine(String[] args) {
        Map<String, String> out = new TreeMap<String, String>();
        for (String token : args) {
            String namePart;                // correspond au nom de l option dans la ligne de commande
            String valuePart;               // correspond a la valeur de l option de la ligne de commande
        	if(!token.contains("=")) {
                if (token.startsWith("-")) {       // separateur entre les arguments de la ligne de commande
                    out.put(token.substring(1), "true");
                }
        	}
            String[] part = token.split("=");   // separateur entre le nom et la valeur de l option des arguments de la ligne de commande
            if (part.length > 1) {
                namePart = part[0];
                valuePart = token.substring(namePart.length() + 1);
                if (namePart.startsWith("-")) {                 
                    out.put(namePart.substring(1), valuePart);
                }
            }
        }
        return out;
    }
	
    
    /**
     * Methode qui contient le texte de la partie "help" de la ligne de commande
     * @return Retourne une chaine de caracteres qui contient le texte qui constitue l aide de la ligne de commande
     */
    private static StringBuilder getHelpContent() {
    	StringBuilder help = new StringBuilder();
    	help.append("La ligne de commande doit contenir au minimum 2 arguments.");
    	help.append(NL);
    	help.append("Format des arguments de la ligne de commande : -Label=Value ");
    	help.append(NL);
    	help.append("Liste des arguments : ");
    	help.append(NL);
    	help.append("	-file=path : ");
    	help.append(NL);
    	help.append("		Correspond au chemin d acces au fichier qui contient les donnees du probleme." + NL + 
    				"		Le fichier doit etre de la forme : " + NL + 
    				"			nombre d'objets " + NL + 
    				"			poids de l objet 0 " + NL +
    				"			poids de l objet n " + NL + 
    				"			nombre sacs " + NL +
    				"			capacite des sacs");
    	help.append(NL);
    	help.append("	-mode=value : ");
    	help.append(NL);
    	help.append("		Correspond au mode de resolution du probleme. " +
    			"Cet argument peut prendre 3 valeurs :" + NL +
    			"1. mode=v   -> propose a l'utilisateur de saisir un certificat et verifie sa validite." + NL + 
    			"2. mode=exh -> genere tous les certificats jusqu a en trouver un valide." + NL + 
    			"3. mode=nd  -> genere aleatoirement un certificat, le teste et retourne Faux si il n est pas valide, Vrai sinon.");
    	help.append(NL);
    	return help;
    }
    
    
	/**
	 * Cette methode va lire un fichier donne en ligne de commande et va recuperer et stocker 
	 * les informations du probleme de BinPack presentes dans le fichier. <br>
	 * En partant du principe que le fichier est structure de la maniere suivante : <br>
	 * int -> nb objets <br>
	 * int -> poids de l objet 0 <br>
	 * int -> poids de l objet n <br>
	 * int -> nb sacs <br>
	 * int -> capacite des sacs <br>
	 * @param path Correspond au chemin d acces du fichier dans lequel se trouve les informations du probleme
	 */
	private static void parseFile(String path) {	
		/* Lecture du fichier contenant l'instance du probleme */
		try {
				InputStream ips = new FileInputStream(path);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String ligne;
				int numLigne = 0;
				int i = 0;
				while ((ligne = br.readLine()) != null) {
					if(numLigne == 0) {                            /* La 1ere ligne contient le nombre d objet */
						nbObjet = Integer.parseInt(ligne);
						poids = new int [nbObjet];                /* On instancie le tableau des poids avec le nombre d objets */
						numLigne++;
					}
					while (((ligne = br.readLine()) != null) && (i < nbObjet)) {  /* Cette partie va recuperer l ensemble des poids et va les stocker dans le tableau des poids */
						poids[i] = Integer.parseInt(ligne);
						i++;
						numLigne++;
					}
					if(numLigne == nbObjet + 1) {          /* Ici on recupere le nombre de sac */
						nSac = Integer.parseInt(ligne);
						numLigne++;
					}
					if(numLigne == nbObjet + 2) {           /* Ici on recupere la capacite des sacs */
						ligne = br.readLine();
						cap = Integer.parseInt(ligne);
					}
				}
				br.close();
		} catch (Exception e) {
				System.out.println(e.toString());
			}
	} 
	
	
	public static void main(String[] args) {
		Map<String, String> cmd = parseCommandeLine(args);
		PblBinPack pbl = null;
		PblPartition part = null;
		CertificatBinPack certificat = null;
		StringBuilder helpContent = null;
		String mode = null;
		String rep = null;
		Scanner sc = new Scanner(System.in);
		if(cmd.containsKey("h")) {
			helpContent = getHelpContent();
			System.out.println(GREEN + helpContent);
		}
		parseFile(cmd.get("file"));
		if(cap != 0 && nSac != 0) {
			pbl = new PblBinPack (nbObjet, poids, cap, nSac);
		} else {
			part = new PblPartition(nbObjet, poids);
			part.affichElemt();
			if(part.aUneSolution()) {
				System.out.println(CYAN + "Le probleme Partition comporte une solution possible :-)");
				part.affichElemt();
				System.out.println(CYAN + "Somme des elements de l ensemble : " + part.sommeElmts());
				System.exit(0);
			} else {
				System.out.println(RED + "Le probleme Partition ne comporte pas de solution possible :-(");
				part.affichElemt();
				System.out.println(RED + "Somme des elements de l ensemble : " + part.sommeElmts());
				System.exit(0);
			}
		}
		certificat = new CertificatBinPack(pbl);
		System.out.println(WHITE + "Vous avez " + pbl.getNb_objets() + " objets");
		System.out.print(WHITE + "Tableau des poids des objets : ");
		pbl.afficherPoids();
		System.out.println(WHITE + "Vous avez " + pbl.getNb_sacs() + " sacs");
		System.out.println(WHITE + "Capacite des sacs = " + pbl.getCap());
		mode = cmd.get("mode");
		switch(mode) {
		case "v": 
			System.out.println(WHITE + NL + "---- Mode verification ----" + NL);
			System.out.println(WHITE + "Voulez vous saisir un certificat? o/n");
			rep = sc.nextLine();
			if(rep.equals("o")) {
				certificat.saisie();
			} else { certificat.alea(); }
			if(certificat.isCorrect()) {
				System.out.println(CYAN + "Le certificat est correct :-)" + WHITE);
			} else { System.out.println(RED + "Le certificat n est pas correct :-(" + WHITE); }
			certificat.afficheCertificat();
			break;
		case "nd":
			System.out.println(WHITE + NL + "---- Mode non-deterministe ----" + NL);
			if(pbl.aUneSolutionNonDeterministe()) {
				System.out.println(CYAN + "Le probleme a une solution non deterministe :-)" + WHITE);
			} else { System.out.println(RED + "Le probleme n'a pas de solution non deterministe :-(" + WHITE); }
			break;
		case "exh":
			System.out.println(WHITE + NL + "---- Mode recherche exhaustive ----" + NL);
			if(pbl.aUneSolution()) {
				System.out.println(CYAN + "Le probleme a une solution non deterministe :-)" + WHITE);
			} else { System.out.println(RED + "Le probleme n'a pas de solution non deterministe :-(" + WHITE); }
			break;
		}
		sc.close();
	}
}