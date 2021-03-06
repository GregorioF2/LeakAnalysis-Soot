package tp.Visitors;

import soot.Local;
import soot.Value;
import soot.jimple.*;
import tp.abs.SensibleLattice;

import java.util.HashMap;

public class StatementVisitor {
    private HashMap<String, SensibleLattice> localsLatticeMap;
    private HashMap<Integer, SensibleLattice> methodParams;
    private ValueVisitor valueVisitor;
    private boolean returnsSensible = false;

    public StatementVisitor(HashMap<String, SensibleLattice> locals,
                            HashMap<Integer, SensibleLattice> params) {
        localsLatticeMap = locals;
        methodParams = params;
        valueVisitor = new ValueVisitor(localsLatticeMap, params);
    }

    public Boolean thereIsPossibleLeak() {
        return this.valueVisitor.thereIsPossibleLeak();
    }

    // The value returned is if the statement creates a new sensible local variable
    public boolean visit(Stmt statement) {
        if (statement instanceof DefinitionStmt) {
            return visitDefinition((DefinitionStmt) statement);
        } else if (statement instanceof InvokeStmt) {
            return visitInvoke((InvokeStmt) statement);
        } else if (statement instanceof ReturnStmt) {
            return visitReturn((ReturnStmt) statement);
        }
        return false;
    }

    private boolean visitReturn(ReturnStmt statement) {
        Value value = statement.getOp();
        boolean isSensible =  valueVisitor.visit(value);
        returnsSensible = isSensible;
        return  isSensible;
    }

    private boolean visitInvoke(InvokeStmt statement) {
        Value value = statement.getInvokeExpr();
        return valueVisitor.visit(value);
    }

    public boolean visitDefinition(DefinitionStmt statement) {

        Value leftOp = statement.getLeftOp();

        Value rightOp = statement.getRightOp();

        boolean possibleLeak = valueVisitor.visit(rightOp);

        if (possibleLeak && (leftOp instanceof Local)) {
            localsLatticeMap.put(((Local) leftOp).getName(), SensibleLattice.HIGH);
        }

        return false;
    }

    public boolean returnSensible() {
        return returnsSensible;
    }

}
