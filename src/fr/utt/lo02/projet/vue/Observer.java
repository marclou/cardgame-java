package fr.utt.lo02.projet.vue;

/**
 * L'interface Observer permet aux objets qui l'implémente d'observer un autre objet de type Observable. Dans le cadre de notre projet c'est
 * la classe VueApplication qui va implémenter Observer. Cette classe pourra ainsi observer le dourelement de la partie pour se mettre a jour.
 * 
 * 
 * @author Arthur Guedon et Marc Louvion
 *
 */
public interface Observer {
	
	/**
	 * Cette methode permet a un objet de se mettre a jour souhaite a la modification de l'objet qu'il observe.
	 * Elle est redefinie dans la classe VueApplication.
	 * 
	 * @param o : c'est l'objet qui est la source de la modification.
	 */
	public void update (Object o);
}
