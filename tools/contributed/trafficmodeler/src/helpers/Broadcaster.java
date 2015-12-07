package helpers;

import interfaces.ProjectChangedListener;

import java.util.ArrayList;
import java.util.List;

import classes.Project;

/**
 * Class providing application-wide notification between UI components for the
 * event of the change of the currently loaded project
 * 
 */
public class Broadcaster {
	/**
	 * List of listeners registered to the project changed event.
	 */
	private static List<ProjectChangedListener> projectChangedListeners = new ArrayList<ProjectChangedListener>();

	/**
	 * Adds a {@link ProjectChangedListener} to the listener list.
	 * 
	 * @param listener
	 *            The {@link ProjectChangedListener} to be added
	 */
	public static void addProjectChangedListener(ProjectChangedListener projectChangedListener) {
		projectChangedListeners.add(projectChangedListener);
	}

	/**
	 * Removes a {@link ProjectChangedListener} from the listener list.
	 * 
	 * @param listener
	 *            The {@link ProjectChangedListener} to be removed
	 */
	public static void removeProjectChangedListener(ProjectChangedListener projectChangedListener) {
		if (projectChangedListeners.contains(projectChangedListener)) {
			projectChangedListeners.remove(projectChangedListener);
		}
	}

	/**
	 * Notifies all registered {@link ProjectChangedListener} that the project
	 * has changed
	 */
	public static void NotifyProjectChanged(Project project) {
		for (ProjectChangedListener projectChangedListener : projectChangedListeners) {
			projectChangedListener.ProjectChanged(project);
		}
	}

}
