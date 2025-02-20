package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import villagegaulois.Etal;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche; 

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}
	
	public static class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i = 0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0; i<etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit = 0;
			for (int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsAvecProduit++;
				}
			}
			Etal[] etalsVendantProduit = new Etal[nbEtalsAvecProduit];
			int current = 0;
			for (int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsVendantProduit[current] = etals[i];
					current++;
				}
			}
			return etalsVendantProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalOccupee = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					nbEtalOccupee++;
				}
			}
			int nbEtalVide = etals.length - nbEtalOccupee;
			if (nbEtalVide != 0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisées dans le marché.\n");
			}
			return chaine.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit){
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ". ");
		int numeroEtal = marche.trouverEtalLibre();
		if(numeroEtal != -1) {
			marche.utiliserEtal(numeroEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (numeroEtal+1) + ". ");
		} else {
			chaine.append("Il n'y a plus de place sur le marché.");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit (String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] vendeursProduit = marche.trouverEtals(produit);
		if(vendeursProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.");
		} else if (vendeursProduit.length == 1) {
			chaine.append("Seul le vendeur " + vendeursProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont : ");
			for (int i = 0; i<vendeursProduit.length; i++) {
				chaine.append("- " + vendeursProduit[i].getVendeur().getNom());
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal (Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur){
		StringBuilder chaine = new StringBuilder();
		chaine.append(rechercherEtal(vendeur).libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}