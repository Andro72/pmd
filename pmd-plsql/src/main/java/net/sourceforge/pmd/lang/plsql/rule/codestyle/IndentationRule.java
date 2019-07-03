/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.plsql.rule.codestyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pmd.lang.plsql.ast.ASTInput;
import net.sourceforge.pmd.lang.plsql.rule.AbstractPLSQLRule;



public class IndentationRule extends AbstractPLSQLRule {


    public IndentationRule() {
        addRuleChainVisit(ASTInput.class);
    }

    @Override
    public Object visit(ASTInput node, Object data) {

        try (BufferedReader in = new BufferedReader(new StringReader(node.getSourcecode()))) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                Pattern pattern = Pattern.compile("^(\\s*)[^\\s].*");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String indetion = matcher.group(1);

                    if (indetion.contains("\t")) {
                        addViolationWithMessage(data, null, "Line indetion contains TAB.",
                                lineNumber, lineNumber);
                    } else {
                        if (indetion.length() % 2 != 0) {
                            addViolationWithMessage(data, null, "Space indetion on line is not even.",
                                    lineNumber, lineNumber);
                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Error while executing rule LineLengthRule", e);
        }
        return data;
    }
}
