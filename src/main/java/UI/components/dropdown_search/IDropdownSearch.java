package UI.components.dropdown_search;

import UI.components.IComponent;
import ACQ.IEventListener;

import java.util.List;
import java.util.Set;

public interface IDropdownSearch<T> extends IComponent {

    void setRequired(IDropdownSearchRequire required);

    void updateList(List<T> searchResults);

    void onType(IEventListener<String> listener);

    void onDone(IEventListener<Set<T>> listIEventListener);

    void expand();

    void collapse();

}
