package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	Plosca plosca;
	private Igralec naPotezi;
	
	public Igra(Igralec prvi) {
		this.plosca = new Plosca();
		plosca.inicializacija();
		this.naPotezi = prvi; //za moznost izbire kdo zacne
	}
			
	//rekurzivna funkcija ki pove ali je dano vozlisce povezano s koncnim robom.
	// ce zmagovalna pot obstaja jo funkcija vrne; v nasprotnem primeru vrne null.
	public List<Tuple> obstaja_pot(Igralec igralec, List<Tuple> potDoSedaj) {
		Tuple zadnji = potDoSedaj.get(potDoSedaj.size() - 1);
		if (igralec == Igralec.MODRI) {
			if (zadnji.getY() == (Plosca.N+1)) {
				return potDoSedaj;
			} else {
				for (Tuple sosed : plosca.sosedi(zadnji.getX(), zadnji.getY())) {
					if (plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.MODRO && !potDoSedaj.contains(sosed)) {
						potDoSedaj.add(sosed);
						List<Tuple> rezultat = obstaja_pot(igralec, potDoSedaj);
						if (rezultat != null) {
							return rezultat;
						} else {
							potDoSedaj.remove(potDoSedaj.size() - 1);
						}
					}
				}
			}
		} else {
			if (zadnji.getX() == (Plosca.N+1)) {
				return potDoSedaj;
			} else {
				for (Tuple sosed : plosca.sosedi(zadnji.getX(), zadnji.getY())) {
					if (plosca.matrikaPolj[sosed.getY()][sosed.getX()] == Polje.RDECE && !potDoSedaj.contains(sosed)) {
						potDoSedaj.add(sosed);
						List<Tuple> rezultat = obstaja_pot(igralec, potDoSedaj);
						if (rezultat != null) {
							return rezultat;
						} else {
							potDoSedaj.remove(potDoSedaj.size() - 1);
						}
					}
				}
			}
			
		}
		return null;
	}
	
	//naprej pogledamo, kdo je na potezi. potem pogledamo, ali je v prejsnji potezi zmagal nasprotnik.
	//to  naredimo tako, da gremo cez vsa robna vozlisca in gledamo ali obstaja pot do nasprotnega roba
		public Stanje stanje() {
			if (naPotezi == Igralec.MODRI) {
				for (int y = 1; y <= Plosca.N; y++) {
					List<Tuple> tmp2 = new LinkedList<Tuple>();
					tmp2.add(new Tuple(1,y));
					if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
						return Stanje.ZMAGA_RDECI;
					}
				}
				return Stanje.POTEZA_MODRI;
			} else {
				for (int x = 1; x <= Plosca.N; x++) {
					List<Tuple> tmp2 = new LinkedList<Tuple>();
					tmp2.add(new Tuple(x,1));
					if (obstaja_pot(naPotezi.nasprotnik(),tmp2) != null) {
						return Stanje.ZMAGA_MODRI;
					}
				}
				return Stanje.POTEZA_RDECI;
			}
		}
		
		//TODO odigraj_potezo
		
		//TODO razpolozljive_poteze


}