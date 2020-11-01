package pl.training.search;

import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ObservableReader {

    public static Observable<String> fromInputStream(InputStream inputStream) {
        return Observable.create(emitter -> {
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String text;
                while ((text = bufferedReader.readLine()) != null) {
                    emitter.onNext(text);
                }
                emitter.onComplete();
            }
        });
    }

}
