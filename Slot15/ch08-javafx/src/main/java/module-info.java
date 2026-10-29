module com.fu.ch08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    opens com.fu.ch08.b4 to javafx.fxml;
    opens com.fu.ch08.b5 to javafx.fxml;
    opens com.fu.ch08.b6 to javafx.fxml;
    opens com.fu.ch08.t1 to javafx.fxml, javafx.base;
    opens com.fu.ch08.t4 to javafx.fxml, javafx.base;

    exports com.fu.ch08.common;
    exports com.fu.ch08.b1;
    exports com.fu.ch08.b2;
    exports com.fu.ch08.b3;
    exports com.fu.ch08.b4;
    exports com.fu.ch08.b5;
    exports com.fu.ch08.b6;
    exports com.fu.ch08.t1;
    exports com.fu.ch08.t2;
    exports com.fu.ch08.t3;
    exports com.fu.ch08.t4;
    exports com.fu.ch08.t5;
    exports com.fu.ch08.t6;
}