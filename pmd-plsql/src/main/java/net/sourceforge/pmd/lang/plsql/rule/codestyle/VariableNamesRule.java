/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.plsql.rule.codestyle;

import net.sourceforge.pmd.lang.plsql.ast.ASTVariableOrConstantDeclaratorId;
import net.sourceforge.pmd.lang.plsql.rule.AbstractPLSQLRule;

public class VariableNamesRule extends AbstractPLSQLRule {

    @Override
    public Object visit(ASTVariableOrConstantDeclaratorId node, Object data) {

        if (
                !node.getImage().startsWith("g_")
                        && !node.getImage().startsWith("v_")
                        && !node.getImage().startsWith("gc_")
                        && !node.getImage().startsWith("c_")
                        && !node.getImage().startsWith("cur_")
                        && !node.getImage().startsWith("rec_")
                        && !node.getImage().startsWith("t_")
                        && !node.getImage().startsWith("o_")
                        && !node.getImage().startsWith("e_")
                        && !node.getImage().endsWith("_type")
        ) {
            addViolationWithMessage(data, node, "Variable: " + node.getImage());
        }

        return super.visit(node, data);
    }

}
