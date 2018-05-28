package UI.components.all_elucidations_view;

import ACQ.IElucidation;
import UI.components.IComponent;
import ACQ.IEventListener;

import java.util.Set;

public interface IHomeView extends IComponent {

    void onElucidationClick(IEventListener<IElucidation> listener);

    void onNewInquiry(IEventListener<?> listener);

    void tickList(Set<IElucidation> elucidations);

    void disableList();

    void enableList();

}
