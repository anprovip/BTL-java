module BTL {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml,java.sql;
	opens database to javafx.graphics, javafx.fxml,java.sql;
	opens model to javafx.graphics, javafx.fxml,java.sql,javafx.controls,javafx.base;
}
