package projectone;


import org.apache.log4j.BasicConfigurator;
import projectone.parsers.FileParser;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        BasicConfigurator.configure();
        logger.info("Hello World!");

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        logger.info(fileParser.getDocumentIds().toString());
    }
}
