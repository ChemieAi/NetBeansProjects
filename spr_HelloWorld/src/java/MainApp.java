/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ozgun
 */
import java.io.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
       File f=new File("web\\WEB-INF\\beans.xml");
       System.out.println(f.getAbsolutePath());
       ApplicationContext context = new ClassPathXmlApplicationContext("file:web\\WEB-INF\\beans.xml");
       HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
       obj.getMessage();
   }
}