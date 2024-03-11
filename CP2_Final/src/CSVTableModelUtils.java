import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CSVTableModelUtils {
    public static DefaultTableModel createTableModel(List<String[]> data, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (String[] rowData : data) {
            tableModel.addRow(rowData);
        }
        return tableModel;
    }

    @SuppressWarnings("unchecked")
	public static List<String[]> getDataVector(TableModel model) {
        List<String[]> data = new ArrayList<>();
        @SuppressWarnings("rawtypes")
		Vector<Vector> vectorData = ((DefaultTableModel) model).getDataVector();

        for (Vector<Object> vectorRow : vectorData) {
            String[] rowData = new String[vectorRow.size()];
            for (int i = 0; i < vectorRow.size(); i++) {
                rowData[i] = String.valueOf(vectorRow.get(i));
            }
            data.add(rowData);
        }

        return data;
    }
}
