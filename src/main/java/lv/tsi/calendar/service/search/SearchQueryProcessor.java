package lv.tsi.calendar.service.search;

import lv.tsi.calendar.antlr.SearchQueryBaseListener;
import lv.tsi.calendar.antlr.SearchQueryLexer;
import lv.tsi.calendar.antlr.SearchQueryParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SearchQueryProcessor {

    private static final Logger logger = Logger.getLogger("SearchQueryProcessor");

    public SearchBean createSearchBean(String searchString) {
        ANTLRInputStream charStream = new ANTLRInputStream(searchString.toCharArray(), searchString.length());
        SearchQueryLexer searchQueryLexer = new SearchQueryLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(searchQueryLexer);
        SearchQueryParser searchQueryParser = new SearchQueryParser(commonTokenStream);
        SearchQueryListenerImpl listener = new SearchQueryListenerImpl();
        ParseTreeWalker.DEFAULT.walk(listener, searchQueryParser.searchQuery());
        SearchBean searchBean = listener.getSearchBean();
        logger.debug("Parsing Search string '" + searchString + "' resulted in search bean: \n" + searchBean);
        searchBean.validateSearchBean();
        return searchBean;
    }

    private class SearchQueryListenerImpl extends SearchQueryBaseListener {

        private SearchBean searchBean = new SearchBean();

        SearchBean getSearchBean() {
            return searchBean;
        }

        @Override
        public void enterNegSearchTerm(SearchQueryParser.NegSearchTermContext ctx) {
            RuleContext ruleContext = ctx.getRuleContext();
            ParseTree node = ruleContext.getChild(1);

            if (node instanceof TerminalNodeImpl) {
                searchBean.addExcludeTerm(SearchBean.SearchField.ALL, getText(node));
            } else {
                String text = getText(node.getChild(1));
                if (node instanceof SearchQueryParser.TeacherContext) {
                    searchBean.addExcludeTerm(SearchBean.SearchField.TEACHER, text);
                } else if (node instanceof SearchQueryParser.GroupContext) {
                    searchBean.addExcludeTerm(SearchBean.SearchField.GROUP, text);
                } else if (node instanceof SearchQueryParser.RoomContext) {
                    searchBean.addExcludeTerm(SearchBean.SearchField.ROOM, text);
                }
            }
        }

        @Override
        public void enterSearchTerm(SearchQueryParser.SearchTermContext ctx) {
            RuleContext ruleContext = ctx.getRuleContext();
            ParseTree node = ruleContext.getChild(0);

            if (node instanceof TerminalNodeImpl) {
                searchBean.addSearchTerm(SearchBean.SearchField.ALL, getText(node));
            } else if (node instanceof SearchQueryParser.TeacherContext) {
                searchBean.addSearchTerm(SearchBean.SearchField.TEACHER, getText(node.getChild(1)));
            } else if (node instanceof SearchQueryParser.GroupContext) {
                searchBean.addSearchTerm(SearchBean.SearchField.GROUP, getText(node.getChild(1)));
            } else if (node instanceof SearchQueryParser.RoomContext) {
                searchBean.addSearchTerm(SearchBean.SearchField.ROOM, getText(node.getChild(1)));
            }
        }

        private String getText(ParseTree node){
            return node.getText().replaceAll("['\"]", "");
        }

    }

}
