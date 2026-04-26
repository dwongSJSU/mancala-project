/**
 * ViewStrategy is the interface used as part of the Strategy pattern for displaying the application.
 * Defines the behavior that all concrete view strategies must implement.
 *
 * @author Brandon Phan
 */
public interface ViewStrategy {

    /**
     * Attaches this view strategy to the Mancala model.
     * Precondition: model is not null
     * Postcondition: the view has access to the model data
     *
     * @param model the Mancala model used by the view
     */
    void attachTo(MancalaLinkedList model);

    /**
     * Updates the view using the current state of the model.
     * Precondition: the view has already been attached to a model
     * Postcondition: the GUI reflects the latest state of the model
     */
    void stateChanged();
}