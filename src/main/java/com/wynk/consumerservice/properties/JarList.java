package com.wynk.consumerservice.properties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.*;
import java.util.stream.Collectors;

public class JarList
{
    public static void main (String args[]) throws IOException
    {

        List<Path> files = Files.list(Paths.get(".")).collect(Collectors.toList());
        for(Path file : files) {
            //System.out.println(file.toString());
            try {
                JarFile jarFile = new JarFile(file.toString());
                Enumeration enu = jarFile.entries();
                while (enu.hasMoreElements())
                    process(jarFile, enu.nextElement());
            } catch (Exception e) {
                System.out.println("error - " + file.toString());
            }
        }
    }

    private static void process(JarFile jarFile, Object obj) throws IOException
    {
        JarEntry entry = (JarEntry)obj;
        String name = entry.getName();
        if(name.endsWith("pom.properties")) {
            InputStream is = jarFile.getInputStream(entry);
            Properties p = new Properties();
            p.load(is);
            String g = p.getProperty("groupId");
            String a = p.getProperty("artifactId");
            String v = p.getProperty("version");
            System.out.println(g+":"+a+":"+v);
        }
    }
}