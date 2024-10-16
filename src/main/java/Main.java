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
        HashSet<Book> books = new HashSet<>();
        for (Visitor visitor : listVisitors) {
            books.addAll(visitor.getFavoriteBooks());
        }
        System.out.println(books);
        System.out.println(books.size());

        //task 3
        System.out.println(books.stream().sorted(Comparator.comparing(Book::getPublishingYear)).toList());

    }
}