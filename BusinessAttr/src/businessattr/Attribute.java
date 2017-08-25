/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessattr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;

/**
 *
 * @author Gianfranco
 */
public class Attribute {

    private String name;
    private String val;

    public Attribute(String name, String val) {
        this.name = name.replaceAll("'", "");
        this.val = val;
        //CreateJson();
    }


    public String getName() {
        return name;
    }

    public String getVal() {
        return val;
    }

    public static void CreateJson(String id,ArrayList<Attribute> at) {
        String attr = "";
        try (FileWriter fw = new FileWriter("AtrrJSON2.json", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            String p1 = "{\"business_id\":" + id + ",\"attributes\":[";
            Iterator itr = at.iterator();
            while (itr.hasNext()) {
                //System.out.println(itr.next());
                Attribute att=(Attribute)itr.next();  
                attr = attr.concat("\"" + att.getName() + ":" + att.getVal() + "\"");
                attr = attr.concat(",");
            }
            attr = attr.substring(0, attr.length() - 1);
            String p2 = "]}";
            out.println(p1.concat(attr).concat(p2));
            //more code

            //out.println("more text");
            //more code
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

    @Override
    public String toString() {
        return name + ":" + val + '\n';
    }

}
