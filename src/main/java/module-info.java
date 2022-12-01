module com.wave.trace.wave_trace {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wave.trace.wave_trace to javafx.fxml;
    exports com.wave.trace.wave_trace;
    exports com.wave.trace.wave_trace.controllers;
    opens com.wave.trace.wave_trace.controllers to javafx.fxml;
}