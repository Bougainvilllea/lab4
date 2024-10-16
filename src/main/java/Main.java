import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        String json = Files.readString(Path.of("src\\main\\resources\\books.json"));
        Type type = new TypeToken<List<Visitor>>(){}.getType();
        List<Visitor> listVisitors = gson.fromJson(json, type);

        //task 1
        System.out.println(listVisitors.size());

        //task 2
        List<Book> booksList = new ArrayList<>();

        for (Visitor visitor : listVisitors) {
            booksList.addAll(visitor.getFavoriteBooks());
        }
        HashSet<Book> books = new HashSet<>(booksList);
        System.out.println(books);
        System.out.println(books.size());

        //task 3
        System.out.println(books.stream().sorted(Comparator.comparing(Book::getPublishingYear)).toList());

        //task 4
        System.out.println(books.stream().anyMatch(book -> book.getAuthor().equals("Jane Austen")));

        //task 5
        listVisitors.stream().max(Comparator.comparing(visitor -> visitor.getFavoriteBooks().size())).ifPresent(visitor -> System.out.println(visitor.getFavoriteBooks().size()));

        //task 6
        double average = Math.ceil(listVisitors.stream().mapToDouble(visitor -> visitor.getFavoriteBooks().size()).sum() / (double) listVisitors.size());
        List<Visitor> visitorsSub = listVisitors.stream().filter(Visitor::isSubscribed).toList();
        System.out.println(visitorsSub.stream().filter(visitor -> visitor.getFavoriteBooks().size() > average).map(visitor -> new Sms(visitor.getPhone(), "you are a bookworm")).toList());
        System.out.println(visitorsSub.stream().filter(visitor -> visitor.getFavoriteBooks().size() < average).map(visitor -> new Sms(visitor.getPhone(), "read more")).toList());
        System.out.println(visitorsSub.stream().filter(visitor -> visitor.getFavoriteBooks().size() == average).map(visitor -> new Sms(visitor.getPhone(), "fine")).toList());
    }
}