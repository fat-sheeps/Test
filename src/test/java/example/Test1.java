package example;


import org.junit.jupiter.api.Test;

public class Test1 {

    @Test
    public void test01(){
        System.out.println(1);
    }

    @Test
    public void test2() {

        System.out.println(~(-1L << 9L));
        System.out.println(32 * 32);
    }

    @Test
    public void test3() {
        System.out.println(Long.valueOf(""));
    }
}
