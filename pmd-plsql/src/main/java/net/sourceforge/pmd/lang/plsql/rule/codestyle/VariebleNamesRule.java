package net.sourceforge.pmd.lang.plsql.rule.codestyle;

import net.sourceforge.pmd.lang.plsql.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.plsql.ast.ASTInput;
import net.sourceforge.pmd.lang.plsql.rule.AbstractPLSQLRule;

public class VariebleNamesRule extends AbstractPLSQLRule {


    public Object visit(ASTFormalParameter node, Object data) {

        if(!node.getImage().startsWith("p_"))
            addViolation(data, node);

        return super.visit(node, data);
    }

}
