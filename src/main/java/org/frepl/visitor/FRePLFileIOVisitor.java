package org.frepl.visitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FRePLFileIOVisitor extends FRePLBaseVisitor<Object> {

    private final FRePLVariablesVisitor varVisitor;

    public FRePLFileIOVisitor(FRePLVariablesVisitor variablesVisitor){
        varVisitor = variablesVisitor;
    }
    @Override
    public Object visitFileVar(FRePLParser.FileVarContext ctx) {
        String filePath = ctx.STRING(0).getText();
        filePath = filePath.substring(1,filePath.length()-1);
        int lineNum = Integer.parseInt(ctx.INT(0).getText());
        int fieldNum = Integer.parseInt(ctx.INT(1).getText());
        String delimiter = ctx.STRING(1).getText();
        delimiter = delimiter.substring(1,delimiter.length()-1);

        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while(lineNum-- > 1){
                reader.readLine();
            }
            String line = reader.readLine();
            String data = line.split(delimiter,-1)[fieldNum-1];
            try {
                return Integer.parseInt(data);
            }catch (NumberFormatException ex1){
                try{
                    return Float.parseFloat(data);
                }catch (NumberFormatException ex2){
                    if(data.toLowerCase(Locale.ROOT).equals("true") || data.toLowerCase(Locale.ROOT).equals("false")){
                        return Boolean.parseBoolean(data);
                    }else{
                        if(data.length() == 1){
                            return data.charAt(0);
                        }else{
                            return data;
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitFileLine(FRePLParser.FileLineContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        int lineNum = Integer.parseInt(ctx.INT().getText());

        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while(lineNum-- > 1){
                reader.readLine();
            }
            return reader.readLine();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitFileText(FRePLParser.FileTextContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        String fullPath = System.getProperty("user.dir") + "\\" + filePath;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fullPath));
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Object visitSaveFunctionCall(FRePLParser.SaveFunctionCallContext ctx) {
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (var key:varVisitor.SymbolTable.currentTable.keySet()) {
                Object value = varVisitor.SymbolTable.currentTable.get(key);
                if(value.getClass() == String.class){
                    String val = value.toString();
                    val = val.replaceAll(System.lineSeparator(),"\\\\r\\\\n");
                    writer.println("\"" + key + "\": \"" + val + "\";");
                }else{
                    writer.println("\"" + key + "\": \"" + value + "\";");
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visitLoadFunctionCall(FRePLParser.LoadFunctionCallContext ctx) {//Currently overrides all current variables with same name. Might change this later
        String filePath = ctx.STRING().getText();
        filePath = filePath.substring(1,filePath.length()-1);
        try{
            String line;
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String text = new String(bytes, StandardCharsets.UTF_8);
            List<String> matches = new ArrayList<>();
            Matcher m = Pattern.compile("\".+?\": \".+?\";")
                    .matcher(text);
            while (m.find()) {
                String match = m.group();
                String[] data = match.split("\": \"");
                String varName = data[0].substring(1);
                String varData = data[1].substring(0, data[1].length() - 2);
                if(varData.matches("\\[.+]")){
                    varData = varData.substring(1, varData.length() - 1);
                    String[] arrayData = varData.split(",");
                    List<String> array = new ArrayList<>();
                    for(var el : arrayData){
                        array.add((String)el.trim());
                    }
                    varVisitor.SymbolTable.currentTable.put(varName, array);
                    return null;
                }
                try {
                    varVisitor.SymbolTable.currentTable.put(varName, Integer.parseInt(varData));
                } catch (NumberFormatException ex1) {
                    try {
                        varVisitor.SymbolTable.currentTable.put(varName, Float.parseFloat(varData));
                    } catch (NumberFormatException ex2) {
                        if (varData.toLowerCase(Locale.ROOT).equals("true") || varData.toLowerCase(Locale.ROOT).equals("false")) {
                            varVisitor.SymbolTable.currentTable.put(varName, Boolean.parseBoolean(varData));
                        } else {
                            if (varData.length() == 1) {
                                varVisitor.SymbolTable.currentTable.put(varName, varData.charAt(0));
                            } else {
                                varVisitor.SymbolTable.currentTable.put(varName, varData.replaceAll("\\\\r\\\\n", System.lineSeparator()));
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
