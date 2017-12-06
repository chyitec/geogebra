package org.geogebra.common.kernel.arithmetic;

/**
 * 
 * Interface for objects determined by single equation, such as line, conic or
 * implicit curve
 *
 */
public interface EquationValue {
	/**
	 * @return equation
	 */
	public Equation getEquation();

	/**
	 * @return array of variables with nonzero cofficients
	 */
	public String[] getEquationVariables();

	public void setToUser();
}
