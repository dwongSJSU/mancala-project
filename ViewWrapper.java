/**
 * Mancala Assignment Solution
 *
 * @author Dylan Wong
 */

/**
 * ViewWrapper takes a ViewStrategy and MancalaLinkedList and displays the game GUI.
 * (i.e. wraps the viewing for Strategy pattern)
 */
public class ViewWrapper {
    /**
     * Attaches the view to the model and draws the initial state.
     * 
     * @param model model
     * @param view view
     */
    public void draw(MancalaLinkedList model, ViewStrategy view) {
        view.attachTo(model);
        view.stateChanged();
    }
}
