package com.qi.test;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author qijun
 * @date 2022/4/2 19:52
 */
public class LogPrintTransformer implements ClassFileTransformer {

    private String inputClassName;

    private String methodName;

    public LogPrintTransformer(String inputClassName, String methodName) {
        this.inputClassName = inputClassName;
        this.methodName = methodName;
    }

    /**
     * 签名会接受 ClassLoader、类名、要重定义的类所对应的 Class 对象、定义权限的 ProtectionDomain 以及这个类的原始字节。
     * 如果从 transform 方法中返回 null 的话，将会告诉运行时环境我们并没有对这个类进行变更。
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {


       if (className.contains(inputClassName)) {
          try {
                CtClass ctClass = ClassPool.getDefault().get(className.replaceAll("/", "."));
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();
                for (CtMethod ctMethod : ctMethods) {
                    if (ctMethod.getName().equals(methodName)) {
                        ctMethod.insertBefore(createJavaString(className, ctMethod));
                    }

                }
                return ctClass.toBytecode();
            } catch(NotFoundException e){
                e.printStackTrace();
            } catch(CannotCompileException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }


    //在javassist中$1代表方法的第一个参数,$2代表第二个参数以此类推可参考https://www.jianshu.com/p/b9b3ff0e1bf8
    private String createJavaString(String className, CtMethod ctMethod){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("System.out.println($1);");
        stringBuilder.append("System.out.println($2);" );

        return stringBuilder.toString();
    }



}
