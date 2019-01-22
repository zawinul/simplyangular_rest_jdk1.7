package it.eng.sa.model.lib;


public class Model  {
	
	public Model(){}  // il costruttore senza parametri è obbligatorio

	public String toString() {
		return ModelHelper.json(this);
	}	
}
