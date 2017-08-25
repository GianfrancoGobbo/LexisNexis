/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessattr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gianfranco
 */
public class BusinessAttr {

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int linha_atual = 1, ba_firstIndex = 0, ba_lastIndex = 0;
        String linha = null;
        String[] palavras = null;
        String[] attrs = null;
        Boolean first = true;
        //Attribute[] attr = null;
        ArrayList<Attribute> list=new ArrayList<Attribute>();
        String attrname[] = {"BusinessParking", "Ambience", "GoodForMeal","DietaryRestrictions","Music","BestNights","HairSpecializesIn"};
        String id = null;
        String end = "C:/Users/Gianfranco/Documents/LexisNexis/YELP/attr.csv";
        File business_file = new File(end);
        Boolean flagNewBusiness=false;

        try (final BufferedReader in = new BufferedReader(new FileReader(business_file))) {
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {                
                    if(first){
                        linha = scanner.nextLine();
                        linha = scanner.nextLine();
                        linha = scanner.nextLine();
                       first=false;
                    }
                //System.out.println(linha);
                palavras = linha.split(",");
                if (isNumeric(palavras[0])) {//check for business_id
                    flagNewBusiness=true;
                    id = palavras[1];
                    //System.out.println(id);
                    System.out.println(palavras[0]);
                    /*
                    if (scanner.hasNext()) {
                        linha = scanner.nextLine();
                    }
                     */       
                    //palavras = linha.split(",");
                    System.out.println(palavras[0]);

                    while (!isNumeric(palavras[0]) || flagNewBusiness) {
                        flagNewBusiness=false;
                        //System.out.println(palavras[1]);
                        String[] aux = palavras[2].split(":");
                        for (int i = 0; i < attrname.length; i++) {
                            //Attribute[] attr = null;
                            if (aux[0].equals("\""+attrname[i])) {
                                //separate nested attributes 
                                System.out.println(attrname[i]);
                                String[] txt = linha.split(",");
                                //attr = new Attribute[txt.length-2];
                                for (int j = 0; j < txt.length; j++) {                                    
                                    String re1 = ".*?";	// Non-greedy match on filler
                                    String re2 = "(\\'.*?\\')";	// Single Quote String 1
                                    String re3 = ".*?";	// Non-greedy match on filler
                                    String re4 = "((?:[a-z][a-z0-9_]*))";	// Variable Name 1

                                    Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                                    Matcher m = p.matcher(txt[j]);
                                    if (m.find()) {              
                                        String strng1 = m.group(1);
                                        String var1 = m.group(2);
                                        //System.out.print("(" + strng1.toString() + ")" + "(" + var1.toString() + ")" + "\n");
                                        list.add(new Attribute(attrname[i]+"_"+strng1, var1));
                                        
                                    }
                                }
                                
                            }
                            
                        }
                        
                        if (scanner.hasNext()) {
                            linha = scanner.nextLine();
                            palavras = linha.split(",");
                        }
                                

                    }
                    if(!list.isEmpty()){
                            list.add(new Attribute("TESTE","TESTE"));
                            Attribute.CreateJson(id, list);
                            list = new ArrayList<Attribute>();
                        }
                    

                }
            }

                   
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Valor lido do arquivo invalido\n" + ex.getMessage());
        } catch (InputMismatchException ex) {
            System.out.println("Valor digitado invalido\n" + ex.getMessage());
        }

//        for(int o=0;o<attrname.length;o++){
//                    for (int q=0; q < attr.length-1;q++){
//                            System.out.println(attr[o][q]);
//                        }
//                    }
    }
}
