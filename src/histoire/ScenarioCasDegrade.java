package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		
		etal.libererEtal();
		
		Gaulois vendeur = new Gaulois("Vendeur", 5);
		etal.occuperEtal(vendeur, "Fleurs", 12);;
		
		etal.acheterProduit(10, null);
		
		Gaulois acheteur = new Gaulois("Acheteur", 5);
		try {
			etal.acheterProduit(0, acheteur);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		etal.libererEtal();
		
		try {
			etal.acheterProduit(5, acheteur);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}

}
