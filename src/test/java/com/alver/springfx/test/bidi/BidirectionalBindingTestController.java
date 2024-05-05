package com.alver.springfx.test.bidi;

import com.alver.springfx.annotations.FXMLPrototype;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@FXMLPrototype
public class BidirectionalBindingTestController {

	@FXML
	public TextField text1;

	@FXML
	public TextField text2;

	@FXML
	public TextField text3;
}
