package jdk8.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import lombok.Data;
import lombok.ToString;

/**
 * @author 陆昆
 **/
public class ProcessFile {
    final static String path = "/Users/lukun/test-data/test-data.txt";

    public static void main(String[] args) throws IOException {
        System.out.println(processFile((BufferedReader br) -> br.readLine()));
        System.out.println(processFile((BufferedReader br) -> br.readLine() + br.readLine()));
        List<Apple> apples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Apple apple = new Apple(new Random().nextInt(10), i % 2 == 0 ? "green" : "red");
            apples.add(apple);
        }
        System.out.println(apples);
        // 注意区分lambda中的形参，不是具体实现，另外就是返回值
        System.out.println(filter(apples, (Apple apple) -> apple.getColor().equals("red") && apple.getWeight() > 5));
    }

    /**
     * 1. 将行为参数化(针对一些可变行为)
     * 2. 将对应行为定义为函数式接口
     * 3. lambda传递不同实现针对函数式接口
     *
     * @param list
     * @param p
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        return p.process(bufferedReader);
    }
}

@FunctionalInterface
interface BufferedReaderProcessor {
    String process(BufferedReader bufferedReader) throws IOException;
}

@ToString
@Data
class Apple {
    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    private int weight;

    private String color;
}
