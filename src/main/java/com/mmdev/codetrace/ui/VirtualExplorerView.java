package com.mmdev.codetrace.ui;

import java.nio.file.Path;
import com.mmdev.codetrace.practice.ReconstructionStatus.State;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * VirtualFlow presentation of an already computed ExplorerRows projection.
 *
 * <p>Only visible ListCell instances allocate graphics. Large expansions
 * never create TreeItem hierarchies or iterate files on the JavaFX thread.</p>
 */
final class VirtualExplorerView extends ListView<ExplorerRows.Row> {
    private final ReferenceTree owner;

    VirtualExplorerView(ReferenceTree owner) {
        this.owner = owner;
        getStyleClass().addAll("reference-flat-explorer", "reference-tree");
        setFixedCellSize(27);
        setCellFactory(view -> new ListCell<>() {
            private final HBox row = new HBox(6);
            private final Region indentation = new Region();
            private final Button disclosure = new Button();
            private final Label filename = new Label();
            private final Label fileCount = new Label();
            private final Tooltip tooltip = new Tooltip();

            {
                row.setAlignment(Pos.CENTER_LEFT);
                row.getStyleClass().add("reference-tree-row");
                filename.getStyleClass().add("reference-name");
                fileCount.getStyleClass().add("reference-file-count");
                disclosure.getStyleClass().add("virtual-tree-disclosure");
                disclosure.setFocusTraversable(false);
                disclosure.setAccessibleText("Expandir o contraer carpeta");
                disclosure.setMinSize(18, 20);
                disclosure.setPrefSize(18, 20);
                disclosure.setMaxSize(18, 20);
                disclosure.setOnAction(event -> {
                    var active = getItem();
                    if (active != null && active.directory()) {
                        VirtualExplorerView.this.getSelectionModel().select(active);
                        owner.toggleProjectedFolder(active.relative());
                    }
                    event.consume();
                });
                row.getChildren().addAll(indentation, disclosure, filename, fileCount);
                setOnContextMenuRequested(event -> {
                    var active = getItem();
                    if (active == null || isEmpty()) return;
                    ContextMenu menu = owner.menuForProjectedPath(active.relative());
                    if (menu != null) {
                        menu.show(this, event.getScreenX(), event.getScreenY());
                        event.consume();
                    }
                });
                selectedProperty().addListener((obs, oldValue, selected) ->
                        filename.setTextFill(selected ? Color.web("#203748")
                                : Color.web("#354f64")));
            }

            @Override protected void updateItem(ExplorerRows.Row entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                    return;
                }
                Path path = owner.absoluteProjectedPath(entry.relative());
                indentation.setMinWidth(entry.depth() * 15.0);
                indentation.setPrefWidth(entry.depth() * 15.0);
                indentation.setMaxWidth(entry.depth() * 15.0);
                disclosure.setVisible(entry.directory());
                disclosure.setManaged(entry.directory());
                disclosure.setText(owner.projectedExpanded(entry.relative()) ? "▾" : "▸");
                filename.setText(path.getFileName() == null
                        ? path.toString() : path.getFileName().toString());
                filename.setTextFill(isSelected()
                        ? Color.web("#203748") : Color.web("#354f64"));
                State state = owner.projectedState(entry.relative());
                getStyleClass().remove("reference-outside-scope");
                if (owner.projectedOutsideScope(entry.relative()))
                    getStyleClass().add("reference-outside-scope");
                filename.getStyleClass().remove("reference-pending-name");
                if (state == State.PENDING)
                    filename.getStyleClass().add("reference-pending-name");
                row.getChildren().setAll(indentation, disclosure);
                row.getChildren().add(FileTypeIcons.forPath(path, entry.directory()));
                if (state != State.NOT_APPLICABLE)
                    row.getChildren().add(ReconstructionStateIcons.forState(state));
                row.getChildren().add(filename);
                if (entry.directory()) {
                    fileCount.setText(owner.projectedFileCount(entry.relative()));
                    row.getChildren().add(fileCount);
                }
                String help = switch (state) {
                    case NOT_APPLICABLE -> "Archivo de consulta";
                    case PENDING -> "Pendiente de recrear";
                    case CREATED -> "Creado virtualmente";
                    case COMPLETED -> "Práctica terminada";
                };
                if (owner.projectedExcluded(entry.relative()))
                    help += "\nExcluida del progreso";
                if (owner.projectedOutsideScope(entry.relative()))
                    help += "\nFuera del ámbito Solo";
                tooltip.setText((entry.relative().isEmpty()
                        ? path.toString() : entry.relative()) + "\n" + help);
                setTooltip(tooltip);
                setText(null);
                setGraphic(row);
            }
        });
        getSelectionModel().selectedItemProperty().addListener(
                (obs, previous, selected) -> owner.projectedSelectionChanged());
        setOnMouseClicked(event -> {
            var item = getSelectionModel().getSelectedItem();
            if (item != null && event.getClickCount() == 2) {
                if (item.directory()) owner.toggleProjectedFolder(item.relative());
                else owner.openProjectedFile(item.relative());
            }
        });
    }

    void publish(ExplorerRows.Result projection) {
        var previous = getSelectionModel().getSelectedItem();
        String selection = previous == null ? null : previous.relative();
        setItems(FXCollections.observableArrayList(projection.rows()));
        if (selection != null) {
            for (int i = 0; i < getItems().size(); i++) {
                if (selection.equals(getItems().get(i).relative())) {
                    getSelectionModel().select(i);
                    // Preserve the navigational context across projection updates.
                    scrollTo(Math.max(0, i - 4));
                    return;
                }
            }
        }
        if (!getItems().isEmpty()) getSelectionModel().selectFirst();
    }
}
