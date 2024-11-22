package top.putileaf.greenhearts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> result = new MutableLiveData<>();

    public LiveData<String> getResult() {
        return result;
    }

    public void handleButtonClick() {
        // 执行复杂的逻辑
        result.setValue("Button Clicked");
    }
}
