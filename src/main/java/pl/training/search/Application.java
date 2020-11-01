package pl.training.search;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.training.search.github.GithubService;
import pl.training.search.github.Repository;
import pl.training.search.wikipedia.Article;
import pl.training.search.wikipedia.WikipediaService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application {

    private final GithubService githubService = new GithubService(retrofitBuilder("https://api.github.com/"));
    private final WikipediaService wikipediaService = new WikipediaService(retrofitBuilder("https://en.wikipedia.org/w/"));
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Retrofit retrofitBuilder(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                        .build())
                .build();
    }

    private boolean hasEmptySpaces(String text) {
        return !text.contains(" ");
    }

    private List<String> collect(List<String> result, String value) {
        List<String> newResult = new ArrayList<>(result);
        newResult.add(value);
        return newResult;
    }

    private Observable<List<String>> getWikipediaArticles(String query) {
        return wikipediaService.getArticles(query)
                .flatMap(Observable::fromIterable)
                .map(Article::getTitle)
                .map(String::toLowerCase)
                .filter(this::hasEmptySpaces)
                .distinctUntilChanged()
                .reduce(new ArrayList<>(), this::collect)
                .toObservable()
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<String>> getGithubRepositories(String query) {
        return githubService.getRepositories(query)
                .flatMap(Observable::fromIterable)
                .map(Repository::getName)
                .map(String::toLowerCase)
                .filter(this::hasEmptySpaces)
                .distinctUntilChanged()
                .reduce(new ArrayList<>(), this::collect)
                .toObservable()
                .subscribeOn(Schedulers.io());
    }

    private List<String> combine(List<String> results, List<String> otherResults) {
        List<String> newResult = new ArrayList<>();
        newResult.addAll(results);
        newResult.addAll(otherResults);
        return newResult;
    }

    private void onNumber(int number) {
        System.out.println("New number: " + number + " " + Thread.currentThread().getName());
    }

    private void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(compositeDisposable::clear));

        var result = ObservableReader.fromInputStream(System.in)
                .flatMap(this::getWikipediaArticles);

       /*var result = ObservableReader.fromInputStream(System.in)
                .debounce(5, TimeUnit.SECONDS)
                .flatMap(query -> Observable.zip(getWikipediaArticles(query), getGithubRepositories(query), this::combine));*/

        Disposable disposable = result.subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"));
        compositeDisposable.add(disposable);
    }

    public static void main(String[] args) throws InterruptedException {
        new Application().start();
        System.out.println("Before sleep");
        Thread.sleep(10_000);
    }

}

/*
  - subscribeOn affects upstream operators (operators above the subscribeOn)
  - observeOn affects downstream operators (operators below the observeOn)
  - If only subscribeOn is specified, all operators will be be executed on that thread
  - If only observeOn is specified, all operators will be executed on the current thread and only operators
    below the observeOn will be switched to thread specified by the observeOn
*/
