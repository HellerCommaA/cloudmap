package photo.heller.android.cloudmap.interfaces;

import java.util.List;

public interface ModelEventListener<T> {
    void onModelChange(List<T> db);
}
