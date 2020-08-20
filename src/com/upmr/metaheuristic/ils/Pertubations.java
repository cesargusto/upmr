/***************************************************************************
 * Esta classe retorna os 3 movimentos basicos de pertubação utilizados no ILS e SA
 * 
 * Classe criada em: 22 de out de 2017
 * @author cesar
 *
 ******************************************************************************/

package com.upmr.metaheuristic.ils;

import com.upmr.localsearch_2.LocalSearch;
import com.upmr.metaheuristic.sa.MovimentosSA;
import com.upmr.core.Solution;

public class Pertubations {
	
	private LocalSearch ls;
	private MovimentosSA m_sa;
	private final int quant_levels = 4;

	public Pertubations(){
		this.ls = new LocalSearch();
		this.m_sa = new MovimentosSA();
	}
	
	public Solution execute(Solution s, int level) throws CloneNotSupportedException{
		
		switch(level){
			//case 1: return ls.change_Maq(s);
			case 1: return m_sa.two_realloc(s);
			case 2: return m_sa.two_swap(s);
			case 3: return m_sa.two_realloc(m_sa.two_swap(s));
			case 4: return m_sa.two_swap(m_sa.two_realloc(s));
			
		}
		return s;
	}
	
	public int getQuant_levels() {
		return quant_levels;
	}

}
