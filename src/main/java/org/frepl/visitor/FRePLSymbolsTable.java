package org.frepl.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FRePLSymbolsTable {//TODO: Make implementation more efficient
    Stack<Map<String,Object>> stack;
    public Map<String,Object> currentTable;
    public FRePLSymbolsTable(){
        stack = new Stack<>();
        currentTable = new HashMap<>();
    }

    public void EnterBlock(boolean passVars) {
        stack.push(currentTable);
        if(!passVars){
            currentTable = new HashMap<>();
        }
        else{
            currentTable = new HashMap<>(currentTable);
        }
    }
    public void ExitBlock(boolean applyChanges) {
        Map<String,Object> last = stack.pop();
        if(applyChanges) {
            last.replaceAll((k, v) -> currentTable.get(k));
        }
        currentTable = last;
    }
}
