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

public class KeywordsRule extends AbstractPLSQLRule {

    static final String[] RESERVED_WORDS = new String[]{"ADMIN", "CURSOR", "FOUND", "MOUNT",
        "AFTER", "CYCLE", "FUNCTION", "NEXT",
        "ALLOCATE", "DATABASE", "GO", "NEW",
        "ANALYZE", "DATAFILE", "GOTO", "NOARCHIVELOG",
        "ARCHIVE", "DBA", "GROUPS", "NOCACHE",
        "ARCHIVELOG", "DEC", "INCLUDING", "NOCYCLE",
        "AUTHORIZATION", "DECLARE", "INDICATOR", "NOMAXVALUE",
        "AVG", "DISABLE", "INITRANS", "NOMINVALUE",
        "BACKUP", "DISMOUNT", "INSTANCE", "NONE",
        "BEGIN", "DOUBLE", "INT", "NOORDER",
        "BECOME", "DUMP", "KEY", "NORESETLOGS",
        "BEFORE", "EACH", "LANGUAGE", "NORMAL",
        "BLOCK", "ENABLE", "LAYER", "NOSORT",
        "BODY", "END", "LINK", "NUMERIC",
        "CACHE", "ESCAPE", "LISTS", "OFF",
        "CANCEL", "EVENTS", "LOGFILE", "OLD",
        "CASCADE", "EXCEPT", "MANAGE", "ONLY",
        "CHANGE", "EXCEPTIONS", "MANUAL", "OPEN",
        "CHARACTER", "EXEC", "MAX", "OPTIMAL",
        "CHECKPOINT", "EXPLAIN", "MAXDATAFILES", "OWN",
        "CLOSE", "EXECUTE", "MAXINSTANCES", "PACKAGE",
        "COBOL", "EXTENT", "MAXLOGFILES", "PARALLEL",
        "COMMIT", "EXTERNALLY", "MAXLOGHISTORY", "PCTINCREASE",
        "COMPILE", "FETCH", "MAXLOGMEMBERS", "PCTUSED",
        "CONSTRAINT", "FLUSH", "MAXTRANS", "PLAN",
        "CONSTRAINTS", "FREELIST", "MAXVALUE", "PLI",
        "CONTENTS", "FREELISTS", "MIN", "PRECISION",
        "CONTINUE", "FORCE", "MINEXTENTS", "PRIMARY",
        "CONTROLFILE", "FOREIGN", "MINVALUE", "PRIVATE",
        "COUNT", "FORTRAN", "MODULE", "PROCEDURE",

        "PROFILE", "SAVEPOINT", "SQLSTATE", "TRACING",
        "QUOTA", "SCHEMA", "STATEMENT_ID", "TRANSACTION",
        "READ", "SCN", "STATISTICS", "TRIGGERS",
        "REAL", "SECTION", "STOP", "TRUNCATE",
        "RECOVER", "SEGMENT", "STORAGE", "UNDER",
        "REFERENCES", "SEQUENCE", "SUM", "UNLIMITED",
        "REFERENCING", "SHARED", "SWITCH", "UNTIL",
        "RESETLOGS", "SNAPSHOT", "SYSTEM", "USE",
        "RESTRICTED", "SOME", "TABLES", "USING",
        "REUSE", "SORT", "TABLESPACE", "WHEN",
        "ROLE", "SQL", "TEMPORARY", "WRITE",
        "ROLES", "SQLCODE", "THREAD", "WORK",
        "ROLLBACK", "SQLERROR", "TIME"};

    public KeywordsRule() {
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
