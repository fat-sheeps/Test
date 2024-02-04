//package org.example.domain;
//
//import java.lang.instrument.ClassFileTransformer;
//import java.lang.instrument.Instrumentation;
//import java.security.ProtectionDomain;
//
//public class ObjectSizeAgent implements ClassFileTransformer {
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
//        return classfileBuffer;
//    }
//    public static void main(String[] args) {
//        // 获取Instrumentation实例
//        Instrumentation instrumentation = getInstrumentation();
//        if (instrumentation == null) {
//            System.out.println("无法获取Instrumentation实例");
//            return;
//        }
//
//        // 创建一个对象
//        String object = "Hello, world!";
//
//        // 获取对象大小
//        long size = instrumentation.getObjectSize(object);
//        System.out.println("对象占用的内存大小： " + size + " 字节");
//    }
//
//    private static Instrumentation getInstrumentation() {
//        try {
//            // 通过反射获取当前线程的Instrumentation实例
//            Class<?> instrumentationClass = Class.forName("java.lang.instrument.Instrumentation");
//            return (Instrumentation) instrumentationClass.getMethod("getInstrumentation").invoke(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
