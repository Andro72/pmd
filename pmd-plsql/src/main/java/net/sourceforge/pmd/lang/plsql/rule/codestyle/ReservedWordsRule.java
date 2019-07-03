/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.plsql.rule.codestyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import net.sourceforge.pmd.lang.plsql.ast.ASTInput;
import net.sourceforge.pmd.lang.plsql.rule.AbstractPLSQLRule;

public class ReservedWordsRule extends AbstractPLSQLRule {

    static final String[] RESERVED_WORDS = new String[]{"ACCESS", "ELSE", "MODIFY", "START",
        "ADD", "EXCLUSIVE", "NOAUDIT", "SELECT",
        "ALL", "EXISTS", "NOCOMPRESS", "SESSION",
        "ALTER", "FILE", "NOT", "SET",
        "AND", "FLOAT", "NOTFOUND", "SHARE",
        "ANY", "FOR", "NOWAIT", "SIZE",
        "ARRAYLEN", "FROM", "NULL", "SMALLINT",
        "AS", "GRANT", "NUMBER", "SQLBUF",
        "ASC", "GROUP", "OF", "SUCCESSFUL",
        "AUDIT", "HAVING", "OFFLINE", "SYNONYM",
        "BETWEEN", "IDENTIFIED", "ON", "SYSDATE",
        "BY", "IMMEDIATE", "ONLINE", "TABLE",
        "CHAR", "IN", "OPTION", "THEN",
        "CHECK", "INCREMENT", "OR", "TO",
        "CLUSTER", "INDEX", "ORDER", "TRIGGER",
        "COLUMN", "INITIAL", "PCTFREE", "UID",
        "COMMENT", "INSERT", "PRIOR", "UNION",
        "COMPRESS", "INTEGER", "PRIVILEGES", "UNIQUE",
        "CONNECT", "INTERSECT", "PUBLIC", "UPDATE",
        "CREATE", "INTO", "RAW", "USER",
        "CURRENT", "IS", "RENAME", "VALIDATE",
        "DATE", "LEVEL", "RESOURCE", "VALUES",
        "DECIMAL", "LIKE", "REVOKE", "VARCHAR",
        "DEFAULT", "LOCK", "ROW", "VARCHAR2",
        "DELETE", "LONG", "ROWID", "VIEW",
        "DESC", "MAXEXTENTS", "ROWLABEL", "WHENEVER",
        "DISTINCT", "MINUS", "ROWNUM", "WHERE",
        "DROP", "MODE", "ROWS", "WITH,"};

    public ReservedWordsRule() {
        addRuleChainVisit(ASTInput.class);
    }

    @Override
    public Object visit(ASTInput node, Object data) {

        try (BufferedReader in = new BufferedReader(new StringReader(node.getSourcecode()))) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;

                if (line.matches("\\s*--.*")
                        || line.matches("\\s*/\\*.*")
                        || line.matches("\\s*\\*.*")
                        || line.matches("\\s*\\*/.*")) {
                    continue;
                }


                for (String reserWord : RESERVED_WORDS) {
                    Boolean contains = line.toUpperCase(Locale.ENGLISH).matches(".*\\b" + reserWord + "\\b.*");
                    if (contains) {

                        int index = line.toUpperCase(Locale.ENGLISH).indexOf(reserWord);
                        String stringBeforeWord = line.substring(0, index);
                        int countOccurence = stringBeforeWord.length() - stringBeforeWord.replace("'", "").length();

                        if (countOccurence % 2 == 1) {
                            continue;
                        }

                        if (!line.contains(reserWord)) {
                            addViolationWithMessage(data, null, "Contains wrong case of Reserved Words: " + reserWord,
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
