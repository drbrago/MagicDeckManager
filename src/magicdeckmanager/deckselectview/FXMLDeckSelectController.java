/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deckselectview;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import magicdeckmanager.MagicDeckManagerPresenter;
import magicdeckmanager.dataModel.deck.DeckDataModel;

/**
 *
 * @author drbra_000
 */
public class FXMLDeckSelectController implements Initializable {

    private MagicDeckManagerPresenter presenter;
    
    @FXML
    private TableView<DeckDataModel> tableView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initDeckList(MagicDeckManagerPresenter presenter, List<DeckDataModel> deckTableData) {
        this.presenter = presenter;
        ObservableList<DeckDataModel> data = tableView.getItems();
        for (DeckDataModel deckData : deckTableData) {
            data.add(deckData);
        }
        tableView.setItems(data);
    }

    @FXML
    protected void handleDeckSelectButtonAction(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        presenter.deckSelected(selectedIndex);
    }

}
