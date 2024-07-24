package com.example;

import com.example.domain.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student().setId(1).setName("wang1"));
        list.add(new Student().setId(2).setName("wang2"));
        list.add(new Student().setId(3).setName("wang3"));
        System.out.println(list);
        List<Student> list1 = new ArrayList<>(list);
        Iterator<Student> iterator = list1.iterator();
        while (iterator.hasNext()) {
            Student next = iterator.next();
            if(next.getId() == 1) {
                iterator.remove();
            }
        }
        list1.get(0).setName("zhang");
        System.out.println(list);
        System.out.println(list1);
    }

}
