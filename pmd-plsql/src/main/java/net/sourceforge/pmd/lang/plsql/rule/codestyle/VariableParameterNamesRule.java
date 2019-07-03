/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.plsql.rule.codestyle;

import net.sourceforge.pmd.lang.plsql.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.plsql.rule.AbstractPLSQLRule;

public class VariableParameterNamesRule extends AbstractPLSQLRule {

    @Override
    public Object visit(ASTFormalParameter node, Object data) {

        if (!node.getImage().startsWith("p_")
                && !node.getImage().endsWith("_out")
                && !node.getImage().endsWith("_io")) {
            addViolationWithMessage(data, node, "Variable: " + node.getImage());
        }

        return super.visit(node, data);
    }

}
