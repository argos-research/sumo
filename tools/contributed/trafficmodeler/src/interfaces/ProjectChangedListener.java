package interfaces;

import classes.Project;

/**
 * Interface used for objects who want to be notified when the currently loaded
 * project has changed.
 * 
 */
public interface ProjectChangedListener {
	/**
	 * Event fired when the currently loaded project has changed. This event is
	 * fired when the user opens or closes a project.
	 * 
	 * @param p
	 *            the new project. If null this means that there is no open
	 *            project in the application.
	 */
	public void ProjectChanged(Project p);
}
