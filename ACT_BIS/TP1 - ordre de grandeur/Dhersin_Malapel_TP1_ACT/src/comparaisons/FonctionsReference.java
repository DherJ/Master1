package comparaisons;

public class FonctionsReference {

	private int[] sizes;

	public FonctionsReference(int[] sizes) {
		this.sizes = sizes;
	}
	
	/**
	 * Retourne le tableau de toutes les valeurs de n^pow pour tous
	 * les n présents dans this.sizes.
	 * @param pow puissance
	 * @return tableau des résultats
	 */
	public double[] puissance (double pow) {
		double[] values = new double[this.sizes.length];
		
		for (int idx=0 ; idx<this.sizes.length ; idx++)
			values[idx] = Math.pow(this.sizes[idx], pow);
		
		return values;
	}
	
	/**
	 * Retourne le tableau de toutes les valeurs de n^pow pour tous
	 * les n présents dans this.sizes.
	 * @param pow puissance
	 * @return tableau des résultats
	 */
	public double[] logarithme () {
		double[] values = new double[this.sizes.length];
		
		for (int idx=0 ; idx<this.sizes.length ; idx++)
			values[idx] = Math.log(this.sizes[idx]);
		
		return values;
	}
	
	public double[] nlog(){
		double[] values = new double[this.sizes.length];
		
		for(int i=0; i<this.sizes.length; i++){
			values[i] = this.sizes[i] * Math.log(this.sizes[i]);
		}
		
		return values;
	}
	
	public double[] exp(){
		double[] values = new double[this.sizes.length];
		
		for(int i=0; i<this.sizes.length; i++){
			values[i] = Math.exp(this.sizes[i]);
		}
		
		return values;
	}
	
	public double[] kPowN(double cst){
		double[] values = new double[this.sizes.length];
		
		for(int i=0; i<this.sizes.length; i++){
			values[i] = Math.pow(cst, this.sizes[i]);
		}
		
		return values;
	}
	
	public int calculFacto(int n){
		int fact = 1;
		for(int i=n; i>=1; i--){
			fact = fact * i;
		}
		return fact;
	}
	
	public double[] fctFacto(){
		double[] values = new double[this.sizes.length];
		
		for(int i=0; i<this.sizes.length; i++){
			values[i] = calculFacto(this.sizes[i]);
		}
		
		return values;
	}
	
	
	public double[] nPowN(){
		double[] values = new double[this.sizes.length];
		
		for(int i=0; i<this.sizes.length; i++){
			values[i] = Math.pow(this.sizes[i], this.sizes[i]);
		}
		
		return values;
	}
}