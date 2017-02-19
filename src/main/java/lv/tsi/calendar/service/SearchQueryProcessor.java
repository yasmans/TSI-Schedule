package lv.tsi.calendar.service;

import lv.tsi.calendar.antlr.SearchQueryLexer;
import lv.tsi.calendar.antlr.SearchQueryListener;
import lv.tsi.calendar.antlr.SearchQueryParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SearchQueryProcessor {

    SearchBean createSearchBean(String searchString) {
        try {
            ANTLRInputStream charStream = new ANTLRInputStream(
                    new ByteArrayInputStream(searchString.getBytes(StandardCharsets.UTF_8)));
            SearchQueryLexer searchQueryLexer = new SearchQueryLexer(charStream);
            CommonTokenStream commonTokenStream = new CommonTokenStream(searchQueryLexer);
            SearchQueryParser searchQueryParser = new SearchQueryParser(commonTokenStream);
            SearchQueryListenerImpl listener = new SearchQueryListenerImpl();
            ParseTreeWalker.DEFAULT.walk(listener, searchQueryParser.searchQuery());
            return listener.getSearchBean();
        } catch (IOException ignore) {
            return null;
        }
    }

    private class SearchQueryListenerImpl implements SearchQueryListener {

        private SearchBean searchBean;

        SearchQueryListenerImpl() {
            this.searchBean = new SearchBean();
        }

        SearchBean getSearchBean() {
            return searchBean;
        }

        public void enterSearchQuery(SearchQueryParser.SearchQueryContext ctx) {}
        public void exitSearchQuery(SearchQueryParser.SearchQueryContext ctx) {}
        public void enterNegSearchTerm(SearchQueryParser.NegSearchTermContext ctx) {
            System.out.printf("Found negated search term: %s \n", ctx.getText());
        }
        public void exitNegSearchTerm(SearchQueryParser.NegSearchTermContext ctx) {}
        public void enterSearchTerm(SearchQueryParser.SearchTermContext ctx) {
            System.out.printf("Found search term: %s \n", ctx.getText());
        }
        public void exitSearchTerm(SearchQueryParser.SearchTermContext ctx) {}
        public void enterGroup(SearchQueryParser.GroupContext ctx) {}
        public void exitGroup(SearchQueryParser.GroupContext ctx) {}
        public void enterTeacher(SearchQueryParser.TeacherContext ctx) {
            System.out.println("Entered teacher term");
        }
        public void exitTeacher(SearchQueryParser.TeacherContext ctx) {}
        public void enterRoom(SearchQueryParser.RoomContext ctx) {}
        public void exitRoom(SearchQueryParser.RoomContext ctx) {}
        public void visitTerminal(TerminalNode node) {}
        public void visitErrorNode(ErrorNode node) {}
        public void enterEveryRule(ParserRuleContext ctx) {}
        public void exitEveryRule(ParserRuleContext ctx) {        }
    }

}
