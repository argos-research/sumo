package interfaces;

/**
 * Interface used by objects who want to be notified when a layer is modified
 * 
 */
public interface ModifiedCollectionListener {
	/**
	 * Event fired when the layer has changed.
	 *
	 */
	public void CollectionModified();
}
